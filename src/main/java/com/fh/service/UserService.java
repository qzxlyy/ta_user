package com.fh.service;

import com.fh.entity.po.Area;
import com.fh.entity.po.User;

import java.util.List;

public interface UserService {
    void addUser(User user);

    List<Area> selectArea();

    User queryByName(String name);

    User selectByName(String name);

    List<User> queryUser();

}
