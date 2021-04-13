// Car PARK Main Class 2 to show the implementation of Concurrency for the First Floor.

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class CarkParkMain2 {
    private static BambaCarParkManager bambaCarParkManager =  BambaCarParkManager.getInstance();

    public static void main(String[] args) {

        // Queue to handle vehicle selection using FIFO.
        Queue<Vehicle> carQueue = new LinkedList<Vehicle>();
        Queue<Vehicle> vanQueue = new LinkedList<Vehicle>();
        Queue<Vehicle> motorbikeQueue = new LinkedList<Vehicle>();

        DateTime dateTime = new DateTime(2021 , 4 , 7, 22 , 52 , 10);

        // set Ground floor parking lot to full in order to put vehicles to First Floor.
        bambaCarParkManager.setGroundFloorAvailableSlots(0);
        Queue<Vehicle> gfVehicleQueue = new LinkedList<Vehicle>();
        for (int i = 0; i < CarParkManager.MAX_GROUND_FLOOR_AVAILABLE_LOT; i += 3) {
            gfVehicleQueue.offer(new Car("car-0"+i, "Toyota", "Premio", dateTime, 4, Color.BLUE));
        }
        bambaCarParkManager.setGroundFloorParkedVehicles(gfVehicleQueue);

        carQueue.offer(new Car("CAR-001", "Toyota", "Premio", dateTime, 4, Color.BLACK));
        carQueue.offer(new Car("CAR-002", "Toyota", "Prado", dateTime, 4, Color.WHITE));
        carQueue.offer(new Car("CAR-003", "Toyota", "Prius", dateTime, 4, Color.GRAY));

        vanQueue.offer(new Van("VAN-001", "Nissan", "Model1", dateTime, 23.5));
        vanQueue.offer(new Van("VAN-002", "Ford", "Model2", dateTime, 12.5));

        motorbikeQueue.offer(new MotorBike("BIKE-001", "Yamaha", "Fz-2", dateTime, "200cc"));

        // create runnables for ground floor gates
        Runnable groundArrival = new Arrival(bambaCarParkManager, carQueue, vanQueue, motorbikeQueue, CarParkManager.GROUND_LEVEL);
        Runnable groundDeparture = new Departure(bambaCarParkManager, (carQueue.size() + vanQueue.size() + motorbikeQueue.size()), CarParkManager.GROUND_LEVEL);


        Thread[] threads = new Thread[45];

        // ground floor northern gates with highest priority
        Thread northernGateIn1 = new Thread(groundArrival, "Ground Northern gate entry 1");
        Thread northernGateIn2 = new Thread(groundDeparture, "Ground Northern gate entry 2");
        northernGateIn1.setPriority(Thread.MAX_PRIORITY);
        northernGateIn2.setPriority(Thread.MAX_PRIORITY);
        threads[0] = northernGateIn1;
        threads[1] = northernGateIn2;

        // other ground floor threads
        threads[2] = new Thread(groundArrival, "Ground West gate entry");
        threads[3] = new Thread(groundDeparture, "Ground West gate exit");

        threads[4] = new Thread(groundArrival, "Ground East gate entry");
        threads[5] = new Thread(groundDeparture, "Ground East gate exit");

        threads[6] = new Thread(groundArrival, "Ground South gate entry");
        threads[7] = new Thread(groundDeparture, "Ground South gate exit");

        
        // create some vehciles for first floor gates
        Queue<Vehicle> carQueue2 = new LinkedList<Vehicle>();
        Queue<Vehicle> vanQueue2 = new LinkedList<Vehicle>();
        Queue<Vehicle> motorbikeQueue2 = new LinkedList<Vehicle>();

        // set some vehicles to first floor
        carQueue2.offer(new Car("FC-1122", "Toyota", "Premio", dateTime, 4, Color.BLACK));
        carQueue2.offer(new Car("FC-5899", "Toyota", "Prado", dateTime, 4, Color.WHITE));

        vanQueue2.offer(new Van("FV-2100", "Ford", "Model2", dateTime, 12.5));

        motorbikeQueue2.offer(new MotorBike("FB-4009", "Yamaha", "Fz-2", dateTime, "200cc"));

        // create runnables for ground floor gates
        Runnable firstArrival = new Arrival(bambaCarParkManager, carQueue2, vanQueue2, motorbikeQueue2, CarParkManager.FIRST_LEVEL);
        Runnable firstDeparture = new Departure(bambaCarParkManager, (carQueue2.size() + vanQueue2.size() + motorbikeQueue2.size()), CarParkManager.FIRST_LEVEL);

        // first floor threads
        threads[8] = new Thread(firstArrival, "First floor West gate entry 1");
        threads[9] = new Thread(firstArrival, "First floor West gate entry 2");

        threads[10] = new Thread(firstDeparture, "First floor East gate exit 1");
        threads[11] = new Thread(firstDeparture, "First floor East gate exit 2");

//        // second floor threads
//        // no cars for second floor for this example, empty vehicle list for second floor
//        Queue<Vehicle> carQueue3 = new LinkedList<Vehicle>();
//        Queue<Vehicle> vanQueue3 = new LinkedList<Vehicle>();
//        Queue<Vehicle> motorbikeQueue3 = new LinkedList<Vehicle>();
//        // create runnables for ground floor gates
//        Runnable liftIn = new GateIn(bambaCarParkManager, CarParkManager.SECOND_FLOOR_LEVEL, carQueue3, vanQueue3, motorbikeQueue3);
//        Runnable liftOut = new GateOut(bambaCarParkManager, CarParkManager.SECOND_FLOOR_LEVEL, (carQueue3.size() + vanQueue3.size() + motorbikeQueue3.size() + 1000));
//
//        // create 12 threads for second floor
//        // create 12 threads for second floor
//        for (int i = 12; i < 24; i++) {
//            threads[i] = new Thread(liftIn, "Second floor lift " + (i-11) + " entry");
//        }
//
//        for (int i = 24; i < 36; i++) {
//            threads[i] = new Thread(liftOut, "Second floor lift " + (i-23) + " exit");
//        }






        for (Thread thread: threads) {
            if (thread != null && !thread.isAlive()) {
//                System.out.println(thread.getName());
                thread.start();
            }
        }

    }

}
