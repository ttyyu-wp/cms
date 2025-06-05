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
 * 联系人信息表
 * </p>
 *
 * @author LiXue
 * @since 2025-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_contact")

public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 联系人ID号码
     */
    @TableId(value = "ct_id", type = IdType.INPUT)
    private String ctId;

    /**
     * 用户名ID号码
     */
    @TableField("user_id")
    private String userId;

    /**
     * 联系人姓名
     */
    @TableField("ct_name")
    private String ctName;

    /**
     * 联系人地址
     */
    @TableField("ct_ad")
    private String ctAd;

    /**
     * 联系人邮编
     */
    @TableField("ct_yb")
    private String ctYb;

    /**
     * 联系人QQ
     */
    @TableField("ct_qq")
    private String ctQq;

    /**
     * 联系人微信
     */
    @TableField("ct_wx")
    private String ctWx;

    /**
     * 联系人邮箱
     */
    @TableField("ct_em")
    private String ctEm;

    /**
     * 联系人性别
     */
    @TableField("ct_mf")
    private String ctMf;

    /**
     * 联系人出生日期
     */
    @TableField("ct_birth")
    private String ctBirth;

    /**
     * 联系人电话
     */
    @TableField("ct_phone")
    private String ctPhone;

    /**
     * 联系人状态 0为正常 1表示屏蔽
     */
    @TableField("ct_delete")
    private Integer ctDelete;


}
