

import java.math.BigDecimal;

public interface CarParkManager {
	
	public static final int MAX=20; //Number of slots available in the Car Park


	// Concurrency Implementation, BEGIN.

	// Multi Parking Floor Levels
	public static final int GROUND_LEVEL = 0;
	public static final int FIRST_LEVEL = 1;
	public static final int SECOND_LEVEL = 2;

	// parkVehicle
	public void allowParkedVehicle(Vehicle obj, int floor);

	// exitVehicle
	public void exitParkedVehicle(int floor);

	// Remove a Vehicle
	public void removeVehicle(String IdPlate);

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
