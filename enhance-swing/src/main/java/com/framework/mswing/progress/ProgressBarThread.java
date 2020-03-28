package com.framework.mswing.progress;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.swing.JProgressBar;

/**
 * 带进度条线程
 * 
 * @author JiangGengchao
 * @datae 2017年12月17日
 */
public abstract class ProgressBarThread extends Thread {

	private JProgressBar progressBar;
	private List<ProgressBarCallBack> progressBarCallBacks;
	private boolean result;

	public ProgressBarThread(JProgressBar bar, List<ProgressBarCallBack> progressBarCallBacks) {
		progressBar = bar;
		this.progressBarCallBacks = progressBarCallBacks;
	}

	@Override
	public void run() {
		for (final ProgressBarCallBack progressBarCallBack : progressBarCallBacks) {
			final CountDownLatch end = new CountDownLatch(1);
			try {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							result = progressBarCallBack.execute();
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if (result) {
								end.countDown();
							}
						}
					}
				}).start();
				for (; progressBarCallBack.getNow() <= progressBarCallBack.end; progressBarCallBack.setNow()) {
					// TODO 判断条件
					progressBar.setValue(progressBarCallBack.getNow());
					Thread.sleep(progressBarCallBack.delay);
				}
				end.await();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (progressBar.getValue() == 100)
					progressEnd();
			}
		}
	}

	/**
	 * 进度条加载完成事件
	 */
	public abstract void progressEnd();

}