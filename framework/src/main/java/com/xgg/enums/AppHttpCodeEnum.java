package com.xgg.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
     PHONENUMBER_EXIST(502,"手机号已存在"), EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(505,"用户名或密码错误"), NULL_CONTENT(506, "评论内容不能为空！"),
    FILE_TYPE_ERROR(507,"文件类型错误，请上传JPG文件" ), USERNAME_NOT_NULL(508,"用户名不能为空" )
    ,PASSWORD_NOT_NULL(510,"密码不能为空" ),NICKNAME_NOT_NULL(509,"名称不能为空" )
    ,EMAIL_NOT_NULL(511,"邮箱不能为空" ), ARTICLE_NOT_NULL(512, "文章不能为空"),
    USER_NOT_EXIST(513,"用户不存在"),Article_NOT_EXIST(514,"文章不存在");;
    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
