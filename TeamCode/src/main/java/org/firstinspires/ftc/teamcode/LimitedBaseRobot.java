package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class LimitedBaseRobot extends OpMode {
    public DcMotor leftDriveMotor, rightDriveMotor, leftFlipMotor, rightFlipMotor, intakeExtensionMotor, intakeMotor, climbMotor;
    public ElapsedTime timer = new ElapsedTime();


    @Override
    public void init() {
        leftDriveMotor = hardwareMap.get(DcMotor.class, "leftDriveMotor");
        rightDriveMotor = hardwareMap.get(DcMotor.class, "rightDriveMotor");
        leftFlipMotor = hardwareMap.get(DcMotor.class, "leftFlipMotor");
        rightFlipMotor = hardwareMap.get(DcMotor.class, "rightFlipMotor");
        intakeExtensionMotor = hardwareMap.get(DcMotor.class, "intakeExtensionMotor");
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        climbMotor = hardwareMap.get(DcMotor.class, "climbMotor");
    }

    @Override
    public void start() {
        timer.reset();
        reset_drive_encoders();
    }

    @Override
    public void loop() {
        telemetry.addData("D00 Left Drive Motor Enc: ", get_left_drive_motor_enc());
        telemetry.addData("D01 Right Drive Motor Enc: ", get_right_drive_motor_enc());
        telemetry.addData("D02 Left Flip Motor Enc: ", get_left_flip_motor_enc());
        telemetry.addData("D03 Right Flip Motor Enc: ", get_right_flip_motor_enc());
        telemetry.addData("D04 Intake Extension Motor Enc: ", get_intake_extension_motor_enc());
        telemetry.addData("D05 Intake Motor Enc: ", get_intake_motor_enc());
        telemetry.addData("D06 Climb Motor Enc: ", get_climb_motor_enc());
        telemetry.addData("D98 Left Trigger: ", gamepad1.right_trigger);
        telemetry.addData("D99 Right Trigger: ", gamepad1.left_trigger);
    }

    public void flip_intake(double power) {
        double left_speed = Range.clip(power, -1, 1);
        double right_speed = Range.clip(power, -1, 1);
        /*double error = get_left_flip_motor_enc() - get_right_flip_motor_enc();

        error /= ConstantVariables.K_FLIP_ERROR_P;
        left_speed -= error;
        right_speed += error;

        left_speed = Range.clip(left_speed, -1, 1);
        right_speed = Range.clip(right_speed, -1, 1);
        if (get_left_flip_motor_enc() >= ConstantVariables.K_FLIP_MAX) {
            left_speed = Range.clip(left_speed, -1, 0);
        } else if (get_left_flip_motor_enc() <= ConstantVariables.K_FLIP_MIN) {
            left_speed = Range.clip(left_speed, 0, 1);
        }

        if (get_right_flip_motor_enc() >= ConstantVariables.K_FLIP_MAX) {
            right_speed = Range.clip(right_speed, -1, 0);
        } else if (get_right_flip_motor_enc() <= ConstantVariables.K_FLIP_MIN) {
            right_speed = Range.clip(right_speed, 0, 1);
        }*/
        leftFlipMotor.setPower(-left_speed);
        rightFlipMotor.setPower(right_speed);
    }

    public void extend_intake(double power) {
        double speed = Range.clip(power, -1, 1);

        if (get_intake_extension_motor_enc() >= ConstantVariables.K_INTAKE_EXTENSION_MAX) {
            speed = Range.clip(speed, -1, 0);
        } else if (get_intake_extension_motor_enc() <= ConstantVariables.K_INTAKE_EXTENSION_MIN) {
            speed = Range.clip(speed, 0, 1);
        }
        intakeExtensionMotor.setPower(speed);
    }

    public void intake(double power) {
        double speed = Range.clip(power, -1, 1);

        intakeMotor.setPower(speed);
    }

    public void climb(double power) {
        double speed = Range.clip(power, -1, 1);

        if (get_climb_motor_enc() >= ConstantVariables.K_CLIMB_MAX) {
            speed = Range.clip(speed, -1, 0);
        } else if (get_climb_motor_enc() <= ConstantVariables.K_CLIMB_MIN) {
            speed = Range.clip(speed, 0, 1);
        }
        climbMotor.setPower(speed);
    }

    public boolean auto_drive(double power, double inches) {
        double TARGET_ENC = ConstantVariables.K_PPIN_DRIVE * inches;
        telemetry.addData("Target_enc: ", TARGET_ENC);
        double left_speed = -power;
        double right_speed = power;
        double error = -get_left_drive_motor_enc() - get_right_drive_motor_enc();

        error /= ConstantVariables.K_DRIVE_ERROR_P;
        left_speed -= error;
        right_speed += error;

        left_speed = Range.clip(left_speed, -1, 1);
        right_speed = Range.clip(right_speed, -1, 1);
        leftDriveMotor.setPower(left_speed);
        rightDriveMotor.setPower(right_speed);


        if (Math.abs(get_left_drive_motor_enc()) >= TARGET_ENC && Math.abs(get_right_drive_motor_enc()) >= TARGET_ENC) {
            leftDriveMotor.setPower(0);
            rightDriveMotor.setPower(0);
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

        if (Math.abs(get_left_drive_motor_enc()) >= TARGET_ENC && Math.abs(get_right_drive_motor_enc()) >= TARGET_ENC) {
            leftDriveMotor.setPower(0);
            rightDriveMotor.setPower(0);
            return true;
        } else {
            leftDriveMotor.setPower(power);
            rightDriveMotor.setPower(power);
        }
        return false;
    }

    public void tank_drive(double leftPwr, double rightPwr) {
        double leftPower = Range.clip(leftPwr, -1.0, 1.0);
        double rightPower = Range.clip(rightPwr, -1.0, 1.0);

        leftDriveMotor.setPower(-leftPower);
        rightDriveMotor.setPower(rightPower);
    }

    public void reset_drive_encoders() {
        leftDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public int get_left_drive_motor_enc() {
        if (leftDriveMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            leftDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return leftDriveMotor.getCurrentPosition();
    }

    public int get_right_drive_motor_enc() {
        if (rightDriveMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            rightDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return rightDriveMotor.getCurrentPosition();
    }

    public int get_left_flip_motor_enc() {
        if (leftFlipMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            leftFlipMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return leftFlipMotor.getCurrentPosition();
    }

    public int get_right_flip_motor_enc() {
        if (rightFlipMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            rightFlipMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return rightFlipMotor.getCurrentPosition();
    }

    public int get_intake_extension_motor_enc() {
        if (intakeExtensionMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            intakeExtensionMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return intakeExtensionMotor.getCurrentPosition();
    }

    public int get_intake_motor_enc() {
        if (intakeMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            intakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return intakeMotor.getCurrentPosition();
    }

    public int get_climb_motor_enc() {
        if (climbMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            climbMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return climbMotor.getCurrentPosition();
    }
}
