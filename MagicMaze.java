/* Ilham Mukati
   Dr. Steinberg
   COP3503 Fall 2022
   Programming Assignment 1
*/

import java.io.*;
import java.util.*;


public class MagicMaze {
	
	public char[][] mazee;
	public boolean[][] seen;
	public HashMap<Character, ArrayList<Integer>> transport = new HashMap<Character, ArrayList<Integer>>();
	public String mazeNumber;
	public int rows;
	public int cols;
	
	
	// Creates a Magic maze object
	public MagicMaze(String nameOfFile, int r, int c) throws Exception {
		
		this.rows = r;
        this.cols = c;
		seen = new boolean[r][c];
		mazee = new char[r][c];
		try {
            Scanner FileIn = new Scanner(new File(nameOfFile));
            int i = 0;
            while(FileIn.hasNextLine()){
                String line = FileIn.nextLine();
                for (int j = 0; j < line.length(); j++) {
                    mazee[i][j] = line.charAt(j);
                }
                i++;
            }
            
        } catch (Exception e) {
            System.out.println("Error: "/*+e.getMessage()*/);
            System.exit(0);
        }
		mapMaker();  
    }

	public static void main(String[] args) throws Exception {
		System.out.println("Hello world!");
	  
		String maze1 = "maze1.txt";
		MagicMaze m1 = new MagicMaze(maze1, 11, 16);
		if (m1.solveMagicMaze()){
			System.out.println("Faks for maze 1");
		}else{
			System.out.println("its grits");
		}

		String maze2 = "maze2.txt";
		MagicMaze m2 = new MagicMaze(maze2, 11, 15);
		if (m2.solveMagicMaze()){
			System.out.println("Faks for maze 2");
		}else{
			System.out.println("its grits 2");
		}

		String maze3 = "maze3.txt";
		MagicMaze m3 = new MagicMaze(maze3, 11, 15);
		if (m3.solveMagicMaze()){
			System.out.println("Faks for maze 3");
		}else{
			System.out.println("its grits 3");
		}

		String maze4 = "maze4.txt";
		MagicMaze m4 = new MagicMaze(maze4, 15, 20);
		if (m4.solveMagicMaze()){
			System.out.println("Faks for maze 4");
		}else{
			System.out.println("its grits 4");
		}

		String maze5 = "maze5.txt";
		MagicMaze m5 = new MagicMaze(maze5, 15, 20);
		if (m5.solveMagicMaze()){
			System.out.println("Faks for maze 5");
		}else{
			System.out.println("its grits 5");
		}
	}

	//creates hashmap of transportation areas where key is the char digit and the value is
	//an arraylist of the positions
	public void mapMaker(){
		for(int i=0; i< this.rows; i++){
			for(int j=0; j< this.cols; j++){
				if(Character.isDigit(mazee[i][j])){
					if(!transport.containsKey(mazee[i][j])){
						transport.put(mazee[i][j], new ArrayList<Integer>());
					}
					transport.get(mazee[i][j]).add(i);
					transport.get(mazee[i][j]).add(j);
				}
			}
		}
	}

	//checks if in bounds, if it has not been visited and if the space is not a wall
    public boolean validSpace(int x, int y){
        if(x>=0 && x<rows && y>=0 && y<cols && !seen[x][y] && mazee[x][y] != '@' ){
			//System.out.println("We movin to"+x+","+y);
            return true;
        }
		//System.out.println("Did not like this space" +x+","+y);
        return false;
    }

	//takes in the number of the transport spots and the row and col its in and returns the row and col of the other number in the maze
	public int[] transportation(char num, int row, int col){
		int[] ans = new int[2];
		if(transport.get(num).get(0)==row && transport.get(num).get(1)==col){
			ans[0] = transport.get(num).get(2);
			ans[1] = transport.get(num).get(3);
		} else{
			ans[0] = transport.get(num).get(0);
			ans[1] = transport.get(num).get(1);
		}
		return ans;
	}
        
	//recursive function that when valid space, implements recursion
	public boolean solveMagicMazeRec(int R, int C){
		if(validSpace(R, C)){
			if (mazee[R][C] == 'X'){
				return true;
			}//for the sake of transportation
			else if(Character.isDigit(mazee[R][C])){
				seen[R][C] = true;
				int newpos[] = transportation(mazee[R][C], R, C);
				if(!seen[newpos[0]][newpos[1]]&&solveMagicMazeRec(newpos[0], newpos[1])){
					return true;
					
				}
				
			}//going through four directionally adjacent positions to find the X
			int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
			for(int i = 0; i<4; i++){
				seen[R][C] = true;
				int newx = R + directions[i][0];
				int newy = C + directions[i][1];
				if (validSpace(newx, newy) && solveMagicMazeRec(newx, newy)){
					return true;
				}/*else{
					//seen[R][C] = false;
				}*/
			}

		}
		//seen[R][C] = false;
		return false;
		
	}
	
	public boolean solveMagicMaze(){
		return solveMagicMazeRec(rows-1, 0);
	}
}
