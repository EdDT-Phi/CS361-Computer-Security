import java.util.*;
import java.io.*;

class PasswordCrack 
{

  public static ArrayList<String> guesses;
  public static void main(String[] args) 
  {
    if(args.length != 2) {
      System.out.println("usage: java PasswordCrack dictionary.txt passwords.txt");
      return;
    }
    long startTime = System.nanoTime();

    try(
      Scanner passReader = new Scanner(new File(args[1]))) 
    {

      ArrayList<String> encryptions = new ArrayList<String>();
      ArrayList<String> salts = new ArrayList<String>();
      ArrayList<String> fname = new ArrayList<String>();
      ArrayList<String> lname = new ArrayList<String>();
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

      // System.out.println("\nNormal Dictionary " + (System.nanoTime()-startTime)/1000000.0 +" \n");
      // try(Scanner dictReader = new Scanner(new File(args[0])))
      // {
      //   while(dictReader.hasNext())
      //   {
      //     String word = dictReader.next();
      //     for(i = 0; i < encryptions.size(); i++)
      //     {
      //       if(checkPassword(salts.get(i), encryptions.get(i), word))
      //       {
      //         encryptions.remove(i);
      //         salts.remove(i);
      //         fname.remove(i);
      //         lname.remove(i);
      //         i--;
      //       }
      //     }
      //   }
      // } catch (Exception e) {
      //   System.out.println(e);
      // }

      // System.out.println("\nMangle Names " + (System.nanoTime()-startTime)/1000000.0 +" \n");
      // for(i = 0; i < encryptions.size(); i++)
      // {
      //   guesses = new ArrayList<String>();
      //   preappend(fname.get(i));
      //   deleteFirst(fname.get(i));
      //   deleteLast(fname.get(i));
      //   reverseString(fname.get(i));
      //   capitalize(fname.get(i));
      //   duplicate(fname.get(i));
      //   toggle(fname.get(i));

      //   preappend(lname.get(i));
      //   deleteFirst(lname.get(i));
      //   deleteLast(lname.get(i));
      //   reverseString(lname.get(i));
      //   capitalize(lname.get(i));
      //   duplicate(lname.get(i));
      //   toggle(lname.get(i));


      //   for(String guess: guesses)
      //   {
      //     if(checkPassword(salts.get(i), encryptions.get(i), guess))
      //     {
      //       encryptions.remove(i);
      //       salts.remove(i);
      //       fname.remove(i);
      //       lname.remove(i);
      //       i--;
      //       break;
      //     }
      //   }
      // }



      // System.out.println("\nMangle Dictionary " + (System.nanoTime()-startTime)/1000000.0 +" \n");
      // try(Scanner dictReader = new Scanner(new File(args[0])))
      // {
      //   while(dictReader.hasNext())
      //   {
      //     String word = dictReader.next();
      //     guesses = new ArrayList<String>();

      //     preappend(word);
      //     deleteFirst(word);
      //     deleteLast(word);
      //     reverseString(word);
      //     capitalize(word);
      //     duplicate(word);
      //     toggle(word);
      //     for(String guess: guesses)
      //     {
      //       for(i = 0; i < encryptions.size(); i++)
      //       {
      //         if(checkPassword(salts.get(i), encryptions.get(i), guess))
      //         {
      //           encryptions.remove(i);
      //           salts.remove(i);
      //           fname.remove(i);
      //           lname.remove(i);
      //           i--;
      //         }
      //       }
      //     }
      //   }
      // } catch (Exception e) {
      //   System.out.println(e);
      // }

      System.out.println("\nDouble Mangle Names " + (System.nanoTime()-startTime)/1000000.0 +" \n");
      for(i = 0; i < encryptions.size(); i++)
      {
        guesses = new ArrayList<String>();
        preappend(fname.get(i));
        deleteFirst(fname.get(i));
        deleteLast(fname.get(i));
        reverseString(fname.get(i));
        capitalize(fname.get(i));
        duplicate(fname.get(i));
        toggle(fname.get(i));
        preappend(lname.get(i));
        deleteFirst(lname.get(i));
        deleteLast(lname.get(i));
        reverseString(lname.get(i));
        capitalize(lname.get(i));
        duplicate(lname.get(i));
        toggle(lname.get(i));

        int end = guesses.size();
        for(int j = 0;j < end;j++)
        {
          preappend(guesses.get(j));
          deleteFirst(guesses.get(j));
          deleteLast(guesses.get(j));
          reverseString(guesses.get(j));
          capitalize(guesses.get(j));
          duplicate(guesses.get(j));
          toggle(guesses.get(j));
        }


        for(String guess: guesses)
        {
          if(checkPassword(salts.get(i), encryptions.get(i), guess))
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

      System.out.println("\nDouble Mangle Dictionary " + (System.nanoTime()-startTime)/1000000.0 +" \n");
      try(Scanner dictReader = new Scanner(new File(args[0])))
      {
        int time = 0;
        while(dictReader.hasNext())
        {
          String word = dictReader.next();
          guesses = new ArrayList<String>();
          if(time++ % 10 == 0)
            System.out.println(word + ": "+ (System.nanoTime()-startTime)/1000000.0 +" \n");

          preappend(word);
          deleteFirst(word);
          deleteLast(word);
          reverseString(word);
          capitalize(word);
          duplicate(word);
          toggle(word);

          int end = guesses.size();
          for(int j = 0;j < end;j++)
          {
            preappend(guesses.get(j));
            deleteFirst(guesses.get(j));
            deleteLast(guesses.get(j));
            reverseString(guesses.get(j));
            capitalize(guesses.get(j));
            duplicate(guesses.get(j));
            toggle(guesses.get(j));
          }


          for(String guess: guesses)
          {
            for(i = 0; i < encryptions.size(); i++)
            {



              if(checkPassword(salts.get(i), encryptions.get(i), guess))
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




      // jcrypt.crypt(salts.get(0), "pasword");

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


  public static void preappend(String word)
  {
    String typableChars = "abcdefghijklmnopqrstuvwxyz1234567890-=[];',./!@#$%^&*()_+{}|:<>?\\\"";
    for (int i = 0; i < typableChars.length(); i++)
    {
      guesses.add(typableChars.charAt(i) + word);
      guesses.add(word + typableChars.charAt(i));
    }
  }

  public static void capitalize(String word)
  {
    String upper = word.toUpperCase();
    String lower = word.toLowerCase();
    guesses.add(lower.charAt(0) + upper.substring(1));
    guesses.add(upper.charAt(0) + lower.substring(1));
    guesses.add(upper);
    guesses.add(lower);
  }


  public static void toggle(String word)
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

  public static void duplicate(String word)
  {
    guesses.add(word + word);
  }

  public static void deleteFirst(String word)
  {
    guesses.add(word.substring(1));
  }

  public static void deleteLast(String word)
  {
    guesses.add(word.substring(0, word.length()-1));
  }

  public static void reverseString(String word)
  {
    String reversed = "";
    for (int i = word.length()-1; i >= 0; i--)
    {
      reversed += word.charAt(i);
    }
    guesses.add(reversed);
    guesses.add(reversed + word);
    guesses.add(word + reversed);
  }

}