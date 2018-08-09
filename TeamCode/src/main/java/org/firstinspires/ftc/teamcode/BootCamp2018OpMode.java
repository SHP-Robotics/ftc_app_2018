package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/*
Created by chun on 8/8/18 for robotics boot camp 2018.
*/

@TeleOp

public class BootCamp2018OpMode extends BaseRobot {

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
        tank_drive(gamepad1.left_stick_y, gamepad1.right_stick_y);
    }
}