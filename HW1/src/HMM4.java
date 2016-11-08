/**
 * Baum-Welch algorithm implementation. Implements the pseudo code in the Stamp tutorial.
 */
public class HMM4 {
	private static final int MAX_ITERS = 1000; // Maximum iterations, know when to stop.
	private Matrix a;
	private Matrix b;
	private Matrix pi;
	private int[] o;
	private double logProb;
	private double[][] alphaProb, betaProb, gamma;
	private double[][][] digamma;
	private double[] scale;

	private HMM4() {
		// Parse input
		Utility u = new Utility();

		this.a = u.parseMatrix();
		this.b = u.parseMatrix();
		this.pi = u.parseMatrix();
		this.o = u.parseEmissions();

		int i = 0; // iters
		double oldLogProb;
		while (i < MAX_ITERS) {
			oldLogProb = this.logProb;

			alphaPass();
			betaPass();
			gammas();
			reEstimate();
			log();

			if (this.logProb < oldLogProb && i != 0) break;

			i++;
		}

		System.out.println(a.toString());
		System.out.println(b.toString());
	}

	public static void main(String[] args) {
		new HMM4();
	}

	private void alphaPass() {
		double[][] alpha = new double[this.o.length][a.rows];
		double[] c = new double[this.o.length];

		// Compute alpha_0(i) using pi
		c[0] = 0;
		for (int i = 0; i < a.rows; i++) {
			alpha[0][i] = this.pi.mat[0][i] * this.b.mat[i][this.o[0]];
			c[0] = c[0] + alpha[0][i];
		}

		// Scale alpha_0(i)
		c[0] = 1 / c[0];
		for (int i = 0; i < a.rows; i++) {
			alpha[0][i] = c[0] * alpha[0][i];
		}

		// Compute alpha_t(i)
		for (int t = 1; t < this.o.length; t++) {
			c[t] = 0;
			for (int i = 0; i < this.a.rows; i++) {
				alpha[t][i] = 0;
				for (int j = 0; j < this.a.rows; j++) {
					alpha[t][i] = alpha[t][i] + alpha[t - 1][j] * this.a.mat[j][i];
				}
				alpha[t][i] = alpha[t][i] * this.b.mat[i][this.o[t]];
				c[t] = c[t] + alpha[t][i];
			}

			// Scale alpha_t(i)
			c[t] = 1 / c[t];
			for (int i = 0; i < this.a.rows; i++) {
				alpha[t][i] = c[t] * alpha[t][i];
			}
		}

		this.alphaProb = alpha;
		this.scale = c;
	}

	private void betaPass() {
		double[][] beta = new double[this.o.length][this.a.rows];

		// Set beta_T-1(i) to 1 scaled by c_T-1
		for (int i = 0; i < this.a.rows; i++) {
			beta[this.o.length - 1][i] = this.scale[this.o.length - 1];
		}

		// Let the consecutive beta pass begin
		for (int t = this.o.length - 2; t >= 0; t--) {
			for (int i = 0; i < this.a.rows; i++) {
				beta[t][i] = 0;

				for (int j = 0; j < this.a.rows; j++) {
					beta[t][i] = beta[t][i] + this.a.mat[i][j]
							* this.b.mat[j][this.o[t + 1]]
							* beta[t + 1][j];
				}

				//scale beta too
				beta[t][i] = this.scale[t] * beta[t][i];
			}
		}

		this.betaProb = beta;
	}

	private void gammas() {
		double denom;
		double[][] gamma = new double[this.o.length][this.a.rows];
		double[][][] digamma = new double[this.o.length][this.a.rows][this.a.rows];

		for (int t = 0; t <= this.o.length - 2; t++) {
			denom = 0;
			for (int i = 0; i < this.a.rows; i++) {
				for (int j = 0; j < this.a.rows; j++) {
					denom = denom + (alphaProb[t][i]
							* this.a.mat[i][j]
							* this.b.mat[j][this.o[t + 1]]
							* this.betaProb[t + 1][j]);
				}
			}

			for (int i = 0; i < this.a.rows; i++) {
				gamma[t][i] = 0;

				for (int j = 0; j < a.rows; j++) {
					digamma[t][i][j] = (this.alphaProb[t][i]
							* this.a.mat[i][j]
							* this.b.mat[j][this.o[t + 1]]
							* this.betaProb[t + 1][j]) / denom;
					gamma[t][i] = gamma[t][i] + digamma[t][i][j];
				}
			}
		}

		// Handle last gamma
		denom = 0;
		for (int i = 0; i < this.a.rows; i++) {
			denom = denom + this.alphaProb[this.o.length - 1][i];
		}

		for (int i = 0; i < this.a.rows; i++) {
			gamma[this.o.length - 1][i] = this.alphaProb[this.o.length - 1][i] / denom;
		}

		this.gamma = gamma;
		this.digamma = digamma;
	}

	private void reEstimate() {
		double numer, denom;
		double[][] a = new double[this.a.rows][this.a.columns];
		double[][] b = new double[this.b.rows][this.b.columns];
		double[][] pi = new double[this.pi.rows][this.pi.columns];

		// Re-estimate pi
		System.arraycopy(this.gamma[0], 0, pi[0], 0, this.a.rows);

		// Re-estimate a
		for (int i = 0; i < this.a.rows; i++) {
			for (int j = 0; j < this.a.columns; j++) {
				numer = 0;
				denom = 0;

				for (int t = 0; t < this.o.length - 1; t++) {
					numer = numer + this.digamma[t][i][j];
					denom = denom + this.gamma[t][i];
				}

				a[i][j] = numer / denom;
			}
		}

		// Re-estimate b
		for (int i = 0; i < this.b.rows; i++) {
			for (int j = 0; j < this.b.columns; j++) {
				numer = 0;
				denom = 0;

				for (int t = 0; t < this.o.length - 1; t++) {
					if (this.o[t] == j) {
						numer = numer + this.gamma[t][i];
					}
					denom = denom + this.gamma[t][i];
				}

				b[i][j] = numer / denom;
			}
		}

		this.pi = new Matrix(pi);
		this.a = new Matrix(a);
		this.b = new Matrix(b);

	}

	private void log() {
		double logProb = 0;
		for (int t = 0; t < this.o.length; t++) {
			logProb = logProb + Math.log(this.scale[t]);
		}
		this.logProb = -1 * logProb;
	}

}
