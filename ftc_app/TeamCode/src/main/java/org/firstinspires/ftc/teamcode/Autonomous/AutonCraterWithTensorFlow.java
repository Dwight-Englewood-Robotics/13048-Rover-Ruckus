package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.Hardware.MovementEnum;
import org.firstinspires.ftc.teamcode.Hardware.PID;
import org.firstinspires.ftc.teamcode.TensorFlowStuff.TensorFlow;
import org.firstinspires.ftc.teamcode.Hardware.bot;

@Autonomous(name="TensorFlow uwuv", group="Autonomous")
public class AutonCraterWithTensorFlow extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DigitalChannel DigChannel;
    bot robot = new bot();
    TensorFlow tensorFlow = new TensorFlow();

    TensorFlow.TFState BigThonk, actualState;

    int auto = 1;
    int center = 150;
    int left = 560;
    int right = 500;

    public void init() {
        robot.init(hardwareMap, telemetry, false);
        tensorFlow.init(hardwareMap, telemetry);
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.BR.setDirection(DcMotorSimple.Direction.REVERSE);
        robot.BL.setDirection(DcMotorSimple.Direction.FORWARD);
        robot.FL.setDirection(DcMotorSimple.Direction.FORWARD);
        robot.FR.setDirection(DcMotorSimple.Direction.REVERSE);
        robot.intake.setDirection(DcMotorSimple.Direction.FORWARD);

        robot.hook.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.claw.setPosition(0.0);

        //hinge.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
        tensorFlow.start();
        BigThonk = tensorFlow.getState();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        switch (auto) {
            case 0:
                // BigThonk = tensorFlow.getState();

                try {
                    Thread.sleep(2000);
                }
                catch (InterruptedException e){
                    telemetry.addLine("Sleep Failed");
                    telemetry.update();
                }
                auto++;
                break;

            case 1:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                robot.claw.setPosition(0.0);
                auto++;
                break;

            case 2:
                robot.hook.setTargetPosition(50000);
                robot.hook.setPower(0.5);
                robot.hook.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                BigThonk = (BigThonk != TensorFlow.TFState.NOTVISIBLE) ? BigThonk : tensorFlow.getState();

                if(!robot.hookLimit.getState()){
                    //  BigThonk = (BigThonk != TensorFlow.TFState.NOTVISIBLE) ? BigThonk : tensorFlow.getState();
                    robot.hook.setPower(0);
                    telemetry.update();
                    auto++;
                }
                break;

            case 3:
                if(Math.abs(0- robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) > 3) {
                    robot.adjustHeading(0);
                }
                else if(Math.abs(0 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) < 3) {
                    robot.drive(MovementEnum.STOP, 0);
                    auto++;
                }
                break;

            case 4:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 5:
                robot.autonDriveUltimate(MovementEnum.BACKWARD, 140, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 6:
                BigThonk = tensorFlow.getState();
                if(BigThonk != TensorFlow.TFState.NOTVISIBLE){auto++;}
                break;

            case 7:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 8:
                robot.autonDriveUltimate(MovementEnum.RIGHTSTRAFE, 280, 0.2);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 9:
                if(Math.abs(-80- robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) > 3) {
                    robot.adjustHeading(-80);
                }
                else if(Math.abs(-80 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) < 3) {
                    robot.drive(MovementEnum.STOP, 0);
                    auto++;
                }
                break;

            case 10:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 11:
                robot.autonDriveUltimate(MovementEnum.FORWARD, 280, 0.6);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 12:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                if(BigThonk == TensorFlow.TFState.CENTER){
                    auto++;
                }else if(BigThonk == TensorFlow.TFState.LEFT){
                    auto = 100;
                }else if(BigThonk == TensorFlow.TFState.RIGHT){
                    auto = 1000;
                }
                break;

                /*
           CASE FOR CENTER
           */
            case 13:
                robot.autonDriveUltimate(MovementEnum.LEFTSTRAFE, center, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

                /*
           CASE FOR LEFT
           */
            case 100:
                robot.autonDriveUltimate(MovementEnum.LEFTSTRAFE, left, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto = 14;
                }
                break;

                /*
           CASE FOR RIGHT
           */
            case 1000:
                robot.autonDriveUltimate(MovementEnum.RIGHTSTRAFE, right, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto = 14;
                }
                break;

            case 14:
                tensorFlow.stop();
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 15:
                robot.autonDriveUltimate(MovementEnum.FORWARD, 275, 0.4);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 16:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                robot.intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 17:
                robot.autonIntake(-560, 1);
                if (Math.abs(robot.intake.getCurrentPosition()) >= Math.abs(robot.intake.getTargetPosition())){
                    auto++;
                }
                break;

            case 18:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                robot.intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 19:
                robot.autonDriveUltimate(MovementEnum.BACKWARD, 400, 0.4);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 20:
                if(Math.abs(-170- robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) > 3) {
                    robot.adjustHeading(-170);
                }
                else if(Math.abs(-170 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) < 3) {
                    // robot.tankDrive(0, 0, 0, 0, false, false);
                    robot.drive(MovementEnum.STOP, 0);
                    auto++;
                }
                break;

            case 21:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 22:
                robot.autonDriveUltimate(MovementEnum.BACKWARD, 1200, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 23:
                if(Math.abs(-135 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) > 3) {
                    robot.adjustHeading(-135);
                }
                else if(Math.abs(-135 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) < 3) {
                    // robot.tankDrive(0, 0, 0, 0, false, false);
                    robot.drive(MovementEnum.STOP, 0);
                    auto++;
                }
                break;

            case 24:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 25:
                robot.autonDriveUltimate(MovementEnum.LEFTSTRAFE, 200, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 26:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 27:
                robot.autonDriveUltimate(MovementEnum.BACKWARD, 1000, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 28:
                robot.claw.setPosition(0.7);
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                if(robot.claw.getPosition() >= 0.7) {

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        telemetry.addLine("Sleep Failed");
                        telemetry.update();
                    }
                    auto++;
                }
                break;

            case 29:
                if(Math.abs(-45 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) > 3) {
                    robot.adjustHeading(-45);
                }
                else if(Math.abs(-45 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) < 3) {
                    // robot.tankDrive(0, 0, 0, 0, false, false);
                    robot.drive(MovementEnum.STOP, 0);
                    auto++;
                }
                break;

            case 30:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 31:
                robot.autonDriveUltimate(MovementEnum.FORWARD, 200, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 32:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 33:
                robot.autonDriveUltimate(MovementEnum.RIGHTSTRAFE, 300, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 34:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 35:
                robot.autonDriveUltimate(MovementEnum.FORWARD, 2000, 1);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())) {
                    telemetry.addLine("End");
                }
                break;
        }
        telemetry.addData("Position", tensorFlow.getState());
        telemetry.addData("Stored Value", BigThonk);
        telemetry.addData("Case Number: ", auto);
        telemetry.update();
    }
}