package org.firstinspires.ftc.teamcode

import com.qualcomm.hardware.limelightvision.LLResult
import com.qualcomm.hardware.limelightvision.Limelight3A
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import kotlin.math.max
import kotlin.math.min
import kotlin.math.atan2
import kotlin.math.PI
import kotlin.math.sqrt
import kotlin.math.sign

@Autonomous(name = "Chase AprilTag", group = "Robot")
class MecanumAprilTagDistance : LinearOpMode() {
    private val targetDistanceMeters = 1.25
    private val targetTagId = -1
    private val forwardGain = 0.80
    private val strafeGain = 0.25
    private val rotateGain = 1.0
    private val drivePowerMultiplier = 0.25 // 25% speed

    override fun runOpMode() {
        val robot = Robot(hardwareMap)
        val limelight = hardwareMap.get(Limelight3A::class.java, "limelight")

        telemetry.setMsTransmissionInterval(50)
        limelight.pipelineSwitch(0)
        limelight.start()
        telemetry.addLine("Limelight ready; point it at an AprilTag")
        telemetry.update()
        waitForStart()

        try {
            while (opModeIsActive()) {
                val result = limelight.latestResultForTag(targetTagId)
                val targetPose = result?.getFiducialResults()
                    ?.firstOrNull { targetTagId < 0 || it.fiducialId == targetTagId }
                    ?.targetPoseRobotSpace

                if (targetPose == null) {
                    robot.stop()
                    telemetry.addLine("No AprilTag target")
                } else {
                    val position = targetPose.position
                    val horizontalDistance = sqrt(position.x * position.x + position.z * position.z)
                    val forwardError = horizontalDistance - targetDistanceMeters
                    val forwardPower = chasePower(forwardError, forwardGain, 0.65)
                    val rightPower = clipped(position.x * strafeGain, 0.30) * drivePowerMultiplier
                    val bearingRadians = atan2(position.x, position.z)
                    val rotatePower = clipped(bearingRadians * rotateGain, 0.45) * drivePowerMultiplier
                    robot.drive(forwardPower, rightPower, rotatePower)

                    telemetry.addData("Tag distance", "%.2f m", horizontalDistance)
                    telemetry.addData("Distance error", "%.2f m", forwardError)
                    telemetry.addData("Lateral error", "%.2f m", position.x)
                    telemetry.addData("Forward depth", "%.2f m", position.z)
                    telemetry.addData("Bearing", "%.1f deg", bearingRadians * 180.0 / PI)
                    telemetry.addData("Rotate power", "%.2f", rotatePower)
                    telemetry.addData("Tag", result.tagId())
                }
                telemetry.update()
                idle()
            }
        } finally {
            robot.stop()
            limelight.stop()
        }
    }

    private fun clipped(value: Double, limit: Double): Double {
        return max(-limit, min(limit, value))
    }

    private fun chasePower(error: Double, gain: Double, limit: Double): Double {
        if (kotlin.math.abs(error) < 0.10) return 0.0
        val proportionalPower = clipped(error * gain, limit)
        val minimumPower = 0.15 * sign(error)
        val power = if (kotlin.math.abs(proportionalPower) < kotlin.math.abs(minimumPower)) {
            minimumPower
        } else {
            proportionalPower
        }
        return power * drivePowerMultiplier
    }

    private fun LLResult.tagId(): Int {
        return getFiducialResults().firstOrNull()?.fiducialId ?: -1
    }

    private fun Limelight3A.latestResultForTag(tagId: Int): LLResult? {
        val result = latestResult ?: return null
        if (!result.isValid) return null
        if (tagId < 0 || result.getFiducialResults().any { it.fiducialId == tagId }) return result
        return null
    }
}
