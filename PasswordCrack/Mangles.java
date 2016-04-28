import java.util.*;
import java.io.*;


class Mangles 
{
  public static void main(String[] args) 
  {
  	ArrayList<String> guesses = new ArrayList<String>();
  	prepend("Prepend", guesses);
  	append("Append", guesses);
    deleteFirst("DeleteFirst", guesses);
    deleteLast("DeleteLast", guesses);
    reverseString("ReverseString", guesses);

    // Print Guesses
  	for (int i = 0; i < guesses.size(); i++)
  	{
  		System.out.println(guesses.get(i));
  	}
  }

  public static void prepend(String word, ArrayList<String> guesses)
  {
  	String typableChars = "abcdefghijklmnopqrstuvwxyz1234567890-=[];',./!@#$%^&*()_+{}|:<>?\\\"";
  	for (int i = 0; i < typableChars.length(); i++)
  	{
  		guesses.add(typableChars.charAt(i) + word);
  	}
  }

  public static void append(String word, ArrayList<String> guesses)
  {
  	String typableChars = "abcdefghijklmnopqrstuvwxyz1234567890-=[];',./!@#$%^&*()_+{}|:<>?\\\"";
  	for (int i = 0; i < typableChars.length(); i++)
  	{
  		guesses.add(word + typableChars.charAt(i));
  	}
  }

  public static void deleteFirst(String word, ArrayList<String> guesses)
  {
    guesses.add(word.substring(1));
  }

  public static void deleteLast(String word, ArrayList<String> guesses)
  {
    guesses.add(word.substring(0, word.length()-1));
  }

  public static void reverseString(String word, ArrayList<String> guesses)
  {
    String reversed = "";
    for (int i = word.length()-1; i >= 0; i--)
    {
      reversed += word.charAt(i);
    }
    guesses.add(reversed);
  }

}