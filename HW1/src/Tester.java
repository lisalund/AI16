public class Tester {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HMM1 hmm = new HMM1();
		Utility u = new Utility();

		Matrix a = u.parseMatrix();
		Matrix b = u.parseMatrix();
		Matrix pi = u.parseMatrix();

		hmm.printMatrices(a, b, pi);

		runTests();
	}

	public static void runTests() {
		double[][] aMatrix = {
				{1, 2, 3},
				{4, 5, 6}
		};
		Matrix a = new Matrix(2, 3, aMatrix);

		double[][] bMatrix = {
				{7, 8},
				{9, 10},
				{11, 12}
		};
		Matrix b = new Matrix(3, 2, bMatrix);

		Matrix res = a.multiply(b);
		res.printMatrix();
	}

}
