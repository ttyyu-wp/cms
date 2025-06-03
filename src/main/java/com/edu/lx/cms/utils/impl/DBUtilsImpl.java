package com.edu.lx.cms.utils.impl;

import com.edu.lx.cms.domain.po.User;
import com.edu.lx.cms.mapper.UserMapper;
import com.edu.lx.cms.utils.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBUtilsImpl implements DBUtils {
    @Autowired
    private UserMapper userMapper;

    /**
     * 获得用户信息
     * @param user
     * @return
     */
    public User getUser(User user) {
        return userMapper.selectById(user.getUserId());
    }

    /**
     * 用户注册
     * @param user
     */
    @Override
    public void register(User user) {
        userMapper.insert(user);
    }
}
