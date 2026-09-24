package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.{LinearOpMode, TeleOp}

@TeleOp(name = "Scala: Example", group = "Scala")
final class ScalaExampleOpMode extends LinearOpMode {
  override def runOpMode(): Unit = {
    val startNanos = System.nanoTime()

    telemetry.addLine("Scala OpMode initialized")
    telemetry.update()

    waitForStart()

    while (opModeIsActive()) {
      val elapsedMs = (System.nanoTime() - startNanos) / 1000000L
      val leftStickX = gamepad1.left_stick_x

      telemetry.addData("elapsedMs", elapsedMs)
      telemetry.addData("leftStickX", leftStickX)
      telemetry.update()

      idle()
    }
  }
}
