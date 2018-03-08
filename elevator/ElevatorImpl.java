package elevator;

import java.util.ArrayList;
import java.util.HashMap;
import gui.*;
import gui.ElevatorDisplay.*;
import door.Door;
import door.DoorFactory;
import exceptions.InvalidArgumentException;
import person.Person;
import building.Building;
import building.BuildingManager;
import building.BuildingXMLReader;
import timemanager.*;
import direction.Direction;
import static direction.Direction.*;


public class ElevatorImpl implements Elevator {

	
	private double timePerFloor;
	private int maxRiders;
	private long maxIdle;
	private int floorTimer = -1;
	private int elevatorId;
	private HashMap<Integer, ArrayList<Person>> riders = new HashMap<>();
	private HashMap<Direction, ArrayList<Integer>> floorStops = new HashMap<>();
	private int currentFloor = 1;
	private Door door = DoorFactory.createDoor(); 
	private Direction queuedDirection = IDLE;
	private Direction direction = IDLE;
	private long idleTime = 0;
	private boolean idleOut = false;
	
	
	
	public ElevatorImpl(int elevatorIdIn) {
		elevatorId = elevatorIdIn;
		setTimePerFloor(new Double(ElevatorXMLReader.elevatorReader().get(0)));
		setMaxRiders(new Integer(ElevatorXMLReader.elevatorReader().get(1)));
		setMaxIdle(new Long(ElevatorXMLReader.elevatorReader().get(2)));
	
	}
	
	public double getTimePerFloor() {
		return new Double(timePerFloor);
	}
	
	private long getMaxIdle() {
		return new Long(maxIdle);
	}
	
	private int getMaxRiders() {
		return new Integer(maxRiders);
	}
	
	public int getCurrentFloor() {
		return new Integer(currentFloor);
	}
	
	public int getElevatorId() {
		return new Integer(elevatorId);
	}
	
	public long getIdleTime() {
		return new Long(idleTime);
	}
	
	public void setTimePerFloor(double timePerFloorIn) {
		timePerFloor = timePerFloorIn;
	}
	
	public void setMaxRiders(int maxRidersIn) {
		maxRiders = maxRidersIn;	
	}
	
	public void setMaxIdle(long maxIdleIn) {
		maxIdle = maxIdleIn;	
	}


	public HashMap<Integer, ArrayList<Person>> getRiders(){
		return riders;
		// This needs to change.
		// it's still returning reference
		// I'm getting a null pointer exception when I change it
		// return new HashMap<Integer, ArrayList<Person>>(riders);
	}

	public HashMap<Direction, ArrayList<Integer>> getFloorStops(){
		return floorStops;
		// This needs to change.
		// it's still returning reference
		// I'm getting a null pointer exception when I change it
		// return new HashMap<Direction, ArrayList<Integer>>(floorStops);
	}
	
	public Direction getQueuedDirection() {
		Direction temp = queuedDirection;
		return temp;
	}
	
	public Direction getDirection() {
		Direction temp = direction;
		return temp;
	}
	
	public boolean getIdleOut() {
		return new Boolean(idleOut);
	}
	
	private void setQueuedDirection(Direction queuedDirectionIn) {
		queuedDirection = queuedDirectionIn;
	}
	
	private void setDirection(Direction directionIn) {
		direction = directionIn;
	}
	
	
	public void update(long time)  {
		
		checkIdleState();
		
		if(floorTimer == 0) {
			updateFloor();
			return;
		} else if (floorTimer == 1) {
			updateFloor();
			return;
		} else if (floorTimer == 2){
			updateFloor();
			return;
		}
		

		
		moveElevator(time);

		if(idleOut == true && !hasHitIdleMax() && getFloorStops().containsKey(UP)) {
			idleOut = false;
			getFloorStops().remove(DOWN);
		}
		
		if (!riders.isEmpty()) {
			
			if (riders.containsKey(new Integer(getCurrentFloor()))) {
				updateFloor();
			}
		}
		
		if (!getFloorStops().isEmpty()) {
			 if (getFloorStops().get(getQueuedDirection()).contains(getCurrentFloor())) {
					
				getFloorStops().get(getQueuedDirection()).remove(new Integer(getCurrentFloor()));
				if (getFloorStops().get( getQueuedDirection()).isEmpty()) {
					getFloorStops().remove(getQueuedDirection());
				}
				
				
				setDirection(getQueuedDirection());
				setQueuedDirection(direction);
				

				

				updateFloor();
			} 
		}
		
		if (!getFloorStops().isEmpty()) {
			 if (getFloorStops().get(getQueuedDirection()).contains(getCurrentFloor())) {
					
				getFloorStops().get(getQueuedDirection()).remove(new Integer(getCurrentFloor()));
				if (getFloorStops().get( getQueuedDirection()).isEmpty()) {
					getFloorStops().remove(getQueuedDirection());
				}

				updateFloor();
			} 
		}
	

		

	}
	
	private void moveElevator(long time) {
		if (direction != IDLE) {
			idleTime = 0;
			double distance = time / getTimePerFloor();
			
			if (direction == UP) {
				currentFloor += distance;
			} else {
				currentFloor -= distance;
			}
			
		}else if(getRiders().isEmpty() && getFloorStops().isEmpty() || isIdle()) {

			elevatorIdleInfo();
			idleTime += 1000;
			return;
		}
		elevatorAtFloorInfo();
	}

	private void updateFloor()  {
		if (floorTimer >= 3 || floorTimer == -1) {
			floorTimer = -1;
		} 
		
		

																										
		
		if (floorTimer == 0){
			System.out.println(TimeManagerImpl.getInstance().getTimeString() + "Elevator " + getElevatorId() + 
					" has arrived at Floor " + getCurrentFloor() + " for Floor Request" + floorRequestsInfo() + riderRequestsInfo());
			updateDoors();
			
		} else if (floorTimer == 1) {
			ArrayList<Person> people = Building.getInstance().getWaitersAtFloor(getCurrentFloor(), direction);
			
			for (Person p : people) {
				addPerson(p);
				
				System.out.println(TimeManagerImpl.getInstance().getTimeString() + "Elevator " + getElevatorId() + 
						" Rider Request made for Floor " + p.getDestinationFloorNum() + floorRequestsInfo() + riderRequestsInfo());
			}
			
			ArrayList<Person> exiting = new ArrayList<>();
			if (riders.containsKey(getCurrentFloor())) {
				for (Person p : riders.get(getCurrentFloor())) {
					exiting.add(p);
					System.out.println(TimeManagerImpl.getInstance().getTimeString() + "Person " + p.getId() + " has left Elevator " + getElevatorId() + riderInfo());
				}
				riders.remove(new Integer(getCurrentFloor()));
				Building.getInstance().addCompleteToFloor(getCurrentFloor(), exiting);
				
			}

			
			if (riders.isEmpty() && getFloorStops().isEmpty()) {
				setDirection(IDLE);
				ElevatorDisplay.getInstance().updateElevator(getElevatorId(), getCurrentFloor(), 0, direction);
			}
		} else if (floorTimer == 2) {
			updateDoors();

		}
		
		floorTimer++;


	}
	
	private void updateDoors() {

		if(!door.getOpen()) {
			System.out.println(TimeManagerImpl.getInstance().getTimeString() + "Elevator " + getElevatorId() + " Doors Open");
			ElevatorDisplay.getInstance().openDoors(getElevatorId());
			door.openDoor();
		}else {
			System.out.println(TimeManagerImpl.getInstance().getTimeString() + "Elevator " + getElevatorId() + " Doors Closed");
			ElevatorDisplay.getInstance().closeDoors(getElevatorId());
			door.closeDoor();
		}
	}

	
	private void elevatorAtFloorInfo() {

		if(getIdleTime() == getMaxIdle()) {
			System.out.println(TimeManagerImpl.getInstance().getTimeString() + "Elevator " + getElevatorId() + " is at floor "
					+ getCurrentFloor() + ", has been idle for " + getIdleTime()/1000.0 + " second(s) and is going to the first floor");
		} else if (!getFloorStops().isEmpty() && getFloorStops().get(getQueuedDirection()).contains(getCurrentFloor())) {
				System.out.println(TimeManagerImpl.getInstance().getTimeString() + "Elevator " + getElevatorId() + 
						" has arrived at Floor " + getCurrentFloor() + " for Floor Request" + floorRequestsInfo() + riderRequestsInfo());
				ElevatorDisplay.getInstance().updateElevator(getElevatorId(), getCurrentFloor(), riders.size(), direction);
				return;
		}
		if(!isIdle()) {
			System.out.println(TimeManagerImpl.getInstance().getTimeString() + "Elevator " + getElevatorId() + 
					" is moving " + getDirection() + " from Floor " + getCurrentFloor() + " to Floor " + nextFloor() +
					floorRequestsInfo() + riderRequestsInfo());
			
			
			ElevatorDisplay.getInstance().updateElevator(getElevatorId(), getCurrentFloor(), riders.size(), direction);
		}
		
	}
	
	public String nextFloor() {
		int nextFloor = getCurrentFloor();
		if (getDirection() == UP && getCurrentFloor() < Building.getInstance().getNumFloors()) {
			nextFloor++;
		} else if (getDirection() == DOWN && getCurrentFloor() > 1){
			nextFloor--;
		}
		return Integer.toString(nextFloor);
	}
	
	public String riderRequestsInfo() {
		String riderRequests = " [Rider Requests: ";
		if (getRiders().isEmpty()) {
			riderRequests += "None]";
		} else if(!getRiders().isEmpty()) {
			
			for (Integer key : getRiders().keySet()) {
				riderRequests += Integer.toString(key) + ", ";
			}
			
			riderRequests = riderRequests.substring(0, riderRequests.length()-2);
			riderRequests += "]";
		}
		return riderRequests;
	}
	
	public String riderInfo() {
		String riders = " [Riders: ";
		if (getRiders().isEmpty()) {
			riders += "None]";
		}else {
			for(Integer key : getRiders().keySet()) {
				for(int i = 0; i < getRiders().get(key).size(); i++) {
					riders += getRiders().get(key).get(i).getId() + ", ";
				}
			}
			
			riders = riders.substring(0, riders.length()-2);
			riders += "]";
		}
		
		return riders;
	}
	
	public String floorRequestsInfo() {
		String floorRequests = " [Floor Requests: ";
		if (getFloorStops().isEmpty()) {
			floorRequests += "None]";
		}else {
			for (int i = 0; i < getFloorStops().get(getQueuedDirection()).size(); i++) {
				
				if(getFloorStops().get(getQueuedDirection()).size() == 1) {
					floorRequests += Integer.toString(getFloorStops().get(getQueuedDirection()).get(i));
				} else if (i == getFloorStops().get(getQueuedDirection()).size()-1) {
					floorRequests += Integer.toString(getFloorStops().get(getQueuedDirection()).get(i));
				} else {
					floorRequests += Integer.toString(getFloorStops().get(getQueuedDirection()).get(i)) + ", ";
				}

			}
			
			floorRequests += "]";
		}
		
		return floorRequests;
		
	}
	
	private void elevatorIdleInfo() {
		
		System.out.println(TimeManagerImpl.getInstance().getTimeString() + "Elevator " + getElevatorId() + " is at floor "
				+ getCurrentFloor() + ", has been idle for " + getIdleTime()/1000.0 + " seconds");

	}
	
	private void addPerson(Person p)  {
		if(!getRiders().containsKey(p.getDestinationFloorNum())) {
			getRiders().put(p.getDestinationFloorNum(), new ArrayList<Person>());
		}
		
		getRiders().get(p.getDestinationFloorNum()).add(p);
		System.out.println(TimeManagerImpl.getInstance().getTimeString() + "Person " + p.getId() + " entered Elevator " + getElevatorId() + riderInfo());
		
		if(this.direction == IDLE) {
			direction = p.getDirection();
			System.out.println(TimeManagerImpl.getInstance().getTimeString() + "Elevator direction is now set to " + direction);
		}
		
		p.setWaitTime(TimeManagerImpl.getInstance().getElapsedTime());
		p.setRideTime(TimeManagerImpl.getInstance().getElapsedTime());
	}
	
	private Direction getElevatorDirection(int floorNum) {
		if((floorNum - getCurrentFloor()) > 0){
			return UP;
			
		}else if((floorNum - getCurrentFloor()) < 0){
			return DOWN;
		}else {
			return IDLE;
		}
	}
	
	public void addFloorStop(int num, Direction directionIn) {
		
		if((idleOut == true && !hasHitIdleMax()) || (idleOut == true && hasHitIdleMax())) {
			idleOut = false;
			getFloorStops().clear();
			setDirection(IDLE);
		}
		
		if(!getFloorStops().containsKey(directionIn)) {
			getFloorStops().put(directionIn, new ArrayList<Integer>());
		}
		
		
		if(!getFloorStops().get(directionIn).contains(num)) {
			getFloorStops().get(directionIn).add(num);
			if (num < getCurrentFloor() && isIdle()) {
				setDirection(DOWN);
			} else if(num > getCurrentFloor() && isIdle()) {
				setDirection(UP);
			}
			setQueuedDirection(directionIn);
			System.out.println(TimeManagerImpl.getInstance().getTimeString() + "Elevator " + getElevatorId() + 
					" going to Floor " + getFloorStops().get(getQueuedDirection()).get(getFloorStops().get(getQueuedDirection()).size()-1) +
					" for " + getQueuedDirection() + " request" + floorRequestsInfo() + riderRequestsInfo());
			return;	
		}
		
		if(isIdle()) {
			direction = getElevatorDirection(num);
			setQueuedDirection(directionIn);
		}


		
		if((direction == UP && getElevatorDirection(num) != UP) || (direction == DOWN && getElevatorDirection(num) != DOWN)) {
			System.err.println(TimeManagerImpl.getInstance().getTimeString() + "******Wrong Direction******");
			System.exit(-1);
		}
		
		
		if(!getFloorStops().get(directionIn).contains(num)){
			getFloorStops().get(directionIn).add(num);
		}
		

	}
	
	public boolean isFull() {
		return riders.size() == getMaxRiders();
	}
	
	public boolean isIdle() {
		return (getDirection() == IDLE);
	}
	
	public boolean noStops() {
		return floorStops.isEmpty();
	}
	
	private boolean hasHitIdleMax() {
		return getIdleTime() == getMaxIdle();
	}
	
	
	private void checkIdleState()  {
		
		if (getFloorStops().isEmpty() && !getRiders().isEmpty() && !hasHitIdleMax()) {
			if (getCurrentFloor() == Building.getInstance().getNumFloors() && getIdleTime() == 0) {
				setDirection(IDLE);
				setQueuedDirection(DOWN);
			}
			
			if (getCurrentFloor() == 1 && getIdleTime() == 0) {
				setQueuedDirection(UP);
			}
		} else if (!getFloorStops().isEmpty() && !getRiders().isEmpty() && !hasHitIdleMax()) {
			if (getCurrentFloor() == Building.getInstance().getNumFloors() && getIdleTime() == 0) {
				setDirection(DOWN);

			}
			
			if (getCurrentFloor() == 1 && getIdleTime() == 0) {
				setDirection(UP);

			}
		}
		

		if(hasHitIdleMax() && getCurrentFloor() != 1 && getFloorStops().isEmpty()) {
			addFloorStop(1, DOWN);
			setDirection(DOWN);
			elevatorIdleInfo();
			idleOut = true;
			
		} 
	}
	

	
}

