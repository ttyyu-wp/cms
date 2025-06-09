package com.edu.lx.cms.service;

import com.edu.lx.cms.domain.po.Picture;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.lx.cms.domain.vo.PictureVO;
import com.edu.lx.cms.utils.JsonResult;
import org.springframework.web.multipart.MultipartFile;

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

    JsonResult updateContactPic(String ctId, String picId, MultipartFile picName);

    JsonResult addContactPic(Picture picture);

}
