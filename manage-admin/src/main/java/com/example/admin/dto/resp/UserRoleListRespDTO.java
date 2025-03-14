package com.example.admin.dto.resp;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Nruonan
 * @description
 */
@Data
@AllArgsConstructor
public class UserRoleListRespDTO {
    private Integer id;
    private List<String> nameList;
}
