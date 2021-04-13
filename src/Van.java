

public class Van extends Vehicle {
	//Properties
	private double cargoVolume;

	// Concurrency Implementation, Begin.
	public static final int ALLOWED_MOTOR_BIKE_UNIT = 6;
	private final String vehicleType = "Van";
	// Concurrency Implementation, End.
	
	//Constructor
	public Van(String idPlate, String brand, String model, DateTime entryTime, double cargoVolume) {
		super(idPlate, brand, model, entryTime);
		this.cargoVolume=cargoVolume;
	}
	
	public double getCargoVolume() {
		return cargoVolume;
	}
	public void setCargoVolume(double cargoVolume) {
		this.cargoVolume=cargoVolume;
	}

	// Concurrency Implementation, Begin.
	public int getParkingUnit() { return ALLOWED_MOTOR_BIKE_UNIT; };
	public String getVehicleType() { return this.vehicleType; };
	// Concurrency Implementation, End.

}
