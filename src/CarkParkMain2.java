// Car Park Main Class 2 to show the implementation of Concurrency for the First Floor.

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class CarkParkMain2 {
    private static BambaCarParkManager bambaCarParkManager =  BambaCarParkManager.getInstance();

    public static void main(String[] args) {

        // Queue to handle vehicle selection using FIFO for Ground Floor.
        Queue<Vehicle> carQueue = new LinkedList<Vehicle>();
        Queue<Vehicle> vanQueue = new LinkedList<Vehicle>();
        Queue<Vehicle> motorbikeQueue = new LinkedList<Vehicle>();

        DateTime dateTime = new DateTime(2021 , 4 , 13, 22 , 51 , 10);

        // set Ground floor parking lot to full in order to put vehicles to First Floor.
        bambaCarParkManager.setGroundFloorAvailableSlots(0);
        Queue<Vehicle> groundVehicleQueue = new LinkedList<Vehicle>();
        for (int i = 0; i < BambaCarParkManager.MAX_GROUND_FLOOR_AVAILABLE_LOT; i += 3) {
            groundVehicleQueue.offer(new Car("car-01"+i, "Toyota", "Premio", dateTime, 4, Color.BLUE));
        }
        bambaCarParkManager.setGroundFloorParkedVehicles(groundVehicleQueue);

        // Adding Vehicles to Queue.
        carQueue.offer(new Car("CAR-001", "Toyota", "Premio", dateTime, 4, Color.BLACK));
        carQueue.offer(new Car("CAR-002", "Toyota", "Prado", dateTime, 4, Color.WHITE));
        carQueue.offer(new Car("CAR-003", "Toyota", "Prius", dateTime, 4, Color.GRAY));

        vanQueue.offer(new Van("VAN-001", "Nissan", "Model1", dateTime, 23.5));
        vanQueue.offer(new Van("VAN-002", "Ford", "Model2", dateTime, 12.5));

        motorbikeQueue.offer(new MotorBike("BIKE-001", "Yamaha", "Fz-2", dateTime, "200cc"));

        // Implement Runnable for Ground Floor
        Runnable groundArrival = new Arrival(bambaCarParkManager, carQueue, vanQueue, motorbikeQueue, CarParkManager.GROUND_LEVEL);
        Runnable groundDeparture = new Departure(bambaCarParkManager, (carQueue.size() + vanQueue.size() + motorbikeQueue.size()), CarParkManager.GROUND_LEVEL);

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
        Runnable firstDeparture = new Departure(bambaCarParkManager, (carQueueFirstFloor.size() + vanQueueFirstFloor.size() + motorbikeQueueFirstFloor.size()), CarParkManager.FIRST_LEVEL);

        // Handling Threads for Other Entry & Exit Point in First Level.
        threads[8] = new Thread(firstArrival, "First Floor West Entry Point 01");
        threads[9] = new Thread(firstArrival, "First Floor West Entry Point 02");

        threads[10] = new Thread(firstDeparture, "First Floor East Exit Point 01");
        threads[11] = new Thread(firstDeparture, "First Floor East Exit Point 02");

        for (Thread thread: threads) {
            if (thread != null && !thread.isAlive()) {
//                System.out.println(thread.getName());
                thread.start();
            }
        }

    }

}
