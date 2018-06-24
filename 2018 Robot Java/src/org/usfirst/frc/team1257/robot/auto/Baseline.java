package org.usfirst.frc.team1257.robot.auto;

import org.usfirst.frc.team1257.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1257.robot.subsystems.Elevator;
import org.usfirst.frc.team1257.robot.subsystems.Intake;
import org.usfirst.frc.team1257.robot.subsystems.Linkage;
import org.usfirst.frc.team1257.util.EnhancedDashboard;

public class Baseline {
	public static void run(DriveTrain driveTrain, Elevator elevator, Intake intake, Linkage linkage)
	{
		EnhancedDashboard.putString("Auto Path", "Baseline");
		driveTrain.driveDistance(150);
		EnhancedDashboard.putString("Auto Path", "Finished Baseline");
	}
}
