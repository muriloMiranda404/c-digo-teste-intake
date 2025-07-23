package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.Intake;

public class IntakeSubsystem extends SubsystemBase{

    public static IntakeSubsystem subsystem;

    public static IntakeSubsystem getInstance(){
        if(subsystem == null){
            return new IntakeSubsystem();
        }
        return subsystem;
    }
    
   private static SparkMax intake;
   private static SparkMax coral;

   private static DutyCycleEncoder encoder;

   private static PIDController controller;

   private static DigitalInput coralSwicth;

    private IntakeSubsystem(){

        intake = new SparkMax(Intake.INTAKE_MOTOR, SparkMax.MotorType.kBrushless);
        coral = new SparkMax(Intake.CORAL_MOTOR, SparkMax.MotorType.kBrushless);

        encoder = new DutyCycleEncoder(Intake.INTAKE_ENCODER);

        controller = Intake.PID_INTAKER;

        coralSwicth = new DigitalInput(Intake.CORAL_SWITCH);
    }

    public void setSpeed(double speed){
        coral.set(speed);

        if(coralSwicth.get()){
            coral.set(0);
            System.out.println("fim de curso acionado");
        }
    }

    public double getDistance(){
        return encoder.get() * 360.0;
    }

    public double calculateOutput(double medido, double setpoint){
       double output = controller.calculate(medido, setpoint);

       return output;
    }

    public void setPosition(double saida){
        intake.set(saida);
    }
    
    public boolean atSetpoint(){
        return controller.atSetpoint();
    }

    public void stopMotor(){
        intake.set(0.0);
    }

    public DutyCycleEncoder getEncoder(){
        return encoder;
    }

    public PIDController getController(){
        return controller;
    }

    public DigitalInput getCoralSwicth(){
        return coralSwicth;
    }
    @Override
    public void periodic(){
        SmartDashboard.putBoolean(" coral switch", coralSwicth.get());
        SmartDashboard.putNumber("angulação", getDistance());
    }
}
