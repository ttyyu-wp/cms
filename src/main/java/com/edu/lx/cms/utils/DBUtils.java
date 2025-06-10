package com.edu.lx.cms.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.lx.cms.domain.dto.MatterDTO;
import com.edu.lx.cms.domain.po.*;

import javax.management.Query;
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

    String getMaxMatterID();

    Integer updateContact(Contact contact);

    Integer updateContactPic(Picture picture);

    void addContact(Contact contact);

    void addContactPic(Picture picture);

    void deleteContact(LambdaUpdateWrapper<Contact> set);

    void cancelDeleteContact(LambdaUpdateWrapper<Contact> eq);

    List<Contact> getAllContact(LambdaQueryWrapper<Contact> eq);




    List<Matter> getMatterContact(LambdaQueryWrapper<Matter> eq);

    void deleteMatter(LambdaUpdateWrapper<Matter> eq, Matter matter);

    void deleteMatterE(String matterId);

    void addMatter(Matter matter);


    UserPicture getMe(LambdaQueryWrapper<UserPicture> eq);

    void updateUserPic(LambdaUpdateWrapper<UserPicture> eq, UserPicture userPicture);

    void addUserPic(UserPicture up);

    List<Matter> getMatterAll(LambdaQueryWrapper<Matter> wrapper);

    IPage<MatterDTO> getMatterUser(Page<MatterDTO> page, String userId, Integer matterDelete, String matter, List<String> ctIdList, Boolean isAsc);

}
