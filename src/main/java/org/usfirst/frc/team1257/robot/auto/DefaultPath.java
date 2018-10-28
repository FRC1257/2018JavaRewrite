package org.usfirst.frc.team1257.robot.auto;

import org.usfirst.frc.team1257.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1257.robot.subsystems.Elevator;
import org.usfirst.frc.team1257.robot.subsystems.Intake;
import org.usfirst.frc.team1257.robot.subsystems.Linkage;
import org.usfirst.frc.team1257.util.EnhancedDashboard;

public class DefaultPath {
	public static void run(char startPosition, char switchPosition, char scalePosition, 
			DriveTrain driveTrain, Elevator elevator, Intake intake, Linkage linkage) {
		EnhancedDashboard.putString("Auto Path", "Default Path from " + startPosition + 
				" to Switch " + switchPosition + " or Scale " + scalePosition);
				
		if(startPosition == switchPosition) 
			SideSwitch.run(startPosition, switchPosition, driveTrain, elevator, intake, linkage);
		else 
			Scale.run(startPosition, scalePosition, driveTrain, elevator, intake, linkage);
		
		EnhancedDashboard.putString("Auto Path", "Finished Default Path");
	}
}
