package com.edu.lx.cms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.lx.cms.domain.dto.MatterDTO;
import com.edu.lx.cms.domain.po.Matter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 联系人事项信息表 Mapper 接口
 * </p>
 *
 * @author LiXue
 * @since 2025-06-04
 */
@Mapper
public interface MatterMapper extends BaseMapper<Matter> {

    IPage<MatterDTO> getMatterUser(@Param("page") Page<MatterDTO> page,
                                          @Param("userId") String userId,
                                          @Param("matterDelete") Integer matterDelete,
                                          @Param("matter") String matter,
                                          @Param("ctIdList") List<String> ctIdList,
                                          @Param("isAsc") Boolean isAsc);
}
