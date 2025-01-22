
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.*;


public class MidtermPasswordEncrypt {

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

        //Finding the first and second characters
        char firstUnused = code.charAt(0);
        char secondUnused = code.charAt(1);

        //Replacing with spaces and periods and stripping off frst 2 characters
        String firstChange = code.replace(firstUnused, ' ');
        String secondChange = firstChange.replace(secondUnused, '.');
        String strippedCode = secondChange.substring(2);

        //Asking user for a password
        String passwordI = input.nextLine();
        StringBuilder passwordStb = new StringBuilder(); //Used for .deleteCharAt() method!
        passwordStb.append(passwordI); 

        //Deletes nonunique characters in password
        for(int i=0; i<passwordI.length()-1; i++){
            for(int k = i+1; k<passwordI.length(); k++){
                if(passwordI.charAt(i)==passwordI.charAt(k)){
                    passwordStb.deleteCharAt(k);
                }
            }
        }

        //Turns password into an array
        String password = passwordStb.toString(); 
        char[] passwordArray = password.toCharArray();

        //Creating reverse alphabet and keeping a count for the number of times repetitions occur
        char[] cipher1 = new char[]{'z','y','x','w','v','u','t','s','r','q','p','o','n','m','l','k','j','i','h','g','f','e','d','c','b','a'};
        int count = 0;

        //This loop is used to remove the non-unique characters from the latter half of the cipher aka the reverse alphabet
        for(int i = 0; i<passwordArray.length; i++){
            for(int k=0;k<cipher1.length;k++){
                if(passwordArray[i]==cipher1[k]){
                    cipher1[i]='!';
                    count++;
                }
            }
        }

        //Creating the reversed alphabet portion of the cipher
        char[] newCipher1 = new char[cipher1.length-count];
        int newCipher1Len = 0;
        for(int i=0; i<cipher1.length; i++){
            //Ensures that whenever an exclamation point is seen, there will not be an added character
            if(cipher1[i]=='!'){
                continue;
            } 
            newCipher1[newCipher1Len] = cipher1[i];
            newCipher1Len++;
        }

        //Creates the cipher using the password and reversed alphabet
        String cipher1Res =password+newCipher1.toString();
        char[] cipher2Res= new char[strippedCode.length()];
        int cipher2Len=0;

        //Create alphabet array
        char[] cipher2Alph = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        
        //Traverse through each char of strippedCode
        for(int i=0; i<strippedCode.length(); i++){

            int pos;
            char currentChar = strippedCode.charAt(i);
            char capitalized = Character.toUpperCase(currentChar);

            //If the index is a letter
            if(cipher2Alph.contains(currentChar) || cipher2Alph.contains(capitalized)){

                //Traverses through first cipher to find the position of the currentChar
                for(int k = 0; k<cipher1Res.length; k++){
                    char alphChar = cipher1Res[k];
                    if(alphChar==currentChar){
                       pos = cipher1Res.indexOf(alphChar);
                    }
                }

                /*Uses the position in the array to correspond with the right letter in the alphabet
                In other words, it will find the position in the first cipher then find where it
                Corresponds to in the alphabet. Then it will append that to the result */
                if(currentChar=Character.toUpperCase(currentChar)){
                    cipher2Res[cipher2Len]=Character.toUpperCase(cipher2Alph[pos]);
                }
                else{
                   cipher2Res[cipher2Len]=cipher2Alph[pos];
                }
             cipher2Len++;
            }

            //Otherwise we append it normally
            else{
                cipher2Res[cipher2Len]=strippedCode[i];
                cipher2Len++;
            }
        }

        //Turn result back into String
        cipher2StrRes=cipher2Res.toString();

        //Print
        printer.writeFile("PasswordDecryptOutput.txt", cipher2StrRes);
    }
}


