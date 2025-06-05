package com.edu.lx.cms.service;

import com.edu.lx.cms.domain.po.Picture;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.lx.cms.utils.JsonResult;

/**
 * <p>
 * 联系人图片信息表 服务类
 * </p>
 *
 * @author LiXue
 * @since 2025-06-03
 */
public interface PictureService extends IService<Picture> {

    JsonResult getUserPic(String userId);

    JsonResult getContactPic(String ctId);

    JsonResult updateContactPic(Picture picture);

    JsonResult addContactPic(Picture picture);
}
