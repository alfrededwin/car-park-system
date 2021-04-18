

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class BambaCarParkManager implements CarParkManager{
	
	private ArrayList<Vehicle> listOfVehicle = new ArrayList<Vehicle>();
	private static BambaCarParkManager instance = null;
	private int availableSlots = 20; 
	private double chargePerHour = 300;
	private double addCharge = 100;
	private double maxCharge = 3000;
	private int addFromthisHour =3;

	// Concurrency Implementation, Begin.

	// Number of Slot available in GROUND FLOOR, FIRST FLOOR, UPPER FLOOR.
	public static final int MAX_GROUND_FLOOR_AVAILABLE_LOT = 80 * 3; // Maximum Number of Parking Lots in Ground Floor.
	public static final int MAX_FIRST_FLOOR_AVAILABLE_LOT = 60 * 3; // Maximum Number of Parking Lots in First Floor.
	public static final int MAX_SECOND_FLOOR_AVAILABLE_LOT = 70 * 3; // Maximum Number of Parking Lots in Upper Floor

	// Assign Maximum Available Parking Lots to particular Floors.
	private int groundFloorAvailableLots = MAX_GROUND_FLOOR_AVAILABLE_LOT;
	private int firstFloorAvailableLots = MAX_FIRST_FLOOR_AVAILABLE_LOT;
	private int upperFloorAvailableLots = MAX_SECOND_FLOOR_AVAILABLE_LOT;

	private Queue<Vehicle> groundFloorParkedVehicle = new LinkedList<Vehicle>();
	private Queue<Vehicle> firstFloorParkedVehicle = new LinkedList<Vehicle>();
	private Queue<Vehicle> upperFloorParkedVehicle = new LinkedList<Vehicle>();

	// Available Parking Lifts in the Car Park.
	private int ParkingLifts = 12;

	// Concurrency Implementation, End.
	
	//private constructor
	private BambaCarParkManager() {
	}
	
	//method which returns an object of same type
	public static BambaCarParkManager getInstance() {
		if(instance == null) {
			synchronized(BambaCarParkManager.class){
				if(instance==null) {
					instance = new BambaCarParkManager();
				}
			}
		}
		return instance;
	}

	// Concurrency Implementation, Begin.

	// Add Vehicle
	@Override
	public void addVehicle(Vehicle obj) {
		listOfVehicle.add(obj);
	}

	public void setGroundFloorAvailableSlots(int groundFloorAvailableSlots) {
		this.groundFloorAvailableLots = groundFloorAvailableSlots;
	}

	public void setFirstFloorAvailableSlots(int firstFloorAvailableSlots) {
		this.firstFloorAvailableLots = firstFloorAvailableSlots;
	}

	public void setSecondFloorAvailableSlots(int secondFloorAvailableSlots) {
		this.upperFloorAvailableLots = secondFloorAvailableSlots;
	}

	public void setGroundFloorParkedVehicles(Queue<Vehicle> groundFloorParkedVehicles) {
		this.groundFloorParkedVehicle = groundFloorParkedVehicles;
	}

	public void setFirstFloorParkedVehicles(Queue<Vehicle> firstFloorParkedVehicles) {
		this.firstFloorParkedVehicle = firstFloorParkedVehicles;
	}

	public void setSecondFloorParkedVehicles(Queue<Vehicle> secondFloorParkedVehicles) {
		this.upperFloorParkedVehicle = secondFloorParkedVehicles;
	}

	// Park Vehicle in a parking lot.
	public synchronized void allowParkedVehicle(Vehicle obj, int floor) {
		boolean parked = false;

		// Check for available parking lots from the Upper Floor.
		while (floor == SECOND_LEVEL &&
				(groundFloorAvailableLots >= obj.getVehicleUnit() || firstFloorAvailableLots >= obj.getVehicleUnit() || ParkingLifts == 0)) {
			try {
				System.out.println("Waiting at S Entry Point ");
				wait(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Check for available parking lots from the First Floor.
		while (floor == FIRST_LEVEL && (groundFloorAvailableLots >= obj.getVehicleUnit())) {
			try {
				System.out.println("Waiting at F Entry Point ");
				wait(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Check for available parking lots from the respective floors.
		while ((floor == GROUND_LEVEL && firstFloorAvailableLots == 0)
				|| (floor == FIRST_LEVEL && firstFloorAvailableLots == 0)
				|| (floor == SECOND_LEVEL && upperFloorAvailableLots == 0)) {
			try {
				// Wait for 1 seconds
				System.out.println("Waiting at G Entry Point ");
				wait(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}


		// Allow Cars & Vans to all Floors
		if (obj instanceof Car || obj instanceof Van) {
			// First Allow the Vehicles to the Ground Floor. Then if the ground is full, admit to other floors.
			if (groundFloorAvailableLots >= obj.getVehicleUnit()) {
				groundFloorAvailableLots -= obj.getVehicleUnit();
				groundFloorParkedVehicle.offer(obj);
				System.out.println(Thread.currentThread().getName());
				System.out.println(obj.getVehicleType() + " with plate ID: "+ obj.getIdPlate() +" parked in the Ground Floor.");
				System.out.println("-------------------------------------------------------------");
				parked = true;
			} else if (firstFloorAvailableLots >= obj.getVehicleUnit()) {
				firstFloorAvailableLots -= obj.getVehicleUnit();
				firstFloorParkedVehicle.offer(obj);
				System.out.println(Thread.currentThread().getName());
				System.out.println(obj.getVehicleType() + " with plate ID: "+ obj.getIdPlate() +" parked in the First Floor.");
				System.out.println("-------------------------------------------------------------");
				parked = true;

			} else if (upperFloorAvailableLots >= obj.getVehicleUnit()) {
				ParkingLifts--;
				upperFloorAvailableLots -= obj.getVehicleUnit();
				upperFloorParkedVehicle.offer(obj);
				System.out.println(Thread.currentThread().getName());
				System.out.println(obj.getVehicleType() + " with plate ID: "+ obj.getIdPlate() +" parked in the Upper Floor.");
				System.out.println("-------------------------------------------------------------");
				ParkingLifts++;
				parked = true;
			}
		}

		// Motor Bike is allowed for GROUND FLOOR & FIRST FLOOR.
		if (obj instanceof MotorBike) {
			if (groundFloorAvailableLots >= obj.getVehicleUnit()) {
				groundFloorAvailableLots -= obj.getVehicleUnit();
				groundFloorParkedVehicle.offer(obj);
				System.out.println(Thread.currentThread().getName());
				System.out.println(obj.getVehicleType() + " with plate ID: "+ obj.getIdPlate() +" parked in the Ground Floor.");
				System.out.println("------------------------------------------------------------");
				parked = true;
			} else if (firstFloorAvailableLots >= obj.getVehicleUnit()) {
				firstFloorAvailableLots -= obj.getVehicleUnit();
				firstFloorParkedVehicle.offer(obj);
				System.out.println(Thread.currentThread().getName());
				System.out.println(obj.getVehicleType() + " with plate ID: "+ obj.getIdPlate() +" parked in the First Floor.");
				System.out.println("-------------------------------------------------------------");
				parked = true;
			}

			if (parked) {
				this.addVehicle(obj);
				System.out.println("-------SUMMARY OF AVAILABLE PARKING LOTS AFTER ARRIVAL(ENTRY) OF VEHICLES --------");
				System.out.println("Ground Floor available parking lots : " + groundFloorAvailableLots/3.0);
				System.out.println("First Floor available parking lots : " + firstFloorAvailableLots/3.0);
				System.out.println("Upper Floor available parking lots : " + upperFloorAvailableLots/3.0);
				System.out.println("-----------------------------------------------------------------------------------");
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			notifyAll();
		}
	}

	// Exit Vehicle
	public synchronized void exitParkedVehicle(int floor) {
		boolean parked = false;
		int totalAvailableParkingLots = groundFloorAvailableLots + firstFloorAvailableLots + upperFloorAvailableLots;
		int TOTAL_PARKING_LOTS = MAX_GROUND_FLOOR_AVAILABLE_LOT + MAX_FIRST_FLOOR_AVAILABLE_LOT + MAX_SECOND_FLOOR_AVAILABLE_LOT;

		while (totalAvailableParkingLots == TOTAL_PARKING_LOTS) {
			try {
				System.out.println("Waiting at Exit Point ");
				wait(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Exit Vehicle from Ground Level.
		if ((floor == GROUND_LEVEL && groundFloorAvailableLots < MAX_GROUND_FLOOR_AVAILABLE_LOT)) {
			while (groundFloorAvailableLots < MAX_GROUND_FLOOR_AVAILABLE_LOT) {
				Vehicle obj = groundFloorParkedVehicle.poll();
				groundFloorAvailableLots += obj.getVehicleUnit();
				System.out.println(Thread.currentThread().getName());
				System.out.println(obj.getVehicleType() + " with plate ID: " + obj.getIdPlate() + " has exited from Ground Floor");
				System.out.println("--------------------------------------------------------------");
				this.removeVehicle(obj.getIdPlate());
				parked = true;
			}
		}

		// Exit Vehicle from First Floor,
		if (floor == FIRST_LEVEL && firstFloorAvailableLots < MAX_FIRST_FLOOR_AVAILABLE_LOT) {
			while (firstFloorAvailableLots < MAX_FIRST_FLOOR_AVAILABLE_LOT) {
				Vehicle obj = firstFloorParkedVehicle.poll();
				firstFloorAvailableLots += obj.getVehicleUnit();

				System.out.println(Thread.currentThread().getName());
				System.out.println(obj.getVehicleType() + " with plate ID: " + obj.getIdPlate() + " has exited from First Floor");
				System.out.println("-------------------------------------------------------------");
				this.removeVehicle(obj.getIdPlate());
				parked = true;
			}
		}

		// Exit Vehicle from Second Floor,
		if (floor == SECOND_LEVEL && upperFloorAvailableLots < MAX_SECOND_FLOOR_AVAILABLE_LOT) {
			while (upperFloorAvailableLots < MAX_SECOND_FLOOR_AVAILABLE_LOT) {
				ParkingLifts--;
				Vehicle obj = upperFloorParkedVehicle.poll();
				upperFloorAvailableLots += obj.getVehicleUnit();

				System.out.println(Thread.currentThread().getName());
				System.out.println(obj.getVehicleType() + " with plate ID: " + obj.getIdPlate() + " has exited from Upper Floor");
				System.out.println("-------------------------------------------------------------");
				this.removeVehicle(obj.getIdPlate());
				ParkingLifts++;
				parked = true;
			}
		}

		if (parked) {
			System.out.println("-------SUMMARY OF AVAILABLE PARKING LOTS AFTER DEPARTURE(EXIT) OF VEHICLES --------");
			System.out.println("Ground Floor available parking lots : " + groundFloorAvailableLots/3.0);
			System.out.println("First Floor available parking lots : " + firstFloorAvailableLots/3.0);
			System.out.println("Upper Floor available parking lots : " + upperFloorAvailableLots/3.0);
			System.out.println("-----------------------------------------------------------------------------------");
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		notifyAll();
	}


	@Override
	public void removeVehicle(String IdPlate) {
		listOfVehicle.removeIf(item -> item.getIdPlate().equals(IdPlate));
	}

	// Concurrency Implementation, End.
	








//	@Override
//	public void addVehicle(Vehicle obj) {
//		//check whether the vehicle is already parked or not
//		for(Vehicle item : listOfVehicle) {
//			if(item.equals(obj)) {
//				System.out.println("This vehicle is already parked.");
//				return;
//			}
//		}
//		// Check whether there are sufficient space available for any vehicle to park
//		if(listOfVehicle.size()<20) {
//			if(obj instanceof Van ) {
//				if(listOfVehicle.size()<19) {
//					listOfVehicle.add(obj);
//					availableSlots -=2;
//					System.out.println("Available slots : "+availableSlots);
//					System.out.println("\n");
//				}else {
//					System.out.println("Sorry..There are no slots available to park your Van."+"\n");
//				}
//			}
//			if(obj instanceof MotorBike || obj instanceof Car) {
//				listOfVehicle.add(obj);
//				availableSlots --;
//				System.out.println("Available slots : "+availableSlots);
//			}
//		}else {
//			System.out.println("Sorry...There are not space availble for parking...");
//		}
//	}

  
	@Override
	public void deleteVehicle(String IdPlate) {
		for(Vehicle item: listOfVehicle) {
			//Checking for a particular vehicle with its' plate ID
			if(item.getIdPlate().equals(IdPlate)) {
				System.out.println("Vehicle Found.");
				if(item instanceof Van) {
					availableSlots+=2;
					System.out.println("Space cleared after deleting a Van.\nAvailable Slots : "
							+availableSlots);
						}else{
							availableSlots++;
							System.out.println("Space cleared after deleting a vehicle.\nAvailable Slots : "
							+availableSlots);
						}
			}else {
				System.out.println("Vehicle not found.");
			}
		}
	}
			
	
	@Override
	public void printcurrentVehicles() {
		Collections.sort(listOfVehicle, Collections.reverseOrder());
		for( Vehicle item:listOfVehicle) {
			if(item instanceof Van) {
				System.out.println("Vehicle Type is a Van");
			}else if(item instanceof MotorBike) {
				System.out.println("Vehicle Type is a MotorBike");
			}else {
				System.out.println("Vehicle Type is a Car.");
			}
			System.out.println("******************");
			System.out.println("ID Plate : "+item.getIdPlate());
			System.out.println("Entry Time : "
			+item.getEntryDate().getHours()+":"+item.getEntryDate().getMinutes()
			+":"+item.getEntryDate().getSeconds()+"-"+item.getEntryDate().getDate()
			+"/"+item.getEntryDate().getMonth()+"/"+item.getEntryDate().getYear());
			System.out.println("\n");
		}	
	}

	@Override
	public void printLongestPark() {
		//sort to the ascending order
		Collections.sort(listOfVehicle);
		System.out.println("The longest parked vehicle is : ");
		System.out.println("................................................");
		System.out.println("ID Plate : "+listOfVehicle.get(0).getIdPlate());
		if(listOfVehicle.get(0) instanceof Car) {
			System.out.println("Vehicle Type is a Car.");
		}else if(listOfVehicle.get(0) instanceof Van){
			System.out.println("Vehicle Type is a Van.");
		}else {
			System.out.println("Vehicle Type is a MotorBike.");
		}
		System.out.println("Parked Time : "+listOfVehicle.get(0).getEntryDate().getHours()
				+":"+listOfVehicle.get(0).getEntryDate().getMinutes()
				+":"+listOfVehicle.get(0).getEntryDate().getSeconds());
		System.out.println("Parked Date  : "+listOfVehicle.get(0).getEntryDate().getDate()
				+"/"+listOfVehicle.get(0).getEntryDate().getMonth()
				+"/"+listOfVehicle.get(0).getEntryDate().getYear());
	}

	@Override
	public void printLatestPark() {
		// sort to the descending order
		Collections.sort(listOfVehicle, Collections.reverseOrder());
		System.out.println("The latest parked vehicle is : ");
		System.out.println("..............................................");
		System.out.println("ID Plate : "+listOfVehicle.get(0).getIdPlate());
		if(listOfVehicle.get(0) instanceof Car) {
			System.out.println("Vehicle Type is a Car.");
		}else if(listOfVehicle.get(0) instanceof Van){
			System.out.println("Vehicle Type is a Van.");
		}else {
			System.out.println("Vehicle Type is a MotorBike.");
		}
		System.out.println("Parked Time : "+listOfVehicle.get(0).getEntryDate().getHours()
				+":"+listOfVehicle.get(0).getEntryDate().getMinutes()
				+":"+listOfVehicle.get(0).getEntryDate().getSeconds());
		System.out.println("Parked Date  : "+listOfVehicle.get(0).getEntryDate().getDate()
				+"/"+listOfVehicle.get(0).getEntryDate().getMonth()
				+"/"+listOfVehicle.get(0).getEntryDate().getYear());	
	}

	
	@Override
	public void printVehicleByDay(DateTime givenDate) {
		for(Vehicle item:listOfVehicle) {
		if(givenDate.getYear()==item.getEntryDate().getYear() &&
				givenDate.getMonth()==item.getEntryDate().getMonth() && 
						givenDate.getDate() == item.getEntryDate().getDate()) {
			
				System.out.println("ID Plate : "+item.getIdPlate());
				
				System.out.println("Parked Date and Time : "+item.getEntryDate().getDate()+"/"+
				item.getEntryDate().getMonth()+"/"+item.getEntryDate().getHours()+"-"
				+item.getEntryDate().getHours()+":"+item.getEntryDate().getMinutes()
				+":"+item.getEntryDate().getYear());
				
				if(item instanceof Van) {
					System.out.println("Vehicle Type is a Van");
				}else if(item instanceof MotorBike) {
					System.out.println("Vehicle Type is a Motor Bike.");
				}else {
					System.out.println("Vehicle Type is a Car.");
				}	
				System.out.println("--------------------------");
				System.out.println("\n");
			}
		}
	}
		
	@Override
	public void printVehiclePercentage() {
		int numCars=0;
		int numBikes=0;
		int numVans=0;
		for(Vehicle item:listOfVehicle) {
			if(item instanceof Car) {
				numCars++;
			}else if(item instanceof MotorBike) {
				numBikes++;
			}else {
				numVans++;
			}
		}
		double carPercentage = (numCars/listOfVehicle.size())*100;
		double bikePercentage = (numBikes/listOfVehicle.size())*100;
		double vanPercentage = (numVans/listOfVehicle.size())*100;
		
		System.out.printf("Car Percentage is : %.2f ",carPercentage);
		System.out.printf("\nBike Percentage is : %.2f ",bikePercentage);
		System.out.printf("\nVan Percentage is : %.2f ",vanPercentage);
		System.out.println("\n");
	}

	@Override
	public BigDecimal calculateChargers(String plateID, DateTime currentTime) {
		boolean found = false;
		BigDecimal charges = null;
		for(Vehicle item:listOfVehicle) {
			if(item.getIdPlate().equals(plateID)) {
				System.out.println("Vehicle found.");
				//vehicle parked time
				System.out.println("Parked Time : "+item.getEntryDate().getDate()+"/"
						+item.getEntryDate().getMonth()+"/"+item.getEntryDate().getDate()
								+"-"+item.getEntryDate().getHours()+":"+item.getEntryDate().getMinutes()
								+":"+item.getEntryDate().getSeconds());
				//making the charges
				found = true;
				DateTime entryDateTime = item.getEntryDate();
				int differenceInSeconds = currentTime.compareTo(entryDateTime);
				double differenceInHours = differenceInSeconds/(60.0*60.0);
				
				double dayCharge=0;
				double hourCharge = 0;
				double totalCost=0;
				double days = differenceInHours/24;
				
				if(days>1) {
					dayCharge =maxCharge;	
				}
				if (differenceInHours>=3) {
					double additional = (differenceInHours-addFromthisHour) ;
					hourCharge=(additional*addCharge)+(addFromthisHour *chargePerHour);
					System.out.printf("hour Charge : %.2f",hourCharge);
				}else if(differenceInHours<1) {
					hourCharge = chargePerHour;
				}else {
					hourCharge=(differenceInHours * chargePerHour);
				}
				
				totalCost=dayCharge + hourCharge;
				BigDecimal vehicleCharge = new BigDecimal(totalCost);
				System.out.printf("Total charge for the vehicle is LKR %.2f", vehicleCharge);
				System.out.println("\n");
			}
		}
		if(!found) {
			System.out.println("Vehicle not found\n");
		}
		return charges;
	}

	

	

	
	
}
