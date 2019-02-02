package org.firstinspires.ftc.teamcode.Autonomous;

/*
* Framed as icicles
Numerous and gray
I will do as spiders do
Then watch you float away

As the night is young
The frostbite lingers
Drips like faucets
From my fingers
And everything you gave to me
I tossed it to the wind

And I will float away
And I will float away
And I will float away
And I will float away

Climbed across my back
Cut into the flesh
Burdened by my heart
Turned and hung my head

And I will float away
And I will float away
And I will float away
And I will float awayFramed as icicles
Numerous and gray
I will do as spiders do
Then watch you float away

As the night is young
The frostbite lingers
Drips like faucets
From my fingers
And everything you gave to me
I tossed it to the wind

And I will float away
And I will float away
And I will float away
And I will float away

Climbed across my back
Cut into the flesh
Burdened by my heart
Turned and hung my head

And I will float away
And I will float away
And I will float away
And I will float away
*
*
*
* */
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.Hardware.MovementEnum;
import org.firstinspires.ftc.teamcode.Hardware.bot;
import org.firstinspires.ftc.teamcode.TensorFlowStuff.TensorFlow;

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

        import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

@Autonomous(name="b(owo)b", group="Autonomous")
public class DoubleSampleCrater extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DigitalChannel DigChannel;
    bot robot = new bot();
    TensorFlow tensorFlow = new TensorFlow();

    TensorFlow.TFState BigThonk, actualState;
    //RevBlinkinLedDriver blinkin;

    int auto = 0;

    int center = 150;
    int left = 600;
    int right = 350;

    int centerBack = 1000;
    int leftBack = 800;
    int rightBack = 1750;

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
        robot.intake.setDirection(DcMotorSimple.Direction.FORWARD);
        robot.intake.setDirection(DcMotorSimple.Direction.FORWARD);

        robot.hook.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.claw.setPosition(0.0);

        //   blinkin = hardwareMap.get(RevBlinkinLedDriver.class, "rgbReady");
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

        //   blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        switch (auto) {
            case 0:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                robot.claw.setPosition(0.0);
                auto++;
                break;

            case 1:
                robot.hook.setTargetPosition(23000);
                robot.hook.setPower(1);
                robot.hook.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                BigThonk = (BigThonk != TensorFlow.TFState.NOTVISIBLE) ? BigThonk : tensorFlow.getState();

                if(!robot.hookLimit.getState() || robot.hook.getCurrentPosition() >= robot.hook.getTargetPosition()){
                    robot.hook.setPower(0);
                    telemetry.update();
                    auto++;
                }
                break;

            case 2:
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
                BigThonk = tensorFlow.getState();
                if(BigThonk != TensorFlow.TFState.NOTVISIBLE){auto++;}
                break;

            case 6:
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
           CASE FOR CENTER
           Framed as icicles
Numerous and gray
I will do as spiders do
Then watch you float away

As the night is young
The frostbite lingers
Drips like faucets
From my fingers
And everything you gave to me
I tossed it to the wind

And I will float away
And I will float away
And I will float away
And I will float away

Climbed across my back
Cut into the flesh
Burdened by my heart
Turned and hung my head

And I will float away
And I will float away
And I will float away
And I will float away
           */
            case 12:
                robot.autonDriveUltimate(MovementEnum.LEFTSTRAFE, center, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

                /*
           CASE FOR LEFT
           Framed as icicles
Numerous and gray
I will do as spiders do
Then watch you float away

As the night is young
The frostbite lingers
Drips like faucets
From my fingers
And everything you gave to me
I tossed it to the wind

And I will float away
And I will float away
And I will float away
And I will float away

Climbed across my back
Cut into the flesh
Burdened by my heart
Turned and hung my head

And I will float away
And I will float away
And I will float away
And I will float away
           */
            case 100:
                robot.autonDriveUltimate(MovementEnum.LEFTSTRAFE, left, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto = 13;
                }
                break;

                /*
           CASE FOR RIGHT
           Framed as icicles
Numerous and gray
I will do as spiders do
Then watch you float away

As the night is young
The frostbite lingers
Drips like faucets
From my fingers
And everything you gave to me
I tossed it to the wind

And I will float away
And I will float away
And I will float away
And I will float away

Climbed across my back
Cut into the flesh
Burdened by my heart
Turned and hung my head

And I will float away
And I will float away
And I will float away
And I will float away
           */
            case 1000:
                robot.autonDriveUltimate(MovementEnum.RIGHTSTRAFE, right, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto = 13;
                }
                break;

            case 13:
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
                robot.intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 16:
                robot.intake.setPower(1);
                robot.intake.setTargetPosition(-280);
                robot.intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                if (Math.abs(robot.intake.getCurrentPosition()) >= Math.abs(robot.intake.getTargetPosition())){
                    robot.intake.setPower(0);
                    auto++;
                }
                break;

            case 17:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                robot.intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 18:
                robot.autonDriveUltimate(MovementEnum.BACKWARD, 375, 0.4);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 19:
                if(Math.abs(-170- robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) > 3) {
                    robot.adjustHeading(-170);
                }
                else if(Math.abs(-170 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) < 3) {
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
           CASE FOR CENTER
           Framed as icicles
Numerous and gray
I will do as spiders do
Then watch you float away

As the night is young
The frostbite lingers
Drips like faucets
From my fingers
And everything you gave to me
I tossed it to the wind

And I will float away
And I will float away
And I will float away
And I will float away

Climbed across my back
Cut into the flesh
Burdened by my heart
Turned and hung my head

And I will float away
And I will float away
And I will float away
And I will float away
           */
            case 21:
                robot.autonDriveUltimate(MovementEnum.BACKWARD, centerBack, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

                /*
           CASE FOR LEFT
           */
            case 200:
                robot.autonDriveUltimate(MovementEnum.BACKWARD, leftBack, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto = 22;
                }
                break;

                /*
           CASE FOR RIGHT

           Framed as icicles
Numerous and gray
I will do as spiders do
Then watch you float away

As the night is young
The frostbite lingers
Drips like faucets
From my fingers
And everything you gave to me
I tossed it to the wind

And I will float away
And I will float away
And I will float away
And I will float away

Climbed across my back
Cut into the flesh
Burdened by my heart
Turned and hung my head

And I will float away
And I will float away
And I will float away
And I will float away
           */
            case 2000:
                robot.autonDriveUltimate(MovementEnum.BACKWARD, rightBack, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto = 22;
                }
                break;

            case 22:
                if(Math.abs(-135 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) > 3) {
                    robot.adjustHeading(-135);
                }
                else if(Math.abs(-135 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) < 3) {
                    robot.drive(MovementEnum.STOP, 0);
                    auto++;
                }
                break;

            case 23:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 24:
                robot.autonDriveUltimate(MovementEnum.LEFTSTRAFE, 350, 0.5);
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
                robot.claw.setPosition(0.7);
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

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

            case 28:
                if(Math.abs(179 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) > 3) {
                    robot.adjustHeading(179);
                }
                else if(Math.abs(179 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) < 3) {
                    robot.drive(MovementEnum.STOP, 0);
                    auto++;
                }
                break;

            case 29:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 30:
                robot.autonDriveUltimate(MovementEnum.FORWARD, 280, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 31:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                if(BigThonk == TensorFlow.TFState.CENTER){
                    auto++;
                }else if(BigThonk == TensorFlow.TFState.LEFT){
                    auto = 300;
                }else if(BigThonk == TensorFlow.TFState.RIGHT){
                    auto = 3000;
                }
                break;

            case 32:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                if(BigThonk == TensorFlow.TFState.CENTER){
                    auto++;
                }else if(BigThonk == TensorFlow.TFState.LEFT){
                    auto = 400;
                }else if(BigThonk == TensorFlow.TFState.RIGHT){
                    auto = 4000;
                }
                break;

                /*
           CASE FOR CENTER

           Framed as icicles
Numerous and gray
I will do as spiders do
Then watch you float away

As the night is young
The frostbite lingers
Drips like faucets
From my fingers
And everything you gave to me
I tossed it to the wind

And I will float away
And I will float away
And I will float away
And I will float away

Climbed across my back
Cut into the flesh
Burdened by my heart
Turned and hung my head

And I will float away
And I will float away
And I will float away
And I will float away
           */
            case 33:
                robot.autonDriveUltimate(MovementEnum.LEFTSTRAFE, center, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

                /*
           CASE FOR LEFT

           */
            case 400:
                robot.autonDriveUltimate(MovementEnum.LEFTSTRAFE, left, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto = 34;
                }
                break;

                /*
           CASE FOR RIGHT
           Framed as icicles
Numerous and gray
I will do as spiders do
Then watch you float away

As the night is young
The frostbite lingers
Drips like faucets
From my fingers
And everything you gave to me
I tossed it to the wind

And I will float away
And I will float away
And I will float away
And I will float away

Climbed across my back
Cut into the flesh
Burdened by my heart
Turned and hung my head

And I will float away
And I will float away
And I will float away
And I will float away
           */
            case 4000:
                robot.autonDriveUltimate(MovementEnum.RIGHTSTRAFE, right, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto = 34;
                }
                break;

            case 34:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 35:
                robot.autonDriveUltimate(MovementEnum.FORWARD, 280, 0.4);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 36:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 37:
                robot.autonDriveUltimate(MovementEnum.BACKWARD, 375, 0.4);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 38:
            if(Math.abs(-45 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) > 3) {
                robot.adjustHeading(-45);
            }
            else if(Math.abs(-45 - robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ) < 3) {
                robot.drive(MovementEnum.STOP, 0);
                auto++;
            }
            break;

            case 39:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 40:
                robot.autonDriveUltimate(MovementEnum.RIGHTSTRAFE, 200, 0.5);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                    auto++;
                }
                break;

            case 41:
                robot.changeRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                auto++;
                break;

            case 42:
                robot.autonDriveUltimate(MovementEnum.FORWARD, 1000, 0.4);
                if (Math.abs(robot.FL.getCurrentPosition()) >= Math.abs(robot.FL.getTargetPosition())){
                }
                break;
        }
        telemetry.addData("BigThonk", BigThonk);
        telemetry.addData("Framed as icicles\n" +
                "Numerous and gray\n" +
                "I will do as spiders do\n" +
                "Then watch you float away\n" +
                "\n" +
                "As the night is young\n" +
                "The frostbite lingers\n" +
                "Drips like faucets\n" +
                "From my fingers\n" +
                "And everything you gave to me\n" +
                "I tossed it to the wind\n" +
                "\n" +
                "And I will float away\n" +
                "And I will float away\n" +
                "And I will float away\n" +
                "And I will float away\n" +
                "\n" +
                "Climbed across my back\n" +
                "Cut into the flesh\n" +
                "Burdened by my heart\n" +
                "Turned and hung my head\n" +
                "\n" +
                "And I will float away\n" +
                "And I will float away\n" +
                "And I will float away\n" +
                "And I will float away", robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle);
        telemetry.update();
    }
}