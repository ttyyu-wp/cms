package com.edu.lx.cms.utils.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.lx.cms.domain.pojo.Contact;
import com.edu.lx.cms.domain.pojo.Picture;
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

import java.util.Comparator;
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

    /**
     * 获取当前联系人详细信息
     * @param wrapper
     * @return
     */
    @Override
    public Contact getOneContact(LambdaQueryWrapper<Contact> wrapper) {
        return contactMapper.selectOne(wrapper);
    }

    /**
     * 获取当前联系人头像图片信息
     * @param wrapper
     * @return
     */
    @Override
    public Picture getContactPic(LambdaQueryWrapper<Picture> wrapper) {
        return pictureMapper.selectOne(wrapper);
    }

    /**
     * 获取数据库中当前最大的联系人 ID
     * 服务于插入新的联系人
     * @return
     */
    @Override
    public String getMaxContactID() {
        //获得全部Contact  获得list中的最大ctId 返回其值
        return contactMapper.selectList(Wrappers.lambdaQuery(Contact.class))
                .stream().max(Comparator.comparing(Contact::getCtId))
                .get().getCtId();
    }

    /**
     * 获取数据库中当前最大的联系人图片 ID
     * 服务于增加新的联系人图片记录
     * @return
     */
    @Override
    public String getMaxContactPicID() {
        //获得全部Picture  获得list中的最大picId 返回其值
        return pictureMapper.selectList(Wrappers.lambdaQuery(Picture.class))
                .stream().max(Comparator.comparing(Picture::getPicId))
                .get().getPicId();
    }

    /**
     * 更新联系人信息
     * @param contact
     * @return
     */
    @Override
    public Integer updateContact(Contact contact) {
        return contactMapper.updateById(contact);
    }

    /**
     * 更新联系人头像图片信息
     * @param picture
     * @return
     */
    @Override
    public Integer updateContactPic(Picture picture) {
        return pictureMapper.updateById(picture);
    }

    @Override
    public void addContact(Contact contact) {
        contactMapper.insert(contact);
    }


}
