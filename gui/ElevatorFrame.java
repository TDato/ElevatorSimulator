package gui;

import direction.Direction;
import java.util.HashMap;

/**
 *
 * @author Tommy Dato
 */
public class ElevatorFrame extends javax.swing.JFrame {

    private final HashMap<Integer, ElevatorPanel> elevatorPanels = new HashMap<>();
    private final int maxFloors;

    public ElevatorFrame(int max) {
        initComponents();

        maxFloors = max;
        setTitle("Elevator Display");
    }

    public void addElevator(int id, int initValue) {
        ElevatorPanel ep = new ElevatorPanel(id);
        ep.setMax(maxFloors);
        ep.setFloor(initValue);
        this.getContentPane().add(ep);
        elevatorPanels.put(id, ep);
        pack();
        setVisible(true);
    }

    public ElevatorPanel getPanel(int i) {
        return elevatorPanels.get(i);
    }
    
    public void setDirection(int elev, Direction dir) {
        getPanel(elev).setDirection(dir);
    }
    
    public void setFloor(int elev, int value) {
        getPanel(elev).setFloor(value);
    }

    public void setNumRiders(int elev, int value) {
        getPanel(elev).setNumRiders(value);
    }

    public void openDoors(int id) {
        getPanel(id).openDoors();
    }

    public void closeDoors(int id) {
        getPanel(id).closeDoors();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.FlowLayout());

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
