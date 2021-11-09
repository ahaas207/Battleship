 /*
    NAME: Aimee Haas
    COS 161, Summer 2021, Prof. Amorelli
    Project 04
    File Name: Peghole.java
*/

public class PegHole {
    private boolean isSunk;
    private boolean isGuessed;
    private boolean isOccupied; 
    
    
    
    public PegHole(boolean occupied, boolean guessed, boolean sunk) {
       isOccupied =occupied;
       isGuessed = guessed;
       isSunk = sunk;
        
    }
    
    public void setOccupied() {
        isOccupied = true;
    }
    
    public boolean getOccupied() {
        return isOccupied;
    }
    
    public boolean getGuessed() {
        return isGuessed;
    }
    
    public boolean getSunk() {
        return isSunk;
    }
    
    public void setGuessed() {
        isGuessed = true;
        if (isOccupied == true) {
            isSunk = true;
        }
    }
    
    
    
    

}
