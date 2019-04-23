import java.util.Scanner;
import java.io.File;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;



//import com.sun.org.apache.bcel.internal.generic.Select;
//

import java.io.FileWriter;


public class SU21991847 {

    //Board size
    static int X = 6;
    static int Y = 7;

    int curDiscPos[] = new int[2];
    static int [][] grid;
    static boolean player1;


    //This variable can be used to turn your debugging output on and off. Preferably use
    static boolean DEBUG = true;
    public static void main (String[] args) {


    	//int answer = Integer.parseInt(n);// System.out.println(answer);


        //Selected colounm as input
        int selectedCol = 0;
        //Input From Terminal using a Scanner obj
        Scanner scanner = new Scanner(System.in);
        //TODO: Read in a comamnd line argument that states whether the program will be in either terminal
        //      mode (T) or in GUI mode (G) [Hint: use args and the variable below]
        String mode = args[0];

        //Sets whether the game is in terminal mode or GUI mode
        boolean gui = mode.equalsIgnoreCase("G"); // Return True or False;


        int input = -1;
        int pos = -1;
        //The 6-by-7 grid that represents the gameboard, it is initially empty
        grid = new int [X][Y];
        //Shows which player's turn it is, true for player 1 and false for player 2
        player1 = true;
        //Shows the number of special tokens a player has left
        int [] p1powers = {2, 2, 2};
        int [] p2powers = {2, 2, 2};

        int [] CurPowers = new int[3];
        selectedCol = 1;




        if (!gui) {
            //Terminal mode

            //TODO: Display 10 line title
           // System.out.println(" * * * * ");
            System.out.print("****************************************************************************\n" +"*  _______  _______  __    _  __    _  _______  _______  _______  _   ___  *  \n" +
 		 		"* |       ||       ||  |  | ||  |  | ||       ||       ||       || | |   | *\n" +
 		 		"* |       ||   _   ||   |_| ||   |_| ||    ___||       ||_     _|| |_|   | *\n" +
 		 		"* |       ||  | |  ||       ||       ||   |___ |       |  |   |  |       | *\n" +
 		 		"* |      _||  |_|  ||  _    ||  _    ||    ___||      _|  |   |  |___    | *\n" +
        "* |     |_ |       || | |   || | |   ||   |___ |     |_   |   |      |   | *\n"+
 		 		"* |_______||_______||_|  |__||_|  |__||_______||_______|  |___|      |___| *\n" +
 		 		"*                                                                          *\n" +
 		 		"****************************************************************************\n"
 		 	  );

            //Array for current player's number of power storage
            int [] curppowers = new int[3];


            while (true) {

                if (player1) {
                    StdOut.println("Player 1's turn (You are Red):");
                    curppowers = p1powers;
                } else {
                    StdOut.println("Player 2's turn (You are Yellow):");
                    curppowers = p2powers;
                }
               // CurPowers = curppowers;
                StdOut.println("Choose your move: \n 1. Play Normal \n 2. Play Bomb ("+curppowers[0]+" left) \n 3. Play Teleportation ("+curppowers[1]+" left) \n 4. Play Colour Changer ("+curppowers[2]+" left)\n 5. Display Gameboard \n 6. Load Test File \n 0. Exit");
                //TODO: Read in chosen move, check that the data is numeric
                //Check possible Cases
                String rawInput = StdIn.readString();
                while(!ValidateInput(rawInput)){
                    StdOut.println("Please choose a valid option\n");
                    Display(grid);
                    if (player1) {
                        StdOut.println("Player 1's turn (You are Red):");
                    } else if (!player1) {
                        StdOut.println("Player 2's turn (You are Yellow):");
                    }
                    StdOut.println("Choose your move: \n 1. Play Normal \n 2. Play Bomb (" + curppowers[0]
                            + " left) \n 3. Play Teleportation (" + curppowers[1] + " left) \n 4. Play Colour Changer ("
                            + curppowers[2] + " left)\n 5. Display Gameboard \n 6. Load Test File \n 0. Exit");
                    rawInput = StdIn.readString();
              
                 
                }
                input = Integer.parseInt(rawInput);
                //MARK:- Input
                switch(input) {

                    case 0:
                    System.exit(0);
                            break;

                    //Normal Disc
                    case 1: StdOut.println("Choose a column to play in:");
                        String raw = StdIn.readString();
                        while(!ValidateInput(raw)){
                            StdOut.println("Illegal move, please input legal move:");
                            Display(grid);
                            raw = StdIn.readString();

                        }
                      
                        grid = Play(Integer.parseInt(raw), grid, player1);
                        grid = GravityRush(grid);
                        break;





                    //  BOMB:
                    case 2: StdOut.println("Choose a column to play in:");
                            //TODO: Read in chosen column
                            rawInput = StdIn.readString();

                            //TODO: Check that value is within the given bounds, check that the data is numeric
                       
                            while(!rawInput.matches("[0-6]")){
                                System.out.println("Illegal move, please input legal move:");
                                //player1 = !player1;
                                rawInput = StdIn.readString();
                                

                            }
                            selectedCol = Integer.parseInt(rawInput);

                           
                            //TODO: Play bomb disc in chosen column and reduce the number of bombs left
                            if(curppowers[0] > 0){
                                grid = Bomb(selectedCol, grid, player1);
                                
                                curppowers[0] -= 1;
                            }

                            //Reduce number of bombs
                            if (player1) {
                                p1powers = curppowers;
                                //System.out.println("P1 Power Bomb is now " + p1powers[0]);
                            }
                            else if(!player1){
                               p2powers = curppowers;
                            }


                            //TODO: Check that player has bomb discs left to play, otherwise print out an error message
                            if(curppowers[0] <= 0){
                                StdOut.println("You have no bomb power discs left");
                                player1 = !player1;
                            }
                           
                            break;
                    //TELEPORTER
                    case 3: StdOut.println("Choose a column to play in:");
                            //TODO: Read in chosen column
                            rawInput = StdIn.readString();
                            //TODO: Check that value is within the given bounds, check that the data is numeric
                            while(!rawInput.matches("[0-6]")){
                                StdOut.println("Illegal move, please input legal move:");
                                Display(grid);
                                rawInput = StdIn.readString();
                            }
                            //TODO: Play teleport disc in chosen column and reduce the number of teleporters left
                            if(curppowers[1] > 0){
                                grid = Teleport(Integer.parseInt(rawInput), grid, player1);
                                curppowers[1] -= 1;
                            }
                            
                           
                            //TODO: Check that player has teleport discs left to play, otherwise print out an error message
                            if(curppowers[1]<=0){
                                StdOut.println("Player is out of Teleportation Discs");
                            }
                            break;

                    case 4: StdOut.println("Choose a column to play in:");
                             grid = GravityRush(grid);
							//TODO: Read in chosen column
							//TODO: Check that value is within the given bounds, check that the data is numeric
							//TODO: Play Colour Change disc in chosen column and reduce the number of colour changers left
							//TODO: Check that player has Colour Change discs left to play, otherwise print out an error message
                            break;

					//This part will be used during testing, please DO NOT change it. This will result in loss of marks
                    case 5: Display(grid);
                    		//Displays the current gameboard again, doesn't count as a move, so the player stays the same
                            player1 = !player1;
                            break;

					//This part will be used during testing, please DO NOT change it. This will result in loss of marks
                    case 6: grid = Test(StdIn.readString());
                            //Reads in a test file and loads the gameboard from it.
                           
                               
                            player1 = !player1;
                            break;

                    case 7: Save(StdIn.readString(), grid);
				                player1 = !player1;
                        break;

                    default: //TODO: Invalid choice was made, print out an error message but do not switch player turns
                            System.out.println("Please choose a valid option");
                            break;
                }

                if(input!=5){
                    Display(grid);
                }

                //CHECK WIN:
				//Checks whether a winning condition was met
                int win = Check_Win(grid,selectedCol);
                int playAgain = 0;
                if(win == 1){
                    System.out.println("Player 1 wins!");
                    System.out.println("Do you want to play again?\n 1. Yes\n 2. No");
                    String raw = StdIn.readString();
                    while(!raw.matches("[1-2]")){
                        
                        StdOut.println("Please choose a valid option.");
                        Display(grid);
                        raw = StdIn.readString();
                    }
                    playAgain = Integer.parseInt(raw);
                    if(playAgain == 1){                    
                        PlayAgain();
                        continue;
                     
                    }   
                  
                    if(playAgain == 2){
                      System.exit(1);
                    }

                }

                if(win == -1){
                    System.out.println("Player 2 wins!");
                    System.out.println("Do you want to play again?\n 1. Yes\n 2. No");
                    
                    String raw = StdIn.readString();
                    while(!raw.matches("[1-2]")){
                        
                        StdOut.println("Please choose a valid option.");
                        Display(grid);
                        raw = StdIn.readString();
                    }
                    playAgain = Integer.parseInt(raw);
                    if(playAgain == 1){                    
                        PlayAgain();
                        continue;
                     
                    }   
                  
                    if(playAgain == 2){
                      System.exit(1);
                    }
                }
                //THIS WILL BE CALLED EACH TURN THAT DOES NOT HAVE A WINNER! (dear I say its a feature??)
                if(win == 0){
                    //System.out.println("Draw");
                    //System.out.println("Would you like to play again?\nDo you want to player again?\n.1. Yes\n2. No");
                  //  int playAgain = scanner.nextInt();
                }


                //TODO: Switch the players turns
                player1 = !player1;
                gui = true;
       
            }//end while true
        } 
        else {
            //Graphics mode

            while (true) {
                StdOut.println("Welcome to graphics motherF%^ker!");
                break;
            }

            StdOut.println("Drop Mic");

        }

    }

    /**
     * Displays the grid, empty spots as *, player 1 as R and player 2 as Y. Shows column and row numbers at the top.
     * @param grid  The current board state
     */
    public static void Display (int [][] grid) {
        //TODO: Display the given board state with empty spots as *, player 1 as R and player 2 as Y. Shows column and row numbers at the top.


        //Grid [row][col]
        System.out.print("  0 1 2 3 4 5 6\n");

        for(int row = 0; row < grid.length;row++){

            if(row==0){
                System.out.print("0");
            }

            else if(row>0){
                System.out.print(""+(row)+"");
            }



            for(int col = 0;col < grid[row].length;col++){

                switch(grid[row][col]){
                    //Empty
                    case 0:
                        System.out.print(" *");
                        break;

                    //Normal Disc
                    //Player 1
                    case 1:
                        System.out.print(" R");
                        break;
                    //Player 2
                    case -1:
                        System.out.print(" Y");
                        break;
         //Bombs
                    case 2:
                        System.out.print(" B");
                        break;

                    case -2:
                        System.out.print("_B");
                        break;


                    default:
                        System.out.print(" "+grid[row][col]+"");

                }



            }
           System.out.println();
        }

        System.out.println();

    }

    /**
     * Exits the current game state
     */
    public static void Exit() {
        //TODO: Exit the game
    }

    /**
     * Plays a normal disc in the specified position (i) in the colour of the player given. Returns the modified grid or
     * prints out an error message if the column is full.
     * @ param i         Column that the disc is going to be played in
     * @param grid      The current board state
     * @param player1   The current player
     * @return grid     The modified board state
     */
    public static int [][] Play (int i, int [][] grid, boolean player1) {
        //TODO: Play a normal disc of the given colour, if the column is full, print out the message: "Column is full."

        //TODO: Read in chosen column
        int selectedCol = i;

        //TODO: Check that value is within the given bounds, check that the data is numeric
        if(!CheckBounds(selectedCol)){
            //ERROR OUT OF BOUNDS
            System.out.println("Illegal move, please input legal move:");
            player1 = !player1;
            return grid;
        }
        //TODO: Play normal disc in chosen column

        // Loop upwards as to check that the last row is not empty, more efficent than
        // looping from top to bottom.

        for (int x = X; x > 0; x--) {
            int slot = grid[x - 1][selectedCol];
            //System.out.println();
            // Check if the slot is empty
            if (slot == 0) {


                // Place disc
                int playerID = (player1) ? 1 : -1; // return the currentPlayers ID
                grid[x - 1][selectedCol] = 1 * playerID;// Current players ID number



                return grid;
            }
         }

        System.out.println("Column is full.");

        return grid;
    }

    /**
     *Checks whether a player has 4 tokens in a row or if the game is a draw.
     * @param grid The current board state in a 2D array of integers
     */
    public static int Check_Win (int [][] grid, int nSelectedCol) {

        //The objective of the game is to be the first to form a horizontal, vertical, or diagonal line of four of one's own discs.



        int winner = 0;
        //TODO: Check for all the possible win conditions as well as for a possible draw.

        //HORIZONTAL
        //Check if there are 4 discs of the same color in a row.

        // Loop through rows/x's

        int row_count_p1 = 0; // consecutive counts
        int row_count_p2 = 0;
        int vcountP1 = 0; // Vertical consecutive counts
        int vcountP2 = 0;
        int dCountP1 = 0;
        int dCountP2 = 0;

        int d2CountP2 = 0;


        //HORIZONTAL FOR LOOP - Loop through all colms
        for(int row = 0; row<X;row++){
            row_count_p1 = 0;
            row_count_p2 = 0;

            for(int col = 0; col<Y;col++){

                //Horizontal Count
                if(grid[row][col] == 0){
                    row_count_p1 = 0;
                    row_count_p2 = 0;
                }

                if(grid[row][col] == 1){
                    row_count_p1 += 1;
                    row_count_p2 = 0;

                }
                if(grid[row][col] == -1){
                    row_count_p2 += 1;
                    row_count_p1 = 0;
                }


                if(calcWin(row_count_p1, row_count_p2) != 99){
                    winner = calcWin(row_count_p1,row_count_p2);
                    Debug("Horizontal Win, "+grid[row][col]);
                }

            }//End Horizontal Check


        }

        //VERTICAL Check
        vcountP1 = 0;
        vcountP2 = 0;
        for(int col=0;col<Y;col++){
            //VERTICAL Check
            vcountP1 = 0;
            vcountP2 = 0;
            for(int row = X-1;row>0;row--){

                if(grid[row][col] == 0){
                    vcountP1 = 0;
                    vcountP1 = 0;
                }
                if (grid[row][col] == 1) {
                    vcountP1 += 1;
                    vcountP2 = 0;

                }
                if (grid[row][col] == -1) {
                    vcountP2 += 1;
                    vcountP1 = 0;
                }

                if(calcWin(vcountP1, vcountP2) != 99){
                    winner = calcWin(vcountP1,vcountP2);
                    Debug("Vertical Win, "+grid[row][col]);
                }



            }

        }//End Vertical Check



        // Positive Diagonal
        int loop = 0;
        while(loop < 4){
            dCountP1 = 0;
            dCountP2 = 0;
            for(int i = loop;i<Y-1;i++){

                if(grid[5-i+loop][i] == 0){
                    dCountP1 = 0;
                    dCountP2 = 0;
                }
                if(grid[5-i+loop][i] == 1){
                    dCountP1 +=1;
                    dCountP2 = 0;

                }

                if(grid[5-i+loop][i] == -1){
                    dCountP2 +=1;
                    dCountP1 = 0;

                }
                if(calcWin(dCountP1, dCountP2) != 99){
                    winner = calcWin(dCountP1,dCountP2);
                    Debug("Postive Diag Win, "+grid[5-1+loop][i] );
                    break;


                }
            }
            loop++;
        }

        //Edge Case : Positivitve Diagonal
        vcountP1 = 0;
        vcountP2 = 0;
        for(int i = 0;i<Y-1;i++){
            //Switch to 4
            if(3-i < 0){
                //Check row = 4;
                //Pos Dig Edge Check at row = 4
                for(int j = 0;j<Y-1;j++){

                    if(4-j < 0){
                        break;
                    }
                    if(grid[4-j][j] == 0){
                        vcountP1 = 0;
                        vcountP2 = 0;
                    }

                    if(grid[4-j][j] == 1){
                        vcountP1 += 1;
                        vcountP2 = 0;

                    }
                    if(grid[4-j][j] == -1){
                        vcountP2 += 1;
                        vcountP1 = 0;

                    }


                    if(calcWin(vcountP1, vcountP2) != 99){
                        winner = calcWin(vcountP1,vcountP2);
                        Debug("Edge Cases Dig Win, "+grid[4-j][j]);
                        break;
                    }
                }
                break;

            }
            if(grid[3-i][i] == 0){
                vcountP2 = 0;
                vcountP1 = 0;

            }
            if(grid[3-i][i] == 1){
                vcountP1 += 1;
                vcountP2 = 0;

            }
        if(grid[3-i][i] == -1){
                vcountP2 += 1;
                vcountP1 = 0;

            }



            if(calcWin(vcountP1, vcountP2) != 99){
                winner = calcWin(vcountP1,vcountP2);
                Debug("Edge Cases Dig Win, "+grid[3-i][i]);
            }


        }//End Edge Pos Check at row = 3

        //Neagtive Diagonal

        loop = 0;
        while(loop < 4){
            dCountP1 = 0;
            dCountP2 = 0;
            for(int i = 0;i<Y-1;i++){

                if(i+loop+1 >= X){
                    break;
                }

                if(grid[i+loop+1][i] == 0){
                    dCountP1 = 0;
                    dCountP2 = 0;
                }
                if(grid[loop+i+1][i] == 1){
                    dCountP1 +=1;
                    dCountP2 = 0;

                }

                if(grid[loop+i+1][i] == -1){
                    dCountP2 +=1;
                    dCountP1 = 0;

                }
                if(calcWin(dCountP1, dCountP2) != 99){
                    winner = calcWin(dCountP1,dCountP2);
                    Debug("-Dig Win, "+grid[loop+i+1][i]);
                    break;
                }
            }
            loop++;
        }
        //Edge Cases Negative Diagonal:
        vcountP1 = 0;
        vcountP2 = 0;
        for(int col = 0; col<Y-1;col++){
            // +- Loop plus one,
            if(2+col >= X){
                vcountP1 = 0;
                vcountP2 = 0;
                for(int j = 0; j<Y-1;j++){
                    if(j+1 >= X){
                        break;
                    }
                    if(grid[1+j][j] == 0){
                        vcountP1 = 0;
                        vcountP2 = 0;
                    }
                    if(grid[1+j][j] == 1){
                        vcountP1 += 1;
                        vcountP2 = 0;
                    }
                    if(grid[1+j][j] == -1){
                        vcountP2 += 1;
                        vcountP1 = 0;
                    }

                    if(calcWin(vcountP1, vcountP2) != 99){
                        winner = calcWin(vcountP1,vcountP2);
                        Debug("Edge Cases -Dig Win, "+grid[1+j][j]);
                        break;
                    }


                }
                break;
            }
            if(grid[2+col][col] == 0){
              vcountP1 = 0;
              vcountP2 = 0;
            }
            if(grid[2+col][col] == 1){
                vcountP1 += 1;
                vcountP2 = 0;
            }
            if(grid[2+col][col] == -1){
                vcountP2 += 1;
                vcountP1 = 0;
            }

            if(calcWin(vcountP1, vcountP2) != 99){
                Debug("Edge Cases -Dig Win, "+grid[2+col][col]);
                winner = calcWin(vcountP1,vcountP2);
            }


        }








        return winner;
    }
    public static int calcWin(int dCountP1, int dCountP2){
        // DRAW CASE
        if (dCountP1 >= 4 && dCountP2 >= 4) {
            Debug("Goku vs Vegitia!!!!");

            return 0;
        }
        // PLayer 1 Wins
        if (dCountP1 >= 4 && dCountP2 < 4) {
            Debug("Diagonal We Have a Winner, Player 1 Wins!");

            return 1;
        }
        // Player 2 Wins
        if (dCountP2 >= 4 && dCountP1 < 4) {
            Debug("Diagonal We Have a Winner, Player 2 Wins!");

            return -1;

        }

        else{
            return 99;
     }


    }
    /**
     * Plays a bomb disc that blows up the surrounding tokens and drops down tokens above it. Should not affect the
     * board state if there's no space in the column. In that case, print the error message: "Column is full."
     * @param i         Column that the disc is going to be played in
     * @param grid      The current board state
     * @param player1   The current player
     * @return grid     The modified board state
     */
    public static int [][] Bomb (int i, int [][] grid, boolean nPlayer1) {
        //TODO: Play a bomb in the given column and make an explosion take place. Discs should drop down afterwards. Should not affect the
        //      board state if there's no space in the column. In that case, print the error message: "Column is full."
        //      Leaves behind a normal disc of the player's colour
        int selectedCol = i;
        int newDiscX = 0;
        boolean player1 = nPlayer1;
        System.out.println();
        for(int x = X - 1; x>=0; x--){
            int slot = grid[x][selectedCol];
            if(x==0){
                StdOut.println("Column is full.");
                break;
            }
          
                // Check if the slot is empty
            if (grid[x][selectedCol] == 0) {
               
                //Check if colounm is full:
              


                // Slot is the first open space found in the column
                //  System.out.println("Gravity will place this bomb at " + (x - 1) + ", " + selectedCol);
                // Place disc
                int playerID = (player1) ? 1 : -1; // return the currentPlayers ID
                grid[x][selectedCol] = playerID;// Bomb Sybmol 2 * playerID

                //Delete disc ~ Default
                grid[x][selectedCol] = 0;

                //If the bomb is in row 5 the ledge of the board then place the players id
                if(x-1 == 5)
                {
                    grid[x-1][selectedCol] = playerID;
                }




                //DAMAGE SURROUNDING DISCS
                //#region Damage Discs
                // Bottom
              
                grid[x][selectedCol] = playerID; // Replace bomb with the players disc
                // Border of board Check

                // Check not on the border of the game board.
              
                if (x + 1 < X) {
                    grid[x + 1][selectedCol] = 0;// RM
                    if (selectedCol + 1 < Y) {
                        grid[x + 1][selectedCol + 1] = 0; // RT
                    }
                    if (selectedCol - 1 >= 0) {
                        grid[x + 1][selectedCol - 1] = 0; // LB
                    }
                }
                if (selectedCol + 1 < Y) {
                    grid[x][selectedCol + 1] = 0;// MM
                }
                if (selectedCol - 1 >= 0) {
                    grid[x][selectedCol - 1] = 0;// MB
                }

                if (x - 1 >= 0) {
                    grid[x - 1][selectedCol] = 0;// LM
                    if (selectedCol - 1 >= 0) {
                        grid[x - 1][selectedCol - 1] = 0; // LB

                    }
                    if (selectedCol + 1 < Y) {
                        grid[x - 1][selectedCol + 1] = 0;// RB
                    }

                }
               

           
                //#endregion




                 //Gravity Rush
                 grid = GravityRush(grid);
                 //System.out.println("Gravity Rush Called.");
                 //loop through the affected discs
                //  for(int row = newDiscX; row > 0; row--){

                //      if(CheckBounds(selectedCol+1)){
                //          int beforeRight = grid[row-1][selectedCol+1]; //Right Side
                //          grid[row][selectedCol+1] = beforeRight;
                //      }

                //      if(CheckBounds(selectedCol-1)){
                //          int beforeLeft = grid[row-1][selectedCol-1]; //Left side
                //          grid[row][selectedCol-1] = beforeLeft;
                //      }

                //  }
                

                break;
            }// End Slot == 0

        }//End For loop

        return grid;




    }

    /**
     * Plays a teleporter disc that moves the targeted disc 3 columns to the right. If this is outside of the board
     * boundaries, it should wrap back around to the left side. If the column where the targeted disc lands is full,
     * destroy that disc. If the column where the teleporter disc falls is full, play as normal, with the teleporter
     * disc replacing the tT < test_files/test1.inop disc.
     * @param i         Column that the disc is going to be played in
     * @param grid      The current board state
     * @param player1   The current player
     * @return grid     The modified board state
     */
    public static int [][] Teleport (int i, int [][] grid, boolean player1) {
        //TODO: Play a teleporter disc that moves the targeted disc 3 columns to the right. If this is outside of the board
        //      boundaries, it should wrap back around to the left side. If the column where the targeted disc lands is full,
        //      destroy that disc. If the column where the teleporter disc falls is full, play as normal, with the teleporter
        //      disc replacing the top disc.
        //      No error message is required.
        //      If the colour change disc lands on the bottom row, it must leave a disc of the current player’s colour.
        int playerID = (player1) ? 1 : -1;
        //Wrap
        if(i>4){

        }else{

        }
        for(int x = 0; x < X;x++){
            
            //Found first slot !empty
            if(grid[x][i] != 0){
               
                //Replace the dics 3 col to the right with the occupying disc ocDisc.
                grid[x][i+3] = grid[x][i];
                //Replace cuurent slot with the Telepoters players disc.
                grid[x][i] = playerID;
                break;
            }

        }

        grid = GravityRush(grid);

        
        return grid;
    }

    /**
     * Plays the colour changer disc that changes the affected disc's colour to the opposite colour
     * @param i         Column that the disc is going to be played in
     * @param grid      The current board state
     * @param player1   The current player
     * @retu<identifier>rn grid     The modified board state
     */
    public static int [][] Colour_Changer (int i, int [][] grid, boolean player) {
        //TODO: Colour Change: If the colour change disc lands on top of another disc, it changes the colour of that
        //      disc to the opposite colour. The power disc does not remain.
        //      If the colour change disc lands on the bottom row, it must leave a disc of the current player’s colour.
        return grid;
    }

     /* Reads in a board from a text file.
     * @param name  The name of the given file
     * @return
     */
    //Reads in a game state from a text file, assumes the file is a txt file
    public static int [][] Test(String name) {
        int [][] grid = new int[6][7];
        try{
            File file = new File(name+".txt");
            Scanner sc = new Scanner(file);

            for (int i = 0; i < X; i++) {
                for (int j = 0; j < Y; j++) {
                    grid[i][j] = sc.nextInt();
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return grid;
    }

    public static void PlayAgain(){
        //Reset Game
        grid = new int [X][Y];
        
        //Switch Players
        player1 = !player1;

    }
    public static Boolean ValidateInput(String n){

        if(n.matches("[0-6]+")){
            //Debug("Validated!");
            return true;
        }
        //Debug("Not valid input.");
        return false;
        
    }


    public static int [][] Save(String name, int [][] grid) {
    	try{
    		FileWriter fileWriter = new FileWriter(name+".txt");
	    	for (int i = 0; i < X; i++) {
	    		for (int j = 0; j < Y; j++) {
		    		fileWriter.write(Integer.toString(grid[i][j]) + " ");
		    	}
		    	fileWriter.write("\n");
		    }
		    fileWriter.close();
	    } catch (Exception ex) {
            ex.printStackTrace();
        }
    	return grid;
    }


    public static int [][] GravityRush(int [][] grid){
        int emptyJumpCount = 0;
        for(int col = 0; col < Y; col++){
            emptyJumpCount = 0;
            for(int x = X-1; x>=0; x--){

                
                if(grid[x][col] == 0){
                    emptyJumpCount += 1;
                }

                if(grid[x][col] != 0){
                    
                    grid[x+emptyJumpCount][col] = grid[x][col];
                    if(emptyJumpCount != 0){
                        grid[x][col] = 0;
                    }
                    x=x+emptyJumpCount;
                   
                    emptyJumpCount = 0;
                    
                    
                }
            }
        }

        return grid;

    }


    public static Boolean CheckBounds(int nSelectedCol){

        if(nSelectedCol >= Y || (nSelectedCol < 0)){

            return false;
        }
        return true;
    }
    /**
     * Debugging tool, preferably use this since we can then turn off your debugging output if you forgot to remove it.
     * Only prints out if the DEBUG variable is set to true.
     * @param line  The String you would like to print out.
     */
    public static void Debug(String line) {
        if (DEBUG)
            System.out.println(line);
    }
}
