public class HMM3 {
	private HMM3() {
		Utility u = new Utility();

		// Parse input
		Matrix a = u.parseMatrix();
		Matrix b = u.parseMatrix();
		Matrix pi = u.parseMatrix();
		int[] o = u.parseEmissions();

		int numStates = a.rows;
		int numObservations = o.length;

		Matrix delta = new Matrix(numStates, numObservations);
		Matrix states = new Matrix(numStates, numObservations, -1);

		double[] normalizationFactors = new double[numObservations];

		Matrix deltaCol = pi.transpose().multiplyElementWise(b.getColumn(o[0]));
		double normalizationFactor = 1 / deltaCol.sum();
		deltaCol = deltaCol.multiplyElementWise(normalizationFactor);
		normalizationFactors[0] = normalizationFactor;
		delta.setColumn(0, deltaCol);

		//For each delta column (othermost loop)
		for (int i = 1; i < delta.columns; i++) {
			Matrix prevDeltaCol = delta.getColumn(i - 1);
			Matrix[] maxCols = new Matrix[numStates];
			Matrix[] indexCols = new Matrix[numStates];

			// For each "max column" (as many as the number of states)
			for (int j = 0; j < numStates; j++) {
				Matrix firstMaxCol = new Matrix(numStates, 1, prevDeltaCol.getElement(j, 0));
				Matrix secondMaxCol = a.getRow(j).transpose();
				Matrix thirdMaxCol = b.getColumn(o[i]);

				Matrix fullMaxCol = firstMaxCol.multiplyElementWise(secondMaxCol).multiplyElementWise(thirdMaxCol);
				Matrix indexCol = firstMaxCol.multiplyElementWise(secondMaxCol);
				maxCols[j] = fullMaxCol;
				indexCols[j] = indexCol;
			}

			// From maxCols, extract both delta column and state column
			deltaCol = calculateDeltaColumn(maxCols);
			Matrix stateCol = calculateStateColumn(indexCols);

			normalizationFactor = 1 / deltaCol.sum();
			deltaCol = deltaCol.multiplyElementWise(normalizationFactor);
			normalizationFactors[i] = normalizationFactor;

			delta.setColumn(i, deltaCol);
			states.setColumn(i, stateCol);
		}

		u.printArray(findStateSequence(delta, states));
	}

	public static void main(String[] args) {
		new HMM3();
	}

	private Matrix calculateDeltaColumn(Matrix[] maxCols) {
		Matrix deltaCol = new Matrix(maxCols[0].rows, 1, -1);

		// For every row of a maxcol
		for (int i = 0; i < maxCols[0].rows; i++) {
			double max = -1;
			for (Matrix maxCol : maxCols) {
				max = Math.max(max, maxCol.getElement(i, 0));
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
			int maxState = -1;
			for (int j = 0; j < maxCols.length; j++) {
				if (maxCols[j].getElement(i, 0) > max) {
					max = maxCols[j].getElement(i, 0);
					maxState = j;
				}
			}

			states.setElement(i, 0, maxState);
		}

		return states;
	}

	private int[] findStateSequence(Matrix delta, Matrix states) {
		int[] stateSequence = new int[delta.columns];

		// Do one column at a time
		for (int i = 1; i < delta.columns; i++) {
			double max = -1;
			int maxState = -1;
			for (int j = 0; j < delta.rows; j++) {
				double value = delta.getElement(j, i);
				if (value > max) {
					max = value;
					maxState = (int) states.getElement(j, i);
				}
			}

			stateSequence[i - 1] = maxState;
		}

		Matrix lastColumn = delta.getColumn(delta.columns - 1);
		double max = -1;
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
}
