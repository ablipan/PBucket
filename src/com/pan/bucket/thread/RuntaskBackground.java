package com.pan.bucket.thread;


public abstract class RuntaskBackground<U, R> implements Runnable, CancelableTask {

	public Object[] objs;
	private boolean isCanceled;

	protected RuntaskBackground(Object... objs) {
		this.objs = objs;
	}

	@Override
	public void run() {
		if (isCanceled) {
			return;
		}
		if (isCanceled) {
			return;
		}
		runInBackground();
		if (isCanceled) {
			return;
		}
	}

	public abstract R runInBackground();

	public void cancel() {
		if (!isCanceled) {
			isCanceled = true;
		}
	}

	@Override
	public boolean cancel(boolean isCanceled) {
		cancel();
		return isCanceled;
	}

	public boolean isCanceled() {
		return isCanceled;
	}

}
