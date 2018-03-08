package floor;

import java.util.ArrayList;
import exceptions.InvalidArgumentException;

//import gui.ElevatorDisplay.Direction;
import  direction.Direction;
import person.Person;

public interface Floor {
	
	public void addWaiting(Person p);
	public void addCompletedPerson(Person p);
	public ArrayList<Person> getWaitersByDirection(Direction direction);
	public void completeReport();
	public int getFloorNum();
	public int getAvgWaitTime();
	public int getMaxWaitTime();
	public int getMinWaitTime();
	public ArrayList<Person> getCompleted();
	public void personReport();
	
}
