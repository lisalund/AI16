import java.util.Vector;

public class Player {
    /**
     * Performs a move
     *
     * @param gameState
     *            the current state of the board
     * @param deadline
     *            time before which we must have returned
     * @return the next state the board is in after our move
     */
    public GameState play(final GameState gameState, final Deadline deadline) {
        Vector<GameState> nextStates = new Vector<GameState>();
        gameState.findPossibleMoves(nextStates);

        if (nextStates.size() == 0) {
            // Must play "pass" move if there are no other moves possible.
            return new GameState(gameState, new Move());
        }

        /**
         * Here you should write your algorithms to get the best next move, i.e.
         * the best next state. This skeleton returns a random move instead.
         */
		int bestMove = -1;
		int index = 0;

		for (int i = 0; i < nextStates.size(); i++) {
			int res;
			res = minMax(nextStates.get(i), false, 4);

			if (res > bestMove) {
				bestMove = res;
				index = i;
			}
		}

		return nextStates.elementAt(index);
	}

	/**
	 * @param currentState the current game state according to the algorithm
	 * @param player       true if it's the player's turn, false if it's the opponent's toggles recursively
	 * @param depth        current depth, decreases recursively
	 * @return
	 */
	public int minMax(GameState currentState, boolean player, int depth) {

		if (currentState.isEOG() || depth == 0) {
			return terminalStateVal(currentState, player);
		}

		Vector<GameState> nextState = new Vector<GameState>();
		currentState.findPossibleMoves(nextState); //fills nextState vector

		int v, bestPossible;

		if (player) {
			bestPossible = Integer.MIN_VALUE;
			for (GameState child : nextState) {
				v = minMax(child, false, depth - 1);
				bestPossible = Math.max(v, bestPossible);
			}
		} else {
			bestPossible = Integer.MAX_VALUE;
			for (GameState child : nextState) {
				v = minMax(child, true, depth - 1);
				bestPossible = Math.min(v, bestPossible);
			}
		}
		return bestPossible;
	}

	/**
	 * Helper method to handle final state.
	 *
	 * @param currentState current game state
	 * @param player       true if player's turn, false if opponent's
	 * @return 1 for player win, -1 for opponent win. 0 for tie.
	 */
	private int terminalStateVal(GameState currentState, boolean player) {
		if (player) {
			if (currentState.isXWin()) return 1;
			if (currentState.isOWin()) return -1;
		}
		if (!player) {
			if (currentState.isXWin()) return -1;
			if (currentState.isOWin()) return 1;
		}

		return 0;

	}
}