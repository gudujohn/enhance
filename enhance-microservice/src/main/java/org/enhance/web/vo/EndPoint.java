package org.enhance.web.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EndPoint implements Serializable {

	private static final long serialVersionUID = -754856987041304559L;

	private String name;
	private String address;
	private int port;
	private String context;

}