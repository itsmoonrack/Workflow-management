package alma.common.models;


import java.util.logging.Logger;

import alma.common.services.AsyncReceiver;

public class StatefulBean implements Runnable {
	
	protected AsyncReceiver receiver = null;
	protected Logger logger;
	private boolean run = true;
	private Thread thread = null;
	
	public StatefulBean() {
		thread = new Thread(this);
		logger = Logger.getLogger(this.getClass().toString());
	}
	
	public void run() {
		receiver.start();
		while(run) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		receiver.close();
	}
	
	public void start() {
		run = true;
		thread.start();
	}
	
	public void stop() {
		run = false;
	}

}
