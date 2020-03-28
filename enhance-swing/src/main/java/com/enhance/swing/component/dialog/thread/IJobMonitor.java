package com.enhance.swing.component.dialog.thread;

public interface IJobMonitor extends IProgressMonitor {
	void addJobListener(IJobListener l);

	void fireJobStart();

	void fireJobErrorHappened(Exception ex);

	void fireJobCompeleted();

	void fireJobCanceled();
}
