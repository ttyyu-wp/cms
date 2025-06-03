package com.edu.lx.cms.service.impl;

import com.edu.lx.cms.domain.po.User;
import com.edu.lx.cms.mapper.UserMapper;
import com.edu.lx.cms.enums.MsgEnum;
import com.edu.lx.cms.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.lx.cms.utils.DBUtils;
import com.edu.lx.cms.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author LiXue
 * @since 2025-06-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private DBUtils utils;

    @Override
    public JsonResult getUser(User user) {
        User userVO = utils.getUser(user);
        //判断是否存在
        if (userVO == null) {
            return JsonResult.error(MsgEnum.USER_NOT_FOUND);
        }
        //判断密码是否一致
        if (!userVO.getUserPassword().equals(user.getUserPassword())) {
            return JsonResult.error(MsgEnum.USER_PASSWORD_ERROR);
        }
        //返回结果
        return JsonResult.success(MsgEnum.USER_LOGIN_SUCCESS);
    }

    @Override
    public JsonResult register(User user) {
        User userVO = utils.getUser(user);
        if (userVO != null) {
            return JsonResult.error(MsgEnum.USER_HAD_EXIST);
        }
        utils.register(user);
        return JsonResult.success(MsgEnum.USER_REGISTER_SUCCESS);
    }
}
