package org.enhance.mybatis.log;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.p6spy.engine.common.P6Util;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

public class P6SpyLogger implements MessageFormattingStrategy {

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

	@Override
	public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
		return !"".equals(sql.trim()) ? this.format.format(new Date()) + " | took " + elapsed + "ms | " + category + " | connection " + connectionId + "\n " + P6Util.singleLine(sql) + ";" : "";
	}
}
