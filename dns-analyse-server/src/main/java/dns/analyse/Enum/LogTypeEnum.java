package dns.analyse.Enum;

public enum LogTypeEnum {
    ERROR(0,"error"),
    WARN(1, "warn"),
    INFO(2, "info");
    private int code;
    private String show;

    LogTypeEnum(int code, String show) {
        this.code = code;
        this.show = show;
    }

    public int getCode() {
        return this.code;
    }

    public String getValue() {
        return this.show;
    }

    public static LogTypeEnum getFromCode(Integer code) {
        for (LogTypeEnum statusEnum : LogTypeEnum.values()) {
            if (code == statusEnum.getCode()) {
                return statusEnum;
            }
        }
        return null;
    }

}
