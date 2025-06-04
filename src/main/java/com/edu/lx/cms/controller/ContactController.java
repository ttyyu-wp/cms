package com.edu.lx.cms.controller;


import com.edu.lx.cms.domain.pojo.Contact;
import com.edu.lx.cms.domain.query.PageQuery;
import com.edu.lx.cms.service.ContactService;
import com.edu.lx.cms.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 联系人信息表 前端控制器
 * </p>
 *
 * @author LiXue
 * @since 2025-06-03
 */
@RestController
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @PostMapping("/page")
    public JsonResult getTotalContactPage(@RequestBody PageQuery query) {
        return contactService.getTotalContactPage(query);
    }

    @PostMapping("/list")
    public JsonResult getContact(@RequestBody PageQuery query) {
        return contactService.getContact(query);
    }

    @PostMapping("/one")
    public JsonResult getOneContact(@RequestBody Contact contact) {
        return contactService.getOneContact(contact);
    }

    @PostMapping("/update")
    public JsonResult updateContact(@RequestBody Contact contact) {
        return contactService.updateContact(contact);
    }
}
