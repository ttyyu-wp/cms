package com.edu.lx.cms.controller;


import com.edu.lx.cms.domain.po.Contact;
import com.edu.lx.cms.domain.query.PageQuery;
import com.edu.lx.cms.service.ContactService;
import com.edu.lx.cms.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public JsonResult addContact(@RequestBody Contact contact) {
        return contactService.addContact(contact);
    }

    @DeleteMapping("/delete")
    public JsonResult deleteContact(@RequestParam("ctId") String ctId) {
        return contactService.deleteContact(ctId);
    }

    @GetMapping("/cancel")
    public JsonResult cancelDeleteContact(@RequestParam("ctId") String ctId) {
        return contactService.cancelDeleteContact(ctId);
    }

}
