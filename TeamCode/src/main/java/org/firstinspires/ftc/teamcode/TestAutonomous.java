package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/*
 * Created by chun on 8/8/18 for robotics boot camp 2018.
 */

@Autonomous

public class TestAutonomous extends BaseRobot {
    private int stage = 0;

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
        switch (stage) {
            case 0:
                if (auto_drive(0.8, 12)) {
                    reset_drive_encoders();
                    stage++;
                }
                break;
            case 1:
                if (auto_turn(0.6, 360)) {
                    reset_drive_encoders();
                    stage++;
                }
                break;
            default:
                break;
        }
    }
}
