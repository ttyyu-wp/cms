package com.edu.lx.cms.service.impl;

import com.edu.lx.cms.domain.po.Contact;
import com.edu.lx.cms.mapper.ContactMapper;
import com.edu.lx.cms.service.ContactService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
