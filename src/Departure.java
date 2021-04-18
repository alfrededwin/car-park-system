// Class to handle the Exit Gate Point of the gate.

public class Departure implements Runnable {

    private CarParkManager carParkManager;
    private final int noOfVehicles;
    private final int floor;

    public Departure(CarParkManager carParkManager, int noOfVehicles, int floor) {
        this.carParkManager = carParkManager;
        this.noOfVehicles = noOfVehicles;
        this.floor = floor;
    }

    @Override
    public void run() {
        for (int i=0; i<noOfVehicles; i++) {
            this.carParkManager.exitParkedVehicle(floor);
        }
    }
}
