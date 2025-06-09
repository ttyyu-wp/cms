package com.edu.lx.cms.enums;


public enum UserEnum {
    USER_LOGIN_SUCCESS("登录成功！"),
    USER_REGISTER_SUCCESS("注册成功"),
    USER_NOT_FOUND("用户不存在！"),
    USER_PIC_ERROR("用户图片上传失败！"),
    USER_PIC_SUCCESS("用户图片上传成功！"),
    USER_FOUND("获取当前用户成功！"),
    USER_PASSWORD_ERROR("密码不一致！"),
    USER_HAD_EXIST("用户已存在");

    private final String description;

    UserEnum(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
