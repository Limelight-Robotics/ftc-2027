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

    fun resetOdometry() {
        odometry.resetPosAndIMU()
        odometry.update()

        telemetry.addLine("Odometry computer reset")
        telemetry.update()
    }

    override fun init() {
        odometry = hardwareMap.get(GoBildaPinpointDriver::class.java, "odo")
        odometry.setEncoderDirections(
            GoBildaPinpointDriver.EncoderDirection.FORWARD,
            GoBildaPinpointDriver.EncoderDirection.REVERSED
        )
        odometry.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_SWINGARM_POD)
        resetOdometry()
    }

    override fun loop() {
        if (gamepad1.a && !resetButtonWasPressed) {
            resetOdometry()
        }
        resetButtonWasPressed = gamepad1.a

        odometry.update()
        telemetry.addData("Device status", odometry.deviceStatus)
        /*
        telemetry.addData("X encoder count", odometry.encoderX)
        telemetry.addData("Y encoder count", odometry.encoderY)
        */
        telemetry.addData("X position", "%.2f m", odometry.getPosX(DistanceUnit.METER))
        telemetry.addData("Y position", "%.2f m", odometry.getPosY(DistanceUnit.METER))
        telemetry.addData("Heading", "%.1f deg", odometry.getHeading(AngleUnit.DEGREES))
        telemetry.update()
    }
}
