package org.usfirst.frc.team1257.util;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class EnhancedDoubleSolenoid extends DoubleSolenoid {

	public EnhancedDoubleSolenoid(int forwardChannel, int reverseChannel) {
		super(forwardChannel, reverseChannel);
	}

	public void set(Value value) {
		if (get() != value)
			super.set(value);
	}
}
