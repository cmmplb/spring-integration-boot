package com.cmmplb.netty.socket.enums;

/**
 * @author penglibo
 * @date 2021-06-29 19:52:19
 * @since jdk 1.8
 */
public enum CmdEnum {

    // ------------------------------设备端请求协议------------------------------------------------------
    LOGIN("设备登录", (byte) 0x01, (byte) 0x02, "loginRsp"),
    REGISTER("设备注册", (byte) 0x05, (byte) 0x06, "registerRsp"),
    HEARTBEAT("设备心跳", (byte) 0x07, (byte) 0x08, "heartbeatReq"),

    // -------------------------------服务端请求协议-----------------------------------------------------
    // 服务器给设备发送消息命令统一为0x30
    // 终端返回统一为0x31
    // 业务区分通过可变数据字节中cmd分类
    CLIENT("统一", (byte) 0x30, (byte) 0x31, "unite"),
    SP_SET_CONFIG("服务端推送配置信息", (byte) 0, (byte) 0, "spSetConfigRsp"),
    //
    DEFAULT("无", (byte) 0, (byte) 0, "0"),
    ;

    /**
     * 名称
     */
    private String name;

    /**
     * 头部消息请求命令
     */
    private byte reqCmd;

    /**
     * 头部消息响应命令
     */
    private byte respCmd;

    /**
     * 消息类型-固定值
     */
    private String message;

    CmdEnum(String name, byte reqCmd, byte respCmd, String message) {
        this.name = name;
        this.reqCmd = reqCmd;
        this.respCmd = respCmd;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getReqCmd() {
        return reqCmd;
    }

    public void setReqCmd(byte reqCmd) {
        this.reqCmd = reqCmd;
    }

    public byte getRespCmd() {
        return respCmd;
    }

    public void setRespCmd(byte respCmd) {
        this.respCmd = respCmd;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 解决枚举不能使用在case中的问题
     * @param reqCmd
     * @return
     */
    public static CmdEnum getInstance(Byte reqCmd) {
        for (CmdEnum cmdEnum : values()) {
            if (cmdEnum.getReqCmd() == (reqCmd)) {
                return cmdEnum;
            }
        }
        return DEFAULT;
    }
}
