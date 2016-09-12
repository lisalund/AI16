public class HMM1 {
	HMM1() {
		Utility u = new Utility();

		Matrix A = u.parseMatrix();
		Matrix B = u.parseMatrix();
		Matrix pi = u.parseMatrix();

		System.out.println((pi.multiply(A)).multiply(B));
	}

	public static void main(String[] args) {
		new HMM1();
	}

	public void printMatrices(Matrix A, Matrix B, Matrix pi) {
		System.out.println("A: ");
		System.out.println(A.toString());
		
		System.out.println("B: ");
		System.out.println(B.toString());
		
		System.out.println("pi: ");
		System.out.println(pi.toString());
	}
}
