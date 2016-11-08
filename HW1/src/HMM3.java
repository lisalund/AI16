/**
 * Vi provar igen.
 *
 */
public class HMM3 {
	int[] o;
	int[] path;
	double[][] map;
	Utility u = new Utility();
	private Matrix a;
	private Matrix b;
	private Matrix pi;

	public HMM3() {

		// Parse input
		this.a = u.parseMatrix();
		this.b = u.parseMatrix();
		this.pi = u.parseMatrix();
		this.o = u.parseEmissions();
		
		double[] delta = pi.transpose().dotMultiply(b.getColumn(o[0])).toArray();
		
		this.path = new int[o.length];
		this.map = new double[o.length][delta.length];
		
		double [] forw = forwards(delta);
		backtrack(forw);
		
		System.out.println(toString());
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new HMM3();

	}
	
	public double[] forwards(double[] initDelta){
		double tmpNext; //temp. probability of next state in delta sequence
		double tmpMax; // temp. probability of most probable next state in delta seq.

		double[] delta = initDelta;
		double[] nextDelta;

		for(int i = 1; i < this.o.length; i++){
			nextDelta = new double[delta.length];

			for(int j = 0; j < delta.length; j++){
				tmpMax = 0; //reset the temporary ArgMax probability

				for(int k = 0; k < delta.length; k++){

					//calculate probability of next node from current
					tmpNext = delta[k] * this.a.mat[k][j] * this.b.mat[j][this.o[i]];

					if(tmpNext > tmpMax){
						tmpMax = tmpNext;
						this.map[i][j] = k; //store current node
					}
				}
				nextDelta[j] = tmpMax;
			}

			delta = nextDelta;
			//System.out.println("NextDelta: " + arrayToString(nextDelta));
		}
		//System.out.println("delta: " + arrayToString(delta));
		return delta;
	}
	
	public void backtrack(double[] delta){
		double tmp = 0;

		for(int i = this.o.length - 1; i > 0; i--){

			//check if first step in backtracking
			if(i == this.o.length-1){

				//check most probable final state
				for(int j = 0; j < delta.length; j++){
					if(tmp < delta[j]){
						tmp = delta[j];
						this.path[i] = j;
						this.path[i-1] = (int) this.map[i][j];
					}
				}
			}
			else{
				//backtrack
				this.path[i-1] = (int) this.map[i][this.path[i]];

			}
		}
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < this.path.length; i++){
			sb.append(this.path[i] + " ");
		}

		return sb.toString();
	}
	
	/**
	 * used for test prints
	 * @param a
	 * @return
	 */
    public String arrayToString(double[] a){
    	StringBuilder sb = new StringBuilder();
    	for(double element : a){
    		sb.append(element + " ");
    	}
    	return sb.toString();
    }

}
