package frc.robot;

import frc.robot.commands.IntakePosition;
import frc.robot.commands.IntakeSpeed;
import frc.robot.constants.Constants.Autonomous;
import frc.robot.constants.Constants.Controllers;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.SwerveSubsystem;
import java.io.File;
import com.pathplanner.lib.commands.PathPlannerAuto;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {

  public final XboxController DriveController = new XboxController(Controllers.DRIVER_CONTROLLER);
  public final XboxController intakeController = new XboxController(Controllers.INTAKE_CONTROLLER);

  public final SwerveSubsystem swerve = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(), "swerve"));
  public final IntakeSubsystem intake = IntakeSubsystem.getInstance();

  public RobotContainer() {

    swerve.setDefaultCommand(swerve.driveCommand(
      () -> MathUtil.applyDeadband(DriveController.getLeftY(), 0),
      () -> MathUtil.applyDeadband(DriveController.getLeftX(), 0),
      () -> MathUtil.applyDeadband(DriveController.getRightX(), 0)));

    configureBindings();
  }

  private void configureBindings() {

    new JoystickButton(intakeController, 10).whileTrue(new IntakeSpeed(intake, 0.7));
    new JoystickButton(intakeController, 9).whileTrue(new IntakeSpeed(intake, -0.7));
    
    new JoystickButton(intakeController, 1).onTrue(new IntakePosition(intake, 120));
  }


  public Command getAutonomousCommand() {
    return new PathPlannerAuto(Autonomous.AUTO);
  }

  public double setChoose(int choose){

    int inverter = DriverStation.getAlliance().get() == Alliance.Red ? -1 : 1;
    double marcha = 0.7;

    if(DriveController.getRightBumperButton()){
      marcha = 1.0;
    } else if(DriveController.getLeftBumperButton()){
      marcha = 0.2;
    } else {
      marcha = 0.7;
    }

    if(choose == 1) return DriveController.getLeftY() * marcha * inverter;
    else if(choose == 2) return DriveController.getLeftX() * marcha * inverter;
    else if(choose == 3) return DriveController.getRightX() * marcha;

    return choose;
  }
}