package building;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import building.Building;

import floor.Floor;
import person.Person;

public final class BuildingManager {
	public int numElevators;
	public int numFloors;
	
	private HashMap<Integer, ArrayList<Integer>> ftfAvgRideTime = new HashMap<>();
	private HashMap<Integer, ArrayList<String>> ftfMinRideTime = new HashMap<>();
	private HashMap<Integer, ArrayList<String>> ftfMaxRideTime = new HashMap<>();
	private static BuildingManager instance;
	
	/*
	 * Implementing Singleton for Building Manager using ourIsntance
	 */
	public static BuildingManager getInstance() {
		if(instance == null) {
			instance = new BuildingManager();
		}
		return instance;
	}
	
	private BuildingManager() {
		setNumFloors(BuildingXMLReader.buildingReader().get(0));
		setNumElevators(BuildingXMLReader.buildingReader().get(1));
	}
	
	public void completeReport() {
		for(Floor f : Building.getInstance().getFloors()) {
			f.completeReport();
		}
		tableReport();
		personReport();
		
	}
	
	public int getNumElevators() {
		return new Integer(numElevators);
	}
	
	public int getNumFloors() {
		return new Integer(numFloors);
	}
	private void setNumFloors(int floors) {
		numFloors = floors;
	}
	
	private void setNumElevators(int elevators) {
		numElevators = elevators;
	}
	

	//LOOK HERE BOY
		//LOOK HERE BOY
			//LOOK HERE BOY
				//LOOK HERE BOY
	public void tableReport() {
		System.out.println(String.format("%s %20s %18s %18s", "Start Floor", "Average Wait Time", "Min Wait Time", " Max Wait Time"));
				for(Floor f : Building.getInstance().getFloors()){
					if(f.getFloorNum() < 10) {
						System.out.println(String.format("%6s %1s %20s %19s %19s", "Floor", f.getFloorNum(), f.getAvgWaitTime() +
					    		" seconds",  f.getMinWaitTime() + " seconds", f.getMaxWaitTime() + " seconds"));
					} else {
					    System.out.println(String.format("%6s %1s %19s %19s %19s", "Floor", f.getFloorNum(), f.getAvgWaitTime() +
					    		" seconds",  f.getMinWaitTime() + " seconds", f.getMaxWaitTime() + " seconds"));
					}

		}
				
		System.out.println("\nMAX FTF");
		initializeMaxFTF();
		System.out.print("\nFloor");
		for(Floor f : Building.getInstance().getFloors()) {
			if(f.getFloorNum() == 1) {
				System.out.print(String.format("%4s", f.getFloorNum()));
			} else {
				System.out.print(String.format("%6s", f.getFloorNum()));
			}
			
		}
		System.out.println();
		for(Floor f : Building.getInstance().getFloors()) {
			System.out.print(String.format("%3s", f.getFloorNum()));
			for (int i = 0; i < ftfMaxRideTime.get(f.getFloorNum()).size(); i++) {
					System.out.print(String.format("%6s", ftfMaxRideTime.get(f.getFloorNum()).get(i)));
	
			}
			System.out.println();
		}
		System.out.println("\nMIN FTF");
		initializeMinFTF();
		System.out.print("\nFloor");
		for(Floor f : Building.getInstance().getFloors()) {
			if(f.getFloorNum() == 1) {
				System.out.print(String.format("%4s", f.getFloorNum()));
			} else {
				System.out.print(String.format("%6s", f.getFloorNum()));
			}

		}
		System.out.println();
		for(Floor f : Building.getInstance().getFloors()) {
			//System.out.print(f.getFloorNum() + "\t");
			System.out.print(String.format("%3s", f.getFloorNum()));
			for (int i = 0; i < ftfMinRideTime.get(f.getFloorNum()).size(); i++) {
				System.out.print(String.format("%6s", ftfMinRideTime.get(f.getFloorNum()).get(i)));

			}
			System.out.println();
		}
		
		System.out.println("\nAVG FTF");
		initializeAvgFTF();
		System.out.print("\nFloor");
		for(Floor f : Building.getInstance().getFloors()) {
			if(f.getFloorNum() == 1) {
				System.out.print(String.format("%4s", f.getFloorNum()));
			} else {
				System.out.print(String.format("%6s", f.getFloorNum()));
			}

		}
		System.out.println();
		for(Floor f : Building.getInstance().getFloors()) {
			System.out.print(String.format("%3s", f.getFloorNum()));
			for (int i = 0; i < ftfAvgRideTime.get(f.getFloorNum()).size(); i++) {
				System.out.print(String.format("%6s", ftfAvgRideTime.get(f.getFloorNum()).get(i)/1000));
			}
			System.out.println();
		}
		
		
	}
	
	public void initializeMaxFTF() {
		for(Floor f : Building.getInstance().getFloors()) {
			if(!ftfMaxRideTime.containsKey(f.getFloorNum())){
				ftfMaxRideTime.put(f.getFloorNum(), new ArrayList<>(Building.getInstance().getNumFloors()));
			}
		}
		
		for (Floor f : Building.getInstance().getFloors()) {
			
			for(int i = 0; i < Building.getInstance().getNumFloors(); i++) {
					if(f.getFloorNum() == i+1) {
						ftfMaxRideTime.get(f.getFloorNum()).add("X");
						continue;
					}
					
					if (!f.getCompleted().isEmpty()){
						int max = Integer.MIN_VALUE;
						for(int j = 0; j < f.getCompleted().size(); j++) {
							
							if (f.getCompleted().get(j).getStartFloorNum() == i+1) {
								if(f.getCompleted().get(j).getRideTime() > max) {
									max = new Integer((int) f.getCompleted().get(j).getRideTime());

								}
							}
							
						}
						
						if(max != Integer.MIN_VALUE) {
							max = max/1000;
							String temp = Integer.toString(max);
							ftfMaxRideTime.get(f.getFloorNum()).add(temp);
						}else {
							ftfMaxRideTime.get(f.getFloorNum()).add("0");
						}
					}else {
						ftfMaxRideTime.get(f.getFloorNum()).add("0");
					}
			}
		}
	
	}
	
	public void initializeMinFTF() {
		for(Floor f : Building.getInstance().getFloors()) {
			if(!ftfMinRideTime.containsKey(f.getFloorNum())){
				ftfMinRideTime.put(f.getFloorNum(), new ArrayList<>(Building.getInstance().getNumFloors()));
			}
		}
		
		for (Floor f : Building.getInstance().getFloors()) {
			
			for(int i = 0; i < Building.getInstance().getNumFloors(); i++) {
					if(f.getFloorNum() == i+1) {
						ftfMinRideTime.get(f.getFloorNum()).add("X");
						continue;
					}
					
					if (!f.getCompleted().isEmpty()){
						int min = Integer.MAX_VALUE;
						for(int j = 0; j < f.getCompleted().size(); j++) {
							
							if (f.getCompleted().get(j).getStartFloorNum() == i+1) {
								if(f.getCompleted().get(j).getRideTime() < min) {
									min = new Integer((int) f.getCompleted().get(j).getRideTime());

								}
							}
							
						}
						
						
						if(min != Integer.MAX_VALUE) {
							min = min/1000;
							String temp = Integer.toString(min);
							ftfMinRideTime.get(f.getFloorNum()).add(temp);
						}else {
							ftfMinRideTime.get(f.getFloorNum()).add("0");
						}
			

					}else {
						ftfMinRideTime.get(f.getFloorNum()).add("0");
					}
			}
		}
	
	}
	
	public void initializeAvgFTF() {
		
		HashMap<Integer, ArrayList<Integer>> temp = new HashMap<>();
		HashMap<Integer, ArrayList<Integer>> temp1 = new HashMap<>();
		for(Floor f : Building.getInstance().getFloors()) {
			if(!temp.containsKey(f.getFloorNum())){
				temp.put(f.getFloorNum(), new ArrayList<>(Building.getInstance().getNumFloors()));
			}
			if(!ftfAvgRideTime.containsKey(f.getFloorNum())){
				ftfAvgRideTime.put(f.getFloorNum(), new ArrayList<>(Building.getInstance().getNumFloors()));
			}
			if(!temp1.containsKey(f.getFloorNum())){
				temp1.put(f.getFloorNum(), new ArrayList<>(Building.getInstance().getNumFloors()));
			}
			
			for(int i = 0; i < Building.getInstance().getNumFloors(); i++) {
				temp.get(f.getFloorNum()).add(i, 0);
				temp1.get(f.getFloorNum()).add(i, 0);
			}
		}
		
		for (Floor f : Building.getInstance().getFloors()) {
			for (int i = 0; i < f.getCompleted().size(); i++) {
				temp.get(f.getCompleted().get(i).getStartFloorNum()).add(f.getFloorNum()-1, temp.get(f.getCompleted().get(i).getStartFloorNum()).get(f.getFloorNum()-1) + 1);;
			}
		}
		
		for (Floor f : Building.getInstance().getFloors()) {
			for (int i = 0; i < f.getCompleted().size(); i++) {
				int current = new Integer((int) f.getCompleted().get(i).getRideTime());
				temp1.get(f.getCompleted().get(i).getStartFloorNum()).add(f.getFloorNum()-1, temp1.get(f.getCompleted().get(i).getStartFloorNum()).get(f.getFloorNum()-1) + current);
			}
		}
		
		for (Floor f : Building.getInstance().getFloors()) {
			for (int i = 0; i < f.getCompleted().size(); i++) {
				if(temp1.get(f.getCompleted().get(i).getStartFloorNum()).get(f.getFloorNum()) != 0 && temp.get(f.getCompleted().get(i).getStartFloorNum()).get(f.getFloorNum()) != 0 ) {
					int avg = ((temp1.get(f.getCompleted().get(i).getStartFloorNum()).get(f.getFloorNum()) / temp.get(f.getCompleted().get(i).getStartFloorNum()).get(f.getFloorNum())));
					temp1.get(f.getCompleted().get(i).getStartFloorNum()).add(f.getFloorNum(), avg);
					
				}

			}
		}
		for (Floor f : Building.getInstance().getFloors()) {
			for(int i = 0; i < Building.getInstance().getNumFloors(); i++) {
				ftfAvgRideTime.get(f.getFloorNum()).add(temp1.get(f.getFloorNum()).get(i));
			}
		}
	}
	
	public void personReport() {
		
		System.out.println(String.format("\n%8s %18s %18s %18s %18s %18s", "Person", "Start Floor", "End Floor", "Wait Time", "Ride Time", "Total Time"));
		Map<String, Person> temp2 = new TreeMap<String, Person>();
		for(Floor f : Building.getInstance().getFloors()) {
			for(int i = 0; i < f.getCompleted().size(); i++) {
	
					temp2.put(f.getCompleted().get(i).getId(), f.getCompleted().get(i));
				
			}
		}

		
		for(int j = 0; j < temp2.size()*2; j++) {
			
				if(temp2.get("P" + (j+1)) != null) {
					if(j+1 < 10) {
						System.out.println(String.format("%7s %1s %11s %19s %23s %18s %18s", "Person", temp2.get("P" + (j+1)).getId(), temp2.get("P" + (j+1)).getStartFloorNum(),
								temp2.get("P" + (j+1)).getDestinationFloorNum(), temp2.get("P" + (j+1)).getWaitTime()/1000 + " seconds", temp2.get("P" + (j+1)).getRideTime()/1000 + " seconds",
								(temp2.get("P" + (j+1)).getWaitTime() + temp2.get("P" + (j+1)).getRideTime())/1000 + " seconds"));
					}else {
						System.out.println(String.format("%7s %1s %10s %19s %23s %18s %18s", "Person", temp2.get("P" + (j+1)).getId(), temp2.get("P" + (j+1)).getStartFloorNum(),
								temp2.get("P" + (j+1)).getDestinationFloorNum(), temp2.get("P" + (j+1)).getWaitTime()/1000 + " seconds", temp2.get("P" + (j+1)).getRideTime()/1000 + " seconds",
								(temp2.get("P" + (j+1)).getWaitTime() + temp2.get("P" + (j+1)).getRideTime())/1000 + " seconds"));
					}
					//System.out.println("Person " + temp2.get("P" + (j+1)).getId() + "\t\t" + temp2.get("P" + (j+1)).getStartFloorNum() + "\t\t" + temp2.get("P" + (j+1)).getDestinationFloorNum() + "\t\t"
						//+ temp2.get("P" + (j+1)).getWaitTime()/1000 + "seconds" + "\t\t" + temp2.get("P" + (j+1)).getRideTime()/1000 + "seconds" + "\t\t" + (temp2.get("P" + (j+1)).getWaitTime() + temp2.get("P" + (j+1)).getRideTime())/1000 + "seconds" );
				}
		}
		

	}
	
}
