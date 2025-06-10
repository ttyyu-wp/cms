package com.edu.lx.cms.controller;


import com.edu.lx.cms.domain.po.Matter;
import com.edu.lx.cms.domain.query.PageQuery;
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
    public JsonResult getMatterUser(@RequestBody PageQuery query) {
        return matterService.getMatterUser(query);
    }

    @PostMapping("/page")
    public JsonResult getTotalMatterPage(@RequestBody PageQuery query) {
        return matterService.getTotalMatterPage(query);
    }

    @PostMapping("/one")
    public JsonResult getMatterContact(@RequestBody MatterVO matterVO) {
        return matterService.getMatterContact(matterVO);
    }

    @PostMapping("/deleteByDel")
    public JsonResult deleteByDel(@RequestBody Matter matter) {
        return matterService.deleteByDel(matter);
    }

    @DeleteMapping("/delete")
    public JsonResult deleteMatter(@RequestParam("matterId") String matterId) {
        return matterService.deleteMatterEnd(matterId);
    }

    @PostMapping("/add")
    public JsonResult addMatter(@RequestBody Matter matter) {
        return matterService.addMatter(matter);
    }

}
