error id: file://<WORKSPACE>/TeamCode/src/main/scala/org/firstinspires/ftc/teamcode/ScalaMecanumDriveOpMode.scala:
file://<WORKSPACE>/TeamCode/src/main/scala/org/firstinspires/ftc/teamcode/ScalaMecanumDriveOpMode.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -com/qualcomm/robotcore/hardware/DcMotor.RunMode.RUN_USING_ENCODER.
	 -com/qualcomm/robotcore/hardware/DcMotor.RunMode.RUN_USING_ENCODER#
	 -com/qualcomm/robotcore/hardware/DcMotor.RunMode.RUN_USING_ENCODER().
	 -DcMotor.RunMode.RUN_USING_ENCODER.
	 -DcMotor.RunMode.RUN_USING_ENCODER#
	 -DcMotor.RunMode.RUN_USING_ENCODER().
	 -scala/Predef.DcMotor.RunMode.RUN_USING_ENCODER.
	 -scala/Predef.DcMotor.RunMode.RUN_USING_ENCODER#
	 -scala/Predef.DcMotor.RunMode.RUN_USING_ENCODER().
offset: 1192
uri: file://<WORKSPACE>/TeamCode/src/main/scala/org/firstinspires/ftc/teamcode/ScalaMecanumDriveOpMode.scala
text:
```scala
package org.firstinspires.ftc.teamcode

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import com.qualcomm.robotcore.eventloop.opmode.{OpMode, TeleOp}
import com.qualcomm.robotcore.hardware.{DcMotor, IMU}
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit

@TeleOp(name = "Scala: Mecanum Drive", group = "Scala")
final class ScalaMecanumDriveOpMode extends OpMode {
  private var frontLeftDrive: DcMotor = _
  private var frontRightDrive: DcMotor = _
  private var backLeftDrive: DcMotor = _
  private var backRightDrive: DcMotor = _
  private var imu: IMU = _

  override def init(): Unit = {
    frontLeftDrive = hardwareMap.get(classOf[DcMotor], "leftFront")
    frontRightDrive = hardwareMap.get(classOf[DcMotor], "rightFront")
    backLeftDrive = hardwareMap.get(classOf[DcMotor], "leftBack")
    backRightDrive = hardwareMap.get(classOf[DcMotor], "rightBack")

    backLeftDrive.setDirection(DcMotor.Direction.REVERSE)
    frontLeftDrive.setDirection(DcMotor.Direction.REVERSE)

    frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER)
    frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER)
    backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_E@@NCODER)
    backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER)

    imu = hardwareMap.get(classOf[IMU], "imu")
    val orientationOnRobot = new RevHubOrientationOnRobot(
      RevHubOrientationOnRobot.LogoFacingDirection.UP,
      RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
    )
    imu.initialize(new IMU.Parameters(orientationOnRobot))
  }

  override def loop(): Unit = {
    telemetry.addLine("Press A to reset Yaw")
    telemetry.addLine("Hold left bumper to drive in robot relative")
    telemetry.addLine("The left joystick sets the robot direction")
    telemetry.addLine("Moving the right joystick left and right turns the robot")

    if (gamepad1.a) {
      imu.resetYaw()
    }

    if (gamepad1.left_bumper) {
      drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x)
    } else {
      driveFieldRelative(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x)
    }
  }

  private def driveFieldRelative(forward: Double, right: Double, rotate: Double): Unit = {
    var theta = Math.atan2(forward, right)
    val r = Math.hypot(right, forward)

    theta = AngleUnit.normalizeRadians(
      theta - imu.getRobotYawPitchRollAngles.getYaw(AngleUnit.RADIANS)
    )

    val newForward = r * Math.sin(theta)
    val newRight = r * Math.cos(theta)

    drive(newForward, newRight, rotate)
  }

  private def drive(forward: Double, right: Double, rotate: Double): Unit = {
    val frontLeftPower = forward + right + rotate
    val frontRightPower = forward - right - rotate
    val backRightPower = forward + right - rotate
    val backLeftPower = forward - right + rotate

    var maxPower = 1.0
    val maxSpeed = 1.0

    maxPower = Math.max(maxPower, Math.abs(frontLeftPower))
    maxPower = Math.max(maxPower, Math.abs(frontRightPower))
    maxPower = Math.max(maxPower, Math.abs(backRightPower))
    maxPower = Math.max(maxPower, Math.abs(backLeftPower))

    frontLeftDrive.setPower(maxSpeed * (frontLeftPower / maxPower))
    frontRightDrive.setPower(maxSpeed * (frontRightPower / maxPower))
    backLeftDrive.setPower(maxSpeed * (backLeftPower / maxPower))
    backRightDrive.setPower(maxSpeed * (backRightPower / maxPower))
  }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: 