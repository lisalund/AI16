public class Tester {
	public static void main(String[] args) {
		Utility u = new Utility();

		Matrix a = u.parseMatrix();
		Matrix b = u.parseMatrix();
		Matrix pi = u.parseMatrix();

		printMatrices(a, b, pi);

		runTests();
	}

	private static void runTests() {
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

		Matrix c = new Matrix(1, 4, -2);

		Matrix res = a.multiply(b);
		res.printMatrix();

		for (double el : a) {
			System.out.println(el);
		}

		for (double el : b) {
			System.out.println(el);
		}

		for (double el : c) {
			System.out.println(el);
		}
	}

	private static void printMatrices(Matrix a, Matrix b, Matrix pi) {
		System.out.println("A: ");
		System.out.println(a.toString());

		System.out.println("B: ");
		System.out.println(b.toString());

		System.out.println("pi: ");
		System.out.println(pi.toString());
	}
}
