public class MatrixOperator {
	public static void main(String[] args) {
		runTests();
	}

	public static double[][] multiply(double[][] a, double[][] b) {
		double[][] res = new double[a.length][b[0].length];

		int rows = res.length;
		int cols = res[0].length;
		int innerLength = a[0].length; // == b.length, m in formula

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				for (int k = 0; k < innerLength; k++) {
					res[i][j] += a[i][k] * b[k][j];
				}
			}
		}

		return res;
	}

	public static void printMatrix(double[][] matrix) {
		for (double[] row : matrix) {
			for (double elem : row) {
				System.out.print(elem + " ");
			}
			System.out.println();
		}
	}

	public static void runTests() {
		double[][] a = {
				{1, 2, 3},
				{4, 5, 6}
		};

		double[][] b = {
				{7, 8},
				{9, 10},
				{11, 12}
		};

		double[][] res = multiply(a, b);
		printMatrix(res);
	}
}