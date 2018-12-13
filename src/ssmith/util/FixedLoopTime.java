package ssmith.util;

public class FixedLoopTime {

	private long duration;
	private long startTime = -1;

	public FixedLoopTime(long _duration) {
		this.duration = _duration;
	}


	public void start() {
		startTime = System.currentTimeMillis();
	}


	/**
	 * 
	 * @return Returns whether the process had time to wait.  False means things are running slowly.
	 */
	public boolean waitForFinish() {
		if (startTime < 0) {
			throw new RuntimeException("Start time not set");
		}
		
		long now = System.currentTimeMillis();
		long diff = duration - (now - startTime); 
		if (diff > 0) {
			try {
				Thread.sleep(diff);
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

}
