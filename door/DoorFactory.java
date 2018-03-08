package door;

import exceptions.InvalidArgumentException;

public class DoorFactory {
	
	
	private DoorFactory() {
		
	}
	
	public static Door createDoor() {
		return new DoorImpl();
	}
	

}
