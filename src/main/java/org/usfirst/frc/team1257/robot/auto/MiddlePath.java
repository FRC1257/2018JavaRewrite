package org.usfirst.frc.team1257.robot.auto;

import org.usfirst.frc.team1257.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1257.robot.subsystems.Elevator;
import org.usfirst.frc.team1257.robot.subsystems.Intake;
import org.usfirst.frc.team1257.robot.subsystems.Linkage;
import org.usfirst.frc.team1257.util.EnhancedDashboard;

// See SideSwitch for more information.
// It's pretty much the same thing, just driving and turning different distances.

public class MiddlePath {
	public static void run(char switchPosition, DriveTrain driveTrain, Elevator elevator, Intake intake, Linkage linkage) {
		EnhancedDashboard.putString("Auto Path", "Middle Path to Switch " + switchPosition);
		
		double angle = switchPosition == 'L' ? -90 : 90;
		driveTrain.driveDistance(48);
		
		driveTrain.turnAngle(angle);
		driveTrain.driveDistance(52);
		
		driveTrain.turnAngle(-angle);
		//Purposely overshoot to hit switch wall and timeout
		driveTrain.driveDistance(64, 2.75);
		
		intake.ejectCube();
		
		EnhancedDashboard.putString("Auto Path", "Finished Middle Path");
	}
}
