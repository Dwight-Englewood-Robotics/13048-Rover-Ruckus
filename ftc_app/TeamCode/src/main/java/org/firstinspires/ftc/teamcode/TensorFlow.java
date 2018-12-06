package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.State;
import org.firstinspires.ftc.teamcode.Subsystem;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

public class TensorFlow implements Subsystem {

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final String VUFORIA_KEY = "AbZUuPf/////AAAAGUmS0Chan00iu7rnRhzu63+JgDtPo889M6dNtjvv+WKxiMJ8w2DgSJdM2/zEI+a759I7DlPj++D2Ryr5sEHAg4k1bGKdo3BKtkSeh8hCy78w0SIwoOACschF/ImuyP/V259ytjiFtEF6TX4teE8zYpQZiVkCQy0CmHI9Ymoa7NEvFEqfb3S4P6SicguAtQ2NSLJUX+Fdn49SEJKvpSyhwyjbrinJbak7GWqBHcp7fGh7TNFcfPFMacXg28XxlvVpQaVNgkvuqolN7wkTiR9ZMg6Fnm0zN4Xjr5lRtDHeE51Y0bZoBUbyLWSA+ts3SyDjDPPUU7GMI+Ed/ifb0csVpM12aOiNr8d+HsfF2Frnzrj2";

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    Telemetry tele;

 //  public TensorFlow() {}

    public enum TFState implements State {
        // crater angle, depot angle, crater distance, depot distance; all in degrees & cm
        LEFT("Left", 0, 0, 0, 0), //temp vals
        CENTER("Center", 0, 0, 0, 0), //temp vals
        RIGHT("Right", 0, 0, 0, 0), //temp vals
        NOTVISIBLE("None", 0, 0, 0, 0);

        private String str;
        private int craterAng;
        private int depotAng;
        private int craterDist;
        private int depotDist;

        TFState(String str, int craterAng, int depotAng, int craterDist, int depotDist) {
            this.str = str;
            this.craterAng = craterAng;
            this.depotAng = depotAng;
            this.craterDist = craterDist;
            this.depotDist = depotDist;
        }

        @Override
        public String toString() {
            return super.toString();
        }
        public int getCraterAng(){
            return craterAng;
        }
        public int getDepotAng(){
            return depotAng;
        }
        public int getCraterDist(){
            return craterDist;
        }
        public int getDepotDist(){
            return depotDist;
        }
    }

    private TFState state;

    @Override
    public void init(HardwareMap hwMap) {
        this.tele = tele;
        this.initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            this.initTfod(hwMap);
        }
    }

    @Override
    public void start() {
        if (tfod != null) {
            tfod.activate();
        }
    }

    @Override
    public void reset() {

    }

    @Override
    public void stop() {
        if (tfod != null) {
            tfod.shutdown();
        }
    }

    @Override
    public TFState getState() {
        this.updateState();
        return state;
    }

    private void updateState() {
        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                if (updatedRecognitions.size() == 3) {
                    int goldMineralX = -1;
                    int silverMineral1X = -1;
                    int silverMineral2X = -1;
                    for (Recognition recognition : updatedRecognitions) {
                        if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                            goldMineralX = (int) recognition.getLeft();
                        } else if (silverMineral1X == -1) {
                            silverMineral1X = (int) recognition.getLeft();
                        } else {
                            silverMineral2X = (int) recognition.getLeft();
                        }
                    }
                    if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                        if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                            this.state = TFState.LEFT;
                        } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                            this.state = TFState.RIGHT;
                        } else {
                            this.state = TFState.CENTER;
                        }
                    }
                } else {
                    this.state = TFState.NOTVISIBLE;
                }
            }
        }
    }

    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod(HardwareMap hardwareMap) {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}