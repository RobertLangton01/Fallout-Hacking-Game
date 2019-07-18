/*
      This file is called wordgame.java
	  
	  This file will ask for a difficulty level (1-5)
	  and then proceed to list a set of words of length determined by 
	  the difficulty, with a correct 'password' among them. 
	  The user will then have 4 chances to guess the password, with an incorrect
	  guess revealing how many letters positions are correct.
*/
import java.util.concurrent.ThreadLocalRandom;
import java.util.*;
import java.io.*;

class wordgame {
	File dict = new File("enable1.txt");
	private int wordLength;
	private int numberOfWords;
	private String password;
	private ArrayList<String> words = new ArrayList<String>();
	private ArrayList<String> chosenWords = new ArrayList<String>();
	
	/* Constructor for the class wordgame,
	   accepts the integer x which will be a value
	   between 1 - 5, and will determine the difficulty of the game
	   via a switch statement
	*/
	public wordgame(int x) {
		wordLength = 0;
		numberOfWords = 0;
		
		// Switch Statement to set the difficulty.
		switch(x) {
			case 1:
			    wordLength = ThreadLocalRandom.current().nextInt(3, 7);
				numberOfWords = ThreadLocalRandom.current().nextInt(5, 9);
				break;
			case 2:
			    wordLength = ThreadLocalRandom.current().nextInt(4, 8);
				numberOfWords = ThreadLocalRandom.current().nextInt(6, 10);
				break;
			case 3:
			    wordLength = ThreadLocalRandom.current().nextInt(5, 9);
				numberOfWords = ThreadLocalRandom.current().nextInt(7, 11);
				break;
			case 4:
			    wordLength = ThreadLocalRandom.current().nextInt(6, 10);
				numberOfWords = ThreadLocalRandom.current().nextInt(8, 12);
				break;
			case 5:
			    wordLength = ThreadLocalRandom.current().nextInt(7, 11);
				numberOfWords = ThreadLocalRandom.current().nextInt(8, 12);
				break;
		}
		
		// We fill in the ArrayList words from the file "enable1.txt"
		// that are of length wordLength.
		try(Scanner scn = new Scanner(dict)) {
			while(scn.hasNext()) {
				String word = scn.nextLine();
				if(word.length() == wordLength) {
					words.add(word);
				}
			}
			
			//We now collect a random sample of words numberOfWords
			//from words and add them to chosenWords.
			for(int i = 0; i < numberOfWords; i++) {
				int rand = ThreadLocalRandom.current().nextInt(0, words.size() + 1);
				chosenWords.add(words.get(rand));
			}
			
			//Set the password to random word in chosenWords
			int rand = ThreadLocalRandom.current().nextInt(0, chosenWords.size() + 1);
			password = chosenWords.get(rand);
		} catch(IOException exc) {
			System.out.println("Error: " + exc);
		}		
	}
	
	/* 
	   The method checkGuess
	   Input arg String guess - the word the user has guessed
	   return int - Number of correct character positions of guess.
	   
	   We first convert the guess into lower case, then compare
	   each character position to that of password.
	*/
	private int checkInput(String guess) {
		if(guess.length() != wordLength) {
			System.out.println("Error: Entered word is incorrect length.");
			return 0;
		}
		String st = guess.toLowerCase();
		int counter = 0;
		for(int i = 0; i < wordLength; i++) {
			if(st.charAt(i) == password.charAt(i)) {
				counter++;
			}
		}
		return counter;
	}
	
	/* 
	   The method play
	   This method runs the wordgame and accepts guesses from
	   the console and uses the checkGuess method to determine
	   if the enetered word is correct.
	*/
	public void play() {
		if(wordLength == 0 || numberOfWords == 0) {
			return;
		}
		Scanner scn = new Scanner(System.in);
	    for(int i = 0; i < wordLength; i++) {
			System.out.println(chosenWords.get(i));
		}
		System.out.println("\n");
		for(int i = 0; i < 4; i++) {
			System.out.println("Guess (" + (4 - i) + " left):");
			String guess = scn.nextLine();
			int x = checkInput(guess);
		    System.out.println(x + "/" + wordLength + " correct");
			if(x == wordLength) {
				System.out.println("You win!");
				scn.close();
				return;
		    }
		}
	}
	
	public static void main(String args[]) {
		System.out.println("Please enter a number indicating difficulty (1-5)\n 1 - easy, 5 very difficult:");
		int x = 0;
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			x = (int) br.read() - '0';
			if(x < 0 || x > 5) {
				throw new IOException();
			}
			wordgame test = new wordgame(x);
	        test.play();
		} catch(IOException exc) {
			System.out.println("Error: Please enter an integer between 1 and 5");
		}
	}
	
}