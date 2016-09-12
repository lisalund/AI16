import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HMM1 {
	/**
	 * Matrices A (transitions), B (emissions), and pi (initial distributions)
	 */
	private Matrix A;
	private Matrix B;
	private Matrix pi;

	HMM1() {
		parse();

		System.out.println((pi.multiply(A)).multiply(B));
	}

	public static void main(String[] args) {
		new HMM1();
	}
	
	/**
	 * Parses matrices from input
	 */
	public void parse(){
		try {
			BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));

			String strLine;
			//3 lines to read, one for A, one for B and one for pi
			for(int i = 0; i < 3; i++){
				strLine = buf.readLine();
				String[] tokens = strLine.split(" ");
				
				int rows = Integer.parseInt(tokens[0]);
				int columns = Integer.parseInt(tokens[1]);
				
				if(i == 0){ //A matrix
					this.A = new Matrix(rows, columns);
					A.fillMatrix(tokens);
				} else if(i == 1){ //B matrix
					this.B = new Matrix(rows, columns);
					B.fillMatrix(tokens);
				} else {
					this.pi = new Matrix(rows, columns);
					pi.fillMatrix(tokens);
				}

			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public void printMatrices(){
		System.out.println("A: ");
		System.out.println(A.toString());
		
		System.out.println("B: ");
		System.out.println(B.toString());
		
		System.out.println("pi: ");
		System.out.println(pi.toString());
	}

}
