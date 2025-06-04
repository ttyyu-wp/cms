package com.edu.lx.cms.enums;

public enum ContactEnum {
    CONTACT_QUERY_SUCCESS("查询成功！"),
    CONTACT_MSG_ERROR("查询总页数参数存在问题！");


    private final String description;

    ContactEnum(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
