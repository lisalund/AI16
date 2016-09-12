/**
 * Class for the matrices used in the HMM lab
 * @author lisa
 *
 */
public class Matrix {
	public int rows;
	public int columns;
	public double[][] mat;

	/**
	 * Constructor for unfilled matrices, where we do not know the values yet
	 */
	public Matrix(int rows, int columns){
		this.rows = rows;
		this.columns = columns;
		this.mat = new double[rows][columns];
	}

	/**
	 * Fills the matrix with values from the token array given.
	 * Note that the first two values in tokens are
	 * row and column data, so these should be skipped.
	 * @param tokens
	 */
	public Matrix(String[] tokens) {
		this.rows = Integer.parseInt(tokens[0]);
		this.columns = Integer.parseInt(tokens[1]);
		this.mat = new double[rows][columns];

		int tokensOffset = 2;
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				mat[i][j] = Double.parseDouble(tokens[tokensOffset]);
				tokensOffset++;
			}
		}
	}

	/**
	 * Constructor for complete, filled-out matrices
	 */
	public Matrix(int rows, int columns, double[][] matrix) {
		this.rows = rows;
		this.columns = columns;
		this.mat = matrix;
	}

	/**
	 * Returns the two-dimentional matrix
	 */
	public double[][] getMatrix(){
		return mat;
	}
	
	/**
	 * 
	 * @return the transposed matrix as a Matrix object
	 */
	public Matrix transpose(){
		double[][] trans = new double[columns][rows];
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				trans[j][i] = mat[i][j];
			}
		}

		return new Matrix(columns, rows, trans);
	}
	
	/**
	 * 
	 * @param column the index of the column wanted (0 indexed)
	 * @return a column vector from the matrix
	 */
	public Matrix getColumn(int column){
		Matrix col = new Matrix(rows, 1, new double[rows][1]);
		
		for(int i = 0; i < rows; i++){
			col.mat[i][1] = mat[i][column];
		}
		return col;
	}
	
	/**
	 * @return a String representing the matrix, as defined by the HW instructions:
	 * rows columns followed by data contained, row for row.
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(rows);
		sb.append(" " + columns);
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				sb.append(" " + mat[i][j]);
			}	
		}
		
		return sb.toString();
	}

	public Matrix multiply(Matrix rightMatrix) {
		double[][] a = mat;
		double[][] b = rightMatrix.mat;

		double[][] res = new double[a.length][b[0].length];

		int rows = res.length;
		int cols = res[0].length;
		int innerLength = a[0].length; // == b.length, m in formula

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				for (int k = 0; k < innerLength; k++) {
					res[i][j] += a[i][k] * b[k][j];
				}
			}
		}


		return new Matrix(rows, cols, res);
	}

	public void printMatrix() {
		double[][] matrix = mat;

		for (double[] row : matrix) {
			for (double elem : row) {
				System.out.print(elem + " ");
			}
			System.out.println();
		}
	}
}
