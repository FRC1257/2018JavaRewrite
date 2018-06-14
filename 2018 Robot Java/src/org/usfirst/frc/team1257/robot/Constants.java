package org.usfirst.frc.team1257.robot;

public class Constants {
	public static class ElectricLayout
	{
		public static final int DRIVE_FRONT_LEFT = 0;
		public static final int DRIVE_FRONT_RIGHT = 1;
		public static final int DRIVE_BACK_LEFT = 2;
		public static final int DRIVE_BACK_RIGHT = 3;
	}
	
	// Auto Constants
	public static final double GAME_DATA_TIMEOUT_S = 1;
	public static final double PID_TIMEOUT_S = 5;
	
	public static final double DRIVE_SPEED_REDUCTION = 5. / 8.;
	
	public static final double ELEVATOR_SPEED_REDUCTION = 1. / 3.;
	public static final int ELEVATOR_CONT_CURRENT_MAX = 60;
	public static final int ELEVATOR_CONT_CURRENT_TIMEOUT_MS = 2000;

	// Talon configuration constants
	public static final int PID_LOOP_ID = 0;
	public static final int TALON_TIMEOUT_MS = 10;

	// Current Limiting Constants
	public static final int FORTY_AMP_FUSE_CONT_MAX = 50; // The continuous max current draw for a 40 amp breaker
	public static final int THIRTY_AMP_FUSE_CONT_MAX = 35; // The continuous max current draw for a 30 amp breaker
	public static final int CONT_CURRENT_TIMEOUT_MS = 500;

	// Encoder Constants
	public static final double PI = 3.1416;
	public static final double WHEEL_DIAMETER = 6;
	public static final double PULSES_PER_REV = 4096;

	// Intake Constants
	public static final double INTAKE_SPEED = 0.80;
}
