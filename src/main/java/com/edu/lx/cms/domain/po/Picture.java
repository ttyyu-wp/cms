package com.edu.lx.cms.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 联系人图片信息表
 * </p>
 *
 * @author LiXue
 * @since 2025-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_picture")
public class Picture implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 图片ID
     */
    @TableId(value = "pic_id", type = IdType.INPUT)
    private String picId;

    /**
     * 联系人ID号码
     */
    @TableField("ct_id")
    private String ctId;

    /**
     * 图片名称
     */
    @TableField("pic_name")
    private String picName;


}
