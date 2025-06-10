package com.edu.lx.cms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.lx.cms.context.UserContext;
import com.edu.lx.cms.domain.dto.MatterDTO;
import com.edu.lx.cms.domain.po.Contact;
import com.edu.lx.cms.domain.po.Matter;
import com.edu.lx.cms.domain.query.PageQuery;
import com.edu.lx.cms.domain.vo.MatterVO;
import com.edu.lx.cms.enums.MatterEnum;
import com.edu.lx.cms.enums.UserEnum;
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
import java.util.*;
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
    public JsonResult getMatterUser(PageQuery query) {
        String userId = UserContext.getCurrentUser();
        // 查询联系人
        List<Contact> contacts = utils.getAllContact(Wrappers.lambdaQuery(Contact.class)
                .eq(Contact::getUserId, userId)
                .eq(Contact::getCtDelete, 0));
        List<String> ctIdList = Optional.ofNullable(contacts)
                .orElse(Collections.emptyList())
                .stream()
                .map(Contact::getCtId)
                .collect(Collectors.toList());
        // 构造查询条件

        // 添加排序逻辑
        Boolean isAsc = "true".equalsIgnoreCase(query.getIsAsc()) ? Boolean.TRUE :
                "false".equalsIgnoreCase(query.getIsAsc()) ? Boolean.FALSE : null;
        // 构建查询参数
        Map<String, Object> params = new HashMap<>();
        params.put("page", new Page<>(query.getPageNo(), query.getPageSize()));
        params.put("userId", userId);
        params.put("matterDelete", query.getMatterDelete());
        params.put("matter", query.getMatter());
        params.put("ctIdList", ctIdList);
        params.put("isAsc", isAsc);

        // 安全获取 matterDelete 参数
        Object matterDeleteObj = params.get("matterDelete");
        Integer matterDelete = null;
        if (matterDeleteObj != null) {
            if (matterDeleteObj instanceof Integer) {
                matterDelete = (Integer) matterDeleteObj;
            } else if (matterDeleteObj instanceof String) {
                try {
                    matterDelete = Integer.parseInt((String) matterDeleteObj);
                } catch (NumberFormatException e) {
                    // 可以记录日志或做默认处理
                    matterDelete = null; // 或者设置为默认值
                }
            }
        }
        // 调用方法
        IPage<MatterDTO> matterUser = utils.getMatterUser(
                (Page<MatterDTO>) params.get("page"),
                (String) params.get("userId"),
                matterDelete,
                (String) params.get("matter"),
                (List<String>) params.get("ctIdList"),
                (Boolean) params.get("isAsc"));

        return JsonResult.success(MatterEnum.MATTER_QUERY_SUCCESS, matterUser);
    }

    @Override
    public JsonResult getMatterContact(MatterVO matterVO) {
        //判断条件
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
    public JsonResult deleteMatterEnd(String matterId) {
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

    @Override
    public JsonResult deleteByDel(Matter matter) {
        if (matter.getMatterId().equals("")) {
            return JsonResult.error(MatterEnum.MATTER_ID_ERROR);
        }
        //根据Delete状态修改
        utils.deleteMatter(Wrappers.lambdaUpdate(Matter.class)
                        .eq(Matter::getMatterId, matter.getMatterId()),
                        matter);
        return JsonResult.success(MatterEnum.MATTER_DELETE_SET_2_SUCCESS);
    }

    @Override
    public JsonResult getTotalMatterPage(PageQuery query) {
        String userId = UserContext.getCurrentUser();
        // 查询联系人
        List<Contact> contacts = utils.getAllContact(Wrappers.lambdaQuery(Contact.class)
                .eq(Contact::getUserId, userId)
                .eq(Contact::getCtDelete, 0));
        List<String> ctIdList = Optional.ofNullable(contacts)
                .orElse(Collections.emptyList())
                .stream()
                .map(Contact::getCtId)
                .collect(Collectors.toList());
        // 构造查询条件
        LambdaQueryWrapper<Matter> wrapper = Wrappers.lambdaQuery(Matter.class)
                .eq(query.getMatterDelete() != null, Matter::getMatterDelete, query.getMatterDelete())
                .in(Matter::getCtId, ctIdList);
        List<Matter> matterList = utils.getMatterAll(wrapper);
        double totalPage = Math.ceil(1.00 * matterList.size() / query.getPageSize());
        return JsonResult.success(MatterEnum.MATTER_QUERY_SUCCESS, totalPage);
    }
}
