package com.edu.lx.cms.enums;

public enum MatterEnum {
    MATTER_DELETE_ERROR("事项状态不合规！"),
    MATTER_QUERY_SUCCESS("获取事项成功！");

    private String description;

    MatterEnum(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
