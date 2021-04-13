

public class MotorBike extends Vehicle {
	private String engineSize;

	// Concurrency Implementation, Begin.
	public static final int ALLOWED_MOTOR_BIKE_UNIT = 1;
	private final String vehicleType = "MotorBike";
	// Concurrency Implementation, End.
	
	public MotorBike(String idPlate, String brand, String model, DateTime entryTime, String engineSize) {
		super(idPlate, brand, model, entryTime);
		this.engineSize=engineSize;
	}
	
	public String getEngineSize() {
		return engineSize;
	}
	public void setEngineSize(String engineSize) {
		this.engineSize=engineSize;
	}

	// Concurrency Implementation, Begin.
	public int getParkingUnit() { return ALLOWED_MOTOR_BIKE_UNIT; };
	public String getVehicleType() { return this.vehicleType; };
	// Concurrency Implementation, End.

}
