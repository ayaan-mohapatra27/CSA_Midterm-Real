
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.*;


public class MidtermEncrypt1 {

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
        String code = reader.ReadFile("src/encrypt.txt");//reads file
        String codeStripped=code.replaceAll("\\s", ""); //Removes whitespace
        String mostUsedChar="";
        int mostCount=0;

        for(int i = 0; i<codeStripped.length(); i++){ //Used to find the most occuring character
            String currentLetter=codeStripped.substring(i,i+1);
            int count =0;
            for(int k = i; k<codeStripped.length();k++){
                if(currentLetter.equals(codeStripped.substring(k,k+1))){
                    count++;
                }
            }
            if(count>mostCount){
                mostUsedChar=currentLetter;
                mostCount=count;
            }
        }
        result+=mostUsedChar;

        char most = mostUsedChar.charAt(0);
        int cipher = (int)most; //Finding how far the shift is needed to be

        for(int i = 0; i<code.length(); i++){
            String currentLetter=code.substring(i,i+1);
            char currentChar = currentLetter.charAt(0);
            int newNum = (int)currentChar; //Finding ascii value
            newNum+=cipher; //Changing ascii value
            while(newNum>126){ //Looping to ensure ascii value stays in range
                newNum-=95;
            }
            result+=(char)newNum;
        }

        printer.writeFile("encryptOutput.txt", result); //prints onto a new file use the variable that you build in the program

    }
}


