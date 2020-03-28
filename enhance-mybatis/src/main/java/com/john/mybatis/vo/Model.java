package com.john.mybatis.vo;

/** 上层基础模型
 * 
 * @author JiangGengchao
 * @date 2018年2月28日 */
public abstract class Model {

    public final static String ID = "id";

    /** 主键ID */
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
