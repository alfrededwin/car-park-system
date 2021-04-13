// Class to handle the Entry Gate Point of the gate.

import java.util.Queue;

public class Arrival implements Runnable {
    private CarParkManager carParkManager;
    private Queue<Vehicle> carQueue;
    private Queue<Vehicle> vanQueue;
    private Queue<Vehicle> motorbikeQueue;

    private int floor;

    // Constructor.
    public Arrival(CarParkManager carParkManager, Queue<Vehicle> carQueue, Queue<Vehicle> vanQueue, Queue<Vehicle> motorbikeQueue, int floor) {
        this.carParkManager = carParkManager;
        this.carQueue = carQueue;
        this.vanQueue = vanQueue;
        this.motorbikeQueue = motorbikeQueue;
        this.floor = floor;
    }

    @Override
    public void run() {

        // First Preference to Car.
        //if (carQueue.size() > 0) {
            for (int i=0; i<carQueue.size(); i++) {
                this.carParkManager.allowParkedVehicle(carQueue.poll(), floor);
            }
       // }

        // Second Preference to Van.
        //if (vanQueue.size() > 0) {
            for (int i=0; i<vanQueue.size(); i++) {
                this.carParkManager.allowParkedVehicle(vanQueue.poll(), floor);
            }
       // }

        // Last Preference to Motorbike
        //if (motorbikeQueue.size() > 0) {
            for (int i=0; i<motorbikeQueue.size(); i++) {
                this.carParkManager.allowParkedVehicle(motorbikeQueue.poll(), floor);
            }
        //}
    }


}
