package cn.corey.demo.day2;

public enum CodeType {
    /**
     * 文本
     */
    TEXT((byte) 1),
    /**
     * 错误
     */
    ERROR((byte) 2),

    /**
     * 命令
     */
    CODE((byte) 3),
    ;


    byte value;

    CodeType(byte value) {
        this.value = value;
    }
}
