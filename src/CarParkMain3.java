// Car Park Main Class 3 to show the implementation of Concurrency for the Upper/Second Floor.

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class CarParkMain3 {
    private static BambaCarParkManager bambaCarParkManager =  BambaCarParkManager.getInstance();

    public static void main(String[] args) {

        // Queue to handle vehicle selection using FIFO for Ground Floor.
        Queue<Vehicle> carQueue = new LinkedList<Vehicle>();
        Queue<Vehicle> vanQueue = new LinkedList<Vehicle>();
        Queue<Vehicle> motorbikeQueue = new LinkedList<Vehicle>();

        DateTime dateTime = new DateTime(2021 , 4 , 13, 22 , 51 , 10);

        // set Ground floor & First Floor parking lot to full in order to put vehicles to Upper Floor.
        bambaCarParkManager.setGroundFloorAvailableSlots(0);
        Queue<Vehicle> GFloorVehicleQueue = new LinkedList<Vehicle>();
        for (int i = 0; i < BambaCarParkManager.MAX_GROUND_FLOOR_AVAILABLE_LOT; i += 3) {
            GFloorVehicleQueue.offer(new Car("car-01"+i, "BMW", "BWB", dateTime, 4, Color.BLUE));
        }
        bambaCarParkManager.setGroundFloorParkedVehicles(GFloorVehicleQueue);

        bambaCarParkManager.setFirstFloorAvailableSlots(0);
        Queue<Vehicle> FFloorVehicleQueue = new LinkedList<Vehicle>();
        for (int i = 0; i < BambaCarParkManager.MAX_FIRST_FLOOR_AVAILABLE_LOT; i += 3) {
            FFloorVehicleQueue.offer(new Car("carF-01"+i, "BENZ", "A5", dateTime, 4, Color.BLUE));
        }
        bambaCarParkManager.setFirstFloorParkedVehicles(FFloorVehicleQueue);

        // Adding Vehicles to Queue.
        carQueue.offer(new Car("CAR-001", "Toyota", "Premio", dateTime, 4, Color.BLACK));
        carQueue.offer(new Car("CAR-002", "Toyota", "Prado", dateTime, 4, Color.WHITE));
        carQueue.offer(new Car("CAR-003", "Toyota", "Prius", dateTime, 4, Color.GRAY));

        vanQueue.offer(new Van("VAN-001", "Nissan", "Model1", dateTime, 23.5));
        vanQueue.offer(new Van("VAN-002", "Ford", "Model2", dateTime, 12.5));

        motorbikeQueue.offer(new MotorBike("BIKE-001", "Yamaha", "Fz-2", dateTime, "200cc"));

        // Implement Runnable for Ground Floor
        Runnable groundArrival = new Arrival(bambaCarParkManager, carQueue, vanQueue, motorbikeQueue, CarParkManager.GROUND_LEVEL);
        Runnable groundDeparture = new Departure(bambaCarParkManager, (GFloorVehicleQueue.size() +  carQueue.size() + vanQueue.size() + motorbikeQueue.size()), CarParkManager.GROUND_LEVEL + 1000);

        // Implement Threads
        Thread[] threads = new Thread[45];

        // Handling Northern Gate which is High Priority for GROUND FLOOR
        Thread northernGateEntry01 = new Thread(groundArrival, "Ground Floor Northern Gate Entry 01");
        Thread northernGateEntry02 = new Thread(groundDeparture, "Ground Floor Northern Gate Entry 02");
        northernGateEntry01.setPriority(Thread.MAX_PRIORITY);
        northernGateEntry02.setPriority(Thread.MAX_PRIORITY);
        threads[0] = northernGateEntry01;
        threads[1] = northernGateEntry02;

        // Handling Threads for Other Entry & Exit Point in Ground Level.
        threads[2] = new Thread(groundArrival, "Ground Floor West Entry Point");
        threads[3] = new Thread(groundDeparture, "Ground Floor West Exit Point");

        threads[4] = new Thread(groundArrival, "Ground Floor East Entry Point");
        threads[5] = new Thread(groundDeparture, "Ground Floor East Exit Point");

        threads[6] = new Thread(groundArrival, "Ground Floor South Entry Point");
        threads[7] = new Thread(groundDeparture, "Ground Floor South Exit Point");


        // Queue to handle vehicle selection using FIFO for First Floor.
        Queue<Vehicle> carQueueFirstFloor = new LinkedList<Vehicle>();
        Queue<Vehicle> vanQueueFirstFloor = new LinkedList<Vehicle>();
        Queue<Vehicle> motorbikeQueueFirstFloor = new LinkedList<Vehicle>();

        // Adding Vehicles to Queue for First Floor.
        carQueueFirstFloor.offer(new Car("FC-1122", "Toyota", "Premio", dateTime, 4, Color.BLACK));
        carQueueFirstFloor.offer(new Car("FC-5899", "Toyota", "Prado", dateTime, 4, Color.WHITE));

        vanQueueFirstFloor.offer(new Van("FV-2100", "Ford", "Model2", dateTime, 12.5));

        motorbikeQueueFirstFloor.offer(new MotorBike("FB-4009", "Yamaha", "Fz-2", dateTime, "200cc"));

        // Implement Runnable for First Floor
        Runnable firstArrival = new Arrival(bambaCarParkManager, carQueueFirstFloor, vanQueueFirstFloor, motorbikeQueueFirstFloor, CarParkManager.FIRST_LEVEL);
        Runnable firstDeparture = new Departure(bambaCarParkManager, (FFloorVehicleQueue.size() + carQueueFirstFloor.size() + vanQueueFirstFloor.size() + motorbikeQueueFirstFloor.size()), CarParkManager.FIRST_LEVEL);

        // Handling Threads for Other Entry & Exit Point in First Level.
        threads[8] = new Thread(firstArrival, "First Floor West Entry Point 01");
        threads[9] = new Thread(firstArrival, "First Floor West Entry Point 02");

        threads[10] = new Thread(firstDeparture, "First Floor East Exit Point 01");
        threads[11] = new Thread(firstDeparture, "First Floor East Exit Point 02");


        // Queue to handle vehicle selection using FIFO for Upper/Second Floor.
        Queue<Vehicle> carQueueUpperFloor = new LinkedList<Vehicle>();
        Queue<Vehicle> vanQueueUpperFloor = new LinkedList<Vehicle>();
        Queue<Vehicle> motorbikeQueueUpperFloor = new LinkedList<Vehicle>();

        // Adding Vehicles to Queue for Upper/Second Floor.
        carQueueUpperFloor.offer(new Car("SC-1122", "Toyota", "Premio", dateTime, 4, Color.BLACK));
        carQueueUpperFloor.offer(new Car("SC-5899", "Toyota", "Prado", dateTime, 4, Color.WHITE));

        vanQueueUpperFloor.offer(new Van("SV-2100", "Ford", "Model2", dateTime, 12.5));

        // Implement Runnable for Upper/Second Floor
        Runnable upperArrival = new Arrival(bambaCarParkManager, carQueueUpperFloor, vanQueueUpperFloor, motorbikeQueueUpperFloor, CarParkManager.SECOND_LEVEL);
        Runnable upperDeparture = new Departure(bambaCarParkManager, (carQueueUpperFloor.size() + vanQueueUpperFloor.size() + motorbikeQueueUpperFloor.size()),  CarParkManager.SECOND_LEVEL);

        // Create 12 Threads for 12 Lifts for Upper Floor.
        for (int i = 12; i < 24; i++) {
            threads[i] = new Thread(upperArrival, "Upper Floor Lift " + (i-11) + " Entry Point");
        }

        for (int i = 24; i < 36; i++) {
            threads[i] = new Thread(upperDeparture, "Upper Floor Lift " + (i-23) + " Exit Point");
        }

        // start all threads
        for (Thread thread: threads) {
            if (thread != null && !thread.isAlive()) {
//                System.out.println(thread.getName());
                thread.start();
            }
        }

    }
}
