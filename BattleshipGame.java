 /*
    NAME: Aimee Haas
    COS 161, Summer 2021, Prof. Amorelli
    Project 04
    File Name: BattleShip.java
*/


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Scanner;

public class BattleshipGame {
    
    static DrawingPanel panel = new DrawingPanel(420, 810);   
    static Graphics g = panel.getGraphics();  
    static Scanner playerInput = new Scanner(System.in);
    static int player = 1;
    private static String[] playerString = new String[] {"One", "Two"};
    private static String[] shipTypes = new String[] {"Carrier", "Battleship", 
            "Destroyer", "Submarine", "Patrol Boat"};
    private static Ship[] allShips = new Ship[10];
    private static boolean playerSunk = false;
    private static String winner = "none"; 
    
            
   
    public static void main(String[] args) {
        drawGrid();
        PegHole[] bothGrids = new PegHole[200]; ///two 10 x 10 grids
        fillGrid(bothGrids);
        drawPegs(bothGrids);
        setBoard(player, bothGrids);
        player++;
        
        //clears board of other ships chosen by opponent
        g.setColor(Color.BLUE);
        g.fillRect(60,75, 300, 300);
        g.fillRect(60,435, 300, 300);
        drawPegs(bothGrids);
        
        setBoard(player, bothGrids);
        player--;
        setShips(bothGrids);
        gameOn(bothGrids);
        System.out.println("Winner is" + winner);
        
    }
    
    ///Recursive method that continues turns until a player's ships are all sunk. 
    private static void gameOn(PegHole[] bothGrids) {
        checkSunkenShips(bothGrids);
        //drawPegs(bothGrids, player);
        if (playerSunk == true) {
            return;
        }
        else {
            takeATurn(bothGrids);
            gameOn(bothGrids);        
        }
     
    }
    
    private static void takeATurn(PegHole[] playPegs) {
        g.setColor(Color.BLUE);
        g.fillRect(60,75, 300, 300);
        g.fillRect(60,435, 300, 300);
        drawPegs(playPegs);
        drawShips();
        //guessing the players opposite board
        int guessPlayer = 0;
        if (player ==1) {
            guessPlayer = 2;
        }
        else if(player == 2) {
            guessPlayer = 1;
        }

        System.out.println("Player: " + player + " Guess your opponet's ship coordinates. \nRow (1-10)");
        int row = playerInput.nextInt(); 
        System.out.println("Player: " + player + " Guess your opponet's ship coordinates. \nWhich Column (A-J)?");
        char column = playerInput.next().charAt(0); 
        int guessCoord = coordToPegIndex(row, column, guessPlayer);
        playPegs[guessCoord].setGuessed();
        
        if (player ==1) {
            player = 2;
        }
        else if (player ==2){
            player = 1;
        }
        
    }
    
    private static void checkSunkenShips(PegHole[] p) {
        int playerOneSunkCounter = 0;
        int playerTwoSunkCounter = 0;
        //checks player one for sunken pegs
        for(int i = 0; i < 100; i ++) {
            if (p[i].getSunk() == true) {
                playerOneSunkCounter++;
            }
        }
        //checks player two for sunken pegs
        for(int i = 100; i < 200; i ++) {
            if (p[i].getSunk() == true) {
                playerTwoSunkCounter++;
            }
        }
        if (playerOneSunkCounter == 17) {
            winner = "Two";
            playerSunk = true; 
            g.setColor(Color.BLUE);
            g.fillRect(60,75, 300, 300);
            g.fillRect(60,435, 300, 300);
            drawPegs(p);
            drawShips();
        }
        else if (playerTwoSunkCounter == 17) {
            winner = "One";
            playerSunk = true;
            g.setColor(Color.BLUE);
            g.fillRect(60,75, 300, 300);
            g.fillRect(60,435, 300, 300);
            drawPegs(p);
            drawShips();
        }

    }
    
    
    private static void fillGrid(PegHole[] grid) {
        for (int i = 0; i < 200; i++) {
            PegHole tempPeg = new PegHole(false, false, false);
            grid[i] = tempPeg;
        }  
    }
    
    private static void drawPegs(PegHole[] bothGrids) {
        
        ////draws unfilled pegholes, changes the order in which the grids are drawn depending upon the player
        //if it's player one, the other players board is on top, and the player one board is on Bottom
        //if hole is guessed, white peg is placed
        //if hole is sunk, red peg is placed
  
        int arrayIndex = 100;
        if (player == 2) {
            arrayIndex = 0;          
        }

        for (int i = 0; i < 100; i++) {

            int xIncrement = i %10;
            int yIncrement = i / 10;
            if (bothGrids[arrayIndex].getGuessed() == false &&
                    bothGrids[arrayIndex].getSunk() == false) {
                    g.setColor(Color.WHITE);
                    g.drawOval(70 + xIncrement*30, 85 + yIncrement*30, 10,10);
                    g.setColor(Color.BLUE);
            }
            else if(bothGrids[arrayIndex].getGuessed() == true &&
                    bothGrids[arrayIndex].getSunk() == false) {
                    g.setColor(Color.WHITE);
                    g.fillOval(70 + xIncrement*30, 85 + yIncrement*30, 10,10);
                    g.setColor(Color.BLUE);
                
            }
            else if(bothGrids[arrayIndex].getSunk() == true){
                    g.setColor(Color.RED);
                    g.fillOval(70 + xIncrement*30, 85 + yIncrement*30, 10,10);
                    g.setColor(Color.BLUE);
                
            }
            arrayIndex++;
        }    
        arrayIndex = 0;
        if (player == 2) {
            arrayIndex = 100;          
        }
        for (int j = 100; j < 200; j++) {
            int xIncrement = (j%10) ;
            int yIncrement = (j / 10) - 10;
            if (bothGrids[arrayIndex].getGuessed() == false &&
                    bothGrids[arrayIndex].getSunk() == false) {
                    g.setColor(Color.WHITE);
                    g.drawOval(70 + xIncrement*30, 445 + yIncrement*30, 10,10);
                    g.setColor(Color.BLUE);
            }
            else if(bothGrids[arrayIndex].getGuessed() == true &&
                    bothGrids[arrayIndex].getSunk() == false) {
                    g.setColor(Color.WHITE);
                    g.fillOval(70 + xIncrement*30, 445 + yIncrement*30, 10,10);
                    g.setColor(Color.BLUE);
                
            }
            else if(bothGrids[arrayIndex].getSunk() == true){
                    g.setColor(Color.RED);
                    g.fillOval(70 + xIncrement*30, 445 + yIncrement*30, 10,10);
                    g.setColor(Color.BLUE);
            }
            arrayIndex++;
        }
        
    }
    
    
    private static void setBoard(int playerNum, PegHole[] bothGrids) {

        int shipIndex = 0;
        if (playerNum == 2) {
            shipIndex = 5;
        }
        
        System.out.println("It's player " + playerString[playerNum-1] + "'s turn."
                + "\n Provide coordinate and orientation (H-horizontal, V-vertical)"
                + "\n for each ship type.  "
                + "\n Coordinate should be the the upper left peg of the ship. "
                + "\n ------D--------------"
                + "\n 1----[x]-------------"
                + "\n 2----[o]-------------"
                + "\n 2----[o]-------------"
                + "\n Submarine input for the above example: D 1 V");
        
        //each player will input top left Coord for each type of ship, creating ship objects/setting pegs/drawing temp ships
        for (String sType : shipTypes) {
            System.out.println("Select where shiptype: "+ sType +" should go. \nWhich Row (1-10)?");
            int row = playerInput.nextInt(); 
            System.out.println("Column (A-J)");
            char column = playerInput.next().charAt(0);
            System.out.println("Orientation (H-horizontal, V-vertical)");
            char orientation = playerInput.next().charAt(0);
            int startIndex = coordToPegIndex(row, column, playerNum);
            Ship tempShip = new Ship(startIndex, orientation, sType);
            allShips[shipIndex] = tempShip;
            drawOneShip(startIndex, shipIndex, orientation);
            shipIndex++;  
        }  
    }
    
    ////sets all pegs associated with ships as occupied 
    private static void setShips(PegHole[] p) {
        for (int i = 0; i < allShips.length; i ++) {
            for(int j = 0; j < allShips[i].getLength(); j++) {
                if (allShips[i].getOrientation() == 'H') {
                    p[allShips[i].getStartIndex()+j].setOccupied();
                }  
                else {
                p[allShips[i].getStartIndex()+ (j*10)].setOccupied();        
                }
            }
        }
    }
    
    private static void drawShips() {
        if (player == 1) {
            for(int i = 0; i < 5; i ++) {
                g.setColor(Color.GRAY);
                int xIncrement = allShips[i].getStartIndex()%10;
                int yIncrement = (allShips[i].getStartIndex()/10);
                int length = allShips[i].getLength(); 
                if (allShips[i].getOrientation() == 'H') {
                    g.drawRect(60 + (xIncrement*30), 435 + (yIncrement * 30), length*30, 30);
                }
                else {
                    g.drawRect(60 + (xIncrement*30), 435 + (yIncrement * 30), 30, length*30);
                }
            }         
        }
        else {
            for(int i = 5; i < 10; i++) {
                g.setColor(Color.GRAY);
                int xIncrement = allShips[i].getStartIndex()%10;
                int yIncrement = (allShips[i].getStartIndex() / 10)- 10;
                int length = allShips[i].getLength(); 
                if (allShips[i].getOrientation() == 'H') {
                    g.drawRect(60 + (xIncrement*30), 435 + (yIncrement * 30), length*30, 30);
                }
                else {
                    g.drawRect(60 + (xIncrement*30), 435 + (yIncrement * 30), 30, length*30);
                }
            }
        }
        g.setColor(Color.BLUE);
    }
    
    //for the initial choosing of ships
    private static void drawOneShip(int northWestIndex, int shipArrayIndex, char orientation) {
        g.setColor(Color.GRAY);
        int x = northWestIndex % 10;
        int y = northWestIndex / 10;
        int length = allShips[shipArrayIndex].getLength();
        if (northWestIndex >= 100) {
            y = (northWestIndex / 10) - 10;
        }
        if (orientation == 'H') {
            g.drawRect(60 + (x*30), 435 + (y * 30), length*30, 30);
        }
        else {
            g.drawRect(60 + (x*30), 435 + (y * 30), 30, length*30);
        }
        g.setColor(Color.BLUE);
        
        
    }
           
    
    private static void drawGrid() {
        String[] rowNums = new String[] {"1", "2", 
                "3", "4", "5", "6", "7", "8", "9", "10"};   
        g.setColor(Color.BLUE);
        g.fillRoundRect(30,30,360,750, 30, 30);
        g.setColor(Color.WHITE);
        g.drawRoundRect(60, 75, 300, 300, 10, 10);
        g.drawRoundRect(60, 435, 300, 300, 10, 10);
        g.setFont(new Font("Courier", Font.PLAIN, 26)); 
        g.drawString("A B C D E F G H I J", 60, 65);
        g.drawString("A B C D E F G H I J", 60, 755);

        //Draw nums
        g.setFont(new Font("Courier", Font.PLAIN, 20)); 
        for (int i = 0; i < rowNums.length; i++) {
            g.drawString(rowNums[i], 36, 98 + (i*30));   
        }
        for (int i = 0; i < rowNums.length; i++) {
            g.drawString(rowNums[i], 36, 458 + (i*30));   
        }
            
    }
    
    //The peg index is for the bothGrids 10x10 of pegholes, player one has pegholes 0-99, 
    //player two has pegholes 100-199. Power of base ten! 
    public static int coordToPegIndex(int roww, char column, int p) {
        int startingNum = 0;
        if (p == 2) {
            startingNum = 100;
        }
        int row = (roww-1)*10;
        int col = convertColToInt(column);
        return startingNum + col + row;    
    }
    
    //Converts from a column letter to the int representation
    public static int convertColToInt(char file) {
        switch (file) {
            case 'A':
                return  0;
            case 'B':
                return 1;
            case 'C':
                return 2;
            case 'D':
                return 3;
            case 'E':
                return 4;
            case 'F':
                return 5;
            case 'G':
                return 6;
            case 'H':
                return 7;
            case 'I':
                return 8;
            case 'J':
                return 9;
                
            default:
                //Incorrect file entered
                return 0;
        }
    }


}
