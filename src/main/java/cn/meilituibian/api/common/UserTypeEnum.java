package cn.meilituibian.api.common;

public enum UserTypeEnum {
    NORMAL(0, "普通用户"),MERCHANT(1, "商家"),SALESMAN(2, "业务员");
    private int type;
    private String name;
    UserTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String getTypeName(int type) {
        for (UserTypeEnum userTypeEnum: UserTypeEnum.values()) {
            if (userTypeEnum.type == type) {
                return userTypeEnum.name;
            }
        }
        return NORMAL.name();
    }
}
