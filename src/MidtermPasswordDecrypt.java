
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.*;


public class MidtermPasswordDecrypt {

    public void writeFile(String pathname, String text){
        Writer writer = null;
        try
        {
            writer = new FileWriter(pathname, false);
        }
        catch(IOException ex)
        {
            System.out.println("Error: could not create/open" + pathname);
            System.exit(1);
        }

        PrintWriter out = new PrintWriter(writer);
        out.println(text);
        out.close();
    }
    public String ReadFile(String fileName) throws FileNotFoundException{
        File text = new File(fileName);
        Scanner scnr = new Scanner(text);
        String out = "";
        while(scnr.hasNextLine())
        {
            String line = scnr.nextLine();
            out = out + line;
        }
        return out;
    }

    public static void main(String[] args) throws FileNotFoundException{
        TextFiles reader = new TextFiles(); //makes a bot that will end up reading file
        TextFiles printer = new TextFiles();//makes a bot that will end up printing file
        Scanner input = new Scanner(System.in);

        String result="";
        String code = reader.ReadFile("src/passwordDecrypt.txt");//reads file

         //Strip the first two characters (encrypted space and period)
        char firstEncrypted = encryptedText.charAt(0);
        char secondEncrypted = encryptedText.charAt(1);

        //Replace the encrypted space and period back to original space and period
        String decryptedText = encryptedText.substring(2);
        decryptedText = decryptedText.replace(firstEncrypted, ' ');
        decryptedText = decryptedText.replace(secondEncrypted, '.');

        //Ask the user for the password to decrypt (this should be the same password used during encryption)
        System.out.print("Enter passcode to decrypt: ");
        String passwordI = input.nextLine();
        StringBuilder passwordStb = new StringBuilder();
        passwordStb.append(passwordI);

        //Remove duplicate characters from the password
        for (int i = 0; i < passwordI.length() - 1; i++) {
            for (int k = i + 1; k < passwordI.length(); k++) {
                if (passwordI.charAt(i) == passwordI.charAt(k)) {
                    passwordStb.deleteCharAt(k);
                }
            }
        }

        // Convert password to a char array
        String password = passwordStb.toString();
        char[] passwordArray = password.toCharArray();

        //Create reverse cipher based on the password
        char[] cipher1 = new char[]{'z', 'y', 'x', 'w', 'v', 'u', 't', 's', 'r', 'q', 'p', 'o', 'n', 'm', 'l', 'k', 'j', 'i', 'h', 'g', 'f', 'e', 'd', 'c', 'b', 'a'};
        int count = 0;

        //Remove non-unique characters from the reverse alphabet (cipher1)
        for (int i = 0; i < passwordArray.length; i++) {
            for (int k = 0; k < cipher1.length; k++) {
                if (passwordArray[i] == cipher1[k]) {
                    cipher1[i] = '!'; // mark duplicates
                    count++;
                }
            }
        }

        //Create the new cipher array after removing duplicates
        char[] newCipher1 = new char[cipher1.length - count];
        int newCipher1Len = 0;
        for (int i = 0; i < cipher1.length; i++) {
            if (cipher1[i] == '!') {
                continue; // skip duplicates
            }
            newCipher1[newCipher1Len] = cipher1[i];
            newCipher1Len++;
        }

        //Create the final reverse cipher by combining the password and the reversed alphabet
        String cipher1Res = password + new String(newCipher1);

        //Decrypt the text by reversing the encryption process
        char[] decryptedRes = new char[decryptedText.length()];
        int decryptedLen = 0;

        //Alphabet array for reference
        char[] cipher2Alph = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        //Decrypt the text using the reverse cipher
        for (int i = 0; i < decryptedText.length(); i++) {
            int pos;
            char currentChar = decryptedText.charAt(i);
            char capitalized = Character.toUpperCase(currentChar);

            //If the character is a letter
            boolean isLetter = new String(cipher2Alph).contains(String.valueOf(currentChar).toLowerCase());
            if (isLetter) {
                //Find the position of currentChar in the cipher1Res and map it to cipher2Alph
                pos = cipher1Res.indexOf(currentChar);
                if (Character.isUpperCase(currentChar)) {
                    decryptedRes[decryptedLen] = Character.toUpperCase(cipher2Alph[pos]);
                } else {
                    decryptedRes[decryptedLen] = cipher2Alph[pos];
                }
                decryptedLen++;
            } else {
                //For non-alphabet characters, keep them as they are
                decryptedRes[decryptedLen] = decryptedText.charAt(i);
                decryptedLen++;
            }
        }

        // Convert char array result back to string
        String decryptedStrRes = new String(decryptedRes);

        // Write the decrypted text to a new file
        printer.writeFile("PasswordDecryptOutput.txt", decryptedStrRes); // writes to output file
    }
}