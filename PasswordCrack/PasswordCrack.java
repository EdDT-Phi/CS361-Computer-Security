import java.util.*;
import java.io.*;

class PasswordCrack 
{
  public static void main(String[] args) 
  {
    if(args.length != 2) {
      System.out.println("usage: java PasswordCrack dictionary.txt passwords.txt");
      return;
    }

    try(
      Scanner dictReader = new Scanner(new File(args[0]));
      Scanner passReader = new Scanner(new File(args[1]))) 
    {

      ArrayList<String> encryptions = new ArrayList<String>();
      ArrayList<String> salts = new ArrayList<String>();
      ArrayList<String> fname = new ArrayList<String>();
      ArrayList<String> lname = new ArrayList<String>();
      int i = 0;
      while(passReader.hasNext())
      {
        String[] tokens = passReader.nextLine().split(":");
        
        encryptions.add(tokens[1]);
        salts.add(tokens[1].substring(0, 2));
        String[] name = tokens[4].toLowerCase().split(" ");
        fname.add(name[0]);
        lname.add(name[1]);

        //pass zero
        if(checkPassword(salts.get(i), encryptions.get(i), name[0]))
        {
          encryptions.remove(i);
          salts.remove(i);
          fname.remove(i);
          lname.remove(i);
          i--;
        }
        else if (checkPassword(salts.get(i), encryptions.get(i), name[1]))
        {
          encryptions.remove(i);
          salts.remove(i);
          fname.remove(i);
          lname.remove(i);
          i--;
        }

        i++;
      }

      //pass one
      while(dictReader.hasNext())
      {
        String word = dictReader.next();
        for(i = 0; i < encryptions.size(); i++)
        {
          if(checkPassword(salts.get(i), encryptions.get(i), word))
          {
            encryptions.remove(i);
            salts.remove(i);
            fname.remove(i);
            lname.remove(i);
            i--;
          }
        }
      }

      jcrypt.crypt(salts.get(0), "pasword");

    } 
    catch (Exception e) 
    {
      System.out.println(e);
      System.out.println("usage: java PasswordCrack dictionary.txt passwords.txt");
      return;
    }
  }

  public static boolean checkPassword(String salt, String encryption, String guess)
  {

    if(jcrypt.crypt(salt, guess).equals(encryption))
    {
      System.out.println(encryption + " -> " + guess);
      return true;
    }
    // System.out.println(encryption + " -> " + jcrypt.crypt(salt, guess));

    return false; 
  }


  public static void toggle(String word, ArrayList<String> guesses)
  {
    String 
  }

}