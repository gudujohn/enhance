package org.enhance.mybatis.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 上层基础模型
 * 
 * @author JiangGengchao
 * @date 2018年2月28日
 */
@Getter
@Setter
@ToString
public abstract class Model implements Serializable {

	private static final long serialVersionUID = 8936166328492738333L;

	public final static String ID = "id";

	/** 主键ID */
	protected long id;

}
