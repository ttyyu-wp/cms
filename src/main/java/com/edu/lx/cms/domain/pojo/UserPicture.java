package com.edu.lx.cms.domain.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户图片信息表
 * </p>
 *
 * @author LiXue
 * @since 2025-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_picture")
public class UserPicture implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 图片ID
     */
    @TableField("pic_id")
    private String picId;

    /**
     * 图片文件名
     */
    @TableField("pic_name")
    private String picName;


}
