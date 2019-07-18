/*    
      This file is called maze.java
	  
	  To run this file: Open command prompt and change
	  directory to where maze.java is saved.
	  File is run the normal way by:
	  javac maze.java
	  java maze
	  
	  The command prompt will ask for the full path of the
	  maze file location, which should be entered.
	  
	  This file will then attempt to find a solution
	  to the maze file and output either the solution in the
	  desired form or that no solution is possible.
	 
*/
import java.io.*;
import java.util.Scanner;

class maze {
    // maze will store the integer values of the arrays
	// x-y start/end will store the start and end points of the maze.
	// output is the output array to print to console if a solution is found.
	// path stores the coordinates of the points in the path
	// currentX & currentY are the coordinates of the point we are currently on
	// counter increases with every additional point added to path
	private int maze[][];
	private int xEnd, yEnd;
	private int xStart, yStart;
	private char output[][];
	private int currentX, currentY;
	private int path[][];
	private int counter;
	private boolean pathPossible;
	
	// Constructor for the maze class.
	public maze(int numbers[][], int x1, int y1, int x2, int y2) {
		maze = numbers;
		xStart = x1;
		yStart = y1;
		xEnd = x2;
		yEnd = y2;
		currentX = x1;
		currentY = y1;
		counter = 0;
		pathPossible = true;
		
		int width = numbers.length;
		int height = numbers[1].length;
		path = new int[width*height][3];
		output = new char[width][height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				if(maze[i][j] == 1) {
					output[i][j] = '#';
				} else {
					output[i][j] = ' ';
				}
			}
		}
	}
	
	/*  The method findPath()
	
	    This method starts at the starting point
		then repeatedly calls the method 
		move() until currentX currentY are equal to the end
		points.
		
		If at a point there are no possible move options,
		we mark this point with an 'O' and return to the previous 
		point in path, unless we are at the start point. In this case, 
		there are no possible paths so we set pathPossible to false and return.
		
		We change the 'O's back to ' ' when returning output. The 'O's are used
		to prevent it going down this route again.
	*/
	public void findPath() {
		output[currentX][currentY] = 'S';
		path[0][0] = currentX;
		path[0][1] = currentY;
		path[0][2] = 0;
		while(currentX != xEnd || currentY != yEnd) {
			move();
			if(path[counter][2] == 3) {
				if(counter == 0) {
					pathPossible = false;
					return;
				}
				output[currentX][currentY] = 'O';
				counter--;
			}
			currentX = path[counter][0];
			currentY = path[counter][1];
			output[currentX][currentY] = 'X';
		}
		output[currentX][currentY] = 'E';
	}
	
	/*  The method move()
	
	    This method starts at the current point in path
		and looks for the next potential point in the order of direction
		E S W N. 
		
		The method checks if the current point is at
		the edge of the maze so it can adjust for wrapping.
		
		The method also checks that the next point chosen 
		hasn't already been written on to prevent looping.
		We add path[counter][2]++ at the beginning of each if statement
		since if we return to a previous point we want to start on the 
		next point of rotation.
	*/
	private void move() {
		boolean madeMove = false;
		while(madeMove == false)
		    switch(path[counter][2]) {
			    case 0:
			        if(currentX == maze.length - 1) {
						if(output[0][currentY] == ' ') {
							path[counter][2]++;
							counter++;
							path[counter][0] = 0;
							path[counter][1] = currentY;
							path[counter][2] = 0;
							madeMove = true;
							break;
						}
					} else {
						if(output[currentX + 1][currentY] == ' ') {
							path[counter][2]++;
							counter++;
							path[counter][0] = currentX + 1;
							path[counter][1] = currentY;
							path[counter][2] = 0;
							madeMove = true;
							break;
							}
						}
					path[counter][2]++;
					break;
			    case 1:
				    if(currentY == maze[1].length - 1) {
						if(output[currentX][0] == ' ') {
							path[counter][2]++;
							counter++;
							path[counter][0] = currentX;
							path[counter][1] = 0;
							path[counter][2] = 0;
							madeMove = true;
							break;
						}
					} else {
						if(output[currentX][currentY + 1] == ' ') {
							path[counter][2]++;
							counter++;
							path[counter][0] = currentX;
							path[counter][1] = currentY + 1;
							path[counter][2] = 0;
							madeMove = true;
							break;
						}
					}
					path[counter][2]++;
					break;
			    case 2:
				    if(currentX == 0) {
						if(output[maze.length - 1][currentY] == ' ') {
							path[counter][2]++;
							counter++;
							path[counter][0] = maze.length - 1;
							path[counter][1] = currentY;
							path[counter][2] = 0;
							madeMove = true;
							break;
						}
					} else {
						if(output[currentX - 1][currentY] == ' ') {
							path[counter][2]++;
							counter++;
							path[counter][0] = currentX - 1;
							path[counter][1] = currentY;
							path[counter][2] = 0;
							madeMove = true;
							break;
						}
					}
					path[counter][2]++;
					break;
			    case 3:
				    if(currentY == 0) {
						if(output[currentX][maze[1].length - 1] == ' ') {
							counter++;
							path[counter][0] = currentX;
							path[counter][1] = maze[1].length - 1;
							path[counter][2] = 0;
							madeMove = true;
							break;
						}
					} else {
						if(output[currentX][currentY - 1] == ' ') {
							counter++;
							path[counter][0] = currentX;
							path[counter][1] = currentY - 1;
							path[counter][2] = 0;
							madeMove = true;
							break;
					    }
					}
					madeMove = true;
					break;
		    }
	}
	
	/* The method getPathPossible
	
	   This method returns the private boolean pathPossible.
	*/
	public boolean getPathPossible() {
		return pathPossible;
	}
	
	/* The method getOutput
	
	   This method converts the 'O's in output back into ' ', since
	   these were paths traversed that did not lead to the end point.
	   The method then prints to the console the output array.
	*/
	public void getOutput() {
		output[xStart][yStart] = 'S';
		for(int j = 0; j < output[1].length; j++) {
			for(int i = 0; i < output.length; i++) {
				if(output[i][j] == 'O') {
					output[i][j] = ' ';
				}
				if(i == output.length - 1) {
					System.out.print(output[i][j] + "\n");
				} else {
					System.out.print(output[i][j] + " ");
				}
			}
		}
	}
	
	public static void main(String args[]) {
		File f = null;
		Scanner scan = null;
		
		/* We recieve a file at runtime and expect an error
		   if the file cannot be found.
		*/ 
		try(BufferedReader br = new BufferedReader(
		                        new InputStreamReader(System.in))) 
		{
			System.out.println("Please enter the full path of the maze file:");
			f = new File(br.readLine());
			scan = new Scanner(f);
			
		    /* We collect integers from the file and enter them
		       into arrays so we can construct a maze object. We
		       assume that the file is in the desired format.
		    */
		    int start[] = new int[2];
		    int end[] = new int[2];
		    int width, height;
		    width = scan.nextInt();
		    height = scan.nextInt();
		    int numbers[][] = new int[width][height];
		    for(int i = 0; i < 2; i++)
			    start[i] = scan.nextInt();
		    for(int i = 0; i < 2; i++)
			    end[i] = scan.nextInt();
		    for(int j = 0; j < height; j++) {
			    for(int i = 0; i < width; i++) {
				    numbers[i][j] = scan.nextInt();
			    }
		    }
		
		    /* We create a maze object with the data from the file
		       then perform the methods to solve the maze. If no possible path
		       was found during findPath then pathPossible will be false so 
		       we need to check this, and return if this is the case.*/
		    maze test = new maze(numbers, start[0], start[1], end[0], end[1]);
		    test.findPath();
		    if(test.getPathPossible() == false) {
			    System.out.println("No path was possible.");
			    return;
		    }
		    test.getOutput();
		} catch(IOException exc) {
			System.out.println("I/O Error: " + exc);
		}
	}
}