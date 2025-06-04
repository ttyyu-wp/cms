package com.edu.lx.cms.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.lx.cms.domain.pojo.Contact;
import com.edu.lx.cms.domain.pojo.Picture;
import com.edu.lx.cms.domain.pojo.User;
import com.edu.lx.cms.domain.pojo.UserPicture;
import com.edu.lx.cms.domain.query.PageQuery;

import java.util.List;

public interface DBUtils {
    User getUser(User user);

    void register(User user);


    List<UserPicture> getUserPic(LambdaQueryWrapper<UserPicture> wrapper);

    List<Contact> getTotalContactPage(LambdaQueryWrapper<Contact> wrapper);

    Page<Contact> getContact(Page<Contact> page, LambdaQueryWrapper<Contact> wrapper);

    Contact getOneContact(LambdaQueryWrapper<Contact> eq);

    Picture getContactPic(LambdaQueryWrapper<Picture> wrapper);
}
