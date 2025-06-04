package com.edu.lx.cms.service;

import com.edu.lx.cms.domain.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.lx.cms.utils.JsonResult;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author LiXue
 * @since 2025-06-03
 */
public interface UserService extends IService<User> {

    JsonResult getUser(User user);

    JsonResult register(User user);
}
