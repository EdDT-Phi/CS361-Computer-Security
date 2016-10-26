import java.util.*;
import java.io.*;

class PasswordCrack
{

  public static HashSet<String> guesses;
  public static ArrayList<String> encryptions = new ArrayList<String>();
  public static ArrayList<String> salts = new ArrayList<String>();
  public static ArrayList<String> fname = new ArrayList<String>();
  public static ArrayList<String> lname = new ArrayList<String>();


  /*
   * This program tries several methods of cracking passwords through brute force
   * It tries faster methods first to elminate passwords asap
   */


  public static void main(String[] args)
  {
    if(args.length != 2) {
      System.out.println("usage: java PasswordCrack dictionary.txt passwords.txt");
      return;
    }
    long startTime = System.nanoTime();

    try(Scanner passReader = new Scanner(new File(args[1])))
    {

      int i = 0;
      System.out.println("\nNormal Names " + (System.nanoTime()-startTime)/1000000.0 +" \n");
      while(passReader.hasNext())
      {
        String[] tokens = passReader.nextLine().split(":");

        encryptions.add(tokens[1]);
        salts.add(tokens[1].substring(0, 2));
        String[] name = tokens[4].toLowerCase().split(" ");
        fname.add(name[0]);
        lname.add(name[1]);

        if(checkPassword(startTime, salts.get(i), encryptions.get(i), name[0]))
        {
          encryptions.remove(i);
          salts.remove(i);
          fname.remove(i);
          lname.remove(i);
          i--;
        }
        else if (checkPassword(startTime, salts.get(i), encryptions.get(i), name[1]))
        {
          encryptions.remove(i);
          salts.remove(i);
          fname.remove(i);
          lname.remove(i);
          i--;
        }

        i++;
      }

      System.out.println("\nMangle Names " + (System.nanoTime()-startTime)/1000000.0 +" \n");
      for(i = 0; i < encryptions.size(); i++)
      {
        guesses = new HashSet<String>();
        preappend(fname.get(i));
        reverseString(fname.get(i));
        mangles(fname.get(i));
        toggle(fname.get(i));

        preappend(lname.get(i));
        reverseString(lname.get(i));
        mangles(lname.get(i));
        toggle(lname.get(i));


        for(String guess: guesses)
        {

          // System.out.println(guess);
          if(checkPassword(startTime, salts.get(i), encryptions.get(i), guess))
          {
            encryptions.remove(i);
            salts.remove(i);
            fname.remove(i);
            lname.remove(i);
            i--;
            break;
          }
        }
      }




      System.out.println("\nDouble Mangle Names " + (System.nanoTime()-startTime)/1000000.0 +" \n");
      for(i = 0; i < encryptions.size(); i++)
      {

        guesses = new HashSet<String>();

        preappend(fname.get(i));
        reverseString(fname.get(i));
        mangles(fname.get(i));
        toggle(fname.get(i));

        preappend(lname.get(i));
        mangles(lname.get(i));
        reverseString(lname.get(i));
        toggle(lname.get(i));

        HashSet<String> temp = guesses;
        guesses = new HashSet<String>();
        for(String guess: temp)
        {
          preappend(guess);
          reverseString(guess);
          mangles(guess);
          toggle(guess);
        }

        for(String guess: guesses)
        {
          if(checkPassword(startTime, salts.get(i), encryptions.get(i), guess))
          {
            encryptions.remove(i);
            salts.remove(i);
            fname.remove(i);
            lname.remove(i);
            i--;
            break;
          }
        }
      }

      System.out.println("\nNormal Dictionary " + (System.nanoTime()-startTime)/1000000.0 +" \n");
      try(Scanner dictReader = new Scanner(new File(args[0])))
      {
        while(dictReader.hasNext())
        {
          String word = dictReader.next();
          for(i = 0; i < encryptions.size(); i++)
          {
            if(checkPassword(startTime, salts.get(i), encryptions.get(i), word))
            {
              encryptions.remove(i);
              salts.remove(i);
              fname.remove(i);
              lname.remove(i);
              i--;
            }
          }
        }
      } catch (Exception e) {
        System.out.println(e);
      }

      System.out.println("\nMangle Dictionary " + (System.nanoTime()-startTime)/1000000.0 +" \n");
      try(Scanner dictReader = new Scanner(new File(args[0])))
      {
        while(dictReader.hasNext())
        {
          String word = dictReader.next();
          guesses.clear();


          reverseString(word);
          mangles(word);
          toggle(word);
          for(String guess: guesses)
          {
            for(i = 0; i < encryptions.size(); i++)
            {
              if(checkPassword(startTime, salts.get(i), encryptions.get(i), guess))
              {
                encryptions.remove(i);
                salts.remove(i);
                fname.remove(i);
                lname.remove(i);
                i--;
              }
            }
          }
        }
      } catch (Exception e) {
        System.out.println(e);
      }


      System.out.println("\nPreApp Dictionary " + (System.nanoTime()-startTime)/1000000.0 +" \n");
      try(Scanner dictReader = new Scanner(new File(args[0])))
      {
        while(dictReader.hasNext())
        {
          String word = dictReader.next();
          guesses.clear();

          preappend(word);
          for(String guess: guesses)
          {
            for(i = 0; i < encryptions.size(); i++)
            {
              if(checkPassword(startTime, salts.get(i), encryptions.get(i), guess))
              {
                encryptions.remove(i);
                salts.remove(i);
                fname.remove(i);
                lname.remove(i);
                i--;
              }
            }
          }
        }
      } catch (Exception e) {
        System.out.println(e);
      }

      System.out.println("\nDouble Mangle Dictionary " + (System.nanoTime()-startTime)/1000000.0 +" \n");
      try(Scanner dictReader = new Scanner(new File(args[0])))
      {
        int time = 0;
        while(dictReader.hasNext())
        {
          String word = dictReader.next();
          guesses = new HashSet<String>();

          // preappend(word);
          reverseString(word);
          mangles(word);
          toggle(word);

          int end = guesses.size();
          for(String guess: guesses)
          {
            reverseString(guess);
            mangles(guess);
            toggle(guess);
          }


          for(String guess: guesses)
          {
            for(i = 0; i < encryptions.size(); i++)
            {
              if(checkPassword(startTime, salts.get(i), encryptions.get(i), guess))
              {
                encryptions.remove(i);
                salts.remove(i);
                fname.remove(i);
                lname.remove(i);
                i--;
              }
            }
          }
        }
      } catch (Exception e) {
        System.out.println(e);
      }

      System.out.println("\nDouble Mangle Dictionary " + (System.nanoTime()-startTime)/1000000.0 +" \n");
      try(Scanner dictReader = new Scanner(new File(args[0])))
      {
        int time = 0;
        while(dictReader.hasNext())
        {
          String word = dictReader.next();
          guesses = new HashSet<String>();

          preappend(word);
          reverseString(word);
          mangles(word);
          toggle(word);

          int end = guesses.size();
          for(String guess: guesses)
          {
            preappend(guess);
            reverseString(guess);
            mangles(guess);
            toggle(guess);
          }


          for(String guess: guesses)
          {
            for(i = 0; i < encryptions.size(); i++)
            {
              if(checkPassword(startTime, salts.get(i), encryptions.get(i), guess))
              {
                encryptions.remove(i);
                salts.remove(i);
                fname.remove(i);
                lname.remove(i);
                i--;
              }
            }
          }
        }
      } catch (Exception e) {
        System.out.println(e);
      }
    }
    catch (Exception e)
    {
      // System.out.println(e.pr);
      e.printStackTrace();
      // System.out.println("usage: java PasswordCrack dictionary.txt passwords.txt");
      return;
    }
  }

  public static boolean checkPassword(long startTime, String salt, String encryption, String guess)
  {

    if(jcrypt.crypt(salt, guess).equals(encryption))
    {
      System.out.println(encryption + " -> " + guess + " -> "+ (System.nanoTime()-startTime)/1000000000.0);
      System.out.println("Passwords Left: " + encryptions.size() + "\n");
      return true;
    }
    return false;
  }


  public static void preappend(String word)
  {
    String typableChars = "abcdefghijklmnopqrstuvwxyz1234567890-=[];',./!@#$%^&*()_+{}|:<>?\\\"";
    for (int i = 0; i < typableChars.length(); i++)
    {
      guesses.add(typableChars.charAt(i) + word);
      guesses.add(word + typableChars.charAt(i));
    }
  }

  public static void toggle(String word)
  {
    StringBuilder temp = new StringBuilder();
    StringBuilder temp2 = new StringBuilder();
    String upper = word.toUpperCase();

    for(int i = 0; i < word.length(); i++)
    {
      if(i % 2 == 0)
      {
        temp.append(word.charAt(i));
        temp2.append(upper.charAt(i));
      }
      else
      {
        temp2.append(word.charAt(i));
        temp.append(upper.charAt(i));
      }
    }

    guesses.add(temp.toString());
    guesses.add(temp2.toString());
  }

  public static void mangles(String word)
  {
    String upper = word.toUpperCase();
    String lower = word.toLowerCase();
    guesses.add(lower.charAt(0) + upper.substring(1));
    guesses.add(upper.charAt(0) + lower.substring(1));
    guesses.add(upper);
    guesses.add(word + word);
    guesses.add(word.substring(1));
    guesses.add(word.substring(0, word.length()-1));
  }

  public static void reverseString(String word)
  {
    String reversed = new StringBuilder(word).reverse().toString();
    guesses.add(reversed);
    guesses.add(reversed + word);
    guesses.add(word + reversed);
  }

}