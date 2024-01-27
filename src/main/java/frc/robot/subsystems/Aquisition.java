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

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class Aquisition extends SubsystemBase {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
private CANSparkMax bottomShaft;
private CANSparkMax topShaft;
private DoubleSolenoid intakeSolenoid;
private DigitalInput noteDetector;
private DigitalInput intakeSwitch;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
private boolean triggercheck = false;
private boolean hasNote = false;

    /**
    *
    */
    public Aquisition() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
bottomShaft = new CANSparkMax(20, MotorType.kBrushless);
 
   
bottomShaft.setInverted(false);
bottomShaft.setIdleMode(IdleMode.kBrake);
bottomShaft.burnFlash();
  

topShaft = new CANSparkMax(21, MotorType.kBrushless);
 
   
topShaft.setInverted(false);
topShaft.setIdleMode(IdleMode.kBrake);
topShaft.burnFlash();
  

intakeSolenoid = new DoubleSolenoid(10, PneumaticsModuleType.CTREPCM, 5, 4);
 addChild("intakeSolenoid", intakeSolenoid);
 

noteDetector = new DigitalInput(0);
 addChild("noteDetector", noteDetector);
 

intakeSwitch = new DigitalInput(1);
 addChild("intakeSwitch", intakeSwitch);
 


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    intakeSolenoid.set(Value.kReverse);
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("intakeSwitch", isNoteCollected());
        // This method will be called once per scheduler run

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    

    public void deployIntake() {
        hasNote = false;
        intakeSolenoid.set(Value.kForward);
    }

    public void retractIntake() {
        intakeSolenoid.set(Value.kReverse);
    }

    public void spinBoth() {
        if (hasNote) {
            stopBoth();
        } else {
            bottomShaft.set(SmartDashboard.getNumber("Bottom Speed", 0.1));
            topShaft.set(SmartDashboard.getNumber("Top Speed", 0.1));
        }
    }
    public void stopBoth(){
        bottomShaft.set(0);
        topShaft.set(0);
        intakeSolenoid.set(Value.kReverse);
    }


    boolean isNoteCollected() {
        if (!intakeSwitch.get()){
            hasNote = true;
        }
        return !intakeSwitch.get();
    }
    public boolean finished() {
        return isNoteCollected();
    }
}

