import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utility {
	BufferedReader buf;

	Utility() {
		buf = new BufferedReader(new InputStreamReader(System.in));
	}

	public Matrix parseMatrix() {
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

	public int[] parseEmissions() {
		try {
			String strLine;
			strLine = buf.readLine();
			String[] tokens = strLine.split(" ");

			int[] emissions = new int[tokens.length];
			for (int i = 0; i < tokens.length; i++) {
				emissions[i] = Integer.parseInt(tokens[i]);
			}

			return emissions;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}
}
