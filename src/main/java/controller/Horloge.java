package controller;

import javafx.animation.AnimationTimer;
import modele.ply.IModele;
import modele.ply.ModelTransforming;

public class Horloge extends AnimationTimer {

	protected IModele model;
	protected int framesPassed;
	protected boolean running, blocked;
	protected Timer timer;
	private static final int TIME_TO_SLEEP = 30;
	
	public Horloge(IModele modele) {
		this.model = modele;
		this.framesPassed = 0;
		this.running = false;
		this.timer = new Timer(this);
	}
	
	@Override
	public void handle(long now) {
		if (blocked || model.isNull()) {
			return;
		}
		if (framesPassed == TIME_TO_SLEEP) {
			ModelTransforming.rotateOnYAxis(model, Math.PI/32);
			framesPassed = 0;
		} else {
			++framesPassed;
		}
	}
	
	@Override
	public void start() {
		super.start();
		if(!blocked) {
			running = true;
			timer.stop();
		}
	}
	
	@Override
	public void stop() {
		super.stop();
		running = false;
		timer.start();
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public void fullStop() {
		this.stop();
		this.timer.stop();
	}
	
	public Timer getTimer() {
		return timer;
	}
	
	public boolean isBlocked() {
		return blocked;
	}
	
	public void setBlocked(boolean block) {
		this.blocked = block;
		if (block) {
			this.fullStop();
		} else {
			this.timer.start();
		}
	}
	
}
