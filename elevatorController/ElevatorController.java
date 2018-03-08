package elevatorController;

import building.Building;
import direction.Direction;
import elevator.Elevator;
import exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.HashMap;
import static direction.Direction.*;
import java.util.Collections;
import java.lang.Math;

public class ElevatorController {
	
	private HashMap<Direction, ArrayList<Integer>> requests = new HashMap<>();
	private HashMap<Direction, ArrayList<Integer>> queuedRequests = new HashMap<>();
	
	private static ElevatorController instance;
	
	public static ElevatorController getInstance() {
		if (instance == null) {
			instance = new ElevatorController();
		}
		return instance;
	}
	
	private ElevatorController() {

	}
	
	private HashMap<Direction, ArrayList<Integer>> getRequests() {
		return requests;
	}
	
	private HashMap<Direction, ArrayList<Integer>> getQueuedRequests() {
		return queuedRequests;
	}
	
	
	
	public void addFloorRequest(Direction directionIn, int floorNum) {
		
		if(!getRequests().containsKey(directionIn)) {
			getRequests().put(directionIn, new ArrayList<Integer>());
		}
		
		getRequests().get(directionIn).add(floorNum);
		
	}
	
	private void addQueuedRequest(Direction directionIn, int floorNum) {
		if(!getQueuedRequests().containsKey(directionIn)) {
			getQueuedRequests().put(directionIn, new ArrayList<Integer>());
		}
		
		if(!getQueuedRequests().get(directionIn).contains(floorNum)) {
			getQueuedRequests().get(directionIn).add(floorNum);
		}

	}
	
	public void update() {
		if(!getQueuedRequests().isEmpty()) {
			if (getQueuedRequests().containsKey(UP) && !getQueuedRequests().get(UP).isEmpty()) {
				chooseQueuedElevator(UP);
			} else if (getQueuedRequests().containsKey(DOWN) && !getQueuedRequests().get(DOWN).isEmpty()) {
				chooseQueuedElevator(DOWN);
			}
		}
	}
	
	public void chooseQueuedElevator(Direction directionIn)  {
		
		int choice;
		
		if(directionIn == UP) {
			choice = chooseUpElevator(getQueuedRequests().get(directionIn).get(0));
		} else {
			choice = chooseDownElevator(getQueuedRequests().get(directionIn).get(0));
		}
		
		
		if (choice != 0) {
			Building.getInstance().getElevators().get(choice - 1).addFloorStop(getQueuedRequests().get(directionIn).get(0), directionIn);
			requestCleanUp(directionIn, getQueuedRequests());
		} else {
			
			addQueuedRequest(directionIn, getQueuedRequests().get(directionIn).get(0));
		}
		
	}
	public void chooseElevator(Direction directionIn) {
		
		int choice;
		
		if(directionIn == UP) {
			choice = chooseUpElevator(getRequests().get(directionIn).get(0));
		} else {
			choice = chooseDownElevator(getRequests().get(directionIn).get(0));
		}
		
		
		if (choice != 0) {
			Building.getInstance().getElevators().get(choice - 1).addFloorStop(getRequests().get(directionIn).get(0), directionIn);
			requestCleanUp(directionIn, getRequests());
		} else {
			
			addQueuedRequest(directionIn, getRequests().get(directionIn).get(0));
		}
		
	}
	/*
	public int chooseElevator(Direction directionIn) {
		
		int choice;
		
		if(directionIn == UP) {
			choice = chooseUpElevator(getRequests().get(directionIn).get(0));
		} else {
			choice = chooseDownElevator(getRequests().get(directionIn).get(0));
		}
		
		
		if (choice != 0) {
			Building.getInstance().getElevators().get(choice - 1).addFloorStop(getRequests().get(directionIn).get(0), directionIn);
		} else {
			
			addQueuedRequest(directionIn, getRequests().get(directionIn).get(0));
		}
		requestCleanUp(directionIn);
		return choice;
		
	}
	*/
	public int chooseUpElevator(int floorNum)  {
		
		int elevatorId = 0;
	
		for (int i = 0; i < Building.getInstance().getElevators().size(); i++) {
			//next elevator if this one is full
			if( Building.getInstance().getElevators().get(i).isFull() || 
					Building.getInstance().getElevators().get(i).getFloorStops().containsKey(DOWN)) {
				continue;
			}
			if(Building.getInstance().getElevators().get(i).isIdle()) {
				elevatorId = Building.getInstance().getElevators().get(i).getElevatorId();
				break;
			}	
			/*if( Building.getInstance().getElevators().get(i).getDirection() == DOWN && 
					Building.getInstance().getElevators().get(i).getRiders().isEmpty() &&
					Building.getInstance().getElevators().get(i).getCurrentFloor() > floorNum &&
					Building.getInstance().getElevators().get(i).getFloorStops().get(DOWN).size() == 1) {
				//I HAVE NO IDEA WHAT I'M EVEN TRYING TO DO ANYMORE
				elevatorId = Building.getInstance().getElevators().get(i).getElevatorId();
				break;
			}*/
			/*if(Building.getInstance().getElevators().get(i).getQueuedDirection() == DOWN &&
					Building.getInstance().getElevators().get(i).getCurrentFloor() < floorNum) {
				
				//ONCE AGAIN I'VE CONFUSED MYSELF
				if(Collections.max(Building.getInstance().getElevators().get(i).getFloorStops().get(DOWN)) > floorNum) 
					elevatorId = Building.getInstance().getElevators().get(i).getElevatorId();
				break;
			}*/
			if( Building.getInstance().getElevators().get(i).getDirection() == UP && 
					Building.getInstance().getElevators().get(i).getCurrentFloor() < floorNum &&
					!Building.getInstance().getElevators().get(i).getFloorStops().containsKey(DOWN)) {
				
				elevatorId = Building.getInstance().getElevators().get(i).getElevatorId();
				break;
			}

			if(Building.getInstance().getElevators().get(i).getIdleOut()) {
				elevatorId = Building.getInstance().getElevators().get(i).getElevatorId();
				break;
			}
		
		}

		/*
		if(elevatorId == 0 ) {
			for (int i = 0; i < Building.getInstance().getElevators().size(); i++) {
				if(Building.getInstance().getElevators().get(i).getRiders().isEmpty() &&
						Building.getInstance().getElevators().get(i).getFloorStops().isEmpty() &&
						Building.getInstance().getElevators().get(i).getQueuedDirection() != Building.getInstance().getElevators().get(i).getDirection()) {
					elevatorId = Building.getInstance().getElevators().get(i).getElevatorId();
					break;
				}
			}

		}
		*/
		if (elevatorId == 0) {
			addQueuedRequest(UP,floorNum);
			
		}

		
		
		return elevatorId;
	}
	
	public int chooseDownElevator(int floorNum) {
		
		int elevatorId = 0;
		
		for (int i = 0; i < Building.getInstance().getElevators().size(); i++) {
			
			if( Building.getInstance().getElevators().get(i).isFull() || 
					Building.getInstance().getElevators().get(i).getFloorStops().containsKey(UP)) {
				continue;
			}
			if(Building.getInstance().getElevators().get(i).isIdle()) {
				elevatorId = Building.getInstance().getElevators().get(i).getElevatorId();
				break;
			}
			if( Building.getInstance().getElevators().get(i).getDirection() == DOWN && 
					Building.getInstance().getElevators().get(i).getCurrentFloor() > floorNum &&
					!Building.getInstance().getElevators().get(i).getFloorStops().containsKey(UP)) {
				
				elevatorId = Building.getInstance().getElevators().get(i).getElevatorId();
				break;
			}	
			if(Building.getInstance().getElevators().get(i).getIdleOut()) {
				elevatorId = Building.getInstance().getElevators().get(i).getElevatorId();
				break;
			}
				

			
		}
		if(elevatorId == 0) {
			addQueuedRequest(DOWN,floorNum);
		}
	
		return elevatorId;
	}	
	
	
	private void requestCleanUp(Direction direction, HashMap<Direction, ArrayList<Integer>> requestsIn) {
		requestsIn.get(direction).remove(0);
	}
	

	
		
	

}
/*
if(Building.getInstance().getElevators().get(i).isIdle() 
	&& !Building.getInstance().getElevators().get(i).hasStops()) {
	//this elevator is IDLE and has no stops and may be the first choice to assign
	temp = Building.getInstance().getElevators().get(i).getElevatorId();
}


if(Building.getInstance().getElevators().get(i).getCurrentFloor() < floorNum
	&& Building.getInstance().getElevators().get(i).getDirection() == UP
	&& Building.getInstance().getElevators().get(i).getQueuedDirection() != DOWN) {
	//this elevator is going up and is continuing to go up (queued direction)
	//it's currently on a floor that is below the incoming request
	temp = Building.getInstance().getElevators().get(i).getElevatorId();
}

if(Building.getInstance().getElevators().get(i).getCurrentFloor() > floorNum
		&& Building.getInstance().getElevators().get(i).getQueuedDirection() == UP
		&& Building.getInstance().getElevators().get(i).getDirection() == DOWN) {
	//this elevator is currently going down but is going up
	temp = Building.getInstance().getElevators().get(i).getElevatorId();
		
}
*/
