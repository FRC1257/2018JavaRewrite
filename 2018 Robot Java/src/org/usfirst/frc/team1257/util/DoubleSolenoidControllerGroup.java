package org.usfirst.frc.team1257.util;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class DoubleSolenoidControllerGroup {
	
	private DoubleSolenoid[] solenoids;
	
	public DoubleSolenoidControllerGroup(DoubleSolenoid solenoid, DoubleSolenoid... extraSolenoids)
	{
		solenoids = new DoubleSolenoid[solenoids.length + 1];
		solenoids[0] = solenoid;
		
		for(int i = 0; i < solenoids.length; i++)
		{
			solenoids[i + 1] = extraSolenoids[i];
		}
	}
	
	public void set(Value value)
	{
		for(DoubleSolenoid solenoid : solenoids)
		{
			solenoid.set(value);
		}
	}
}
