// Car Park Main Class to show the implementation of Concurrency for the Ground Floor.

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class CarParkMain {

    // Bamba Car Park Manager Object
    private static BambaCarParkManager bambaCarParkManager = BambaCarParkManager.getInstance();

    public static void main(String[] args) {

      // Queue to handle vehicle selection using FIFO.
      Queue<Vehicle> carQueue = new LinkedList<Vehicle>();
      Queue<Vehicle> vanQueue = new LinkedList<Vehicle>();
      Queue<Vehicle> motorbikeQueue = new LinkedList<Vehicle>();

       DateTime dateTime = new DateTime(2021 , 4 , 7, 22 , 52 , 10);

       carQueue.offer(new Car("CAR-001", "Toyota", "Cruser", dateTime, 4, Color.black));
       carQueue.offer(new Car("CAR-002", "Mitsubishi", "Speedo", dateTime, 4, Color.darkGray));
       carQueue.offer(new Car("CAR-003", "Jeep", "Land Rover", dateTime, 4, Color.blue));
//       carQueue.offer(new Car("CAR-004", "tata", "Land Rover", dateTime, 4, Color.blue));

       vanQueue.offer(new Van("VAN-001", "Nissan", "M1", dateTime, 25.5));
       vanQueue.offer(new Van("VAN-002", "Pajero", "M2", dateTime, 24.9));

       motorbikeQueue.offer(new MotorBike("BIKE-001", "Royal", "Enfield", dateTime, "100-CC"));
//       motorbikeQueue.offer(new MotorBike("BIKE-002", "Royal", "Enfield", dateTime, "100-CC"));

       // Implement Runnable
       Runnable arrival = new Arrival(bambaCarParkManager, carQueue, vanQueue, motorbikeQueue, CarParkManager.GROUND_LEVEL);
       Runnable departure = new Departure(bambaCarParkManager, (carQueue.size() + vanQueue.size() + motorbikeQueue.size()), CarParkManager.GROUND_LEVEL);

       // Implement Threads
       Thread[] threads = new Thread[48];

       // Handling Northern Gate which is High Priority.
        Thread northernGateEntry01 = new Thread(arrival, "Ground Floor Northern Gate Entry 01");
        Thread northernGateEntry02 = new Thread(arrival, "Ground Floor Northern Gate Entry 02");

        // Set Max Priority to Northern Gate as they are the main Entry Points.
        northernGateEntry01.setPriority(Thread.MAX_PRIORITY);
        northernGateEntry02.setPriority(Thread.MAX_PRIORITY);
        threads[0] = northernGateEntry01;
        threads[1] = northernGateEntry02;

        // Handling Threads for Other Entry & Exit Point in Ground Level.
        threads[2] = new Thread(arrival, "Ground Floor West Entry Point");
        threads[3] = new Thread(departure, "Ground Floor West Exit Point");

        threads[4] = new Thread(arrival, "Ground Floor East Entry Point");
        threads[5] = new Thread(departure, "Ground Floor East Exit Point");

        threads[6] = new Thread(arrival, "Ground Floor South Entry Point");
        threads[7] = new Thread(departure, "Ground Floor South Exit Point");

        // Start All above Threads
        for (Thread thread: threads) {
            if(thread != null && !thread.isAlive()) {
//                System.out.println(thread.getName());
                thread.start();
            }
        }

    }


}
