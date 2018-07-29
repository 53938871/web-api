package cn.meilituibian.api.common;

public enum JobTitleEnum {
    INDIVIDUAL(-1, "个人"),MEMBER(0,"见习导师"),LEADER(1,"银牌导师"),MANAGER(2,"金牌导师"),CHIEF(3,"钻石导师");
    JobTitleEnum(int titleCode, String titleName) {
        this.titleCode = titleCode;
        this.titleName = titleName;
    }
    private int titleCode;
    private String titleName;

    public static String getTitle(int code) {
        for (JobTitleEnum titleEnum : JobTitleEnum.values()) {
            if (titleEnum.titleCode == code) {
                return titleEnum.titleName;
            }
        }
        return INDIVIDUAL.name();
    }

    public int getTitleCode() {
        return titleCode;
    }
}
