
/**
 * Class for the matrices used in the HMM lab
 * @author lisa
 *
 */
public class Matrix {
	
	public int rows;
	public int columns;
	public double[][] mat;
	
	
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
