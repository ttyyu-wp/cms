package com.edu.lx.cms.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 联系人事项信息表
 * </p>
 *
 * @author LiXue
 * @since 2025-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_contact_matter")
public class Matter implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 事项ID
     */
    @TableId(value = "matter_id", type = IdType.INPUT)
    private String matterId;

    /**
     * 事项ID
     */
    @TableField("matter_time")
    private LocalDateTime matterTime;

    /**
     * 事项信息
     */
    @TableField("matter")
    private String matter;

    /**
     * 事项状态
     */
    @TableField("matter_delete")
    private Integer matterDelete;

    /**
     * 联系人ID号码
     */
    @TableField("ct_id")
    private String ctId;


}
