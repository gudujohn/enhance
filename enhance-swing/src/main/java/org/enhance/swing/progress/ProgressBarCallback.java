package org.enhance.swing.progress;

public abstract class ProgressBarCallback {
	public String progressName;
	public int start;
	public int end;
	/**
	 * 毫秒
	 */
	public int delay;
	public int increment;

	public ProgressBarCallback(String name, int start, int end, int increment, int delay) {
		this.progressName = name;
		this.start = start;
		this.end = end;
		this.increment = increment;
		this.delay = delay;
	}

	public abstract boolean execute() throws Exception;
}