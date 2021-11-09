 /*
    NAME: Aimee Haas
    COS 161, Summer 2021, Prof. Amorelli
    Project 05
    File Name: Ship.java
*/

public class Ship {
    private char orient;
    private String typeShip;
    private int shipLength;
    private int pegIndexStart;
 
    public Ship(int startingIndex, char orientation, String type) {
        typeShip = type;
        orient = orientation;
        pegIndexStart = startingIndex;
        setLength(typeShip);

    }
    
    private void setLength(String sType) {
        if(sType.equals("Carrier")) {
            shipLength = 5;
        }
        else if(sType.equals("Battleship")) {
            shipLength = 4;
        }
        else if(sType.equals("Submarine")) {
            shipLength = 3;
        }
        else if(sType.equals("Destroyer")) {
            shipLength = 3;
        }
        else if(sType.equals("Patrol Boat")) {
            shipLength = 2;
        }    
    }
    
    public char getOrientation() {
        return orient;
    }
    
    public int getStartIndex() {
        return pegIndexStart;
    }
    
    public int getLength() {
        return shipLength;
    }
    
    

    

}
