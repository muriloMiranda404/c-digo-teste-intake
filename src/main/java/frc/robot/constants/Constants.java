package frc.robot.constants;

import edu.wpi.first.math.controller.PIDController;

public class Constants {
    
    public static final class Swerve{
        public static final double MAX_SPEED = 7.0;
    }

    public static final class Controllers{
        public static final int DRIVER_CONTROLLER = 0;
        public static final int INTAKE_CONTROLLER = 1;
    }

    public static final class Intake{
        public static final int INTAKE_MOTOR = 17;
        public static final int CORAL_MOTOR = 18;

        public static final int INTAKE_ENCODER = 1;
        public static final int CORAL_SWITCH = 0;

        public static final PIDController PID_INTAKER = new PIDController(0.01, 0, 0);
    }

    public static final class Autonomous{
        public static final String AUTO = "new Auto";
    }
}
