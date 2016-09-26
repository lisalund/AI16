import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Utility {
	private BufferedReader buf;

	Utility() {
		buf = new BufferedReader(new InputStreamReader(System.in));
	}

	Matrix parseMatrix() {
		try {
			String strLine;
			strLine = buf.readLine();
			String[] tokens = strLine.split(" ");

			return new Matrix(tokens);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	int[] parseEmissions() {
		try {
			String strLine;
			strLine = buf.readLine();
			String[] tokens = strLine.split(" ");

			int sequenceLength = Integer.parseInt(tokens[0]);
			int[] emissions = new int[sequenceLength];
			for (int i = 1; i <= sequenceLength; i++) {
				emissions[i - 1] = Integer.parseInt(tokens[i]);
			}

			return emissions;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Print an array as specified output.
	 */
	public void printArray(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(i);
			if (i != array.length - 1) {
				System.out.print(" ");
			}
		}
	}
}
