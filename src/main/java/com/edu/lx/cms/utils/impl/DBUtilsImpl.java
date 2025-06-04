package com.edu.lx.cms.utils.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.lx.cms.domain.pojo.Contact;
import com.edu.lx.cms.domain.pojo.User;
import com.edu.lx.cms.domain.pojo.UserPicture;
import com.edu.lx.cms.domain.query.PageQuery;
import com.edu.lx.cms.mapper.ContactMapper;
import com.edu.lx.cms.mapper.PictureMapper;
import com.edu.lx.cms.mapper.UserMapper;
import com.edu.lx.cms.mapper.UserPictureMapper;
import com.edu.lx.cms.utils.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBUtilsImpl implements DBUtils {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PictureMapper pictureMapper;
    @Autowired
    private ContactMapper contactMapper;
    @Autowired
    private UserPictureMapper userPictureMapper;

    /**
     * 获取用户登录账号密码
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

    /**
     * 获取登录用户头像图片信息
     * @param wrapper
     * @return
     */
    @Override
    public List<UserPicture> getUserPic(LambdaQueryWrapper<UserPicture> wrapper) {
        return userPictureMapper.selectList(wrapper);
    }

    /**
     * 获取当前登录账户中联系人的总页数
     * @param wrapper
     * @return
     */
    @Override
    public List<Contact> getTotalContactPage(LambdaQueryWrapper<Contact> wrapper) {

        return contactMapper.selectList(wrapper);
    }

    /**
     * 获取当前用户所有联系人信息
     * @param page
     * @param wrapper
     * @return
     */
    @Override
    public Page<Contact> getContact(Page<Contact> page, LambdaQueryWrapper<Contact> wrapper) {
        return contactMapper.selectPage(page, wrapper);
    }


}
