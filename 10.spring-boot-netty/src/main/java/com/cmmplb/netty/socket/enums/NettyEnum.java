package com.cmmplb.netty.socket.enums;

/**
 * @author penglibo
 * @date 2021-07-01 20:02:15
 * @since jdk 1.8
 * 设备状态码
 */

public enum NettyEnum {

    OK(200, "Success", "成功"),
    REQUEST_FAILED(500, "RequestFailed", "请求失败"),
    AUTH_FAILED(401, "AuthFailed", "认证失败"),
    NO_ACCESS(403, "NoAccess ", "无权访问"),
    REQUEST_FREQUENT(429, "RequestFrequent ", "请求太频繁"),
    PUSH_FAILED(450, "PushFailed", "推送失败"),
    DEVICE_ID_MUST_NOT_EMPTY(1001, "DeviceIDMustNotEmpty", "设备ID不得为空"),
    DEVICE_NOT_EXIST(1002, "DeviceNotExist", "设备不存在"),
    DEVICE_SN_NOT_EMPTY(1003, "DeviceSNNotEmpty", "设备序列号不得为空"),
    DEVICE_SN_REGISTERED(1004, "DeviceSNRegistered", "设备序列号已经注册"),
    UNBINDING_FAILED(1005, "UnbindingFailed", "解绑失败"),
    ABNORMAL_BASE64_CONVERSION_OF_IMAGE(2001, "AbnormalBase64ConversionOfImage", "图片转Base64异常"),
    EQUIPMENT_PERSONNEL_ALREADY_EXIST(2003, "EquipmentPersonnelAlreadyExist", "设备人员已经存在"),
    STAFF_DOES_NOT_EXIST(2006, "StaffDoesNotExist", "人员不存在"),
    ;

    /**
     * 状态码
     */
    private final int code;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 消息说明
     */
    private final String message;

    NettyEnum(final int code, final String desc, final String message) {
        this.code = code;
        this.desc = desc;
        this.message = message;
    }

    public String getDesc() {
        return desc;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }


}
