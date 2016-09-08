
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
	 * Constructor for complete, filled-out matrices
	 */
	public Matrix(int rows, int columns, double[][] matrix){
		this.rows = rows;
		this.columns = columns;
		this.mat = matrix;
	}
	
	/**
	 * Fills the matrix with values from the token array given.
	 * Note that the first two values in tokens are 
	 * row and column data, so these should be skipped.
	 * @param tokens
	 */
	public void fillMatrix(String[] tokens){
		int tokensOffset = 2;
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				mat[i][j] = Double.parseDouble(tokens[tokensOffset]);
				tokensOffset++;
			}
		}
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

}
