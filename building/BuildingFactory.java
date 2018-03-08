package building;

import exceptions.InvalidArgumentException;

public class BuildingFactory {
	
	private BuildingFactory(){
	}

	public static Building createBuilding() {
		return Building.getInstance();
	}
}
