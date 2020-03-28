package com.framework.swing.progress;

/**
 * 线程回调处理器
 * 
 * @author JiangGengchao
 * @datae 2017年12月17日
 */
public abstract class ProgressBarCallBack {

	public int start;
	public int end;
	public int delay; // 延迟
	public int increment; // 增量

	private int now;

	public int getNow() {
		return now;
	}

	public void setNow() {
		this.now++;
	}

	public ProgressBarCallBack(int start, int end, int increment, int delay) {
		this.start = start;
		this.end = end;
		this.increment = increment;
		this.delay = delay;
		this.now = start;
	}

	public abstract boolean execute() throws Exception;
}