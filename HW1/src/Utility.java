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
}
