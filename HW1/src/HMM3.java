public class HMM3 {
	HMM3() {
		Utility u = new Utility();

		// Parse input
		Matrix a = u.parseMatrix();
		Matrix b = u.parseMatrix();
		Matrix pi = u.parseMatrix();
		int[] o = u.parseEmissions();

		Matrix delta = new Matrix(a.rows, o.length);

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

		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new HMM3();
	}

	private Matrix calculateMaxes(Matrix[] maxCols) {
		// For every row of a maxcol
		for (int i = 0; i < maxCols[0].rows; i++) {
			double max = -1;
			for (int j = 0; j < maxCols.length; j++) {
				max = Math.max(max, maxCols[j].getElement(j, 0));
			}

			for (Matrix maxCol : maxCols) {
				if (max == maxCol.getElement(i, 0)) {


				}
			}

		}

		return null;
	}
}
