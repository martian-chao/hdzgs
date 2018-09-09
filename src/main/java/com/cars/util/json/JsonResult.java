package com.cars.util.json;

/**
 * 所有Rest接口返回公共对象
 * Json返回结果
 *
 */
public class JsonResult {
    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 消息
     */
    private String message;
    /**
     * 返回码
     */
    private String code;

    /**
     * 额外的数据
     */
    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public JsonResult(boolean success, String code, Object data) {
        this(success, code);
        this.data = data;
    }

    public JsonResult() {
    }

    public JsonResult(boolean success) {
        this.success = success;
    }

    /**
     * message通过code获取
     *
     * @param success
     * @param code
     */
    public JsonResult(boolean success, String code) {
        this(success);
        this.code = code;
//        String msg = ReturnCodeUtil.getMsg(code);
//        this.message = StringUtil.isNullOrEmpty(msg) ? code : msg;
    }

    /**
     * 三个参数，message自己赋值，不通过code获取
     *
     * @param success
     * @param code
     * @param message
     */
    public JsonResult(boolean success, String code, String message) {
        this(success);
        this.code = code;
        this.message = message;
    }

    /**
     * 成功的Json结果
     */
    private static final JsonResult success_instance = new JsonResult(true);

    /**
     * 获取成功的Json结果
     *
     * @return 成功的Json结果
     */
    public static JsonResult getSuccess_instance() {
        return success_instance;
    }
}
