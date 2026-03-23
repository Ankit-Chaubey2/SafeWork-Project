package com.cts.SafeWork.dto;


import com.cts.SafeWork.enums.UserRole;
import lombok.Data;

@Data
public class UserUpdateDTO {

    private String userName;
    private String userEmail;
    private String userContact;
    private String userStatus;
    private UserRole userRole;
}