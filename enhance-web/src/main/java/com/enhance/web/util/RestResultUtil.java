package com.enhance.web.util;

import com.enhance.common.util.ExceptionUtil;
import com.enhance.web.enums.RestResultCodeEnum;
import com.enhance.web.vo.RestResult;

public class RestResultUtil {
	private RestResultUtil() {
		throw new IllegalStateException("Constant class");
	}

	public static RestResult success() {
		return success(null, null);
	}

	public static RestResult success(Object data) {
		return success(null, data);
	}

	public static RestResult success(String msg, Object data) {
		return generateRestResult(RestResultCodeEnum.SUCCESS.getCode(), msg, data);
	}

	public static RestResult failure(String msg) {
		return failure(msg, null);
	}

	public static RestResult failure(Throwable throwable) {
		String msg = throwable.getMessage();
		Object data = ExceptionUtil.toFormatDetailErrorMessage(throwable);
		return failure(msg, data);
	}

	public static RestResult failure(String msg, Object data) {
		return generateRestResult(RestResultCodeEnum.FAILURE.getCode(), msg, data);
	}

	private static RestResult generateRestResult(String code, String msg, Object data) {
		RestResult restResult = new RestResult();
		restResult.setCode(code);
		restResult.setData(data);
		restResult.setMsg(msg);
		return restResult;
	}
}
