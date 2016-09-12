public class HMM2 {
	private HMM2() {
		Utility u = new Utility();

		Matrix a = u.parseMatrix();
		Matrix b = u.parseMatrix();
		Matrix pi = u.parseMatrix();
		int[] o = u.parseEmissions();

		Matrix alpha = new Matrix(a.rows, o.length);
		Matrix alphaCol = pi.multiplyElementWise(b.getColumn(o[0]));
		alpha.setColumn(0, alphaCol);

		for (int i = 1; i < alpha.columns; i++) {
			alphaCol = alpha.getColumn(i - 1).transpose() // alpha col transposed to a row before multiplying
					.multiply(a).multiplyElementWise(b.getColumn(o[i]));
			alpha.setColumn(i, alphaCol);
		}

		System.out.println(alpha.getColumn(alpha.columns - 1).sum());
	}
	
	public static void main(String[] args) {
		new HMM2();
	}
}
