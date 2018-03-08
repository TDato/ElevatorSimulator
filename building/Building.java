package building;

import floor.Floor; 
import elevatorController.ElevatorController;
import exceptions.InvalidArgumentException;
import floor.FloorFactory;
import elevator.Elevator;
import elevator.ElevatorFactory;
import direction.Direction;
import person.Person;
import java.util.ArrayList;
import gui.*;
import timemanager.TimeManagerImpl;

public class Building {

	private int numFloors;
	private int numElevators;
	private ArrayList<Floor> floors = new ArrayList<>();
	private ArrayList<Elevator> elevators = new ArrayList<>();
	private ElevatorController controller = ElevatorController.getInstance();
	private static Building instance;
	
	public static Building getInstance() {
		if (instance == null) {
			instance = new Building();
		}
		return instance;
	}
	
	private Building(){
		numFloors = BuildingManager.getInstance().getNumFloors();
		numElevators = BuildingManager.getInstance().getNumElevators();
		initializeFloors();
		initializeElevators();
		initializeGuiElevators();
	}
	
	private void initializeGuiElevators() {
		ElevatorDisplay.getInstance().initialize(numFloors);
		
		for(int i = 0; i < getNumElevators(); i++) {
			ElevatorDisplay.getInstance().addElevator(i+1, 1);
			ElevatorDisplay.getInstance().setIdle(i+1);
		}
	}

	public ArrayList<Floor> getFloors() {
		return new ArrayList<Floor>(floors);
	}
	
	public int getNumElevators() {
		return new Integer(numElevators);
	}
	
	public int getNumFloors() {
		return new Integer(numFloors);
	}
	
	
	private void initializeFloors() {
		for (int i = 0; i < getNumFloors(); i++) {
			floors.add(FloorFactory.createFloor(i+1));
		}
	}

	public ArrayList<Elevator> getElevators() {
		return new ArrayList<Elevator>(elevators);
	}

	private void initializeElevators(){
		for (int i = 0; i < getNumElevators(); i++)  {
			elevators.add(ElevatorFactory.createElevator(i+1));
		}
	}

	public void update(long time)  {
		for(Elevator e : elevators) {
			e.update(time);
		}
		controller.update();
	}
	
	public void addCompleteToFloor(int floorNum, ArrayList<Person> people)  {
		for(Person p : people) {
			floors.get(floorNum - 1).addCompletedPerson(p);
			p.setRideTime(TimeManagerImpl.getInstance().getElapsedTime());
		}
	}

	public void addWaitersToFloor(Person p, int floorNum)  {
		floors.get(floorNum - 1).addWaiting(p);
	}

	public void floorButtonPress(int floorNum, Direction directionIn, Person p) {
		System.out.println(TimeManagerImpl.getInstance().getTimeString() + "Person " + p.getId() 
		+ " presses " + p.getDirection() + " button on Floor " + floorNum);

		controller.addFloorRequest(directionIn, floorNum);
		controller.chooseElevator(directionIn);
		addWaitersToFloor(p, floorNum);
	}

	public ArrayList<Person> getWaitersAtFloor(int floorNum, Direction direction){
		ArrayList<Person> waiters = floors.get(floorNum - 1).getWaitersByDirection(direction);
		
		return waiters;
	}
	


}