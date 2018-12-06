package org.usfirst.frc.team1257.util;

import org.usfirst.frc.team1257.robot.Constants;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.RemoteFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

// This is a motor class.
// We used Talons in 2018.

public class EnhancedTalonSRX extends WPI_TalonSRX {
	private int timeoutMs = 10;
	private int pidLoopId = 0;

	double diameter = Constants.WHEEL_DIAMETER;
	double pulsesPerRev = Constants.PULSES_PER_REV;

	public EnhancedTalonSRX(int deviceNumber) {
		super(deviceNumber);
	}

	public void setTimeoutMs(int timeout) {
		timeoutMs = timeout;
	}

	public void setPidLoopId(int pidLoopId) {
		this.pidLoopId = pidLoopId;
	}

	public void setDiameter(double diameter) {
		this.diameter = diameter;
	}

	public void setPulsesPerRev(double pulsesPerRev) {
		this.pulsesPerRev = pulsesPerRev;
	}

	// Current Limiting
	public void enableCurrentLimit(int amps, int timeoutMs) {
		configContinuousCurrentLimit(amps, timeoutMs);
		super.enableCurrentLimit(true);
	}

	public void disableCurrentLimit() {
		super.enableCurrentLimit(false);
	}

	// Encoder Values
	public int getSensorValue() {
		return super.getSelectedSensorPosition(pidLoopId);
	}

	public void setSensorValue(int value) {
		super.setSelectedSensorPosition(value, pidLoopId, timeoutMs);
	}

	public void zeroSensor() {
		setSensorValue(0);
	}

	public int getSensorVelocity() {
		return super.getSelectedSensorVelocity(pidLoopId);
	}

	public double getSensorValueInches() {
		double circumference = diameter * Constants.PI;
		double revolutions = getSensorValue() / pulsesPerRev;
		double distance = revolutions * circumference;

		return distance;
	}

	public double getSensorVelocityInchesPerSecond() {
		double circumference = diameter * Constants.PI;
		double revolutionsPerSecond = (getSensorVelocity() * 10 / pulsesPerRev);
		double distancePerSecond = revolutionsPerSecond * circumference;

		return distancePerSecond;
	}

	public void configFeedbackSensor(FeedbackDevice feedbackDevice, boolean sensorPhase) {
		configSelectedFeedbackSensor(feedbackDevice, pidLoopId, timeoutMs);
		setSensorPhase(sensorPhase);
	}

	public void configFeedbackSensor(RemoteFeedbackDevice feedbackDevice, boolean sensorPhase) {
		configSelectedFeedbackSensor(feedbackDevice, pidLoopId, timeoutMs);
		setSensorPhase(sensorPhase);
	}

	// Limit switch
	public void configForwardLimitSwitchSource(LimitSwitchSource limitSwitchSource,
			LimitSwitchNormal normalOpenOrClose) {
		super.configForwardLimitSwitchSource(limitSwitchSource, normalOpenOrClose, timeoutMs);
	}

	public void configReverseLimitSwitchSource(LimitSwitchSource limitSwitchSource,
			LimitSwitchNormal normalOpenOrClose) {
		super.configReverseLimitSwitchSource(limitSwitchSource, normalOpenOrClose, timeoutMs);
	}

	public void configLimitSwitchSources(LimitSwitchSource forwardLimitSwitchSource,
			LimitSwitchNormal forwardNormalOpenOrClose, LimitSwitchSource reverseLimitSwitchSource,
			LimitSwitchNormal reverseNormalOpenOrClose) {
		configForwardLimitSwitchSource(forwardLimitSwitchSource, forwardNormalOpenOrClose);
		configReverseLimitSwitchSource(reverseLimitSwitchSource, reverseNormalOpenOrClose);
	}

	public void configForwardSoftLimitThresholdInches(double inches) {
		double circumference = diameter * Constants.PI;
		double revolutions = inches / circumference;
		int pulses = (int) (revolutions * pulsesPerRev);

		super.configForwardSoftLimitThreshold(pulses, timeoutMs);
	}

	public void configReverseSoftLimitThresholdInches(double inches) {
		double circumference = diameter * Constants.PI;
		double revolutions = inches / circumference;
		int pulses = (int) (revolutions * pulsesPerRev);

		super.configReverseSoftLimitThreshold(pulses, timeoutMs);
	}

	void configSoftLimitThresholdsInches(double forwardInches, double reverseInches) {
		configForwardSoftLimitThresholdInches(forwardInches);
		configReverseSoftLimitThresholdInches(reverseInches);
	}

	void configForwardSoftLimitEnable(boolean enabled) {
		super.configForwardSoftLimitEnable(enabled, timeoutMs);
	}

	void configReverseSoftLimitEnable(boolean enabled) {
		super.configReverseSoftLimitEnable(enabled, timeoutMs);
	}

	void enableSoftLimits(boolean enabled) {
		configForwardSoftLimitEnable(enabled);
		configReverseSoftLimitEnable(enabled);
	}
}
