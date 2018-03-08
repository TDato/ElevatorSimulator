package timemanager;

import building.*;
import exceptions.InvalidArgumentException;
import java.util.Random;
import person.*;

public class TimeManagerImpl {

	private static TimeManagerImpl instance;
	private long elapsedTime = 0;
	private int peoplePerMinute;
	private long duration;
	
	public TimeManagerImpl() {
		setPeoplePerMinute(new Integer(TimeManagerXMLReader.timeManagerReader().get(0)));
		setDuration(new Long(TimeManagerXMLReader.timeManagerReader().get(1)));
	}
	
	public String getTimeString() {
		long seconds = elapsedTime/1000;
		long hours = seconds/3600;
		seconds -= hours * 3600;
		long minutes = seconds/60;
		seconds -=  minutes * 60;
		return String.format("%d:%02d:%02d ", hours, minutes, seconds);
	}
	
	public static TimeManagerImpl getInstance() {
		if(instance == null) {
			instance = new TimeManagerImpl();
		}
		return instance;
	}
	
	public long getElapsedTime() {
		return elapsedTime;
	}
	
	public long getDuration() {
		return new Long(duration);
	}
	
	public int getPeoplePerMinute() {
		return new Integer(peoplePerMinute);
	}
	
	public void setPeoplePerMinute(int peoplePerMinuteIn) {
		peoplePerMinute = peoplePerMinuteIn;
	}
	
	public void setDuration(long durationIn) {
		duration = durationIn;
	}
	
	public void runTime(){
		int num = 1;
		int peopleTime = 60_000/getPeoplePerMinute();
		while(getElapsedTime() <= getDuration()) {
			System.out.println("\nTIME: " + getTimeString());
			if ((getElapsedTime()-1000) % peopleTime == 0) {
				generatePeople(num);
				num++;
			}
			Building.getInstance().update(1000);
			try {
				Thread.sleep(1000);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			elapsedTime += 1000;
		}
		BuildingManager.getInstance().completeReport();
		System.exit(0);
	}
	
	private void generatePeople(int num){

		Random rand = new Random();
		int start = rand.nextInt(Building.getInstance().getNumFloors()) + 1;
		int dest = rand.nextInt(Building.getInstance().getNumFloors()) + 1;
		
		while(dest == start) {
			dest = rand.nextInt(Building.getInstance().getNumFloors()) + 1;
		}
		Person p = PersonFactory.createPerson("P" + num, start, dest);
		Building.getInstance().floorButtonPress(start, p.getDirection(), p);	
	}
	
}
