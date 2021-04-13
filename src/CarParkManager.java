

import java.math.BigDecimal;

public interface CarParkManager {
	
	public static final int MAX=20; //Number of slots available in the Car Park


	// Concurrency Implementation, BEGIN.

	// Number of Slot available in GROUND FLOOR, FIRST FLOOR, UPPER FLOOR.
	public static final int MAX_GROUND_FLOOR_AVAILABLE_LOT = 80 * 3;
	public static final int MAX_FIRST_FLOOR_AVAILABLE_LOT = 60 * 3;
	public static final int MAX_SECOND_FLOOR_AVAILABLE_LOT = 70 * 3;

	// Multi Parking Floor Levels
	public static final int GROUND_LEVEL = 0;
	public static final int FIRST_LEVEL = 1;
	public static final int SECOND_LEVEL = 2;

	// parkVehicle
	public void allowParkedVehicle(Vehicle obj, int floor);

	// exitVehicle
	public void exitParkedVehicle(int floor);

	// Concurrency Implementation, END.



	public void addVehicle(Vehicle obj);
	public void deleteVehicle(String IdPlate);
	public void printcurrentVehicles();
	public void printVehiclePercentage();
	public void printLongestPark();
	public void printLatestPark();
	public void printVehicleByDay(DateTime entryTime);
	public BigDecimal calculateChargers(String plateID, DateTime currentTime);
	
}
