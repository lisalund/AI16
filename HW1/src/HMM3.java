public class HMM3 {
	private int[] o;
	private int[] path;
	private double[][] map;
	private Matrix a;
	private Matrix b;

	private HMM3() {
		// Parse input
		Utility u = new Utility();

		this.a = u.parseMatrix();
		this.b = u.parseMatrix();
		Matrix pi = u.parseMatrix();
		this.o = u.parseEmissions();

		double[] delta = pi.transpose().dotMultiply(b.getColumn(o[0])).toArray();

		this.path = new int[o.length];
		this.map = new double[o.length][delta.length];

		double[] forw = forwards(delta);
		backtrack(forw);

		System.out.println(toString());
	}

	public static void main(String[] args) {
		new HMM3();
	}

	private double[] forwards(double[] initDelta) {
		double tmpNext; //temp. probability of next state in delta sequence
		double tmpMax; // temp. probability of most probable next state in delta seq.

		double[] delta = initDelta;
		double[] nextDelta;

		for (int i = 1; i < this.o.length; i++) {
			nextDelta = new double[delta.length];

			for (int j = 0; j < delta.length; j++) {
				tmpMax = 0; //reset the temporary ArgMax probability

				// max_(j ∈ [1,..N])
				for (int k = 0; k < delta.length; k++) {
					//calculate probability of next node from current
					tmpNext = this.a.mat[k][j] * delta[k] * this.b.mat[j][this.o[i]]; // a_j,i * δ_t−1(j) * b_i(o_t)

					if (tmpNext > tmpMax) {
						tmpMax = tmpNext;
						this.map[i][j] = k; //store current node
					}
				}

				nextDelta[j] = tmpMax;
			}

			delta = nextDelta;
		}

		return delta;
	}

	private void backtrack(double[] delta) {
		double tmp = 0;

		for (int i = this.o.length - 1; i > 0; i--) {
			// Check if first step in backtracking
			if (i == this.o.length - 1) {
				// Check most probable final state
				for (int j = 0; j < delta.length; j++) {
					if (tmp < delta[j]) {
						tmp = delta[j];
						this.path[i] = j;
						this.path[i - 1] = (int) this.map[i][j];
					}
				}
			} else {
				// Backtrack
				this.path[i - 1] = (int) this.map[i][this.path[i]];
			}
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int aPath : this.path) {
			sb.append(aPath).append(" ");
		}

		return sb.toString();
	}
}
