package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeSpeed extends Command{
    
    public IntakeSubsystem intake = IntakeSubsystem.getInstance();
    public double speed;
    public DigitalInput coralSwicth = intake.getCoralSwicth();

    public IntakeSpeed(IntakeSubsystem intake, double speed){
        this.intake = intake;
        this.speed = speed;
        addRequirements(intake);
    }

    @Override
    public void initialize(){
        System.out.println("iniciando");
    }

    @Override
    public void execute(){
        try{
            intake.setSpeed(speed);
            System.out.println("valocidade: " + speed);

            if(coralSwicth.get()){
                speed = 0.0;
            }
        } catch(Exception e){
            System.out.println("erro ao executar velocidade");
            intake.setSpeed(0.0);
        }
    }

    @Override
    public boolean isFinished(){
        return false;
    }

    @Override
    public void end(boolean interrupted){
        intake.setSpeed(0.0);
    }
}
