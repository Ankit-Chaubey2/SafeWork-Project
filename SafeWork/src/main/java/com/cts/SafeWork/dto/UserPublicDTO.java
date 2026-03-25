package com.cts.SafeWork.dto;

import com.cts.SafeWork.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPublicDTO {
    private String userName;
    private String userEmail;
    private String userContact;
    private String userStatus;
    private String userRole;
   private String token; // Crucial for returning the JWT
}