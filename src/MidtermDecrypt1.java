
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.*;


public class MidtermDecrypt1 {

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
        /*
        method used to print onto a new file
        */

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
    /*
    method used to read a file
    */


    public static void main(String[] args) throws FileNotFoundException{
        TextFiles reader = new TextFiles(); //makes a bot that will end up reading file
        TextFiles printer = new TextFiles();//makes a bot that will end up printing file

        String result="";
        String code = reader.ReadFile("src/decrypt.txt");//reads file

        //Find first letter and remove it
        char cipher = code.charAt(0);
        code=code.substring(1);
        int shift = (int) cipher; //Finding how far the shift is needed to be


        for(int i = 0; i<code.length(); i++){
            char currentChar = code.charAt(i);
            int currentNum = (int) currentChar;
            currentNum-=shift; //Subtracting the ascii value from the shift
            while(currentNum<32){ //Ensures that the value is between the range of 32-126
                currentNum+=95;
            }
            char newChar = (char) currentNum;
            result+=newChar;
        }

        printer.writeFile("decryptOutput.txt", result); //prints onto a new file use the variable that you build in the program

    }
}


