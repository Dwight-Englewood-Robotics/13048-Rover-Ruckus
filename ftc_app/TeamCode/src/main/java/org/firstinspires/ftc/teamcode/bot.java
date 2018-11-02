package org.firstinspires.ftc.teamcode;

/**
 * Created by joonsoolee on 9/21/18.
 */
    import android.text.StaticLayout;

    import com.qualcomm.hardware.bosch.BNO055IMU;
        import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
    import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
    import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
        import com.qualcomm.robotcore.util.Range;
        import com.qualcomm.robotcore.hardware.*;
        import org.firstinspires.ftc.robotcore.external.Telemetry;
        import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
        import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
        import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
        import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
        import java.util.*;
    import org.firstinspires.ftc.teamcode.MovementEnum;


public class bot {

    //TODO add vex motor as a Servo for extension
    // TODO change dump mechanism to a vex motor? 
    static DcMotor BL, BR, FL, FR, lift, intake, hook, hinge, left, right, extend ;
    Servo dump, claw;
    HardwareMap map;
    Telemetry tele;
    BNO055IMU.Parameters parameters;
    Orientation angles;
    Double powerModifier = 0.02;
    ModernRoboticsI2cColorSensor colorSensor;
    //Double turnSpeed = 0.5;
    //Integer angle = -45;

    //Gyroscope gyro;
    //Static Gyro QLEFTTURN, QRIGHTTURN;
    //boolean isStopRequested;
    //int sleep;
    //boolean idle;

    public bot() {}

    public void init(HardwareMap map, Telemetry tele, boolean auton){
        this.map = map;
        this.tele = tele;

        left = this.map.get(DcMotor.class, "left");
        right = this.map.get(DcMotor.class, "right");
      //  BL = this.map.get(DcMotor.class, "BL");
      //  BR = this.map.get(DcMotor.class, "BR");
      //  FL = this.map.get(DcMotor.class, "FL");
      //  FR = this.map.get(DcMotor.class, "FR");
        lift = this.map.get(DcMotor.class, "lift");
        hinge = this.map.get(DcMotor.class, "hinge");
        intake = this.map.get(DcMotor.class, "intake");
        hook = this.map.get(DcMotor.class, "hook");
        extend = this.map.get(DcMotor.class, "extend");

        dump = this.map.get(Servo.class,"dump");
        claw = this.map.get(Servo.class, "claw" );

        left.setDirection(DcMotorSimple.Direction.FORWARD);
        right.setDirection(DcMotorSimple.Direction.REVERSE);
       // BR.setDirection(DcMotorSimple.Direction.FORWARD);
       // BL.setDirection(DcMotorSimple.Direction.FORWARD);
       // FL.setDirection(DcMotorSimple.Direction.FORWARD);
       // FR.setDirection(DcMotorSimple.Direction.REVERSE);
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        hinge.setDirection(DcMotorSimple.Direction.FORWARD);
        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        hook.setDirection(DcMotorSimple.Direction.FORWARD);
        extend.setDirection(DcMotorSimple.Direction.REVERSE);

        this.changeRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);  //TODO: Change to Run With Encoders Later
        this.setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //hinge.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //hook.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

       left.setPower(0);
       right.setPower(0);
    }
    //public void gyroTurn(double turnSpeed, int angle) {
       // QLEFTTURN.setPower(turnSpeed);
       // QLEFTTURN.setAngle(angle);
      //  QRIGHTTURN.setPower(turnSpeed);
      //  QRIGHTTURN.setAngle(angle);
 //   }

    public void changeRunMode(DcMotor.RunMode runMode){
        left.setMode(runMode);
        right.setMode(runMode);
        lift.setMode(runMode);
        intake.setMode(runMode);
    }

    public void setRunMode(DcMotor.RunMode encoderRunMode) {
        left.setMode(encoderRunMode);
        right.setMode(encoderRunMode);
        lift.setMode(encoderRunMode);
        intake.setMode(encoderRunMode);
    }

    public void drive(double in) {
        BL.setPower(in);
        BR.setPower(in);
        FR.setPower(in);
        FL.setPower(in);
    }
    public void tankDriveNoStrafe (double leftStick, double rightStick){
        left.setPower(leftStick);
        right.setPower(rightStick);

    }
    public void tankDrive(double leftStick, double rightStick, double leftTrigger, double rightTrigger, boolean invert, boolean brake) {
        double i = invert ? -0.75:0.75;
        if (leftTrigger > .3) {
            drive(MovementEnum.LEFTSTRAFE, leftTrigger * i);
            return;
        }
        if (rightTrigger > .3) {
            drive(MovementEnum.RIGHTSTRAFE, rightTrigger *i);
            return;
        }
        leftStick *= i;
        rightStick *= i;

        FL.setPower(leftStick);
        FR.setPower(rightStick);
        BL.setPower(-leftStick);
        BR.setPower(-rightStick);
    }

    public void setPower(double power){
        FL.setPower(power);
        BL.setPower(power);
        FR.setPower(power);
        BR.setPower(power);
    }

 /**   public void twoDrive(MovementEnum movement, double power){
        switch(movement){
            case FORWARD:
                left.setPower(power);
                right.setPower(power);
                break;

            case BACKWARD:
                left.setPower(-power);
                right.setPower(-power);
                break;

            case LEFTTURN:
                left.setPower(-power);
                right.setPower(power);
                break;

            case RIGHTTURN:
                left.setPower(power);
                right.setPower(-power);
                break;

            case STOP:
                left.setPower(0);
                right.setPower(0);
                break;
        }
    }

  **/

    //TODO fix the the driver values and restrict the motor values
    public void drive(MovementEnum movement, double power) {
        switch (movement) {
            case FORWARD:
                FL.setPower(power);
                FR.setPower(power);
                BL.setPower(power);
                BR.setPower(power);
                break;

            case BACKWARD:
                left.setPower(-power);
                right.setPower(-power);
                BL.setPower(-power);
                BR.setPower(-power);
                break;

            case LEFTSTRAFE:
                FL.setPower(power);
                FR.setPower(-power);
                BL.setPower(-power);
                BR.setPower(power);
                break;

            case RIGHTSTRAFE:
                FL.setPower(-power);
                FR.setPower(power);
                BL.setPower(power);
                BR.setPower(-power);
                break;

            case LEFTTURN:
                FL.setPower(-power);
                FR.setPower(power);
                BL.setPower(-power);
                BR.setPower(power);
                break;

            case RIGHTTURN:
                FL.setPower(power);
                FR.setPower(-power);
                BL.setPower(power);
                BR.setPower(-power);
                break;

            case STOP:
                FL.setPower(0);
                FR.setPower(0);
                BL.setPower(0);
                BR.setPower(0);
                break;
        }
    }

    public void turn(double in){
        BL.setPower(-in);
        BR.setPower(in);
        FR.setPower(-in);
        FL.setPower(in);
    }
}
