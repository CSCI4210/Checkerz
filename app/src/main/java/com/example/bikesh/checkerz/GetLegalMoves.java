public class GetLegalMoves {
	
	/* In checkers, if a enemy piece is captureable the player must capture it.
Because of this, all possible moves must first be known before a player is able 
to choose a move. Need to create a basic move method and a jump (capture) method.
Create a boolean method that determines if a jump move is possible and limit
the player to jump moves. Store all the legal moves for the specific player 
on the current board in an array.
*/

	
	public CheckersMove[] getLegalMoves (PieceColor player) {
		//method needs to know which player it is
		//return array of CheckersMove containing all legal moves
		//if player can jump, they must do so
		
		Vector moves = new Vector();
			//store the moves
			
		//first we want to check for jumps because if one is available, simple
		//moves arent possible
		
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (GameBoard[row][col] == player) {
					if (canJump(player, row, col, row + 1, col + 1, row + 2,
							col + 2))
						moves.addElement(new CheckersMove(row, col, row + 2,
								col + 2));
					if (canJump(player, row, col, row - 1, col + 1, row - 2,
							col + 2))
						moves.addElement(new CheckersMove(row, col, row - 2,
								col + 2));
					if (canJump(player, row, col, row + 1, col - 1, row + 2,
							col - 2))
						moves.addElement(new CheckersMove(row, col, row + 2,
								col - 2));
					if (canJump(player, row, col, row - 1, col - 1, row - 2,
							col - 2))
						moves.addElement(new CheckersMove(row, col, row - 2,
								col - 2));
				}
			}
		}
	}
}