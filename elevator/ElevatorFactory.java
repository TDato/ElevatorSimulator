package elevator;

import exceptions.InvalidArgumentException;

public class ElevatorFactory {
	
	private ElevatorFactory() {
		
	}
	
	public static Elevator createElevator(int elevatorId)  {
		return new ElevatorImpl(elevatorId);
	}

}
