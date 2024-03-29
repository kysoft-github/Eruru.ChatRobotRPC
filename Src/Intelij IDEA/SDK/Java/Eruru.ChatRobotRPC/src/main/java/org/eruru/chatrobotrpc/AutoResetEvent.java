package org.eruru.chatrobotrpc;

import java.io.Closeable;
import java.io.IOException;

class AutoResetEvent implements Closeable {

	private final Object monitor = new Object ();
	private volatile boolean open;

	private long millisecondsTimeout = 60 * 1000;

	public long getMillisecondsTimeout () {
		return millisecondsTimeout;
	}

	public void setMillisecondsTimeout (long millisecondsTimeout) {
		this.millisecondsTimeout = millisecondsTimeout;
	}

	public AutoResetEvent (boolean initialState) {
		this.open = initialState;
	}

	public void waitOne () throws InterruptedException {
		synchronized (monitor) {
			while (!open) {
				monitor.wait (millisecondsTimeout);
			}
			open = false;
		}
	}

	public void set () {
		synchronized (monitor) {
			open = true;
			monitor.notify ();
		}
	}

	public void reset () {
		open = false;
	}

	@Override
	public void close () throws IOException {

	}

}