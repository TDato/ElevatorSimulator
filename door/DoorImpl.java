package door;

import exceptions.InvalidArgumentException;

public class DoorImpl implements Door  {
	private boolean open;
	
	public DoorImpl() {
		setOpen(false);
	}
	
	public void openDoor() {
		setOpen(true);
	}

	public void closeDoor() {
		setOpen(false); 
	}
	
	public void setOpen(boolean openIn) {
		open = openIn;
	}

	public boolean getOpen() {
		return new Boolean(open);
	}
	


}
