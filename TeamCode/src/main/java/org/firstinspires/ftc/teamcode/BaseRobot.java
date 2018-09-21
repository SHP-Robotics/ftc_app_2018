package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class BaseRobot extends OpMode {
    public DcMotor leftBackMotor, rightBackMotor, leftFrontMotor, rightFrontMotor;
    public ElapsedTime timer = new ElapsedTime();

    @Override
    public void init() {
        leftBackMotor = hardwareMap.get(DcMotor.class, "leftBackMotor");
        rightBackMotor = hardwareMap.get(DcMotor.class, "rightBackMotor");
        leftFrontMotor = hardwareMap.get(DcMotor.class, "leftFrontMotor");
        rightFrontMotor = hardwareMap.get(DcMotor.class, "rightFrontMotor");
    }

    @Override
    public void start() {
        timer.reset();
        reset_drive_encoders();
    }

    @Override
    public void loop() {
        telemetry.addData("D00 Left Back Motor Enc: ", get_left_back_motor_enc());
        telemetry.addData("D01 Right Back Motor Enc: ", get_right_back_motor_enc());
        telemetry.addData("D02 Left Front Motor Enc: ", get_left_front_motor_enc());
        telemetry.addData("D03 Right Front Motor Enc: ", get_right_front_motor_enc());
//        telemetry.addData("D04 Left Joystick Y: ", gamepad1.left_stick_y);
//        telemetry.addData("D05 Left Joystick X: ", gamepad1.left_stick_x);
//        telemetry.addData("D06 Right Joystick Y: ", gamepad1.right_stick_y);
//        telemetry.addData("D07 Right Joystick X: ", gamepad1.right_stick_x);
    }

    public boolean auto_drive(double power, double inches) {
        double TARGET_ENC = ConstantVariables.K_PPIN_DRIVE * inches;
        telemetry.addData("Target_enc: ", TARGET_ENC);
        double left_speed = -power;
        double right_speed = power;
        double error = -get_left_front_motor_enc() - get_right_front_motor_enc();

        error /= ConstantVariables.K_DRIVE_ERROR_P;
        left_speed -= error;
        right_speed += error;

        left_speed = Range.clip(left_speed, -1, 1);
        right_speed = Range.clip(right_speed, -1, 1);
        leftBackMotor.setPower(left_speed);
        rightBackMotor.setPower(right_speed);
        leftFrontMotor.setPower(left_speed);
        rightFrontMotor.setPower(right_speed);

        if (Math.abs(get_left_front_motor_enc()) >= TARGET_ENC && Math.abs(get_right_front_motor_enc()) >= TARGET_ENC) {
            leftBackMotor.setPower(0);
            rightBackMotor.setPower(0);
            leftFrontMotor.setPower(0);
            rightFrontMotor.setPower(0);
            return true;
        }
        return false;
    }

    /**
     * @param power:   the speed to turn at. Negative for left.
     * @param degrees: the number of degrees to turn.
     * @return Whether the target angle has been reached.
     */
    public boolean auto_turn(double power, double degrees) {
        double TARGET_ENC = Math.abs(ConstantVariables.K_PPDEG_DRIVE * degrees);
        telemetry.addData("D99 TURNING TO ENC: ", TARGET_ENC);

        if (Math.abs(get_left_front_motor_enc()) >= TARGET_ENC && Math.abs(get_right_front_motor_enc()) >= TARGET_ENC) {
            leftBackMotor.setPower(0);
            rightBackMotor.setPower(0);
            leftFrontMotor.setPower(0);
            rightFrontMotor.setPower(0);
            return true;
        } else {
            leftBackMotor.setPower(power);
            rightBackMotor.setPower(power);
            leftFrontMotor.setPower(power);
            rightFrontMotor.setPower(power);
        }
        return false;
    }

    public void tank_drive(double leftPwr, double rightPwr) {
        double leftPower = Range.clip(leftPwr, -1.0, 1.0);
        double rightPower = Range.clip(rightPwr, -1.0, 1.0);

        leftBackMotor.setPower(leftPower);
        rightBackMotor.setPower(rightPower);
        leftFrontMotor.setPower(leftPower);
        rightFrontMotor.setPower(rightPower);
    }

    public void reset_drive_encoders() {
        leftBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public int get_left_back_motor_enc() {
        if (leftBackMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return leftBackMotor.getCurrentPosition();
    }

    public int get_right_back_motor_enc() {
        if (rightBackMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            rightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return rightBackMotor.getCurrentPosition();
    }

    public int get_left_front_motor_enc() {
        if (leftBackMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return leftBackMotor.getCurrentPosition();
    }

    public int get_right_front_motor_enc() {
        if (rightFrontMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return rightFrontMotor.getCurrentPosition();
    }
}
