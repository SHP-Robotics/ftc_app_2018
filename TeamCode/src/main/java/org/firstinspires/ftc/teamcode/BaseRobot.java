package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class BaseRobot extends OpMode {
    public DcMotor leftMotor, rightMotor;
    public ElapsedTime timer = new ElapsedTime();

    @Override
    public void init() {
        leftMotor = hardwareMap.get(DcMotor.class, "leftMotor");
        rightMotor = hardwareMap.get(DcMotor.class, "rightMotor");
    }

    @Override
    public void start() {
        timer.reset();
        reset_drive_encoders();
    }

    @Override
    public void loop() {
        telemetry.addData("D00 Left Motor Enc: ", get_left_motor_enc());
        telemetry.addData("D01 Right Motor Enc: ", get_right_motor_enc());
        telemetry.addData("D02 Left Joystick Y: ", gamepad1.left_stick_y);
        telemetry.addData("D03 Left Joystick X: ", gamepad1.left_stick_x);
        telemetry.addData("D04 Right Joystick Y: ", gamepad1.right_stick_y);
        telemetry.addData("D05 Right Joystick X: ", gamepad1.right_stick_x);
    }

    public boolean auto_drive(double power, double inches) {
        double TARGET_ENC = ConstantVariables.K_PPIN_DRIVE * inches * Math.sqrt(2);

        double left_speed = -power;
        double right_speed = power;
        double error = get_left_motor_enc() - get_right_motor_enc();

        error /= ConstantVariables.K_DRIVE_ERROR_P;
        left_speed -= error;
        right_speed += error;

        left_speed = Range.clip(left_speed, -1, 1);
        right_speed = Range.clip(right_speed, -1, 1);
        leftMotor.setPower(left_speed);
        rightMotor.setPower(right_speed);

        if (Math.abs(get_left_motor_enc()) >= TARGET_ENC &&
                Math.abs(get_right_motor_enc()) >= TARGET_ENC) {
            leftMotor.setPower(0);
            rightMotor.setPower(0);
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

        if (Math.abs(get_left_motor_enc()) >= TARGET_ENC &&
                Math.abs(get_right_motor_enc()) >= TARGET_ENC) {
            leftMotor.setPower(0);
            rightMotor.setPower(0);
            return true;
        } else {
            leftMotor.setPower(power);
            rightMotor.setPower(power);
        }
        return false;
    }

    public void tank_drive(double leftPwr, double rightPwr) {
        double leftPower = Range.clip(leftPwr, -1.0, 1.0);
        double rightPower = Range.clip(rightPwr, -1.0, 1.0);

        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);
    }

    public void reset_drive_encoders() {
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public int get_left_motor_enc() {
        if (leftMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return leftMotor.getCurrentPosition();
    }

    public int get_right_motor_enc() {
        if (rightMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return rightMotor.getCurrentPosition();
    }
}
