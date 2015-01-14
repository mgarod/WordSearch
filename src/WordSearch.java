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
 * 		1/14/2015 - Fixed bug which would use tiles previously
 * 					used in the target
 * To-Do List:	- Change HARD CODE variables to dynamic or random
 * 				- Generate a random game_board
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
			{'k','y','a','a','y'},
			{'k','a','k','a','k'},
			{'a','y','k','k','y'},
			{'k','a','a','y','a'},
			{'a','y','a','k','a'}
		};	//HARD CODE
	private final int array_rows = 5;	// Top to Bottom HARD CODE
	private final int array_columns = 5;// Left to right HARD CODE
	private final char[] target = {'k','a','y','a','k'};	// HARD CODE
	private final int size_of_target = 5;	// HARD CODE
	private final int max_index_of_target = size_of_target - 1;
	private int num_of_targets_found = 0;
	private int[][] coordArray = new int[size_of_target][2];
	
	/*** Public Functions ***/
	
/*	 Description: Prints the game_board to console
	 Pre: The object has a gameBoard with rows and columns defined
	 Post: n/a */
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
	
/*	 Description: Iterates over every space on the game and initiates search
	 Pre: The object has a game_board of letters,
		the number of rows and columns are defined
	 Post: num_of_targets_found is altered with the number of targets found */
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

/*	 Description:	These 4 functions use the current position and maximum
		size of the game board to determine if adjacent tiles exist. These
		functions are only used by search_board function. These functions
		only take as arguments the coordinate necessary for their check.
		Pre: Used by search_board
		Post: n/a */
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
	
	/* Description: Compares coordinates against the coordArray to see if
	 * 	the space being observed has been used before 
	 * Pre: n/a
	 * Post: n/a
	 */
	private final boolean isNotBackTracking(int current_row, int current_column,
			int index_of_target_char)
	{
		int index = 0;
		boolean isNotBackTrack = true;
		
		while ( (true == isNotBackTrack) &&
				(index < index_of_target_char) )
		{
			if ( (coordArray[index][0] == current_row) &&
				 (coordArray[index][1] == current_column) )
			{
				isNotBackTrack = false;
			}
			index++;
		}
		return isNotBackTrack;
	}
	
	/* Description: Prints coordinates of the found word sequence
	 * 	into human coordinates (i.e. not using 0th row or column)
	 * Pre: A target word has been found
	 * Post: n/a
	 */
	private final void print_found()
	{
		for(int i=0; i < size_of_target; i++)
		{
			System.out.printf("(%d,%d)", coordArray[i][0]+1, coordArray[i][1]+1);
		}
		System.out.println();
	}
	
	/* Description: When moving to the next tile on the game
		board, reset coordinates of previous the search trees.
	*  Pre: n/a
	*  Post: Moving to the next tile in the game board
	*/
	public void resetArray()
	{
		coordArray = new int[size_of_target][2];
	}
	
/*	 Description: Checks if current char == target char of the appropriate
		index. If it is, check all 4 adjacent spaces of the game_board. 
		For all existing adjacent tiles, search them (recursively) for the next
		target char. Will not use tiles that have been used once already. 
		Recursion ceases when the final target char is found.
	 Pre: Used by search_board
	 Post: n/a
*/
	private void find_next(int current_row, int current_column,
			int index_of_target_char)
	{
		// Case for any letter except for the last in target
		if	( (game_board[current_row][current_column] ==
				target[index_of_target_char])
				&&
				(index_of_target_char < max_index_of_target) )
		{
			// Store the location of the currently found letter
			coordArray[index_of_target_char][0] = current_row;
			coordArray[index_of_target_char][1] = current_column;
			
			index_of_target_char += 1;
			
			// First checks if the adjacent tile is not out of bounds
			// Then checks if the adjacent tile would be backtracking
			if ( upExists(current_row) && isNotBackTracking(current_row - 1,
					current_column, index_of_target_char-1))
			{
				find_next(current_row - 1, current_column,
						index_of_target_char);
			}
			
			if ( downExists(current_row) && isNotBackTracking(current_row + 1,
					current_column, index_of_target_char-1) )
			{
				find_next(current_row + 1, current_column,
						index_of_target_char);
			}
			
			if ( leftExists(current_column) && isNotBackTracking(current_row,
					current_column - 1, index_of_target_char-1))
			{
				find_next(current_row, current_column - 1,
						index_of_target_char);
			}
			
			if ( rightExists(current_column) && isNotBackTracking(current_row,
					current_column + 1, index_of_target_char-1) )
			{
				find_next(current_row, current_column + 1,
						index_of_target_char);
			}
		}
		// Case for the final letter of target
		if( (game_board[current_row][current_column] ==
				target[index_of_target_char])
				&&
				(index_of_target_char == max_index_of_target)
				&&
				isNotBackTracking(current_row, current_column,
						index_of_target_char)
				)
		{
			coordArray[index_of_target_char][0] = current_row;
			coordArray[index_of_target_char][1] = current_column;
			num_of_targets_found++;
			print_found();
		}
	}	
}


/* Repository for HELLO game_board


			{'h','e','o','h','e'},
			{'h','l','l','e','l'},
			{'o','l','l','o','l'},
			{'e','o','e','h','e'},
			{'e','h','l','l','o'}
*/