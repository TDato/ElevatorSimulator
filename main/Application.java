package main;
/*
 * @author Tommy Dato
 */

import exceptions.InvalidArgumentException;
import timemanager.TimeManagerImpl;

public class Application {
	
	public static void main(String[] args) throws InterruptedException, InvalidArgumentException {

       TimeManagerImpl.getInstance().runTime();
    }
}
