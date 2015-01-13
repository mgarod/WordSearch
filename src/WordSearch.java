/**********************************************************
 * Title:		WordSearch.java
 * Author:		Michael Garod
 * Created on:	1/12/2015
 * Description:	Searches an MxN matrix of characters(game_board)
 * 				and counts the number of letter sequences that are
 * 				equal to the target letter sequence.
 * Purpose:		An exercise in constructing a complicated algorithm,
 * 				as well as learning more complicated concepts with
 * 				Java (2D arrays, recursion, trees)
 * Modifications:
 * To-Do List:	- Change HARD CODE variables to dynamic or random
 * 				- Generate a random game_board
 * 				- Hold results until user input "show answer"
 ***********************************************************/

public class WordSearch {

	public static void main(String[] args)
	{
		WordSearch newGame = new WordSearch();
		newGame.print_board();
		newGame.search_board();
	}
	
	/*** Data Members ***/
	private final char[][] game_board = 
		{
			{'h','e','o','h','e'},
			{'h','l','l','e','l'},
			{'o','l','l','o','l'},
			{'e','o','e','h','e'},
			{'e','h','l','l','o'}
		};	//HARD CODE
	private final int array_rows = 5;	// Top to Bottom HARD CODE
	private final int array_columns = 5;// Left to right HARD CODE
	private final char[] target = {'h','e','l','l','o'};	// HARD CODE
	private final int size_of_target = 5;	// HARD CODE
	private final int max_index_of_target = size_of_target - 1;
	private int num_of_targets_found = 0;
	
	/*** Public Functions ***/
	
	// Description: Prints the game_board to console
	// Pre: The object has a gameBoard with rows and columns defined
	// Post: n/a
	public void print_board()
	{
		for(int i=0 ; i < array_rows ; i++)
		{
			for(int j=0 ; j < array_columns ; j++)
			{
				System.out.print(game_board[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	// Description: Iterates over every space on the game and initiates search
	// Pre: The object has a game_board of letters,
	//	the number of rows and columns are defined
	// Post: num_of_targets_found is altered with the number of targets found
	public void search_board()
	{
		// This is the root of every tree
		for(int i=0 ; i < array_rows ; i++)
		{
			for(int j=0 ; j < array_columns ; j++)
			{	// Every search begins with the 0th char of target
				find_next(i, j, 0);
			}
		}
		// Print results
		System.out.println(num_of_targets_found);
	}
	
	/*** Private Functions ***/
	
	// Pre: Used by search_board -> find_next
	private void increment_num_found()
	{
		num_of_targets_found++;
	}
	
	// Description:	These 4 functions use the current position and maximum
	//	size of the game board to determine if adjacent tiles exist. These
	//	functions are only used by search_board function. These functions
	//	only take as arguments the coordinate necessary for their check.
	// Pre: Used by search_board
	// Post: n/a
	private final boolean upExists(int current_row)
	{
		if ( (current_row - 1) >= 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	private final boolean downExists(int current_row)
	{
		if ( (current_row + 1) < array_rows)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	private final boolean leftExists(int current_column)
	{
		if ( (current_column - 1) >= 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	private final boolean rightExists(int current_column)
	{
		if ( (current_column + 1) < array_columns)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	// Description: Checks if current char == target char of the appropriate
	//	index. If it is, check all 4 adjacent spaces of the game_board. 
	//	For all existing adjacent spaces, search them (recursively) for the next
	//	target char. Recursion ceases when the final target char is found.
	// Pre: Used by search_board
	// Post: 
	private void find_next(int current_row, int current_column,
			int index_of_target_char)
	{
		// TRUE if current space on game_board is the same as
		//	the letter in the target word we are seeking AND we
		//	we are NOT at final letter of our target
		if( (game_board[current_row][current_column] ==
				target[index_of_target_char])
				&&
				(index_of_target_char < max_index_of_target) )
		{
			// Move to the next character in target
			index_of_target_char += 1;
			
			if ( upExists(current_row) )
			{	// If there is a valid space ABOVE current,
				// recur find_next on that space
				find_next(current_row - 1, current_column,
						index_of_target_char);
			}
			
			if ( downExists(current_row) )
			{	// If there is a valid space BELOW current,
				// recur find_next on that space
				find_next(current_row + 1, current_column,
						index_of_target_char);
			}
			
			if ( leftExists(current_column) )
			{	// If there is a valid space LEFT of current,
				// recur find_next on that space
				find_next(current_row, current_column - 1,
						index_of_target_char);
			}
			
			if ( rightExists(current_column) )
			{	// If there is a valid space RIGHT of current,
				// recur find_next on that space
				find_next(current_row, current_column + 1,
						index_of_target_char);
			}
		}
		// TRUE if current space on game_board is the same as
		//	the letter in the target word we are seeking AND we
		//	we ARE at the final letter of our target.
		if( (game_board[current_row][current_column] ==
				target[index_of_target_char])
				&&
				(index_of_target_char == max_index_of_target) )
		{
			increment_num_found();	// Found an instance of target (+1)
		}
	}
	
}
