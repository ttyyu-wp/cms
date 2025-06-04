package com.edu.lx.cms.enums;

public enum PictureEnum {
    PICTURE_QUERY_SUCCESS("图片查询成功！"),
    PICTURE_UPDATE_SUCCESS("图片更新成功！"),
    PICTURE_NOT_FOUND("不存在此用户图片！"),
    PICTURE_COUNT_ERROR("所包含的用户图片数量有异常！");

    private final String description;

    PictureEnum(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
