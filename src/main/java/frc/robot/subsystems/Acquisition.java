// RobotBuilder Version: 6.1
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: Subsystem.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class Acquisition extends SubsystemBase {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
private DoubleSolenoid intakeSolenoid;
private DigitalInput noteDetectorAcquisition;
private DigitalInput intakeInSwitch;
private DigitalInput intakeOutSwitch;
private PWMSparkMax bottomShaft;
private PWMSparkMax topShaft;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private double intakeGearRatio = Constants.MaxRPMConstants.maxRPMNeo550 / 7;
    public double bottomShaftSpeed;
    public double topShaftSpeed;

    /**
    *
    */
    public Acquisition() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
intakeSolenoid = new DoubleSolenoid(20, PneumaticsModuleType.REVPH, 5, 4);
 addChild("intakeSolenoid", intakeSolenoid);
 

noteDetectorAcquisition = new DigitalInput(1);
 addChild("noteDetectorAcquisition", noteDetectorAcquisition);
 

intakeInSwitch = new DigitalInput(0);
 addChild("intakeInSwitch", intakeInSwitch);
 

intakeOutSwitch = new DigitalInput(9);
 addChild("IntakeOutSwitch", intakeOutSwitch);
 

bottomShaft = new PWMSparkMax(0);
 addChild("bottomShaft",bottomShaft);
 bottomShaft.setInverted(false);

topShaft = new PWMSparkMax(1);
 addChild("topShaft",topShaft);
 topShaft.setInverted(false);


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // topShaft.setSmartCurrentLimit(20);
        // bottomShaft.setSmartCurrentLimit(20);
        intakeSolenoid.set(Value.kReverse);
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("isNoteInAquisition", isNoteInAcquisition());
        SmartDashboard.putBoolean("driver/Intake Retracted", isIntakeRetracted());
        SmartDashboard.putBoolean("driver/Intake Out", isIntakeOut());
        
        // This method will be called once per scheduler run
        if (readyToTransfer()) {
            bottomShaft.set(bottomShaftSpeed / intakeGearRatio);
            topShaft.set(topShaftSpeed / intakeGearRatio);
        }
        if (!isIntakeRetracted() && isNoteInAcquisition()) {
            stopBoth();
            retractIntake();
        }
        if (isIntakeRetracted() && !isNoteInAcquisition()) {
            stopBoth();
        }

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void deployIntake() {
        intakeSolenoid.set(Value.kForward);
    }

    public void retractIntake() {
        intakeSolenoid.set(Value.kReverse);
    }

    public void runIntakeIn() {
        if (isNoteInAcquisition()) {
            stopBoth();
        } else {
            topShaftSpeed = SmartDashboard.getNumber("Top Shaft RPM", 120);
            bottomShaftSpeed = SmartDashboard.getNumber("Bottom Shaft RPM", 120);

            bottomShaft.set(bottomShaftSpeed / intakeGearRatio);
            topShaft.set(topShaftSpeed / intakeGearRatio);
        }
    }

    public void stopBoth() {
        bottomShaft.set(0);
        topShaft.set(0);
    }

    public boolean readyToTransfer() {
        return isNoteInAcquisition() && isIntakeRetracted();
    }

    public boolean isNoteInAcquisition() {
        return noteDetectorAcquisition.get();
    }

    boolean isIntakeRetracted() {
        return !intakeInSwitch.get();
    }

    boolean isIntakeOut() {
        return !intakeOutSwitch.get();
    }
}
