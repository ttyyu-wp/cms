package com.edu.lx.cms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.edu.lx.cms.context.UserContext;
import com.edu.lx.cms.domain.po.User;
import com.edu.lx.cms.domain.po.UserPicture;
import com.edu.lx.cms.enums.PictureEnum;
import com.edu.lx.cms.mapper.UserMapper;
import com.edu.lx.cms.enums.UserEnum;
import com.edu.lx.cms.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.lx.cms.utils.AliOSSUtils;
import com.edu.lx.cms.utils.DBUtils;
import com.edu.lx.cms.utils.JsonResult;
import com.edu.lx.cms.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author LiXue
 * @since 2025-06-03
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private DBUtils utils;
    @Autowired
    private AliOSSUtils aliOSSUtils;

    @Override
    public JsonResult getUser(User user) {
        User userVO = utils.getUser(user);
        //判断是否存在
        if (userVO == null) {
            return JsonResult.error(UserEnum.USER_NOT_FOUND);
        }
        //判断密码是否一致
        if (!userVO.getUserPassword().equals(user.getUserPassword())) {
            return JsonResult.error(UserEnum.USER_PASSWORD_ERROR);
        }
        //返回结果
        String token = JwtUtil.createToken(user);
        return JsonResult.success(UserEnum.USER_LOGIN_SUCCESS, token);
    }

    @Override
    public JsonResult register(User user) {
        User userVO = utils.getUser(user);
        if (userVO != null) {
            return JsonResult.error(UserEnum.USER_HAD_EXIST);
        }
        utils.register(user);
        return JsonResult.success(UserEnum.USER_REGISTER_SUCCESS);
    }

    @Override
    public JsonResult getMe() {
         UserPicture up = utils.getMe(Wrappers.lambdaQuery(UserPicture.class)
                 .eq(UserPicture::getUserId, UserContext.getCurrentUser()));
         return JsonResult.success(UserEnum.USER_FOUND, up);
    }

    @Override
    public JsonResult upUserPic(String userId, MultipartFile picName) {
        String url = null;
        try {
            //调用AliOSS上传图片
            log.debug("上传过来的参数：{}", picName);
            url = aliOSSUtils.upload(picName);
        } catch (IOException e) {
            return JsonResult.error(UserEnum.USER_PIC_ERROR);
        }
        log.debug("文件上传完成，url是：{}", url);

        List<UserPicture> userPic = utils.getUserPic(Wrappers.lambdaQuery(UserPicture.class)
                .eq(UserPicture::getUserId, userId));

        if (userPic.size() == 0) {
            UserPicture up = new UserPicture();
            up.setUserId(userId);
            up.setPicName(url);
            utils.addUserPic(up);
            return JsonResult.success(UserEnum.USER_PIC_SUCCESS);
        }
        userPic.get(0).setPicName(url);
        utils.updateUserPic(Wrappers.lambdaUpdate(UserPicture.class)
                .eq(UserPicture::getUserId, userId), userPic.get(0));

        return JsonResult.success(UserEnum.USER_PIC_SUCCESS);
    }
}
