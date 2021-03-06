package org.enhance.mybatis.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class LifeCycleModel extends Model {
	private static final long serialVersionUID = -7825474763978277585L;

	public final static String CREATEDATE = "createDate";
	public final static String CREATOR = "creator";
	public final static String UPDATEDATE = "updateDate";
	public final static String UPDATER = "updater";

	/** 创建时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	protected Date createDate;
	/** 新增人 */
	protected String creator;
	/** 更新时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	protected Date updateDate;
	/** 修改人 */
	protected String updater;

}