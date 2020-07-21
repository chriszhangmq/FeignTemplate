package com.chris.userservice.service;

import org.springframework.stereotype.Service;

/**
 * @Author: Chris
 * @Date: 2020-07-21 16:13
 */
@Service
public class UserService {

    public String userInfo(String userName){
        return "log in User service !  UserName = " + userName;
    }
}
