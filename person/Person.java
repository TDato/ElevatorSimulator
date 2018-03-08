package person;
import  direction.Direction;
import exceptions.InvalidArgumentException;

public interface Person {
	
	public String getId();
	public int getStartFloorNum();
	public int getDestinationFloorNum();
	public long getRideTime();
	public long getWaitTime();
	public Direction getDirection();
	public void timeOutput();
	public void setWaitTime(long time);
	public void setRideTime(long time);
}
