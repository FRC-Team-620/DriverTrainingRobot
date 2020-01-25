/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * This is a demo program showing the use of the DifferentialDrive class.
 * Runs the motors with arcade steering.
 */
public class Robot extends TimedRobot {
  private final Spark leftMotorFront = new Spark(1);
  private final Spark rightMotorFront = new Spark(0);
  private final Spark leftMotorBack = new Spark(3);
  private final Spark rightMotorBack = new Spark(2);

  private final Spark nicksMotor = new Spark(4);

  private final SpeedControllerGroup left = new SpeedControllerGroup(leftMotorFront, leftMotorBack);
  private final SpeedControllerGroup right = new SpeedControllerGroup(rightMotorFront, rightMotorBack);


  private final DifferentialDrive robotDrive = new DifferentialDrive(left, right);
  private final XboxController stick = new XboxController(0);

  @Override
  public void robotInit() 
  {
    super.robotInit();
    robotDrive.setDeadband(.1);
  }

  @Override
  public void teleopPeriodic() 
  {
    var speed = stick.getY(Hand.kLeft);
    var rotation = -1 * stick.getX(Hand.kLeft);

    // speed = Math.pow(speed, 3);
    // rotation = Math.pow(rotation, 3);

     if(stick.getBumper(Hand.kRight)) 
     {
       speed *= 0.65/*Math.pow(speed, 3)*/;
       rotation *= 0.65/*Math.pow(rotation, 3)*/;
     }

    robotDrive.curvatureDrive(speed, rotation, true);
    if(stick.getTriggerAxis(Hand.kLeft) > stick.getTriggerAxis(Hand.kRight))
    {
      nicksMotor.setSpeed(stick.getTriggerAxis(Hand.kLeft));
    }
    else  
    {
      nicksMotor.setSpeed(-1 * stick.getTriggerAxis(Hand.kRight));
    }
  }
}