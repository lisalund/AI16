public class HMM1 {
	private HMM1() {
		Utility u = new Utility();

		Matrix A = u.parseMatrix();
		Matrix B = u.parseMatrix();
		Matrix pi = u.parseMatrix();

		System.out.println((pi.multiply(A)).multiply(B));
	}

	public static void main(String[] args) {
		new HMM1();
	}
}
