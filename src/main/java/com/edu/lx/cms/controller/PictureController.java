package com.edu.lx.cms.controller;


import com.edu.lx.cms.context.UserContext;
import com.edu.lx.cms.domain.po.Picture;
import com.edu.lx.cms.domain.vo.PictureVO;
import com.edu.lx.cms.service.PictureService;
import com.edu.lx.cms.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 联系人图片信息表 前端控制器
 * </p>
 *
 * @author LiXue
 * @since 2025-06-03
 */
@RestController
@RequestMapping("/picture")
public class PictureController {
    @Autowired
    private PictureService pictureService;

    @GetMapping
    public JsonResult getUserPic() {
        return pictureService.getUserPic(UserContext.getCurrentUser());
    }

    @PostMapping("/ctPic")
    public JsonResult getContactPic(@RequestBody Picture picture) {
        return pictureService.getContactPic(picture.getCtId());
    }

    @PostMapping("/update")
    public JsonResult updateContactPic(@RequestPart("ctId") String ctId,
                                       @RequestPart("picId") String picId,
                                       @RequestPart("picName")MultipartFile picName) {
        return pictureService.updateContactPic(ctId, picId, picName);
    }

    @PostMapping("/add")
    public JsonResult addContactPic(@RequestPart("picName") MultipartFile picName) {
        return pictureService.addContactPic(picName);
    }
}
