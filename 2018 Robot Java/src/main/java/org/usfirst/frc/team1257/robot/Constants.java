package org.usfirst.frc.team1257.robot;

public class Constants {

	public static double deadband(double value) {
		return Math.abs(value) < 0.08 ? 0 : value;
	}

	public static class ElectricLayout {
		public static final int DRIVE_FRONT_LEFT = 0;
		public static final int DRIVE_FRONT_RIGHT = 1;
		public static final int DRIVE_BACK_LEFT = 2;
		public static final int DRIVE_BACK_RIGHT = 3;

		public static final int MOTOR_ELEVATOR_RIGHT = 4;
		public static final int MOTOR_ELEVATOR_LEFT = 5;

		public static final int MOTOR_INTAKE_RIGHT = 6;
		public static final int MOTOR_INTAKE_LEFT = 7;

		public static final int MOTOR_LINKAGE = 8;

		public static final int SOLENOID_INTAKE_RIGHT_FORWARD = 0;
		public static final int SOLENOID_INTAKE_RIGHT_REVERSE = 1;

		public static final int SOLENOID_INTAKE_LEFT_FORWARD = 2;
		public static final int SOLENOID_INTAKE_LEFT_REVERSE = 3;
	}

	// Auto Constants
	public static final double GAME_DATA_TIMEOUT_S = 1;
	public static final double PID_TIMEOUT_S = 5;

	public static final double DRIVE_SPEED_REDUCTION = 5. / 8.;
	public static final double ELEVATOR_SPEED_REDUCTION = 1. / 3.;

	public static enum AutoPosition {
		LEFT,
		MIDDLE,
		RIGHT
	}
	
	public static enum AutoObjective {
		SWITCH,
		SCALE,
		BASELINE,
		DEFAULT
	}

	public static class PID {
		public static final double[] DISTANCE_CONSTANTS = { 0.03, 0, 0.06 };
		public static final double[] MAINTAIN_CONSTANTS = { 0.04, 0.0, 0.0 };
		public static final double[] TURN_CONSTANTS = { 0.04, 0, 0.04 };

		public static final double DISTANCE_ABS_TOL = 3.5;
		public static final double DISTANCE_OUTPUT_RANGE = 0.7;

		public static final double MAINTAIN_ABS_TOL = 0.5;
		public static final double MAINTAIN_OUTPUT_RANGE = 1.0;

		public static final double TURN_ABS_TOL = 1;
		public static final double TURN_OUTPUT_RANGE = 0.3;
	}

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
	public static final double INTAKE_EJECT_TIME = 0.2;

	// Elevator Constants
	public static final int ELEVATOR_CONT_CURRENT_MAX = 60;
	public static final int ELEVATOR_CONT_CURRENT_TIMEOUT_MS = 2000;
	public static final double ELEVATOR_MAX_HEIGHT = 80;
}
