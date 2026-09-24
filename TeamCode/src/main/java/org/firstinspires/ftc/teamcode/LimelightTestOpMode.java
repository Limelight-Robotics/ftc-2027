package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.navigation.Limelight3A;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.vision.limelight.LLResult;

@TeleOp(name = "LimelightTestOpMode", group = "Testing")
public class LimelightTestOpMode extends LinearOpMode {

    private Limelight3A limelight;

    @Override
    public void runOpMode() {
        // Initialize the Limelight from the hardware map
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        telemetry.setMsTransmissionInterval(11);
        telemetry.addData("Status", "Limelight Initialized. Waiting for start...");
        telemetry.update();

        // Start polling the camera data
        limelight.start();

        // Switch to pipeline 0
        limelight.pipelineSwitch(0);

        waitForStart();

        while (opModeIsActive()) {
            // Grab the latest telemetry frame from the camera
            LLResult result = limelight.getLatestResult();

            if (result != null && result.isValid()) {
                telemetry.addData("Target Found", "YES");
                telemetry.addData("TX (Horizontal Deg)", "%.2f", result.getTx());
                telemetry.addData("TY (Vertical Deg)", "%.2f", result.getTy());
                telemetry.addData("TA (Target Area %)", "%.2f", result.getTa());
                
                // Print out 3D Pose data if using AprilTags
                Pose3D botpose = result.getBotpose();
                if (botpose != null) {
                    telemetry.addData("Botpose X", "%.2f", botpose.getPosition().x);
                    telemetry.addData("Botpose Y", "%.2f", botpose.getPosition().y);
                }
            } else {
                telemetry.addData("Target Found", "NO");
                telemetry.addData("Status", "Searching for targets or camera offline...");
            }

            telemetry.update();
        }
        
        limelight.stop();
    }
}
