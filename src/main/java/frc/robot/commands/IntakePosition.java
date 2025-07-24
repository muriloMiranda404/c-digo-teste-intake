package frc.robot.commands;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakePosition extends Command{
    
    private IntakeSubsystem subsystem;
    private double setpoint;
    private DutyCycleEncoder encoder = subsystem.getEncoder();

    double angulo;
    double output;

    public IntakePosition(IntakeSubsystem subsystem, double setpoint){
        this.subsystem = subsystem;
        this.setpoint = setpoint;
        addRequirements(subsystem);
    }

    @Override
    public void initialize(){
        encoder.setDutyCycleRange(0, 360);
    }

    @Override
    public void execute(){
        try{
        angulo = subsystem.getDistance();
        output = subsystem.calculateOutput(angulo, setpoint);

        if(angulo < 55){
            if(output > 0) output = 0.0;

            if(setpoint > 55.0) setpoint = 55.0;
        }

        if(angulo > 230.0){
            if(output > 0.0) output = 0.0;

            if(setpoint > 230.0) setpoint = 230.0;
        }

        subsystem.setPosition(output);

    } catch(Exception e){
        System.out.println("erro ao colocar posição");
        subsystem.stopMotor();
    }
    }

    @Override
    public boolean isFinished(){
        return subsystem.atSetpoint();
    }

    @Override
    public void end(boolean interrupted){
        subsystem.stopMotor();
    }
}
