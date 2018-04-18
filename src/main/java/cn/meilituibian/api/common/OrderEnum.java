package cn.meilituibian.api.common;

public enum  OrderEnum {
    FREEZE(-1, "冻结"),WAIT(0,"待处理"),PROCESSING(1,"处理中"),FINISH(2, "处理完成");
    private String name;
    private int code;

    OrderEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getOrderStatus(int code) {
        for (OrderEnum e : OrderEnum.values()) {
            if(e.code == code) {
                return e.name;
            }
        }
        return "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
