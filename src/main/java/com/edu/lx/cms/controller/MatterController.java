package com.edu.lx.cms.controller;


import com.edu.lx.cms.domain.vo.MatterVO;
import com.edu.lx.cms.service.MatterService;
import com.edu.lx.cms.utils.JsonResult;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
    private JsonResult getMatterUser(@RequestBody MatterVO matterVO) {
        return matterService.getMatterUser(matterVO);
    }
}
