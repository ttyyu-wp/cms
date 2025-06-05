package com.edu.lx.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.lx.cms.domain.pojo.Contact;
import com.edu.lx.cms.domain.pojo.Picture;
import com.edu.lx.cms.domain.query.PageQuery;
import com.edu.lx.cms.enums.ContactEnum;
import com.edu.lx.cms.mapper.ContactMapper;
import com.edu.lx.cms.service.ContactService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.lx.cms.utils.DBUtils;
import com.edu.lx.cms.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 联系人信息表 服务实现类
 * </p>
 *
 * @author LiXue
 * @since 2025-06-03
 */
@Service
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact> implements ContactService {

    private final Long DEFAULT_PAGESIZE = Long.parseLong("5");
    private final Long DEFAULT_PAGENO = Long.parseLong("0");
    private static final String POSTAL_CODE_REGEX = "^[1-9]\\d{5}(?!\\d)$";
    private static final String QQ_REGEX = "^[1-9][0-9]{4,}$";
    private static final String PHONE_REGEX = "^1[3-9]\\d{9}$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @Autowired
    private DBUtils utils;

    @Override
    public JsonResult getTotalContactPage(PageQuery query) {
        //判断参数是否符合要求
        if (query.getUserId() == null || query.getCtDelete() == null) {
            return JsonResult.error(ContactEnum.CONTACT_MSG_ERROR);
        }
        //设定每页默认值
        if (query.getPageSize() == null || query.getPageSize() == 0) {
            query.setPageSize(DEFAULT_PAGESIZE);
        }
        //构造查询条件
        LambdaQueryWrapper<Contact> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Contact::getUserId, query.getUserId())
                .eq(Contact::getCtDelete, query.getCtDelete());
        List<Contact> list = utils.getTotalContactPage(wrapper);
        double totalPage = Math.ceil(1.00 * list.size() / query.getPageSize());

        return JsonResult.success(ContactEnum.CONTACT_QUERY_SUCCESS, totalPage);
    }

    @Override
    public JsonResult getContact(PageQuery query) {
        //判断参数是否符合要求
        if (query.getUserId() == null ||
            query.getCtDelete() == null) {
            return JsonResult.error(ContactEnum.CONTACT_MSG_ERROR);
        }
        //设定每页默认值
        if (query.getPageNo() == null || query.getPageSize() == null || query.getPageSize() == 0) {
            query.setPageSize(DEFAULT_PAGESIZE);
            query.setPageNo(DEFAULT_PAGENO);
        }
        //构造查询条件
        Page<Contact> page = new Page<>(query.getPageNo(), query.getPageSize());

        LambdaQueryWrapper<Contact> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Contact::getUserId, query.getUserId())
                .eq(Contact::getCtDelete, query.getCtDelete());
        List<Contact> records = utils.getContact(page, wrapper).getRecords();
        return JsonResult.success(ContactEnum.CONTACT_QUERY_SUCCESS, records);
    }

    @Override
    public JsonResult getOneContact(Contact contact) {
        //判断参数是否符合要求
        if (contact.getCtId() == null || contact.getCtDelete() == null) {
            return JsonResult.error(ContactEnum.CONTACT_MSG_ERROR);
        }
        //构造查询语句

        //查询对应Contact
        Contact contactVO = utils.getOneContact(Wrappers.lambdaQuery(Contact.class)
                .eq(Contact::getCtId, contact.getCtId())
                .eq(Contact::getCtDelete, contact.getCtDelete()));
        //为空报错
        if (contactVO == null) {
            return JsonResult.error(ContactEnum.CONTACT_NOT_FOUND);
        }
        return JsonResult.success(ContactEnum.CONTACT_QUERY_SUCCESS, contactVO);
    }

    /**
     * 更新联系人信息
     * @param contact
     * @return
     */
    @Override
    public JsonResult updateContact(Contact contact) {
        //获取更新的结果条数
        Integer count = utils.updateContact(contact);
        //若异常 报错
        if (count < 0) {
            return JsonResult.error(ContactEnum.CONTACT_MSG_ERROR);
        }
        //返回成功结果
        return JsonResult.success(ContactEnum.CONTACT_UPDATE_SUCCESS);
    }

    @Override
    public JsonResult addContact(Contact contact) {
        //判断邮编格式是否合规
        if (contact.getCtYb() == null || !contact.getCtYb().matches(POSTAL_CODE_REGEX)) {
            return JsonResult.error(ContactEnum.CONTACT_YB_ERROR);
        }
        //判断QQ格式是否合规
        if (contact.getCtQq() == null || !contact.getCtQq().matches(QQ_REGEX)) {
            return JsonResult.error(ContactEnum.CONTACT_QQ_ERROR);
        }
        //判断邮箱是否合规
        if (contact.getCtEm() == null || !contact.getCtEm().matches(EMAIL_REGEX)) {
            return JsonResult.error(ContactEnum.CONTACT_EMAIL_ERROR);
        }
        //判断电话格式是否合规
        if (contact.getCtPhone() == null || !contact.getCtPhone().matches(PHONE_REGEX)) {
            return JsonResult.error(ContactEnum.CONTACT_PHONE_ERROR);
        }
        //设定联系人id为数据库中最大ID值加1
        contact.setCtId(Integer.parseInt(utils.getMaxContactID()) + 1 + "");
        //设定联系人状态默认为正常0
        contact.setCtDelete(0);
        utils.addContact(contact);
        return JsonResult.success(ContactEnum.CONTACT_ADD_SUCCESS);
    }

    @Override
    public JsonResult deleteContact(String ctId) {
        if (ctId.equals("") || ctId == null) {
            return JsonResult.error(ContactEnum.CONTACT_MSG_ERROR);
        }
        utils.deleteContact(Wrappers.lambdaUpdate(Contact.class)
                .eq(Contact::getCtId, ctId));
        return JsonResult.success(ContactEnum.CONTACT_DELETE_SUCCESS);

    }
}
