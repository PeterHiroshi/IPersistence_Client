package com.peter8icestone.dao;

import com.peter8icestone.pojo.User;

import java.util.List;

public interface IUserDao {

    // get all users
    List<User> findAll();
    // get user by condition
    User findByCondition(User user);
}
