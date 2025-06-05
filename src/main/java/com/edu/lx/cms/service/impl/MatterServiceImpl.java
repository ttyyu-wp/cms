package com.edu.lx.cms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.edu.lx.cms.domain.po.Contact;
import com.edu.lx.cms.domain.po.Matter;
import com.edu.lx.cms.domain.vo.MatterVO;
import com.edu.lx.cms.enums.MatterEnum;
import com.edu.lx.cms.mapper.MatterMapper;
import com.edu.lx.cms.service.MatterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.lx.cms.utils.DBUtils;
import com.edu.lx.cms.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 联系人事项信息表 服务实现类
 * </p>
 *
 * @author LiXue
 * @since 2025-06-05
 */
@Service
public class MatterServiceImpl extends ServiceImpl<MatterMapper, Matter> implements MatterService {
    @Autowired
    private DBUtils utils;

    @Override
    public JsonResult getMatterUser(MatterVO matterVO) {
        //判断条件
        if (!(matterVO.getMatterDelete() + "").matches("^[012]$")) {
            return JsonResult.error(MatterEnum.MATTER_DELETE_ERROR);
        }
        if (matterVO.getUserId() == null || matterVO.getUserId().equals("")) {
            return JsonResult.error(MatterEnum.MATTER_DELETE_ERROR);
        }
        //根据userId获得所有当前用户的联系人状态为 0 正常 的contactList 再通过stream流获得ctIdList
        List<String> ctIdList = utils.getAllContact(Wrappers.lambdaQuery(Contact.class)
                        .eq(Contact::getUserId, matterVO.getUserId())
                        .eq(Contact::getCtDelete, matterVO.getMatterDelete()))
                .stream()
                .map(Contact::getCtId)
                .collect(Collectors.toList());
        //根据contactList中的ctId查找所有matter
        List<Matter> matterList = utils.getMatterUser(Wrappers.lambdaQuery(Matter.class)
                .eq(Matter::getMatterDelete, matterVO.getMatterDelete())
                .in(Matter::getCtId, ctIdList));
        return JsonResult.success(MatterEnum.MATTER_QUERY_SUCCESS, matterList);
    }
}
