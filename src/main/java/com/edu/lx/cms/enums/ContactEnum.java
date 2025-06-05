package com.edu.lx.cms.enums;

public enum ContactEnum {
    CONTACT_QUERY_SUCCESS("查询成功！"),
    CONTACT_ADD_SUCCESS("添加成功！"),
    CONTACT_UPDATE_SUCCESS("更新成功！"),
    CONTACT_YB_ERROR("邮编格式不正确！"),
    CONTACT_QQ_ERROR("QQ格式不正确！"),
    CONTACT_EMAIL_ERROR("邮箱格式不正确！"),
    CONTACT_PHONE_ERROR("电话号码格式不正确！"),
    CONTACT_NOT_FOUND("不存在此联系人！"),
    CONTACT_MSG_ERROR("参数存在问题！");


    private final String description;

    ContactEnum(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
