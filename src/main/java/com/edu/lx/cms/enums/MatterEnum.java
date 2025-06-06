package com.edu.lx.cms.enums;

public enum MatterEnum {
    MATTER_DELETE_ERROR("事项状态不合规！"),
    MATTER_ID_ERROR("事项不合规！"),
    MATTER_DELETE_SET_1_SUCCESS("事项状态已改为取消！"),
    MATTER_DELETE_SET_2_SUCCESS("事项状态已改为已完成！"),
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
