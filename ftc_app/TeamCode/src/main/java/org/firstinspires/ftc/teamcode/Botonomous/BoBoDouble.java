package org.firstinspires.ftc.teamcode.Botonomous;
import android.graphics.Camera;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.Hardware.MovementEnum;
import org.firstinspires.ftc.teamcode.TensorFlowStuff.TensorFlow;

import org.firstinspires.ftc.teamcode.Hardware.BoBot;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.vuforia.CameraDevice;

@Disabled
@Autonomous(name="[OLD]BoBo Sells Rice", group="Autonomous")
public class BoBoDouble extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DigitalChannel DigChannel;
    BoBot robot = new BoBot();
    TensorFlow tensorFlow = new TensorFlow();

    TensorFlow.TFState BigThonk, actualState;

    int auto = 0;

    int curVal = 0;

    int center = 150;
    int left = 550;
    int right = 350;

    int centerBack = 1150;
    int leftBack = 800;
    int rightBack = 1650;

    int centerForward = 100;
    int leftForward = 50;
    int rightForward = 150;

    public void init() {
        robot.init(hardwareMap, telemetry, false);
        tensorFlow.init(hardwareMap, telemetry);
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.BR.setDirection(DcMotorSimple.Direction.FORWARD);
        robot.BL.setDirection(DcMotorSimple.Direction.REVERSE);
        robot.FL.setDirection(DcMotorSimple.Direction.REVERSE);
        robot.FR.setDirection(DcMotorSimple.Direction.FORWARD);
     //   robot.intake.setDirection(DcMotorSimple.Direction.FORWARD);

        robot.hook.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       // robot.intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    //    robot.claw.setPosition(0.0);

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

        robot.blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BREATH_BLUE);

    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */

    @Override
    public void loop() {
        switch (auto) {
            case 0:
                CameraDevice.getInstance().setFlashTorchMode(true);
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                robot.hook.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                robot.hook.setDirection(DcMotorSimple.Direction.FORWARD);
             //   robot.claw.setPosition(0.0);
                auto++;
                break;

            case 1:
                robot.hook.setTargetPosition(23000);
                robot.hook.setPower(1);
                robot.hook.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                //  BigThonk = (BigThonk != TensorFlow.TFState.NOTVISIBLE) ? BigThonk : tensorFlow.getState();

                if(!robot.hookLimit.getState() || robot.hook.getCurrentPosition() >= robot.hook.getTargetPosition()){
                    robot.blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE_GREEN);
                    robot.hook.setPower(0);
                    telemetry.update();
                    auto++;
                }
                break;

            case 2:
                robot.blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BREATH_BLUE);
                if(Math.abs(0- robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) > 3) {
                    robot.adjustHeading(0);
                }
                else if(Math.abs(0 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) < 3) {
                    robot.drive(MovementEnum.STOP, 0);
                    auto++;
                }
                break;

            case 3:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 4:
                robot.autonDriveUltimate(MovementEnum.BACKWARD, 140, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 5:
                BigThonk = (BigThonk != TensorFlow.TFState.NOTVISIBLE) ? BigThonk : tensorFlow.getState();
                if(BigThonk != TensorFlow.TFState.NOTVISIBLE){auto++;}
                else {
                    BigThonk = TensorFlow.TFState.RIGHT;
                    auto = 6;
                }
                break;

            case 6:
                CameraDevice.getInstance().setFlashTorchMode(false);
                tensorFlow.stop();
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 7:
                robot.autonDriveUltimate(MovementEnum.RIGHTSTRAFE, 280, 0.2);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 8:
                if(Math.abs(-80- robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) > 3) {
                    robot.adjustHeading(-80);
                }
                else if(Math.abs(-80 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) < 3) {
                    this.curVal = -80;
                    robot.drive(MovementEnum.STOP, 0);
                    auto++;
                }
                break;

            case 9:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 10:
                robot.autonDriveUltimate(MovementEnum.FORWARD, 280, 0.6);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 11:
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
           CASE FOR CENTER uno
           */
            case 12:
                robot.blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.STROBE_GOLD);
                robot.autonDriveUltimate(MovementEnum.LEFTSTRAFE, center, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

                /*
           CASE FOR LEFT uno
           */
            case 100:
                robot.blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.STROBE_GOLD);
                robot.autonDriveUltimate(MovementEnum.LEFTSTRAFE, left, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto = 13;
                }
                break;

                /*
           CASE FOR RIGHT uno
           */
            case 1000:
                robot.blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.STROBE_GOLD);
                robot.autonDriveUltimate(MovementEnum.RIGHTSTRAFE, right, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto = 13;
                }
                break;

            case 13:
                robot.blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BREATH_BLUE);
                tensorFlow.stop();
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 14:
                robot.autonDriveUltimate(MovementEnum.FORWARD, 275, 0.4);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 15:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
           //     robot.intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto+=2;
                break;

            case 16:
             //   robot.intake.setPower(1);
             //   robot.intake.setTargetPosition(-280);
             //   robot.intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);

               // if (Math.abs(robot.intake.getCurrentPosition()) >= Math.abs(robot.intake.getTargetPosition())){
                 //   robot.intake.setPower(0);
                   // auto++;
                //}
                break;

            case 17:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
              //  robot.intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 18:
                robot.autonDriveUltimate(MovementEnum.BACKWARD, 375, 0.4);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 19:
                if(Math.abs(this.curVal-95- robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) > 3) {
                    robot.adjustHeading(this.curVal-95);
                }
                else if(Math.abs(this.curVal-95 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) < 3) {
                    this.curVal += -95;
                    robot.drive(MovementEnum.STOP, 0);
                    auto++;
                }
                break;

            case 20:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                if(BigThonk == TensorFlow.TFState.CENTER){
                    auto++;
                }else if(BigThonk == TensorFlow.TFState.LEFT){
                    auto = 200;
                }else if(BigThonk == TensorFlow.TFState.RIGHT){
                    auto = 2000;
                }
                break;

                /*
           CASE FOR CENTER dos
           */
            case 21:
                robot.autonDriveUltimate(MovementEnum.BACKWARD, centerBack, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

                /*
           CASE FOR LEFT dos
           */
            case 200:
                robot.autonDriveUltimate(MovementEnum.BACKWARD, leftBack, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto = 22;
                }
                break;

                /*
           CASE FOR RIGHT dos
           */
            case 2000:
                robot.autonDriveUltimate(MovementEnum.BACKWARD, rightBack, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto = 22;
                }
                break;

            case 22:
                if(Math.abs(this.curVal+40 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) > 3) {
                    robot.adjustHeading(this.curVal+40);
                }
                else if(Math.abs(this.curVal+40 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) < 3) {
                    this.curVal += 40;
                    robot.drive(MovementEnum.STOP, 0);
                    auto++;
                }
                break;

            case 23:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 24:
                robot.autonDriveUltimate(MovementEnum.LEFTSTRAFE, 350, 0.2);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 25:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 26:
                robot.autonDriveUltimate(MovementEnum.BACKWARD, 1000, 0.8);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 27:
           //    robot.claw.setPosition(0.7);
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
/*
                if(robot.claw.getPosition() >= 0.7) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        telemetry.addLine("Sleep Failed");
                        telemetry.update();
                    }
                    auto++;
                }
                break;
*/
            case 28:
                robot.blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
                if(BigThonk == TensorFlow.TFState.CENTER){
                    auto++;
                }else if(BigThonk == TensorFlow.TFState.LEFT){
                    auto = 300;
                }else if(BigThonk == TensorFlow.TFState.RIGHT){
                    auto = 3000;
                }
                break;

            //negative is clockwise
            //positive is counterclockwise

               /*
           CASE FOR CENTER tres
           */
            case 29:
                robot.blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BREATH_BLUE);
                if(Math.abs(this.curVal+110 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) > 3) {
                    robot.adjustHeading(this.curVal+110);
                }
                else if(Math.abs(this.curVal+110 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) < 3) {
                    this.curVal += 110;
                    robot.drive(MovementEnum.STOP, 0);
                    auto++;
                }
                break;

            case 30:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 31:
                robot.autonDriveUltimate(MovementEnum.BACKWARD, 1250, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 32:
                if(Math.abs(this.curVal-55 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) > 3) {
                    robot.adjustHeading(this.curVal-55);
                }
                else if(Math.abs(this.curVal-55 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) < 3) {
                    this.curVal += -55;
                    robot.drive(MovementEnum.STOP, 0);
                    auto++;
                }
                break;

            case 33:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 34:
                robot.autonDriveUltimate(MovementEnum.FORWARD, 1500, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 35:
                if(Math.abs(this.curVal-20 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) > 3) {
                    robot.adjustHeading(this.curVal-20);
                }
                else if(Math.abs(this.curVal-20 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) < 3) {
                    this.curVal += -20;
                    robot.drive(MovementEnum.STOP, 0);
                    auto++;
                }
                break;

            case 36:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 37:
                robot.autonDriveUltimate(MovementEnum.FORWARD, 500, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto = 10000;
                }
                break;

                /*
           CASE FOR LEFT tres
           */
            case 300:
                robot.blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BREATH_BLUE);
                if(Math.abs(this.curVal+85- robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) > 3) {
                    robot.adjustHeading(this.curVal+85);
                }
                else if(Math.abs(this.curVal+85 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) < 3) {
                    this.curVal += 85;
                    robot.drive(MovementEnum.STOP, 0);
                    auto++;
                }
                break;

            case 301:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 302:
                robot.autonDriveUltimate(MovementEnum.LEFTSTRAFE, 40, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 303:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 304:
                robot.autonDriveUltimate(MovementEnum.BACKWARD, 1500, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 305:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 306:
                robot.autonDriveUltimate(MovementEnum.LEFTSTRAFE, 500, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 307:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 308:
                robot.autonDriveUltimate(MovementEnum.BACKWARD, 700, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto = 10000;
                }
                break;

                /*
           CASE FOR RIGHT tres
           */
            case 3000:
                robot.blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BREATH_BLUE);
                robot.autonDriveUltimate(MovementEnum.RIGHTSTRAFE, 400, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 3001:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 3002:
                robot.autonDriveUltimate(MovementEnum.FORWARD, 1000, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 3003:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 3004:
                robot.autonDriveUltimate(MovementEnum.LEFTSTRAFE, 500, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 3005:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 3006:
                robot.autonDriveUltimate(MovementEnum.FORWARD, 800, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto = 10000;
                }
                break;

            case 10000:
                robot.blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.RAINBOW_LAVA_PALETTE);
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                break;
        }
        telemetry.addData("Big Thonk", BigThonk);
        telemetry.addData("getState", tensorFlow.getState());
        telemetry.addData("Case Number:", auto);
        telemetry.addData("CurVal:", curVal);
        telemetry.update();
    }
}