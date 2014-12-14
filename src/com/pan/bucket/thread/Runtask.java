package com.pan.bucket.thread;

import java.lang.ref.WeakReference;

import android.os.Handler;
import android.os.Message;

@SuppressWarnings("rawtypes")
public abstract class Runtask<U, R> implements Runnable, CancelableTask {

	private static class MyHandler extends Handler {
		private WeakReference<Runtask> run;

		public MyHandler(Runtask activity) {
			this.run = new WeakReference<Runtask>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			Runtask _this = run.get();
			if (_this == null)
				return;
			_this.handleMessage(msg);
		}
	}

	private Handler rHandler = new MyHandler(this);
	public Object[] objs;
	private static final int TASK_BEFORE_UI = 0X001;
	private static final int TASK_UPDATE_UI = 0x002;
	private static final int TASK_RESULT = 0x003;
	private boolean isCanceled;

	/**
	 * handleMessage
	 */
	@SuppressWarnings("unchecked")
	private void handleMessage(Message msg) {
		switch (msg.arg1) {
		case TASK_BEFORE_UI:
			onBefore();
			break;
		case TASK_UPDATE_UI:
			onUpdateUiCallBack((U) msg.obj);
			break;
		case TASK_RESULT:
			onResult((R) msg.obj);
			break;
		}

	}
	protected Runtask(Object... objs) {
		this.objs = objs;
	}
	
	// protected Handler rHandler = new Handler() {
	// @SuppressWarnings("unchecked")
	// @Override
	// public void handleMessage(Message msg) {
	// super.handleMessage(msg);
	// switch (msg.arg1) {
	// case TASK_BEFORE_UI:
	// onBefore();
	// break;
	// case TASK_UPDATE_UI:
	// onUpdateUiCallBack((U) msg.obj);
	// break;
	// case TASK_RESULT:
	// onResult((R) msg.obj);
	// break;
	// }
	//
	// }
	// };
	@Override
	public void run() {
		if (isCanceled) {
			return;
		}

		Message msg = rHandler.obtainMessage();
		msg.arg1 = TASK_BEFORE_UI;
		rHandler.sendMessage(msg);

		if (isCanceled) {
			return;
		}
		R result = runInBackground();
		if (isCanceled) {
			return;
		}

		msg = rHandler.obtainMessage();
		msg.arg1 = TASK_RESULT;
		msg.obj = result;
		rHandler.sendMessage(msg);
	}

	public abstract R runInBackground();

	public void updateUi(U obj) {
		Message msg = rHandler.obtainMessage();
		msg.arg1 = TASK_UPDATE_UI;
		msg.obj = obj;
		rHandler.sendMessage(msg);
	}

	public void onBefore() {
	}

	public void onUpdateUiCallBack(U obj) {
	}

	public void onResult(R result) {
	}

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
