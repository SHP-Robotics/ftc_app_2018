package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/*
Created by chun on 8/8/18 for robotics boot camp 2018.
*/

@TeleOp
@Disabled

public class TestOpMode extends BaseRobot { //CHANGE TO BaseRobot

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        super.loop();
        //drive train
        tank_drive(gamepad1.right_stick_y, gamepad1.left_stick_y);
        /*
        //intake flip
        if(gamepad1.x) {
            flip_intake(0.35);
        } else if (gamepad1.y) {
            flip_intake(-0.35);
        } else {
            flip_intake(0);
        }

        //intake extension
        if(gamepad1.right_bumper) {
            extend_intake(0.5);
        } else if (gamepad1.left_bumper) {
            extend_intake(-0.5);
        } else {
            extend_intake(0);
        }

        //intake
        if(gamepad1.a) {
            intake(1);
        } else if (gamepad1.b) {
            intake(-1);
        } else {
            intake(0);
        }
        */
        //climber
        if(gamepad1.dpad_up) {
            climb(1);
        } else if (gamepad1.dpad_down) {
            climb(-1);
        } else {
            climb(0);
        }

        //flip_intake(gamepad1.right_trigger - gamepad1.left_trigger);
        //extend_intake(gamepad1.right_bumper ? -1 : gamepad1.left_bumper ? 1 : 0);
    }
}