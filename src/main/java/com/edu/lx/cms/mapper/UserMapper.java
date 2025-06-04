package com.edu.lx.cms.mapper;

import com.edu.lx.cms.domain.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author LiXue
 * @since 2025-06-03
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
