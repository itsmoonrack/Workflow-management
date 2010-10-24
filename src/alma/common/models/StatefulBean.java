package alma.common.models;


import alma.common.services.AsyncReceiver;

public class StatefulBean implements Runnable {
	
	protected AsyncReceiver receiver = null;
	private boolean run = true;
	private Thread thread = null;
	
	public StatefulBean() {
		thread = new Thread(this);
	}
	
	public void run() {
		receiver.start();
		while(run) {
			try {
				Thread.sleep(3000);
				tick(); //Active une fonction toutes les 1sec.
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
	
	protected void tick() {};

}
