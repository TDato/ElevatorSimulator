package elevator;

import java.util.ArrayList;
import java.util.HashMap;
import  direction.Direction;
import exceptions.InvalidArgumentException;
import person.Person;

public interface Elevator {
	
	public int getElevatorId();
	public int getCurrentFloor();
	public void addFloorStop(int num, Direction direction);
	public void update(long time);
	public HashMap<Integer, ArrayList<Person>> getRiders();
	public HashMap<Direction, ArrayList<Integer>> getFloorStops();
	public boolean isFull();
	public boolean isIdle();
	public boolean noStops();
	public Direction getDirection();
	public Direction getQueuedDirection();
	public double getTimePerFloor();
	public long getIdleTime();
	public boolean getIdleOut();

}
