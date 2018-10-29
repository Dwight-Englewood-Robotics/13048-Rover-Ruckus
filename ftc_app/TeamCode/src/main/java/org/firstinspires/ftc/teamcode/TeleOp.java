package org.firstinspires.ftc.teamcode;/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.bot;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.robotcore.external.Telemetry; //[Why did you import telemetry twice?]
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import org.firstinspires.ftc.teamcode.bot;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="teleop",group="Teleop")
//@Disabled
public class TeleOp extends OpMode {
    // Declare OpMode members.
    private ElapsedTime timer = new ElapsedTime();
    bot robot = new bot();
    int pos = 0;
    int extPos = 0;

    @Override
    public void init() {
        robot.init(hardwareMap, telemetry, false);
//      robot.resetServo();
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        timer.reset();
        robot.dump.setPosition(0.0);
        robot.claw.setPosition(0.0);
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

//        double leftPower = Range.clip(gamepad1.left_stick_y, -0.75,0.75);
  //      double rightPower = Range.clip(gamepad1.right_stick_y, -0.75, 0.75);
        robot.tankDriveNoStrafe(gamepad1.left_stick_y, gamepad1.right_stick_y);
        //TODO: After competition, comment out tankDriveNoStrafe and enable normal tankDrive for strafable Mechanum Wheels.
        //robot.tankDrive(gamepad1.left_stick_y, gamepad1.right_stick_y, gamepad1.left_trigger, gamepad1.right_trigger, false, false);

        //intake
        if(gamepad2.right_trigger > .3){
            robot.intake.setPower(1.0);
        } else if(gamepad2.left_trigger > .3){
            robot.intake.setPower(-1.0);

        }
        else {
            robot.intake.setPower(0);
        }

        //hinge
        if(gamepad2.right_bumper) {
            robot.hinge.setPower(1.0);
        } else if (gamepad2.left_bumper) {
            robot.hinge.setPower(-1.0);
        } else {
            robot.hinge.setPower(0);
        }

        //dump
        if(gamepad2.a) {
           // pos += (0.25);
            robot.dump.setPosition(1.0);
        }
        else if (gamepad2.b){
          //  pos -= (0.25);
            robot.dump.setPosition(0);
        }

       // robot.dump.setPosition(pos);

        //lift
        //Change direction
        if (gamepad2.dpad_up) {
            robot.lift.setPower(1.0);
        } else if (gamepad2.dpad_down) {
            robot.lift.setPower(-1.0);
        } else {
            robot.lift.setPower(0);
        }

        //hook
        if (gamepad1.a) {
            robot.hook.setPower(1.0);
        } else if (gamepad1.b) {
            robot.hook.setPower(-1.0);
        }
        else{
            robot.hook.setPower(0);
        }

        //extend
        if(gamepad2.x){
            robot.extend.setPower(1.0);
        }
        else if (gamepad2.y){
            robot.extend.setPower(-1.0);
        }
        else{
            robot.extend.setPower(0);
        }

        if(gamepad1.x){
            robot.claw.setPosition(0.0);
        }
        else if (gamepad1.y){
            robot.claw.setPosition(1.0);
        }
/*
        if(gamepad2.x){
            extPos += 0.25;
        }
        else if (gamepad2.y){
            extPos -=0.25;
        }

        robot.extend.setPosition(extPos);

        */
      //  if (gamepad2.x) {
      //      robot.extend.setPosition(1.0);
       // } else if (gamepad2.y){
       //     robot.extend.setPosition(0.5);
       // } else {
        //    robot.extend.setPosition(0.0);
       // }

//        telemetry.addData("degrees: ", robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle);
//        telemetry.update();
//        robot.testServos(telemetry);
//        telemetry.update();
//        telemetry.addData("Status", "Run Time: " + runtime.toString());
//        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}