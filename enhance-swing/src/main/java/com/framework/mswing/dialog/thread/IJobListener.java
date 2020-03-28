package com.framework.mswing.dialog.thread;

public interface IJobListener {
	void beforeJob();

	void afterJob();

	void jobErrorHappened(Exception ex);

	void jobCanceled();
}
