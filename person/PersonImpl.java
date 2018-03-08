package person;

import  direction.Direction;
import static direction.Direction.*;
import exceptions.InvalidArgumentException;
import timemanager.TimeManagerImpl;
import building.Building;

public class PersonImpl implements Person {
	
	private String id;
	private int destinationFloorNum;
	private int startFloorNum;
	private long waitTime; 
	private long rideTime;
	private Direction direction;
	
	public PersonImpl(String idIn, int startFloorNumIn, int destinationFloorNumIn) {
		setId(idIn);
		setSourceFloorNum(startFloorNumIn);
		setDestinationFloorNum(destinationFloorNumIn);
		setDirection(determineDirection(startFloorNumIn, destinationFloorNumIn));
		System.out.println(createdPerson());
	}
	
	public String getId() {
		return new String(id);
	}
	
	public int getStartFloorNum() {
		return new Integer(startFloorNum);
	}
	
	public int getDestinationFloorNum() {
		return new Integer(destinationFloorNum);
	}
	
	public long getWaitTime() {
		return new Long(waitTime);
	}
	
	public long getRideTime() {
		return new Long(rideTime);
	}
	
	public Direction getDirection() {
		Direction temp = this.direction;
		return temp;
	}
	
	private Direction determineDirection(int startFloorNum, int destinationFloorNum) {
		if((startFloorNum - destinationFloorNum) > 0) {
			return DOWN;
		}else{
		return UP;
		}
	}
	
	public void setWaitTime(long time)  {
		
		/*if(time < 0) {
			throw new InvalidArgumentException("Invalid (negative) value passed to PersonImpl 'setWaitTime'");
		}*/
		
		if(waitTime == 0) {
			waitTime = time;
		} else {
			waitTime = time - waitTime;
		}
	}
	
	public void setRideTime(long time){
		/*if(time < 0) {
			throw new InvalidArgumentException("Invalid (negative) value passed to PersonImpl 'setWaitTime'");
		}*/
		if(rideTime == 0) {
			rideTime = time;
		} else {
			rideTime = time - rideTime;
		}
	}
		
	private void setId(String idIn){
		/*if (idIn == null || idIn.isEmpty()) {
			throw new InvalidArgumentException("Invalid (null or empty) value passed to PersonImpl 'setId'"); 
		}*/
		id = idIn;
	}
		
	private void setSourceFloorNum(int sourceFloorNumIn){
		/*if(sourceFloorNumIn <= 0 || sourceFloorNumIn > Building.getInstance().getNumFloors()) {
			throw new InvalidArgumentException("Invalid (out of range) value passed to PersonImpl 'setSourceFloorNum'");
		}*/
		startFloorNum = sourceFloorNumIn;
	}
	
	private void setDestinationFloorNum(int destinationFloorNumIn)  {
			/*if(destinationFloorNumIn <= 0 || destinationFloorNumIn > Building.getInstance().getNumFloors()) {
				throw new InvalidArgumentException("Invalid (out of range) value passed to PersonImpl 'setDestinationFloorNum'");
			}*/
		destinationFloorNum = destinationFloorNumIn;
	}
	
	private void setDirection(Direction directionIn) {
		/*if(directionIn == null || (directionIn != UP && directionIn != DOWN)) {
			throw new InvalidArgumentException("Invalid (null or non valid direction) value passed to PersonImpl 'setDirection'");
		}*/
		direction = directionIn;
	}
	
	
	public void timeOutput() {
		System.out.println(String.format("\n%17s", id) + " ");
		System.out.println(String.format("%30s", " Wait time: " + getWaitTime()/1000.0 + " seconds"));
		System.out.println(String.format("%31s", " Ride time: " + getRideTime()/1000.0 + " seconds\n"));
	}
	
	public String createdPerson() {
		return(TimeManagerImpl.getInstance().getTimeString() + "Person " + getId() + " created on Floor " + getStartFloorNum() + ", wants to go " + getDirection()
		+ " to Floor " + getDestinationFloorNum());
	}
			
	public String toString() {
		return (getId());
	}
}

