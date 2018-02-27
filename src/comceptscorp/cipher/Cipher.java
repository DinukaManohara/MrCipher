package comceptscorp.cipher;

import java.io.*;

/**
 * Created by conceptscorp on 2/27/18.
 */
public class Cipher {
    public static void main(String[] args) {
        String fileName = "src/comceptscorp/cipher/SecretText.txt";

        // This will reference one line at a time
        String line = null;
        String plainText = null;

        try {
            FileReader fileReader = new FileReader(fileName);

            // Wrapping FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                plainText += line;
            }

            // Close the file
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            ex.printStackTrace();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
