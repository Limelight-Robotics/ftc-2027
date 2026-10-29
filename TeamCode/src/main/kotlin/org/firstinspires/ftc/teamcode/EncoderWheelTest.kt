package org.firstinspires.ftc.teamcode

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit

@TeleOp(name = "Encoder Wheel Test", group = "Testing")
class EncoderWheelTest : OpMode() {
    private lateinit var odometry: GoBildaPinpointDriver
    private var resetButtonWasPressed = false

    override fun init() {
        odometry = hardwareMap.get(GoBildaPinpointDriver::class.java, "odo")
        odometry.setEncoderDirections(
            GoBildaPinpointDriver.EncoderDirection.FORWARD,
            GoBildaPinpointDriver.EncoderDirection.FORWARD
        )
        odometry.resetPosAndIMU()

        telemetry.addLine("A resets the odometry computer (no work rn)")
        telemetry.update()
    }

    override fun loop() {
        val resetButtonPressed = gamepad1.a
        if (resetButtonPressed && !resetButtonWasPressed) {
            odometry.resetPosAndIMU()
        }
        resetButtonWasPressed = resetButtonPressed

        odometry.update()
        telemetry.addData("Device status", odometry.deviceStatus)
        telemetry.addData("X encoder count", odometry.encoderX)
        telemetry.addData("Y encoder count", odometry.encoderY)
        telemetry.addData("X position", "%.2f in", odometry.getPosX(DistanceUnit.INCH))
        telemetry.addData("Y position", "%.2f in", odometry.getPosY(DistanceUnit.INCH))
        telemetry.addData("Heading", "%.1f deg", odometry.getHeading(AngleUnit.DEGREES))
        telemetry.update()
    }
}
