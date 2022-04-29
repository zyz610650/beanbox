package com.beanbox.enums;

public enum Isolation {

    /**
     * 默认使用数据库本身的隔离级别
     */
    DEFAULT(0),
    /**
     * 读未提交
     */
    REEAD_UNCOMMITTED(1),
    /**
     * 读已提交
     */
    READ_COMMITTED(2),

    /**
     * 可重复读
     */
    REPEATABLE_READ(4),
    /**
     * 串行化
     */
    SERIALIZABLE(8);


    Isolation(int i) {
        this.value=i;
    }

    private int value;

    public int getValue() {
        return value;
    }


}
