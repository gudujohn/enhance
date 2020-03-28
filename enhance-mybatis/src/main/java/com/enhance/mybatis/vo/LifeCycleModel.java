package com.enhance.mybatis.vo;

import java.util.Date;

public abstract class LifeCycleModel extends Model {

    public final static String CREATEDATE = "createDate";
    public final static String CREATOR = "creator";
    public final static String UPDATEDATE = "updateDate";
    public final static String UPDATER = "updater";


    /** 创建时间 */
    protected Date createDate;
    /** 新增人 */
    protected String creator;
    /** 更新时间 */
    protected Date updateDate;
    /** 修改人 */
    protected String updater;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
    public Date getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

}