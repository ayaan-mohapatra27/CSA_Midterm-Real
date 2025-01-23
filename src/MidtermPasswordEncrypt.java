
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.*;


public class MidtermPasswordEncrypt {

    public void writeFile(String pathname, String text)
    {
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
    public String ReadFile(String fileName) throws FileNotFoundException
    {
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

        String code = reader.ReadFile("src/passwordEncrypt.txt");//reads file

        //Ask the user for a password to use for encryption
        System.out.print("Enter passcode: ");
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

        //Convert password to a char array
        String password = passwordStb.toString();
        char[] passwordArray = password.toCharArray();

        //Create cipher using the passcode and reverse alphabet
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

        //Create the final cipher by combining the password and the reversed alphabet
        String cipher1Res = password + new String(newCipher1);

        // Replace spaces and periods in the original text before encryption
        char firstUnused = cipher1Res.charAt(0);
        char secondUnused = cipher1Res.charAt(1);
        String firstChange = originalText.replace(' ', firstUnused);
        String secondChange = firstChange.replace('.', secondUnused);
        String strippedCode = secondChange;

        //Encrypt the stripped code using the cipher
        char[] cipher2Res = new char[strippedCode.length()];
        int cipher2Len = 0;

        // Alphabet array for reference
        char[] cipher2Alph = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        // Encrypt the stripped code
        for (int i = 0; i < strippedCode.length(); i++) {
            int pos;
            char currentChar = strippedCode.charAt(i);
            char capitalized = Character.toUpperCase(currentChar);

            // If the character is an alphabet letter
            boolean isLetter = new String(cipher2Alph).contains(String.valueOf(currentChar).toLowerCase());
            if (isLetter) {
                // Find the position of currentChar in the cipher1Res and map it to cipher2Alph
                pos = cipher1Res.indexOf(currentChar);
                if (Character.isUpperCase(currentChar)) {
                    cipher2Res[cipher2Len] = Character.toUpperCase(cipher2Alph[pos]);
                } else {
                    cipher2Res[cipher2Len] = cipher2Alph[pos];
                }
                cipher2Len++;
            } else {
                // For non-alphabet characters, keep them as they are
                cipher2Res[cipher2Len] = strippedCode.charAt(i);
                cipher2Len++;
            }
        }

        // Convert char array result back to string
        String cipher2StrRes = new String(cipher2Res);

        //Write the encrypted text to a new file
        printer.writeFile("PasswordEncryptOutput.txt", cipher2StrRes); // writes to output file
    }
}