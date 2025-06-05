package com.edu.lx.cms.service;

import com.edu.lx.cms.domain.pojo.Contact;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.lx.cms.domain.query.PageQuery;
import com.edu.lx.cms.utils.JsonResult;

/**
 * <p>
 * 联系人信息表 服务类
 * </p>
 *
 * @author LiXue
 * @since 2025-06-03
 */
public interface ContactService extends IService<Contact> {

    JsonResult getTotalContactPage(PageQuery query);

    JsonResult getContact(PageQuery query);

    JsonResult getOneContact(Contact contact);

    JsonResult updateContact(Contact contact);

    JsonResult addContact(Contact contact);

    JsonResult deleteContact(String ctId);
}
