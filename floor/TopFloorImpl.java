package floor;

import  direction.Direction;
import exceptions.InvalidArgumentException;
import person.Person;
import timemanager.TimeManagerImpl;
import java.util.ArrayList;


public class TopFloorImpl implements Floor {
	private int floorNum;
	private ArrayList<Person> completed = new ArrayList<>();
	private ArrayList<Person> waiting = new ArrayList<>();
		
	public TopFloorImpl(int floorNumIn) {
		setFloorNum(floorNumIn);
	}
	
	public int getFloorNum() {
		return new Integer(floorNum);
	}
	  
	private void setFloorNum(int floorNumIn) {
		floorNum = floorNumIn;
	}
	
	public ArrayList<Person> getCompleted(){
		return new ArrayList<Person>(completed);
	}
	
	public void addCompletedPerson(Person p) {
		System.out.println(TimeManagerImpl.getInstance().getTimeString() + "Person " + p.getId() 
							+ " has entered Floor " + getFloorNum());
		
		completed.add(p);
	}
	
	public void addWaiting(Person p)  {

		waiting.add(p);
		p.setWaitTime(TimeManagerImpl.getInstance().getElapsedTime());
	}
	
	public ArrayList<Person> getWaitersByDirection(Direction direction){
		ArrayList<Person> waiters = new ArrayList<>();
		
		for (Person p : waiting) {
			if(p.getDirection() == direction) {
				waiters.add(p);
				System.out.println(TimeManagerImpl.getInstance().getTimeString() + "Person " 
									+ p.getId() + " has left floor " + getFloorNum());
			}
		}
		waiting.removeAll(waiters);
		return waiters;
	}


	
	public void completeReport() {
		if(!completed.isEmpty()) {
		System.out.println("Floor " + getFloorNum() + " - Completed Riders: " + completed.size());
		}
		else {
			System.out.println("Floor " + getFloorNum());
		}
		for(Person p : completed) {
			p.timeOutput();
		}
	}
	
	public int getAvgWaitTime() {
		int sum = 0;
		int avg = 0;
		
		if(!getCompleted().isEmpty()) {
			for (int i = 0; i < getCompleted().size(); i++) {
				sum += getCompleted().get(i).getWaitTime();
			}
			
			avg = sum/getCompleted().size();
		}		
		
		
		
		return avg/1000;
	}
	
	public int getMaxWaitTime() {
		
		int max = 0;
		
		if(!getCompleted().isEmpty()) {
			for (int i = 0; i < getCompleted().size(); i++) {
				
				if (getCompleted().get(i).getWaitTime() > max) {
					max = (int) getCompleted().get(i).getWaitTime();
				}
			}
		}

		
		return max/1000;
	}
	
	public int getMinWaitTime() {
		
		int min = 0;
		if(!getCompleted().isEmpty()) {
				
			min = (int)getCompleted().get(0).getWaitTime();
			
			for (int i = 0; i < getCompleted().size(); i++) {
				
				if (getCompleted().get(i).getWaitTime() < min) {
					min = (int) getCompleted().get(i).getWaitTime();
				}
			}
		}
		
		return min/1000;
	}
	
	public int getAvgRideTime() {
		
		int sum = 0;
		
		for (int i = 0; i < getCompleted().size(); i++) {
			sum += getCompleted().get(i).getRideTime();
		}
		
		return sum/getCompleted().size();
	}
	
	public int getMaxRideTime() {
		
		int max = 0;
		
		for (int i = 0; i < getCompleted().size(); i++) {
			
			if (getCompleted().get(i).getRideTime() > max) {
				max = (int) getCompleted().get(i).getRideTime();
			}
		}
		
		return max;
	}
	
	public int getMinRideTime() {
		
		int min = (int)getCompleted().get(0).getRideTime();
		
		for (int i = 0; i < getCompleted().size(); i++) {
			
			if (getCompleted().get(i).getRideTime() < min) {
				min = (int) getCompleted().get(i).getRideTime();
			}
		}
		
		return min;
	}
	
	public void personReport() {
		
		
		for(Person p : completed) {
			System.out.println("Person " + p.getId() + "\t\t" + p.getStartFloorNum() + "\t\t" + p.getDestinationFloorNum() + "\t\t"
								+ p.getWaitTime()/1000 + "seconds" + "\t\t" + p.getRideTime()/1000 + "seconds" + "\t\t" + (p.getWaitTime() + p.getRideTime())/1000 + "seconds" );
		}
	}
	

}
	