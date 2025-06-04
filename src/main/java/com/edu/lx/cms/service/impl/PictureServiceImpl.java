package com.edu.lx.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.edu.lx.cms.domain.pojo.Picture;
import com.edu.lx.cms.domain.pojo.UserPicture;
import com.edu.lx.cms.enums.PictureEnum;
import com.edu.lx.cms.mapper.PictureMapper;
import com.edu.lx.cms.service.PictureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.lx.cms.utils.DBUtils;
import com.edu.lx.cms.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 联系人图片信息表 服务实现类
 * </p>
 *
 * @author LiXue
 * @since 2025-06-03
 */
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements PictureService {

    @Autowired
    private DBUtils utils;

    @Override
    public JsonResult getUserPic(String userId) {
        //自定义Wrapper
        LambdaQueryWrapper<UserPicture> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserPicture::getUserId, userId);
        //查找用户对应的图片
        List<UserPicture> pictureList = utils.getUserPic(wrapper);
        //若不存在返回错误
        if (pictureList.isEmpty()) {
            return JsonResult.error(PictureEnum.PICTURE_NOT_FOUND);
        }
        //若存在多个返回错误
        if (pictureList.size() != 1) {
            return JsonResult.error(PictureEnum.PICTURE_COUNT_ERROR);
        }
        //返回图片名称
        String picName = pictureList.get(0).getPicName();
        //TODO html中不需要添加alert
        return JsonResult.success(PictureEnum.PICTURE_SUCCESS, picName);
    }

    @Override
    public JsonResult getContactPic(String ctId) {
        Picture picture = utils.getContactPic(Wrappers.lambdaQuery(Picture.class)
                .eq(Picture::getCtId, ctId));
        if (picture == null) {
            return JsonResult.error(PictureEnum.PICTURE_NOT_FOUND);
        }
        return JsonResult.success(PictureEnum.PICTURE_SUCCESS, picture.getPicName());
    }
}
