package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/*
Created by chun on 8/8/18 for robotics boot camp 2018.
*/

@TeleOp

public class MainTeleOp extends BaseRobot { //CHANGE TO BaseRobot

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
        tank_drive(gamepad1.left_stick_y, gamepad1.right_stick_y);

        //climber
        if(gamepad1.dpad_up) {
            climb(1);
        } else if (gamepad1.dpad_down) {
            climb(-1);
        } else {
            climb(0);
        }

        if(gamepad1.a) {
            set_wedge_servo(ConstantVariables.K_WEDGE_SERVO_DOWN);
        } else if (gamepad1.b){
            set_wedge_servo(ConstantVariables.K_WEDGE_SERVO_UP);
        }

        //flip_intake(gamepad1.right_trigger - gamepad1.left_trigger);
        //extend_intake(gamepad1.right_bumper ? -1 : gamepad1.left_bumper ? 1 : 0);
    }
}