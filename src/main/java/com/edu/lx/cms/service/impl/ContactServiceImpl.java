package com.edu.lx.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.lx.cms.domain.pojo.Contact;
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
}
