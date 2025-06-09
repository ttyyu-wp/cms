package com.edu.lx.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.edu.lx.cms.domain.po.Picture;
import com.edu.lx.cms.domain.po.UserPicture;
import com.edu.lx.cms.domain.vo.PictureVO;
import com.edu.lx.cms.enums.PictureEnum;
import com.edu.lx.cms.mapper.PictureMapper;
import com.edu.lx.cms.service.PictureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.lx.cms.utils.AliOSSUtils;
import com.edu.lx.cms.utils.DBUtils;
import com.edu.lx.cms.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
@Slf4j
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements PictureService {

    @Autowired
    private DBUtils utils;
    @Autowired
    private AliOSSUtils aliOSSUtils;

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
        UserPicture userPicture = pictureList.get(0);
        return JsonResult.success(PictureEnum.PICTURE_QUERY_SUCCESS, userPicture);
    }

    @Override
    public JsonResult getContactPic(String ctId) {
        Picture picture = utils.getContactPic(Wrappers.lambdaQuery(Picture.class)
                .eq(Picture::getCtId, ctId));
        if (picture == null) {
            return JsonResult.error(PictureEnum.PICTURE_NOT_FOUND);
        }
        return JsonResult.success(PictureEnum.PICTURE_QUERY_SUCCESS, picture);
    }

    @Override
    public JsonResult updateContactPic(String ctId, String picId, MultipartFile picName) {
        String url = null;
        try {
            //调用AliOSS上传图片
            log.debug("上传过来的参数：{}", picName);
            url = aliOSSUtils.upload(picName);
        } catch (IOException e) {
            return JsonResult.error(PictureEnum.PICTURE_UPLOAD_ERROR);
        }
        log.debug("文件上传完成，url是：{}", url);
        //传参到Picture
        Picture picture = new Picture();
        picture.setPicId(picId);
        picture.setCtId(ctId);
        picture.setPicName(url);
        //进行更新
        Integer count = utils.updateContactPic(picture);

        if (count < 0) {
            return JsonResult.error(PictureEnum.PICTURE_NOT_FOUND);
        }
        return JsonResult.success(PictureEnum.PICTURE_UPDATE_SUCCESS);
    }

    @Override
    public JsonResult addContactPic(MultipartFile picName) {

        String url = null;
        try {
            //调用AliOSS上传图片
            log.debug("上传过来的参数：{}", picName);
            url = aliOSSUtils.upload(picName);
        } catch (IOException e) {
            return JsonResult.error(PictureEnum.PICTURE_ADD_ERROR);
        }
        Picture picture = new Picture();
        picture.setCtId(Integer.parseInt(utils.getMaxContactID()) + 1 + "");
        picture.setPicName(url);
        picture.setPicId(Integer.parseInt(utils.getMaxContactPicID()) + 1 + "");

        utils.addContactPic(picture);
        log.debug("文件上传完成，url是：{}", url);
        return JsonResult.success(PictureEnum.PICTURE_ADD_SUCCESS);
    }
}
