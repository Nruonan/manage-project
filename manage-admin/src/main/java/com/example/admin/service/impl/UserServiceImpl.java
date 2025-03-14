package com.example.admin.service.impl;


import static com.example.admin.common.constant.RedisConstantKey.LOCK_USER_REGISTER_KEY;
import static com.example.admin.common.enums.UserErrorCodeEnum.USER_EXIST;
import static com.example.admin.common.enums.UserErrorCodeEnum.USER_NAME_EXIST;
import static com.example.admin.common.enums.UserErrorCodeEnum.USER_SAVE_ERROR;

import cn.dev33.satoken.secure.SaBase64Util;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.common.exception.ClientException;
import com.example.admin.common.exception.ServiceException;
import com.example.admin.dto.req.UserRegisterReqDTO;
import com.example.admin.dto.resp.UserRespDTO;
import com.example.admin.entity.UserDO;
import com.example.admin.mapper.UserMapper;
import com.example.admin.service.UserService;
import jakarta.annotation.Resource;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author Nruonan
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2025-03-14 10:40:50
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO>
    implements UserService {

    @Resource
    RedissonClient redissonClient;
    @Resource
    private RBloomFilter<String> userRegisterCacheBloomFilter;
    @Override
    public UserRespDTO findUserByNameOrEmailOrPhone(String text) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
            .eq(UserDO::getUsername, text)
            .or()
            .eq(UserDO::getPhone, text)
            .or()
            .eq(UserDO::getMail, text);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if (userDO == null){
            return null;
        }else{
            return BeanUtil.toBean(userDO, UserRespDTO.class);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UserRegisterReqDTO requestParam) {
        if (!hasUsername(requestParam.getUsername())){
            throw new ClientException(USER_NAME_EXIST);
        }
        // 上锁
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY + requestParam.getUsername());
        if(!lock.tryLock()) {
            throw new ClientException(USER_NAME_EXIST);
        }
        try{
            String encode = SaBase64Util.encode(requestParam.getPassword());
            requestParam.setPassword(encode);
            // 添加进数据库
            int insert = baseMapper.insert(BeanUtil.toBean(requestParam, UserDO.class));
            if(insert < 1){
                throw new ClientException(USER_SAVE_ERROR);
            }
            // 布隆过滤器添加姓名
            userRegisterCacheBloomFilter.add(requestParam.getUsername());
        }catch (DuplicateKeyException ex){
            throw new ClientException(USER_EXIST);
        } finally {
            lock.unlock();
        }
    }

    private boolean hasUsername(String username) {
        return !userRegisterCacheBloomFilter.contains(username);
    }
}




