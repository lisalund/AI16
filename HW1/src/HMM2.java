public class HMM2 {
	/**
	 * Matrices A (transitions), B (emissions), and pi (initial distributions)
	 */
	private Matrix a;
	private Matrix b;
	private Matrix pi;
	private int[] o;

	HMM2() {
		Utility u = new Utility();

		a = u.parseMatrix();
		b = u.parseMatrix();
		pi = u.parseMatrix();
		o = u.parseEmissions();

		Matrix[] alphas = new Matrix[o.length];

		Matrix alpha1 = pi.multiplyElementWise(b.getColumn(o[0]));
		alphas[0] = alpha1;

		for (int i = 1; i < alphas.length; i++) {
			Matrix alpha = alphas[i - 1].multiply(a).multiplyElementWise(b.getColumn(o[i]));
			alphas[i] = alpha;
		}

		System.out.println(alphas[alphas.length - 1].sum());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new HMM2();
	}

}
