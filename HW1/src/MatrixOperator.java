public class MatrixOperator {
	public static void main(String[] args) {
		double[][] res = multiply(new double[5][3], new double[3][6]);
		printMatrix(res);
	}

	public static double[][] multiply(double[][] a, double[][] b) {
		double[][] res = new double[a.length][b[0].length];

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
}