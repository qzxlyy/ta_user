package com.fh.service.impl;

import com.fh.dao.AreaDao;
import com.fh.dao.UserDao;
import com.fh.entity.po.Area;
import com.fh.entity.po.User;
import com.fh.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Resource
    private AreaDao areaDao;

    @Override
    public void addUser(User user) {
        userDao.insert(user);
    }

    @Override
    public List<Area> selectArea() {
        List<Area> area = areaDao.selectLists();
        return area;
    }

    @Override
    public User queryByName(String name) {
        return  userDao.queryByName(name);
    }

    @Override
    public User selectByName(String name) {

        return userDao.selectByName(name);
    }

    @Override
    public List<User> queryUser() {
        List<User> list = userDao.selectList(null);
        return list;
    }


}
