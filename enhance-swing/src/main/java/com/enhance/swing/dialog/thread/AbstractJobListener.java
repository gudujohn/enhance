package com.enhance.swing.dialog.thread;

public class AbstractJobListener implements IJobListener {
	public AbstractJobListener() {
	}

	@Override
	public void beforeJob() {
	}

	@Override
	public void afterJob() {
	}

	@Override
	public void jobErrorHappened(Exception ex) {
	}

	@Override
	public void jobCanceled() {
	}
}
