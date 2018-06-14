package org.usfirst.frc.team1257.util;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;

public class AngleSensorGroup implements PIDSource
{
	private AHRS m_NavX;
	private ADXRS450_Gyro m_Gyro;
	
	public AngleSensorGroup(SPI.Port navXPort, SPI.Port gyroPort)
	{
		m_NavX = new AHRS(navXPort);
		m_Gyro = new ADXRS450_Gyro(gyroPort);
	}
	
	public void reset()
	{
		m_NavX.zeroYaw();
		m_Gyro.reset();
	}
	
	public double getAngle()
	{
		if(m_NavX.isConnected()) return m_NavX.getYaw();
		else return m_Gyro.getAngle();
	}

	public void setPIDSourceType(PIDSourceType pidSource) 
	{
		
	}

	public PIDSourceType getPIDSourceType() 
	{
		return PIDSourceType.kDisplacement;
	}

	public double pidGet()
	{
		return getAngle();
	}
}
