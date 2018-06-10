package cn.meilituibian.api.common;

public enum JobTitleEnum {
    INDIVIDUAL(-1, "个人"),MEMBER(0,"队员"),LEADER(1,"支队长"),MANAGER(2,"分队长"),CHIEF(3,"大队长");
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
        return null;
    }

    public int getTitleCode() {
        return titleCode;
    }
}
