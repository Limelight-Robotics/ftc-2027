package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import kotlin.math.abs
import kotlin.math.max

class Robot(hardwareMap: HardwareMap) {
    private val frontLeftDrive = hardwareMap.get(DcMotor::class.java, "leftFront")
    private val frontRightDrive = hardwareMap.get(DcMotor::class.java, "rightFront")
    private val backLeftDrive = hardwareMap.get(DcMotor::class.java, "leftBack")
    private val backRightDrive = hardwareMap.get(DcMotor::class.java, "rightBack")

    init {
        backLeftDrive.direction = DcMotorSimple.Direction.REVERSE
        frontLeftDrive.direction = DcMotorSimple.Direction.REVERSE

        listOf(frontLeftDrive, frontRightDrive, backLeftDrive, backRightDrive).forEach {
            it.mode = DcMotor.RunMode.RUN_USING_ENCODER
        }
    }

    fun drive(forward: Double, right: Double, rotate: Double) {
        val frontLeftPower = forward + right + rotate
        val frontRightPower = forward - right - rotate
        val backRightPower = forward + right - rotate
        val backLeftPower = forward - right + rotate
        val maxPower = max(
            1.0,
            max(
                max(abs(frontLeftPower), abs(frontRightPower)),
                max(abs(backLeftPower), abs(backRightPower))
            )
        )

        frontLeftDrive.power = frontLeftPower / maxPower
        frontRightDrive.power = frontRightPower / maxPower
        backLeftDrive.power = backLeftPower / maxPower
        backRightDrive.power = backRightPower / maxPower
    }

    fun stop() {
        drive(0.0, 0.0, 0.0)
    }
}
