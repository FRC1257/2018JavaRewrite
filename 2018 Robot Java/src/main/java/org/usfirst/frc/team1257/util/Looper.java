package org.usfirst.frc.team1257.util;

import java.util.Vector;

import edu.wpi.first.wpilibj.Notifier;

// Credit to 5895 for Looper Code

public class Looper {
	
	private final Notifier notifier;
	private final Vector<Runnable> methods;
	private final double period; //Period is in sec
	
	// Period is in ms
	public Looper(double period) {
		notifier = new Notifier(this::update);
		methods = new Vector<Runnable>();
		this.period = period / 1000.0;
	}
	
	public synchronized void add(Runnable method) {
		methods.add(method);
	}
	
	public synchronized void start() {
		notifier.startPeriodic(period);
	}
	
	public synchronized void stop() {
		notifier.stop();
	}
	
	private synchronized void update() {
		for(Runnable method : methods) {
			method.run();
		}
	}
}
