package com.enhance.mybatis.criteria;

import java.util.ArrayList;
import java.util.List;

public class Ordering {

	public static final String ASC = "ASC";

	public static final String DESC = "DESC";

	private List<String> orders;

	public Ordering() {
		this.orders = new ArrayList<>();
	}

	public Ordering(String _name, String _type) {
		this.orders = new ArrayList<>();
		this.add(_name, _type);
	}

	public Ordering addAsc(String _name) {
		this.add(_name, ASC);
		return this;
	}

	public Ordering addDesc(String _name) {
		this.add(_name, DESC);
		return this;
	}

	public Ordering add(String _name, String _type) {
		this.orders.add(_name + " " + _type);
		return this;
	}

	public String orderClause() {
		if (null != orders) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < orders.size(); i++) {
				if (i > 0) {
					sb.append(",");
				}
				sb.append(orders.get(i));
			}
			return sb.toString();
		}
		return null;
	}

}
