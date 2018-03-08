package floor;

import exceptions.InvalidArgumentException;

public class FloorFactory {
	
	private FloorFactory(){
		
	}
	
	public static Floor createFloor(int floorNumIn) {

		
		if (floorNumIn == 1) {
			return new GroundFloorImpl(floorNumIn);
		}
		else if (floorNumIn == 20) { 
			return new TopFloorImpl(floorNumIn);
		}
		else
			return new FloorImpl(floorNumIn);
			
	}
}
