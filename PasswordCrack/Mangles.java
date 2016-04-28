import java.util.*;
import java.io.*;


class Mangles 
{
  public static void main(String[] args) 
  {
  	ArrayList<String> guesses = new ArrayList<String>();
  	prepend("Prepend", guesses);
  	append("Append", guesses);


    capitalize("capitalize", guesses);
    // ncapitalize("ncapitalize", guesses);
    toggle("toggle", guesses);
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

  public static void capitalize(String word, ArrayList<String> guesses)
  {
    String upper = word.toUpperCase();
    String lower = word.toLowerCase();
    guesses.add(lower.charAt(0) + upper.substring(1));
    guesses.add(upper.charAt(0) + lower.substring(1));
    guesses.add(upper);
    guesses.add(lower);
  }


  public static void toggle(String word, ArrayList<String> guesses)
  {
    StringBuilder temp = new StringBuilder();
    StringBuilder temp2 = new StringBuilder();
    int i = 0;
    for(char c : word.toCharArray())
    {
      if(i % 2 == 0)
      {
        temp.append(Character.toUpperCase(c));
        temp2.append(c);
      }
      else
      {
        temp2.append(Character.toUpperCase(c));
        temp.append(c);
      }
      i++;
    }

    guesses.add(temp.toString());
    guesses.add(temp2.toString());
  }
}