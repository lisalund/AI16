import java.util.Arrays;

public class HMM3 {
	private HMM3() {
		Utility u = new Utility();

		// Parse input
		Matrix a = u.parseMatrix().logElementWise();
		Matrix b = u.parseMatrix().logElementWise();
		Matrix pi = u.parseMatrix().logElementWise();
		int[] o = u.parseEmissions();

		Matrix delta = new Matrix(a.rows, o.length);
		State[][] states = new State[a.rows][o.length];

		Matrix deltaCol = pi.transpose().sumElementWise(b.getColumn(o[0]));
		delta.setColumn(0, deltaCol);

		//For each delta column (othermost loop)
		int[] prevstate = new int[delta.columns];
		Arrays.fill(prevstate, -1);
		for (int i = 1; i < delta.columns; i++) {
			Matrix prevDeltaCol = delta.getColumn(i - 1);
			Matrix[] maxCols = new Matrix[a.columns];

			// For each "max column" (as many as the number of states)
			for (int j = 0; j < a.columns; j++) {
				Matrix firstMaxCol = new Matrix(a.rows, 1, prevDeltaCol.getElement(j, 0));
				Matrix secondMaxCol = a.getRow(j).transpose();
				Matrix thirdMaxCol = b.getColumn(o[i]);

				Matrix fullMaxCol = firstMaxCol.sumElementWise(secondMaxCol).sumElementWise(thirdMaxCol);
				maxCols[j] = fullMaxCol;
			}

			// From maxCols, extract both delta column and state column
			deltaCol = calculateDeltaColumn(maxCols);
			Matrix stateCol = new Matrix(new double[1][1]);

			delta.setColumn(i, deltaCol);
			//states.setColumn(i, stateCol);
			for(int j = 0; j < stateCol.rows; j++){
				int elem = (int) stateCol.getElement(j, i);
				states[j][i] = new State(elem, prevstate[i]);
				prevstate[i] = elem;
			}
		}

		//u.printArray(findStateSequence(delta, states));
		backTrack(delta, states);
	}

	public static void main(String[] args) {
		new HMM3();
	}

	private Matrix calculateDeltaColumn(Matrix[] maxCols) {
		Matrix deltaCol = new Matrix(maxCols[0].rows, 1, -1);

		// For every row of a maxcol
		for (int i = 0; i < maxCols[0].rows; i++) {
			double max = Double.NEGATIVE_INFINITY;
			for (Matrix maxCol : maxCols) {
				max = Math.max(max, maxCol.getElement(i, 0));
			}

			deltaCol.setElement(i, 0, max);
		}

		return deltaCol;
	}

	private State[][] calculateStateColumn(Matrix[] maxCols) {
		State[][] states = new State[maxCols[0].rows][1];

		// For every row of a maxcol
		for (int i = 0; i < maxCols[0].rows; i++) {
			double max = Double.NEGATIVE_INFINITY;
			State maxState = null;
			for (int j = 0; j < maxCols.length; j++) {
				if (maxCols[j].getElement(i, 0) > max) {
					max = maxCols[j].getElement(i, 0);
					maxState = new State(j);
				}
			}

			states[i][0] = maxState;
		}

		return states;
	}

	private int[] findStateSequence(Matrix delta, State[][] states) {
		int[] stateSequence = new int[delta.columns];

		// Do one column at a time
		for (int i = 1; i < delta.columns; i++) {
			double max = Double.NEGATIVE_INFINITY;
			int maxState = -1;
			for (int j = 0; j < delta.rows; j++) {
				double value = delta.getElement(j, i);
				if (value > max) {
					max = value;
					maxState = (int) states[j][i].getState();
				}
			}

			stateSequence[i - 1] = maxState;
		}

		Matrix lastColumn = delta.getColumn(delta.columns - 1);
		double max = Double.NEGATIVE_INFINITY;
		int maxState = -1;
		for (int i = 0; i < lastColumn.rows; i++) {
			if (lastColumn.getElement(i, 0) > max) {
				max = lastColumn.getElement(i, 0);
				maxState = i;
			}
		}
		stateSequence[stateSequence.length - 1] = maxState;

		return stateSequence;
	}
	
	public void backTrack(Matrix delta, State[][] states){
		StringBuilder sb = new StringBuilder();
		State lastState;
	}
	
	private class State{
		private int state;
		private int cameFrom = -1;
		
		//Constructor for the first state
		public State(int state){
			this.state = state;
		}
		
		//Constructor for other states
		public State(int state, int cameFrom){
			this.state = state;
			this.cameFrom = cameFrom;
		}
		
		public int getState(){
			return state;
		}
		
	}
}
