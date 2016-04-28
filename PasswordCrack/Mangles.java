import java.util.*;
import java.io.*;


class Mangles 
{
  public static void main(String[] args) 
  {
  	ArrayList<String> guesses = new ArrayList<String>();
  	prepend("Prepend", guesses);
  	append("Append", guesses);
  	for (int i = 0; i < guesses.size(); i++)
  	{
  		System.out.println(guesses.get(i));
  	}
  }

  public static void toggle(String word, ArrayList<String> guesses)
  {
    String temp = "";
    int i = 0;
    for(char c : word.toCharArray())
    {
      if(i % 2 = 0)
      temp += 
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
}