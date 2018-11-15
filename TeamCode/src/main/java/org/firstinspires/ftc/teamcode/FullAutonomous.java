package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/*
 * Created by chun on 8/8/18 for robotics boot camp 2018.
 */

@Autonomous

public class FullAutonomous extends BaseRobot {
    private int stage = 0;
    private GoldAlignDetector detector;

    @Override
    public void init() {
        super.init();

        telemetry.addData("Status", "DogeCV 2018.0 - Gold Align Example");

        detector = new GoldAlignDetector();
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        detector.useDefaults();

        // Optional Tuning
        detector.alignSize = 50; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
        detector.alignPosOffset = 0; // How far from center frame to offset this alignment zone.
        detector.downscale = 0.4; // How much to downscale the input frames

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.005;

        detector.ratioScorer.weight = 5;
        detector.ratioScorer.perfectRatio = 1.0;

        detector.enable();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        super.loop();
        telemetry.addData("IsAligned" , detector.getAligned()); // Is the bot aligned with the gold mineral
        telemetry.addData("X Pos" , detector.getXPosition()); // Gold X pos.

        switch (stage) {
            case 0:
                //detach climber
                if (Math.abs(get_climb_motor_enc())>4000) {
                    climb(0);
                    stage++;
                } else {
                    climb(1);
                }

                break;
            case 1:
                //move away from lander
                if (auto_drive(0.8, 6)) {
                    reset_drive_encoders();
                    stage++;
                }
                break;
            case 2:
                //turn left
                if (auto_turn(-0.5,45)) {
                    reset_drive_encoders();
                    stage++;
                }/*
            case 3:
                //move to wall
                if (auto_drive(0.8, 50)) {
                    reset_drive_encoders();
                    stage++;
                }
                break;
            case 4:
                //back off wall
                if (auto_drive(-0.5, 4)) {
                    reset_drive_encoders();
                    stage++;
                }
                break;
            case 5:
                //turn right
                if (auto_turn(0.5,90)) {
                    reset_drive_encoders();
                    stage++;
                }
                break;
            case 6:
                //move into corner
                if (auto_drive(0.8, 40)) {
                    reset_drive_encoders();
                    stage++;
                }
                break;
            case 7:
                set_marker_servo(ConstantVariables.K_MARKER_SERVO_DOWN);
                stage++;
                break;
            case 8:
                //back out of corner
                if (auto_drive(-0.5, 8)) {
                    reset_drive_encoders();
                    stage++;
                }
                break;
            case 9:
                //turn right
                if (auto_turn(1,90)) {
                    reset_drive_encoders();
                    stage++;
                }
                break;
            case 10:
                if (detector.getAligned()) {
                    stage++;
                }
                if (detector.getXPosition()>280) {
                    if (auto_turn(0.4,10)) { //turns left
                        reset_drive_encoders();
                    }
                } else if (detector.getXPosition()<280) {
                    if (auto_turn(-0.4, 10)) { //turns right
                        reset_drive_encoders();
                    }
                }
                break;
            case 11:
                //move gold mineral
                if (auto_drive(1, 15)) {
                    reset_drive_encoders();
                    stage++;
                }
                break;*/
            default:
                break;
        }
    }

    @Override
    public void stop() {
        detector.disable();
    }
}
