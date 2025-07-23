// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.IntakePosition;
import frc.robot.commands.IntakeSpeed;
import frc.robot.constants.Constants.Autonomous;
import frc.robot.constants.Constants.Controllers;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.SwerveSubsystem;

import java.io.File;
import java.nio.file.FileSystem;

import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {

  public final CommandXboxController DriveController = new CommandXboxController(Controllers.DRIVER_CONTROLLER);
  public final XboxController intakeController = new XboxController(Controllers.INTAKE_CONTROLLER);

  public static SwerveSubsystem swerve = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(), "swerve"));
  public static final IntakeSubsystem intake = IntakeSubsystem.getInstance();

  public RobotContainer() {

    swerve.setDefaultCommand(swerve.driveCommand(
      () -> MathUtil.applyDeadband(DriveController.getLeftY(), 0.0),
      () -> MathUtil.applyDeadband(DriveController.getLeftX(), 0.,0),
      () -> MathUtil.applyDeadband(DriveController.getRightX(), 0.0)
    ));

    configureBindings();
  }

 
  private void configureBindings() {

    new JoystickButton(intakeController, 10).whileTrue(new IntakeSpeed(intake, 1.0));
    new JoystickButton(intakeController, 9).whileTrue(new IntakeSpeed(intake, -1.0));
  
    new JoystickButton(intakeController, 1).onTrue(new IntakePosition(intake, 231));
    
  }


  public Command getAutonomousCommand() {
    return new PathPlannerAuto(Autonomous.AUTO);
  }
}
