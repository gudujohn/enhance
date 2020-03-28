package com.enhance.swing.component.dialog.thread;

import java.awt.*;
import java.text.NumberFormat;

import javax.swing.*;

import com.enhance.swing.component.dialog.ExceptionDialog;

/**
 * 可以设置进度情况和提示信息的进度条
 * 阻塞但是能够取消，注意点击取消后对话框消失
 * 但若用户代码中没有对isCanceled进行判断处理，则执行线程仍然在后台继续
 */
public class BlockProgressMonitor extends AbstractJobMonitor {
	private int autoIncrease;
	private boolean isPercentPrompt;

	private Frame parentFrame;
	private Dialog parentDialog;

	private JobDialog jobDialog;

	private NumberFormat format;

	private boolean isIndeterminate;
	private boolean canceled;
	private boolean over;

	public BlockProgressMonitor(final Frame parentFrame, int autoIncrease, boolean isPercentPrompt) {
		this.parentFrame = parentFrame;
		this.autoIncrease = autoIncrease;
		this.isPercentPrompt = isPercentPrompt;
		addJobListener(new AbstractJobListener() {
			@Override
			public void jobErrorHappened(final Exception ex) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						ExceptionDialog.traceException(parentFrame, "请求数据异常", ex);
						ex.printStackTrace();
					}
				});
			}
		});
	}

	public BlockProgressMonitor(final Dialog parentDialog, int autoIncrease, boolean isPercentPrompt) {
		this.parentDialog = parentDialog;
		this.autoIncrease = autoIncrease;
		this.isPercentPrompt = isPercentPrompt;
		addJobListener(new AbstractJobListener() {
			@Override
			public void jobErrorHappened(final Exception ex) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						ExceptionDialog.traceException(parentDialog, "请求数据异常", ex);
						ex.printStackTrace();
					}
				});
			}
		});
	}

	@Override
	public void begin(String taskName) {
		if (parentFrame != null) {
			jobDialog = new JobDialog(parentFrame, taskName, this);
		} else if (parentDialog != null) {
			jobDialog = new JobDialog(parentDialog, taskName, this);
		}
		if (!isPercentPrompt) {
			jobDialog.getProgressBar().setIndeterminate(true);
		}

		// 默认是模糊进度，即在调用setProgress前，进度条会自动增长。
		setIndeterminate(true);

		new Thread() {
			@Override
			public void run() {
				// 如果对话框还没显示任务就已经结束
				if (!isOver()) {
					jobDialog.setVisible(true);
				}
			}
		}.start();

		// 让对话框有时间秀出来先
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
	}

	public void componentShown() {
		new Thread() {
			@Override
			public void run() {
				while (jobDialog.isVisible()) {
					if (isIndeterminate) {
						// 应该是线程安全的
						int n = jobDialog.getProgressBar().getValue();
						n = n >= jobDialog.getProgressBar().getMaximum() ? 0 : n + autoIncrease;
						jobDialog.getProgressBar().setValue(n);
					}

					// 避免独占cpu
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
					}
				}
			}
		}.start();
	}

	public void setAutoIncrease(int autoIncrease) {
		this.autoIncrease = autoIncrease;
	}

	@Override
	public void setProgress(double value) {
		/* setIndeterminate(false); */
		if (jobDialog != null) {
			/*
			 * jobDialog.getProgressBar().setValue((int) (value *
			 * jobDialog.getProgressBar().getMaximum()));
			 */
			if (isPercentPrompt) {
				if (format == null) {
					format = NumberFormat.getPercentInstance();
					format.setMinimumFractionDigits(1);
					format.setMaximumFractionDigits(1);
				}
				jobDialog.getPercentLabel().setText(format.format(value));
			}
		}
	}

	@Override
	public void done() {
		setProgress(1);
		dispose();
	}

	public synchronized boolean isOver() {
		return over;
	}

	public synchronized void setOver(boolean over) {
		this.over = over;
	}

	@Override
	public void setLabel(String labelContent) {
		if (jobDialog != null) {
			jobDialog.getTextLabel().setText(labelContent);
		}
	}

	@Override
	public void setIndeterminate(boolean indeterminate) {
		this.isIndeterminate = indeterminate;
	}

	@Override
	public boolean isCanceled() {
		return canceled;
	}

	@Override
	public void setCanceled(boolean cancel) {
		this.canceled = cancel;
	}

	@Override
	public void dispose() {
		setOver(true);
		jobDialog.setVisible(false);
		jobDialog.dispose();
	}
}
