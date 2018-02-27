package comceptscorp.cipher;

import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by conceptscorp on 2/27/18.
 */
public class Cipher {
    private char[] espeInstance = new char[13];
    private char[] alphInstance = new char[13];
    private int[] orderInstance = new int[13];
    private String plainText = "";
    private String encryptedText = "";
    private String key = "";

    public static void main(String[] args) {
        String secretFile = "src/comceptscorp/cipher/SecretText.txt";
        String encryptFile = "src/comceptscorp/cipher/EncryptedText.txt";
        String decryptFile = "src/comceptscorp/cipher/DecryptedText.txt";

        Cipher myCipher = new Cipher();
        Decrypter myDecrypter = new Decrypter();

        myCipher.readFile(secretFile);
        myCipher.encrypt();
        myCipher.writeToFile(encryptFile);

        String key = myCipher.getKey();
        myDecrypter.readFile(encryptFile);
        myDecrypter.decrypt(key);
        myDecrypter.writeToFile(decryptFile);
    }

    public void readFile(String fileName){
        String line = null;

        try {
            FileReader fileReader = new FileReader(fileName);

            // Wrapping FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
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

    public void writeToFile(String fileName){
        try {
            // Assume default encoding.
            FileWriter fileWriter = new FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(encryptedText);

            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getPlainText(){
        return this.plainText;
    }

    public String getEncryptedText(){
        return this.encryptedText;
    }

    private void doMapping(int a, int b, int c){
        char[] espe = {'!','@','#','$','%','^','&','*','?','<','>',';','~'};
        char[] alph = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        int[] order = {0,1,2,3,4,5,6,7,8,9,10,11,12};

        for(int j = 0; j < 13; j++){
            if(j < b) {
                espeInstance[13-b+j] = espe[j];
            }else{
                espeInstance[j-b] = espe[j];
            }
        }

        //System.out.println(espeInstance);

        char[] tempAlphIns = new char[26];

        for(int i = 0; i < 26; i++){
            if(i < a){
                tempAlphIns[26-a+i] = alph[i];
            }else{
                tempAlphIns[i-a] = alph[i];
            }
        }

        for(int i = 0; i < 13; i++){
            alphInstance[i] = tempAlphIns[i*2];
        }

        //System.out.println(alphInstance);

        for(int i = 0; i < 13; i++){
            if(i < c){
                orderInstance[13-c+i] = order[i];
            }else{
                orderInstance[i-c] = order[i];
            }
        }

        /*for(int i = 0; i < 13; i++){
            System.out.print(orderInstance[i]);
            System.out.print(" ");
        }*/
        //System.out.println();
    }

    private void generateKey(int a, int b, int c){
        if(a < 10){
            key = "0" + String.valueOf(a);
        }else{
            key = String.valueOf(a);
        }

        if(b < 10){
            key += "0" + String.valueOf(b);
        }else{
            key += String.valueOf(b);
        }

        if(c < 10){
            key += "0" + String.valueOf(c);
        }else{
            key += String.valueOf(c);
        }
        //System.out.println("KEY : "+key);
    }

    public String getKey(){
        return this.key;
    }

    public void encrypt(){
        String text = plainText.toUpperCase();
        char[] textArr = text.toCharArray();

        int espeRand = ThreadLocalRandom.current().nextInt(0, 13);
        int alphRand = ThreadLocalRandom.current().nextInt(0, 26);
        int orderRand = ThreadLocalRandom.current().nextInt(0, 13);

        this.generateKey(alphRand,espeRand,orderRand);

        this.doMapping(alphRand,espeRand,orderRand);

        for (char chr : textArr) {
            switch(chr){
                case ' ':
                    encryptedText += '+';
                    break;
                case 'A':
                    encryptedText += String.valueOf(alphInstance[orderInstance[0]]) + ":";
                    break;
                case 'B':
                    encryptedText += String.valueOf(alphInstance[orderInstance[0]]) + String.valueOf(espeInstance[orderInstance[0]]);
                    break;
                case 'C':
                    encryptedText += String.valueOf(alphInstance[orderInstance[1]]) + ":";
                    break;
                case 'D':
                    encryptedText += String.valueOf(alphInstance[orderInstance[1]]) + String.valueOf(espeInstance[orderInstance[1]]);
                    break;
                case 'E':
                    encryptedText += String.valueOf(alphInstance[orderInstance[2]]) + ":";
                    break;
                case 'F':
                    encryptedText += String.valueOf(alphInstance[orderInstance[2]]) + String.valueOf(espeInstance[orderInstance[2]]);
                    break;
                case 'G':
                    encryptedText += String.valueOf(alphInstance[orderInstance[3]]) + ":";
                    break;
                case 'H':
                    encryptedText += String.valueOf(alphInstance[orderInstance[3]]) + String.valueOf(espeInstance[orderInstance[3]]);
                    break;
                case 'I':
                    encryptedText += String.valueOf(alphInstance[orderInstance[4]]) + ":";
                    break;
                case 'J':
                    encryptedText += String.valueOf(alphInstance[orderInstance[4]]) + String.valueOf(espeInstance[orderInstance[4]]);
                    break;
                case 'K':
                    encryptedText += String.valueOf(alphInstance[orderInstance[5]]) + ":";
                    break;
                case 'L':
                    encryptedText += String.valueOf(alphInstance[orderInstance[5]]) + String.valueOf(espeInstance[orderInstance[5]]);
                    break;
                case 'M':
                    encryptedText += String.valueOf(alphInstance[orderInstance[6]]) + ":";
                    break;
                case 'N':
                    encryptedText += String.valueOf(alphInstance[orderInstance[6]]) + String.valueOf(espeInstance[orderInstance[6]]);
                    break;
                case 'O':
                    encryptedText += String.valueOf(alphInstance[orderInstance[7]]) + ":";
                    break;
                case 'P':
                    encryptedText += String.valueOf(alphInstance[orderInstance[7]]) + String.valueOf(espeInstance[orderInstance[7]]);
                    break;
                case 'Q':
                    encryptedText += String.valueOf(alphInstance[orderInstance[8]]) + ":";
                    break;
                case 'R':
                    encryptedText += String.valueOf(alphInstance[orderInstance[8]]) + String.valueOf(espeInstance[orderInstance[8]]);
                    break;
                case 'S':
                    encryptedText += String.valueOf(alphInstance[orderInstance[9]]) + ":";
                    break;
                case 'T':
                    encryptedText += String.valueOf(alphInstance[orderInstance[9]]) + String.valueOf(espeInstance[orderInstance[9]]);
                    break;
                case 'U':
                    encryptedText += String.valueOf(alphInstance[orderInstance[10]]) + ":";
                    break;
                case 'V':
                    encryptedText += String.valueOf(alphInstance[orderInstance[10]]) + String.valueOf(espeInstance[orderInstance[10]]);
                    break;
                case 'W':
                    encryptedText += String.valueOf(alphInstance[orderInstance[11]]) + ":";
                    break;
                case 'X':
                    encryptedText += String.valueOf(alphInstance[orderInstance[11]]) + String.valueOf(espeInstance[orderInstance[11]]);
                    break;
                case 'Y':
                    encryptedText += String.valueOf(alphInstance[orderInstance[12]]) + ":";
                    break;
                case 'Z':
                    encryptedText += String.valueOf(alphInstance[orderInstance[12]]) + String.valueOf(espeInstance[orderInstance[12]]);
                    break;
                default:
                    encryptedText = "Invalid secret text ! (only accepts alphabetical characters)";
                    System.out.println(encryptedText);
                    System.exit(1);
                    break;
            }
        }
        //System.out.println(encryptedText);
    }
}
