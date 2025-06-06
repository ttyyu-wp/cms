package com.edu.lx.cms.service;

import com.edu.lx.cms.domain.po.Matter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.lx.cms.domain.vo.MatterVO;
import com.edu.lx.cms.utils.JsonResult;

/**
 * <p>
 * 联系人事项信息表 服务类
 * </p>
 *
 * @author LiXue
 * @since 2025-06-05
 */
public interface MatterService extends IService<Matter> {

    JsonResult getMatterUser(MatterVO matterVO);

    JsonResult getMatterContact(MatterVO matterVO);

    JsonResult delete1Matter(String matterId);

    JsonResult delete2Matter(String matterId);

    JsonResult deleteMatter(String matterId);
}
