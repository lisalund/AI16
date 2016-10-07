import java.util.Arrays;
import java.util.Iterator;

/**
 * Class for the matrices used in the HMM lab
 * @author lisa
 *
 */
class Matrix implements Iterable<Double> {
	int rows;
	int columns;
	private double[][] mat;

	/**
	 * Constructor for unfilled matrices, where we do not know the values yet
	 */
	Matrix(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		this.mat = new double[rows][columns];
	}

	/**
	 * Fills the matrix with values from the token array given.
	 * Note that the first two values in tokens are
	 * row and column data, so these should be skipped.
	 * @param tokens Supplied input
	 */
	Matrix(String[] tokens) {
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
	Matrix(int rows, int columns, double[][] matrix) {
		this.rows = rows;
		this.columns = columns;
		this.mat = matrix;
	}
	
	/**
	 * Constructor for a matrix with all non-zero values
	 */
	Matrix(int rows, int columns, double fillWith) {
		this.rows = rows;
		this.columns = columns;
		this.mat = new double[rows][columns];
		for(double[] row : mat){
			Arrays.fill(row, fillWith);
		}
	}

	/**
	 * Returns the two-dimentional matrix
	 */
	private double[][] getMatrix() {
		return mat;
	}
	
	/**
	 * 
	 * @return the transposed matrix as a Matrix object
	 */
	Matrix transpose() {
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
	Matrix getColumn(int column) {
		Matrix col = new Matrix(rows, 1, new double[rows][1]);
		
		for(int i = 0; i < rows; i++){
			col.mat[i][0] = mat[i][column];
		}
		return col;
	}

	/**
	 * Get a (zero-indexed) row.
	 */
	Matrix getRow(int rowIndex) {
		Matrix row = new Matrix(1, columns);

		System.arraycopy(mat[rowIndex], 0, row.mat[0], 0, columns);
		return row;
	}

	/**
	 * Overwrites a column in this with another column of the same height.
	 * @param column The column to write to
	 * @param matrix The column to write
	 */
	void setColumn(int column, Matrix matrix) {
		if ((this.rows != matrix.rows || this.columns != matrix.columns) && !(matrix.rows == 1 || matrix.columns == 1)) {
			throw new IllegalArgumentException("Input matrix must be a vector with the same length as a matrix column.");
		}

		for (int i = 0; i < rows; i++) {
			if (matrix.columns == 1) {
				mat[i][column] = matrix.mat[i][0];
			} else {
				mat[i][column] = matrix.mat[0][i];
			}
		}
	}

	void setElement(int row, int column, double value) {
		mat[row][column] = value;
	}

	/**
	 * Returns a value in the matrix (zero-indexed).
	 *
	 * @param row The row the element is in
	 * @param column The column the element is in
	 * @return The element
	 */
	double getElement(int row, int column) {
		return mat[row][column];
	}

	/**
	 * @return a String representing the matrix, as defined by the HW instructions:
	 * rows columns followed by data contained, row for row.
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(rows);
		sb.append(" ").append(columns);
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				sb.append(" ").append(mat[i][j]);
			}	
		}
		
		return sb.toString();
	}

	Matrix multiply(Matrix rightMatrix) {
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

	Matrix logElementWise() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				mat[i][j] = Math.log(mat[i][j]);
			}
		}


		return this;
	}

	Matrix sumElementWise(Matrix rightMatrix) {
		double[][] a = mat;
		double[][] b = rightMatrix.getMatrix();

		double[][] res = new double[rows][columns];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (a.length == b.length && a[0].length == b[0].length) {
					res[i][j] = a[i][j] + b[i][j];
				} else if (a.length == b[0].length && b.length == a[0].length) {
					res[i][j] = a[i][j] + b[j][i];
				} else {
					throw new IllegalArgumentException("Matrices must be of equal size (possibly when transposed).");
				}
			}
		}

		return new Matrix(rows, columns, res);
	}

	Matrix multiplyElementWise(double term) {
		return multiplyElementWise(new Matrix(rows, columns, term));
	}

	Matrix multiplyElementWise(Matrix rightMatrix) {
		double[][] a = mat;
		double[][] b = rightMatrix.getMatrix();

		double[][] res = new double[rows][columns];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (a.length == b.length && a[0].length == b[0].length) {
					res[i][j] = a[i][j] * b[i][j];
				} else if (a.length == b[0].length && b.length == a[0].length) {
					res[i][j] = a[i][j] * b[j][i];
				} else {
					throw new IllegalArgumentException("Matrices must be of equal size (possibly when transposed).");
				}
			}
		}

		return new Matrix(rows, columns, res);
	}

	void printMatrix() {
		double[][] matrix = mat;

		for (double[] row : matrix) {
			for (double elem : row) {
				System.out.print(elem + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Returns the sum of all elements.
	 *
	 * @return The sum of all elements
	 */
	double sum() {
		double sum = 0;
		for (double[] row : mat) {
			for (double element : row) {
				sum += element;
			}
		}

		return sum;
	}

	@Override
	public Iterator<Double> iterator() {
		return new MatrixIterator();
	}

	class MatrixIterator implements Iterator {
		private int index = 0;

		@Override
		public boolean hasNext() {
			return index < rows * columns;
		}

		@Override
		public Object next() {
			double elem = getElement(index / columns, index % columns);
			index++;
			return elem;
		}
	}
}
