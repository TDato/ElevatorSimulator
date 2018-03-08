package person;

import exceptions.InvalidArgumentException;

public class PersonFactory {
	
	private PersonFactory() {
		
	}
	
	public static Person createPerson(String id,int startFloorNum,int destinationFloorNum)  {
		return new PersonImpl(id, startFloorNum, destinationFloorNum);
	}

}
