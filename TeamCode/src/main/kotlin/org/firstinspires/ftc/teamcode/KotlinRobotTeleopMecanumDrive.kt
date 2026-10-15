package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import kotlin.math.abs
import kotlin.math.max

@TeleOp(name = "Mecanum Drive", group = "Robot")
class KotlinRobotTeleopMecanumDrive : OpMode() {
    private lateinit var frontLeftDrive: DcMotor
    private lateinit var frontRightDrive: DcMotor
    private lateinit var backLeftDrive: DcMotor
    private lateinit var backRightDrive: DcMotor

    override fun init() {
        frontLeftDrive = hardwareMap.get(DcMotor::class.java, "leftFront")
        frontRightDrive = hardwareMap.get(DcMotor::class.java, "rightFront")
        backLeftDrive = hardwareMap.get(DcMotor::class.java, "leftBack")
        backRightDrive = hardwareMap.get(DcMotor::class.java, "rightBack")

        // Motor Direction
        backLeftDrive.direction = DcMotorSimple.Direction.REVERSE
        frontLeftDrive.direction = DcMotorSimple.Direction.REVERSE

        // Run Mode
        frontLeftDrive.mode = DcMotor.RunMode.RUN_USING_ENCODER
        frontRightDrive.mode = DcMotor.RunMode.RUN_USING_ENCODER
        backLeftDrive.mode = DcMotor.RunMode.RUN_USING_ENCODER
        backRightDrive.mode = DcMotor.RunMode.RUN_USING_ENCODER
    }

    override fun loop() {
        drive(
            -gamepad1.left_stick_y.toDouble(),
            gamepad1.left_stick_x.toDouble(),
            gamepad1.right_stick_x.toDouble()
        )
    }

    private fun drive(forward: Double, right: Double, rotate: Double) {
        val frontLeftPower = forward + right + rotate
        val frontRightPower = forward - right - rotate
        val backRightPower = forward + right - rotate
        val backLeftPower = forward - right + rotate

        var maxPower = 1.0

        maxPower = max(maxPower, abs(frontLeftPower))
        maxPower = max(maxPower, abs(frontRightPower))
        maxPower = max(maxPower, abs(backRightPower))
        maxPower = max(maxPower, abs(backLeftPower))

        frontLeftDrive.power = frontLeftPower / maxPower
        frontRightDrive.power = frontRightPower / maxPower
        backLeftDrive.power = backLeftPower / maxPower
        backRightDrive.power = backRightPower / maxPower
    }
}
