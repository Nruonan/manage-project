package com.example.admin.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Nruonan
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterReqDTO {
    String username;
    String password;
    String mail;
    String phone;
}
