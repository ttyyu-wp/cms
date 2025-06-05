package com.edu.lx.cms.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.lx.cms.domain.po.*;

import java.util.List;

public interface DBUtils {
    User getUser(User user);

    void register(User user);

    List<UserPicture> getUserPic(LambdaQueryWrapper<UserPicture> wrapper);

    List<Contact> getTotalContactPage(LambdaQueryWrapper<Contact> wrapper);

    Page<Contact> getContact(Page<Contact> page, LambdaQueryWrapper<Contact> wrapper);

    Contact getOneContact(LambdaQueryWrapper<Contact> eq);

    Picture getContactPic(LambdaQueryWrapper<Picture> wrapper);

    String getMaxContactID();

    String getMaxContactPicID();

    Integer updateContact(Contact contact);

    Integer updateContactPic(Picture picture);

    void addContact(Contact contact);

    void addContactPic(Picture picture);

    void deleteContact(LambdaUpdateWrapper<Contact> set);

    void cancelDeleteContact(LambdaUpdateWrapper<Contact> eq);

    List<Contact> getAllContact(LambdaQueryWrapper<Contact> eq);


    List<Matter> getMatterUser(LambdaQueryWrapper<Matter> in);
}
