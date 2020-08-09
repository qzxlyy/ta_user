package com.fh.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.entity.po.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao  extends BaseMapper<User> {
    User queryByName(String name);

    User selectByName(String name);
}
