package com.enhance.swing.dialog.thread;

import java.lang.reflect.InvocationTargetException;

public class JobThread extends Thread {
	private IJob job;
	private IJobMonitor monitor;
	private static boolean debug = true;

	class DummyMonitor extends AbstractJobMonitor {
		@Override
		public void begin(String taskname) {
		}

		@Override
		public void setLabel(String label) {
		}

		@Override
		public void setIndeterminate(boolean indeterminate) {
		}

		@Override
		public void setProgress(double value) {
		}

		@Override
		public void done() {
		}

		@Override
		public void dispose() {
		}

		@Override
		public void setCanceled(boolean cancel) {
		}

		@Override
		public boolean isCanceled() {
			return false;
		}
	}

	public JobThread(IJob job, IJobMonitor monitor) {
		setName("Thread " + job.getName());
		this.job = job;
		this.monitor = monitor == null ? new DummyMonitor() : monitor;

		if (debug) {
			monitor.addJobListener(new AbstractJobListener() {
				@Override
				public void beforeJob() {
				}

				@Override
				public void afterJob() {

				}
			});
		}
	}

	@Override
	public void run() {
		try {
			monitor.fireJobStart();

			job.run(monitor);

			monitor.fireJobCompeleted();
		} catch (InvocationTargetException e) {
			monitor.fireJobErrorHappened((Exception) e.getTargetException());
		} catch (InterruptedException e) {
			monitor.fireJobCanceled();
		} finally {
			monitor.dispose();
		}
	}
}
