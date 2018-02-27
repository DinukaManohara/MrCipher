package comceptscorp.cipher;

import java.io.*;
import java.util.List;
import java.util.Arrays;

/**
 * Created by conceptscorp on 2/27/18.
 */
public class Decrypter {
    private char[] espeInstance = new char[13];
    private char[] alphInstance = new char[13];
    private int[] orderInstance = new int[13];
    private String plainText = "";
    private String encryptedText = "";

    public void readFile(String fileName){
        String line = null;

        try {
            FileReader fileReader = new FileReader(fileName);

            // Wrapping FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                encryptedText += line;
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

            bufferedWriter.write(plainText);

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
    }

    private int findIndexAlph(char chr){
        int index = -1;
        for(int i = 0; i < 13; i++){
            if(chr == alphInstance[i]){
                index = i;
                break;
            }
        }
        return index;
    }

    private int findIndexOrder(int val){
        int index = -1;
        for(int i = 0; i < 13; i++){
            if(val == orderInstance[i]){
                index = i;
                break;
            }
        }
        return index;
    }

    public void decrypt(String key){
        char[] textArr = encryptedText.toCharArray();
        char[] keyArr = key.toCharArray();

        int a = Integer.valueOf(String.valueOf(keyArr[0]) + String.valueOf(keyArr[1]));
        int b = Integer.valueOf(String.valueOf(keyArr[2]) + String.valueOf(keyArr[3]));
        int c = Integer.valueOf(String.valueOf(keyArr[4]) + String.valueOf(keyArr[5]));

        this.doMapping(a,b,c);

        String[] region1 = {"a","c","e","g","i","k","m","o","q","s","u","w","y"};
        String[] region2 = {"b","d","f","h","j","l","n","p","r","t","v","x","z"};

        for(int i = 0; i < textArr.length; i+=2){
            int reqIndex = findIndexOrder(findIndexAlph(textArr[i]));

            if(reqIndex == -1){
                System.out.println("Invalid cipher text !");
                plainText = "Invalid cipher text !";
                break;
            }

            if(textArr[i+1] == ':'){
                plainText += region1[reqIndex];
            }else{
                plainText += region2[reqIndex];
            }
        }
    }
}
