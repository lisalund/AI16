public class HMM3 {
	private HMM3() {
		Utility u = new Utility();

		// Parse input
		Matrix a = u.parseMatrix();
		Matrix b = u.parseMatrix();
		Matrix pi = u.parseMatrix();
		int[] o = u.parseEmissions();

		Matrix delta = new Matrix(a.rows, o.length);
		Matrix states = new Matrix(a.rows, o.length, -1);

		Matrix deltaCol = pi.transpose().multiplyElementWise(b.getColumn(o[0]));
		delta.setColumn(0, deltaCol);

		//For each delta column (othermost loop)
		for (int i = 1; i < delta.columns; i++) {
			Matrix prevDeltaCol = delta.getColumn(i - 1);
			Matrix[] maxCols = new Matrix[a.columns];

			// For each "max column" (as many as the number of states)
			for (int j = 0; j < a.columns; j++) {
				Matrix firstMaxCol = new Matrix(a.rows, 1, prevDeltaCol.getElement(j - 1, 0));
				Matrix secondMaxCol = a.getRow(o[i]).transpose();
				Matrix thirdMaxCol = b.getColumn(o[i]);

				Matrix fullMaxCol = firstMaxCol.multiplyElementWise(secondMaxCol).multiplyElementWise(thirdMaxCol);
				maxCols[j] = fullMaxCol;
			}

			// From maxCols, extract both delta column and state column
			deltaCol = calculateDeltaColumn(maxCols);
			Matrix stateCol = calculateStateColumn(maxCols);

			delta.setColumn(i, deltaCol);
			states.setColumn(i, stateCol);
		}

		findStateSequence(delta, states);
	}

	public static void main(String[] args) {
		new HMM3();
	}

	private Matrix calculateDeltaColumn(Matrix[] maxCols) {
		Matrix deltaCol = new Matrix(maxCols[0].rows, 1, -1);

		// For every row of a maxcol
		for (int i = 0; i < maxCols[0].rows; i++) {
			double max = -1;
			for (int j = 0; j < maxCols.length; j++) {
				max = Math.max(max, maxCols[j].getElement(j, 0));
			}

			deltaCol.setElement(i, 0, max);
		}

		return deltaCol;
	}

	private Matrix calculateStateColumn(Matrix[] maxCols) {
		Matrix states = new Matrix(maxCols[0].rows, 1, -1);

		// For every row of a maxcol
		for (int i = 0; i < maxCols[0].rows; i++) {
			double max = -1;
			for (int j = 0; j < maxCols.length; j++) {
				max = Math.max(max, maxCols[j].getElement(j, 0));
			}

			for (int j = 0; j < maxCols.length; j++) {
				if (max == maxCols[j].getElement(i, 0)) {
					states.setElement(i, 0, j); // Set the state for this row to j
				}
			}
		}

		return states;
	}

	private int[] findStateSequence(Matrix delta, Matrix states) {
		int[] stateSequence = new int[delta.columns];

		double max = -1;
		int maxState = -1;
		for (int i = 0; i < delta.columns; i++) {
			for (int j = 0; j < delta.rows; j++) {
				double value = delta.getElement(j, i);
				if (max > value) {
					max = value;
					maxState = (int) states.getElement(j, i);
				}
			}

			stateSequence[i] = maxState;
		}

		return stateSequence;
	}
}
