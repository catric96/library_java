import java.io.*;
import java.util.*;

public class Database implements Library{

private String libName = "";

//
  public boolean setLibraryFile(String libraryFileName){
    libName = libraryFileName;
    File libraryfile = new File(libraryFileName);
    return libraryfile.exists();
  }
  
  
  
//  
  public File getLibraryFile(){
    if(libName == null)
      return null;    
    File libFile = new File(libName);
    return libFile;
  }
  
  
  
// 
  public String[] getLibraryBooks(boolean withPath){
    int count = 0;
    
    try{
    Scanner in = new Scanner(getLibraryFile());
    while(in.hasNext()){
      count++;
      in.next();
    }
    in.close();
    }
    catch(FileNotFoundException e){
      System.err.println("FileNotFoundException: " + e.getMessage());
    }
    
    String[] paths = new String[count];
    
    try{
    Scanner in2 = new Scanner(getLibraryFile());
    for(int x = 0; x < paths.length; x++){
        String path = in2.nextLine();
        File n = new File(path);
        path = n.getName();
        if(withPath){
          path = n.getPath();
        }
        paths[x] = path;
    }
    in2.close();
    }
    catch(FileNotFoundException e){
      System.err.println("FileNotFoundException: " + e.getMessage());
    }
    
    return paths;
  }
  
  
  
//  
  public int getNumberOfBooks(){
    return getLibraryBooks(false).length;
  }
  
  
//  
  public int getLibraryOccurrences(String phrase){
    int count = 0;
    for(int x = 0; x < getNumberOfBooks(); x++){
      count += getBookOccurrences(x, phrase);
    }
    return count; 
  }
  
//  
  public double getLibraryNewlinePercentage(char[] newlineChars){
    int count = 0;
    int total = getNumberOfBooks();
    for(int x = 0; x < total; x++){
      char[] a = getBookNewlineCharacters(x);
      boolean isequal = false;
      if(a.length == newlineChars.length){
        isequal = true;
        for(int i = 0; i < a.length; i++){
          if(a[i] != newlineChars[i])
            isequal = false;
        }
      }
      if(isequal)
        count++;
    }
    return count/total;
  }

  
//  
  public int getBookOccurrences(int bookNumber, String phrase){
    String[] paths = getLibraryBooks(true);
    int count = 0;
    String s = "";
    try{
      Scanner in = new Scanner(new File(paths[bookNumber]));
      while(in.hasNextLine()){
       s = s + " " + in.nextLine();
      }
      in.close();
    }
    catch(FileNotFoundException e){
      System.err.println("FileNotFoundException: " + e.getMessage());
    }
    s = s.toLowerCase();
    s = s.replaceAll("[!.;?,:\"'/\\()`]", " ");
    char[] sarray = s.toCharArray();
    char[] phrasearray = phrase.toCharArray();
    boolean isequal = false;
    for(int x = 0; x < (sarray.length - phrasearray.length);x++){
      if(sarray[x] == phrasearray[0]){
        isequal = true;
        if((x > 0)||(x<(sarray.length - phrasearray.length))){
          if((sarray[x-1] != ' '))//||(sarray[x+phrasearray.length] != ' '))
            isequal = false;
        }
        for(int i = 1; i<phrasearray.length; i++){
          if(phrasearray[i] != sarray[x+i])
            isequal = false;
        }
        if(isequal)
          count++;
      } 
    }
    return count;
  }

  
//  
  public int getBookNumLines(int bookNumber){
    String[] paths = getLibraryBooks(true);
    int count = 0;
    try{
      Scanner in = new Scanner(new File(paths[bookNumber]));
      while(in.hasNextLine()){
       count++;
       in.nextLine();
      }
      in.close();
    }
    catch(FileNotFoundException e){
      System.err.println("FileNotFoundException: " + e.getMessage());
    }
    return count;
  }
  
//  
  public char[] getBookNewlineCharacters(int bookNumber){
    String[] paths = getLibraryBooks(true);
    String a = "";
    char c;
    try {
      FileInputStream f = new FileInputStream(paths[bookNumber]);
        while (f.available() > 0) {
            c = (char) f.read();
            if ((c == '\n') || (c == '\r')) {
                a += c;
                if (f.available() > 0) {
                    char next = (char) f.read();
                    if ((next != c)
                            && ((next == '\r') || (next == '\n'))) {
                        a += next;
                    }
                }
                return a.toCharArray();
            }
        }
    } 
    catch(IOException e){
        System.err.println("FileNotFoundException: " + e.getMessage());
    }
    return a.toCharArray();
  } 

  //
  public static void main(String[] args){
    System.out.print("Enter a database file: ");
    Scanner in = new Scanner(System.in); 
    Database d = new Database();
 
    //set the library
    boolean couldSetBook = d.setLibraryFile(in.nextLine());
    if(!couldSetBook) {
      System.out.println("Could not set library!");
    }
 
    //get the library
    File libraryFile = d.getLibraryFile();
    if(libraryFile == null) {
      System.out.println("Could not get library!");
    }
 
    //check number of books in the library
    int numBooks = d.getNumberOfBooks();
    if(numBooks == -1) {
      System.out.println("Could not get number of books!");
    }
 
    //get the list of books with paths
    String[] books = d.getLibraryBooks(true);
    if(books == null) {
      System.out.println("Could get list of books (w/path)!");
    }
    
    System.out.println("Books:");
    for(int i = 0; i < books.length; i++)
      System.out.println("\t" + i + ". " + books[i]);
 
    //get the list of books without paths
    books = d.getLibraryBooks(false);
    if(books == null) {
      System.out.println("Could get list of books (no path)!");
    }
    System.out.println("Books:");
    for(int i = 0; i < books.length; i++)
      System.out.println("\t" + i + ". " + books[i]);
    
    //number of lines
    System.out.println("Number of lines per book:");
    for(int i = 0; i < books.length; i++) {
      int numLines = d.getBookNumLines(i);
      System.out.println("\t" + i + ". " + ((numLines == -1) ? "Can not load book" : numLines));
    }
    
    //newline characters
    System.out.println("Newline characters per book:");
    for(int i = 0; i < books.length; i++) {
      char[] newline = d.getBookNewlineCharacters(i);
      System.out.print("\t" + i + ". ");
      if(newline == null) {
        System.out.println("Can not load book");
      }
      else if(newline.length == 0) {
        System.out.println("No newline characters!");
      }
      else if(newline.length == 1) {
        System.out.println(newline[0] == '\n' ? "\\n" : "\\r");
      }
      else {
        System.out.print(newline[0] == '\n' ? "\\n" : "\\r");
        System.out.println(newline[1] == '\n' ? "\\n" : "\\r");
      }
    }
    
     //newline characters
    System.out.println("Percentage newline:");
    System.out.println("\t\\r: " + String.format("%.2f",d.getLibraryNewlinePercentage(new char[] {'\r'})));
    System.out.println("\t\\n: " + String.format("%.2f",d.getLibraryNewlinePercentage(new char[] {'\n'})));
    System.out.println("\t\\r\\n: " + String.format("%.2f",d.getLibraryNewlinePercentage(new char[] {'\r','\n'})));
    System.out.println("\t\\n\\r: " + String.format("%.2f",d.getLibraryNewlinePercentage(new char[] {'\n','\r'})));
    
    //occurrences
    String phrase = "the";
    int occurr = d.getBookOccurrences(0, phrase);
    if(occurr == -1)
      System.out.println("Could not load book!");
    System.out.println("Occurrences of the phrase \""+phrase+"\":" + occurr);
 
    //occurrences
    phrase = "the house";
    occurr = d.getBookOccurrences(0, phrase);
    if(occurr == -1)
      System.out.println("Could not load book!");
    System.out.println("Occurrences of the phrase \""+phrase+"\":" + occurr);
 
  }
}