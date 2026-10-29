package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp(name = "Mecanum Drive", group = "Robot")
class KotlinRobotTeleopMecanumDrive : OpMode() {
    private lateinit var robot: Robot

    override fun init() {
        robot = Robot(hardwareMap)
    }

    override fun loop() {
        robot.drive(
            -gamepad1.left_stick_y.toDouble(),
            gamepad1.left_stick_x.toDouble(),
            gamepad1.right_stick_x.toDouble()
        )
    }
}
