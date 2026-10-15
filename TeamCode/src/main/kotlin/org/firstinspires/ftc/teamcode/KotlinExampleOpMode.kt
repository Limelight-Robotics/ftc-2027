package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp(name = "Kotlin: Example", group = "Kotlin")
class KotlinExampleOpMode : LinearOpMode() {
    override fun runOpMode() {
        val startNanos = System.nanoTime()
        telemetry.addLine("Kotlin OpMode initialized")
        telemetry.update()
        waitForStart()

        while (opModeIsActive()) {
            val elapsedMs = (System.nanoTime() - startNanos) / 1_000_000L
            telemetry.addData("elapsedMs", elapsedMs)
            telemetry.addData("leftStickX", gamepad1.left_stick_x)
            telemetry.update()
            idle()
        }
    }
}
