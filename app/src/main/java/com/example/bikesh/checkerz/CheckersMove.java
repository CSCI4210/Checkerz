public class CheckersMove {

/*represents a single move in the game. Contains the starting position and
ending position but does not know if the move is legal. */

int startRow, startCol; //starting position for piece
int endRow, endCol; //ending position

CheckersMove(int r1, int c1, int r2, int c2) {
	startRow = r1;
	startCol = c1;
	endRow = r2;
	endRow = c2;
}

boolean isJump() {
	return(startRow - endRow == 2) || startRow - endRow == -2);
	//determine if move is a jump, again assuming it is legal. 
	}

public boolean canJump(PieceColor player, int r1, int c1, int r2, int c2, int r3, int c3) {
	//determines if a piece can move, called by GetLegalMoves
	if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8)
			return false;
		//off the board

	if (GameBoard[r3][c3] != null)
			return false; 
		//occupied position
	
	if (player == PieceColor.RED) {
		if (GameBoard[r1][c1] == PieceColor.RED && r3 < r1)
			return false;
			//nonking red piece must move upwards
		if(GameBoard[r2][c2] != PieceColor.BLACK)
			return false;
			//there is not a black piece b/w position 1 and 3			
			return true;
			//legal jump
	}
	else {
	
		if (GameBoard[r1][c1] == PieceColor.BLACK && r3 > r1)
			return false;
			//nonking black piece must move downwards
		if(GameBoard[r2][c2] != PieceColor.RED)
			return false;
			//there is not a red piece b/w position 1 and 3			
		return true;
		//legal jump
	}
	} //end of canJump()
	
	public boolean canMove(PieceColor player, int r1, int c1, int r2, int c2){
		//simple one space move used by GetLegalMoves
		
		if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8)
			return false;
		//off the board

		if (GameBoard[r2][c2] != null)
			return false;
		//space occupied

		if (player == PieceColor.RED) {
			if (GameBoard[r1][c1] == true && r2 < r1)
				return false; 
			//nonking red piece must move upwards
			return true; 
			//legal move
		} else {
			if (GameBoard[r1][c1] == false && r2 > r1)
				return false; 
			//nonking black piece must move downwards
			return true; 
			//legal move
		}

	} // end canMove()
	
}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
}