package org.usfirst.frc.team1257.robot.auto;

import org.usfirst.frc.team1257.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1257.robot.subsystems.Elevator;
import org.usfirst.frc.team1257.robot.subsystems.Intake;
import org.usfirst.frc.team1257.robot.subsystems.Linkage;
import org.usfirst.frc.team1257.util.EnhancedDashboard;

public class SideSwitch {
	public static void run(char startPosition, char switchPosition, DriveTrain driveTrain, 
			Elevator elevator, Intake intake, Linkage linkage) {
		EnhancedDashboard.putString("Auto Path", "Side Switch from " + startPosition);
		
		double angle = startPosition == 'L' ? 90 : -90;
		
		// Drive to middle of switch
		driveTrain.driveDistance(150);
		
		// If switch is on same side, place cube
		if(startPosition == switchPosition) {
			driveTrain.turnAngle(angle);
			driveTrain.driveDistance(30, 2.75);
			
			intake.ejectCube();
		}
		
		EnhancedDashboard.putString("Auto Path", "Finished Side Switch");
	}
}
