package com.edu.lx.cms.controller;


import com.edu.lx.cms.context.UserContext;
import com.edu.lx.cms.domain.po.Picture;
import com.edu.lx.cms.service.PictureService;
import com.edu.lx.cms.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public JsonResult getUserPic() {
        return pictureService.getUserPic(UserContext.getCurrentUser());
    }

    @PostMapping("/ctPic")
    public JsonResult getContactPic(@RequestParam("ctId") String ctId) {
        return pictureService.getContactPic(ctId);
    }

    @PostMapping("/update")
    public JsonResult updateContactPic(@RequestBody Picture picture) {
        return pictureService.updateContactPic(picture);
    }

    @PostMapping("/add")
    public JsonResult addContactPic(@RequestBody Picture picture) {
        return pictureService.addContactPic(picture);
    }
}
