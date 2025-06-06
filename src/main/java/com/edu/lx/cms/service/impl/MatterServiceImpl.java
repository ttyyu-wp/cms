package com.edu.lx.cms.service.impl;

import cn.hutool.core.date.DateTime;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
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
@Slf4j
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

    @Override
    public JsonResult getMatterContact(MatterVO matterVO) {
        //判断条件
        if (!(matterVO.getMatterDelete() + "").matches("^[012]$")) {
            return JsonResult.error(MatterEnum.MATTER_DELETE_ERROR);
        }
        if (matterVO.getCtId() == null || matterVO.getCtId().equals("")) {
            return JsonResult.error(MatterEnum.MATTER_DELETE_ERROR);
        }
        //获取matterList
        List<Matter> list = utils.getMatterContact(Wrappers.lambdaQuery(Matter.class)
                .eq(Matter::getCtId, matterVO.getCtId())
                .eq(Matter::getMatterDelete, matterVO.getMatterDelete()));
        return JsonResult.success(MatterEnum.MATTER_QUERY_SUCCESS, list);
    }

    @Override
    public JsonResult delete1Matter(String matterId) {
        if (matterId.equals("")) {
            return JsonResult.error(MatterEnum.MATTER_ID_ERROR);
        }
        //根据Delete状态删除
        utils.deleteMatter(Wrappers.lambdaUpdate(Matter.class)
                .eq(Matter::getMatterId, matterId),
                new Matter().setMatterDelete(1));
        return JsonResult.success(MatterEnum.MATTER_DELETE_SET_1_SUCCESS);
    }

    @Override
    public JsonResult delete2Matter(String matterId) {
        if (matterId.equals("")) {
            return JsonResult.error(MatterEnum.MATTER_ID_ERROR);
        }
        //根据Delete状态修改
        utils.deleteMatter(Wrappers.lambdaUpdate(Matter.class)
                        .eq(Matter::getMatterId, matterId),
                new Matter().setMatterDelete(2));
        return JsonResult.success(MatterEnum.MATTER_DELETE_SET_2_SUCCESS);
    }

    @Override
    public JsonResult deleteMatter(String matterId) {
        if (matterId.equals("")) {
            return JsonResult.error(MatterEnum.MATTER_ID_ERROR);
        }
        //彻底删除事项
        utils.deleteMatterE(matterId);
        return JsonResult.success(MatterEnum.MATTER_DELETE_SUCCESS);
    }

    @Override
    public JsonResult addMatter(Matter matter) {
        //判断条件
        if (matter.getMatter().equals("")) {
            return JsonResult.error(MatterEnum.MATTER_TEXT_EMPTY);
        }
        //校验时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(matter.getMatterTime());
            if (date.before(new Date())) {
                return JsonResult.error(MatterEnum.MATTER_TIME_ERROR);
            }
        } catch (ParseException e) {
            log.error(e.getMessage());
            return JsonResult.error(MatterEnum.MATTER_TIME_FORMAT_ERROR);
        }
        //设定事项状态默认为 0 待完成
        matter.setMatterDelete(0);
        //设定matterId为数据库最大matterId加1
        matter.setMatterId(Integer.parseInt(utils.getMaxMatterID()) + 1 + "");
        //添加
        utils.addMatter(matter);
        return JsonResult.success(MatterEnum.MATTER_ADD_SUCCESS);
    }
}
