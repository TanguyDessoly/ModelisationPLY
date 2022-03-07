package controller;

import javafx.animation.AnimationTimer;

public class Timer extends AnimationTimer {
	
	private static final int SECOND = 60;
	private static final int TIME_TO_WAIT = 10;
	protected int framesPassed, secondsPassed;
	protected Horloge horloge;
	public boolean running;
	
	public Timer(Horloge horloge) {
		this.horloge = horloge;
		this.framesPassed = 0;
		this.secondsPassed = 0;
	}

	@Override
	public void handle(long now) {
		if (framesPassed == SECOND) {
			++secondsPassed;
			framesPassed = 0;
			if (secondsPassed >= TIME_TO_WAIT) {
				horloge.start();
			}
		} else {
			++framesPassed;
		}
	}
	
	@Override
	public void stop() {
		super.stop();
		secondsPassed = 0;
		running = false;
	}
	
	@Override
	public void start() {
		super.start();
		if(!horloge.isBlocked()) running = true;
	}
	
	public boolean isRunning() {
		return running;
	}
	
}
