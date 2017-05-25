import java.io.*;
/**
 *  Defines some basic methods a libary of books should
 *  define.
 *  
 *  @author Raven Russell
 *  @version 0.1
 */
interface Library {
 /**
  *  Sets the library to use a specific database file.
  *  
  *  @param libraryFileName the name of the file which contains the list of book files
  *  @return true if the database file was set, false if an error occurred
  */
 public boolean setLibraryFile(String libraryFileName);
 
 /**
  *  Returns the current library file
  *  
  *  @return the current library file, null if no file set
  */
 public File getLibraryFile();
 
 /**
  *  Gets the number of books in the library.
  *  
  *  @param withPath specifies if the return strings should include the path to the files or just the file names
  *  @return an array of book files contained in the library, null if no library
  */
 public String[] getLibraryBooks(boolean withPath);
 
 /**
  *  Gets the number of books in the previously set library.
  *  
  *  @return the number of books in the library, -1 if no library has been set
  */
 public int getNumberOfBooks();

 /**
  *  Gets the number of times a phrase appears in the library.
  *  In other words, checks every book in the library and adds up the
  *  number of times the phrase has appeared in all books. Note: if
  *  the phrase appears across multiple lines (e.g. the book contains
  *  "the\nhouse" and you search for "the house", "the\nhouse" should
  *  be counted).
  *  
  *  @param phrase the phrase to look for
  *  @return the number of times the phrase appears in the entire library, -1 if no library has been set
  */
 public int getLibraryOccurrences(String phrase);
 
 /**
  *  Gets the percentage of books in the library that use a certain
  *  set of characters to denote a newline. For example, if 50% of
  *  the books use "\r\n" to denote a new line, and newlineChars
  *  is set to {'\r', '\n'}, this method will return 0.5
  *  
  *  @param newlineChars an array containing the sequence of character which might denote a new line
  *  @return the percent of books in the library which use the given sequence for new lines
  */
 public double getLibraryNewlinePercentage(char[] newlineChars);
 
 /**
  *  Gets the number of times a phrase appears in a given book.
  *  Note: if the phrase appears across multiple lines (e.g. the
  *  book contains "the\nhouse" and you search for "the house",
  *  "the\nhouse" should be counted).
  *  
  *  Accepts a book number which assumed to be an index into the
  *  array returned by #getLibraryBooks(boolean).
  *  
  *  @param bookNumber an index into the set of books in the library
  *  @param phrase the phrase to look for
  *  @return the number of times the phrase appears in the book, -1 if the bookNumber is out of bounds of the library
  */
 public int getBookOccurrences(int bookNumber, String phrase);
 
 /**
  *  Gets the number of lines in a given book.
  *  
  *  Accepts a book number which assumed to be an index into the
  *  array returned by #getLibraryBooks(boolean).
  *  
  *  @param bookNumber an index into the set of books in the library
  *  @return the number of lines in the given book, -1 if the bookNumber is out of bounds of the library
  */
 public int getBookNumLines(int bookNumber);
 
 /**
  *  Returns the set of characters which denote a newline in
  *  a given book. For example {'\r', '\n'} or {'\n'}. If the
  *  book uses more than one newline character set, use the first
  *  one found.
  *  
  *  Accepts a book number which assumed to be an index into the
  *  array returned by #getLibraryBooks(boolean).
  *  
  *  @param bookNumber an index into the set of books in the library
  *  @return the sequence of characters used to denote the newline in this book, null if the bookNumber is out of bounds of the library
  */
 public char[] getBookNewlineCharacters(int bookNumber);
}