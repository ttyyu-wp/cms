package com.edu.lx.cms.controller;


import com.edu.lx.cms.domain.vo.MatterVO;
import com.edu.lx.cms.service.MatterService;
import com.edu.lx.cms.utils.JsonResult;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 联系人事项信息表 前端控制器
 * </p>
 *
 * @author LiXue
 * @since 2025-06-05
 */
@RestController
@RequestMapping("/matter")
public class MatterController {
    @Autowired
    private MatterService matterService;

    @PostMapping("/all")
    public JsonResult getMatterUser(@RequestBody MatterVO matterVO) {
        return matterService.getMatterUser(matterVO);
    }

    @PostMapping("/one")
    public JsonResult getMatterContact(@RequestBody MatterVO matterVO) {
        return matterService.getMatterContact(matterVO);
    }

    @DeleteMapping("/delete1")
    public JsonResult delete1Matter(@RequestParam("matterId") String matterId) {
        return matterService.delete1Matter(matterId);
    }

    @DeleteMapping("/delete2")
    public JsonResult delete2Matter(@RequestParam("matterId") String matterId) {
        return matterService.delete2Matter(matterId);
    }

    @DeleteMapping("/delete")
    public JsonResult deleteMatter(@RequestParam("matterId") String matterId) {
        return matterService.deleteMatter(matterId);
    }

}
