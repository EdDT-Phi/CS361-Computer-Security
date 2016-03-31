UTEID: jdt2725; et7226;
FIRSTNAME: Justin; Eddie;
LASTNAME: Tran; Tribaldos;
CSACCOUNT: justint; eddt;
EMAIL: tran.d.justin@gmail.com; eddie.tribaldos@cs.utexas.edu;

[Program 3]
[Description]
EXTRA CREDIT:
Our program supports png and bmp images.

It is run using Usage: java Steganography -[ED] image file.

There is only one java file (Steganography.java).
The main method takes in the three argument and passes it into the decode or decode method, depending on the flag.
The encode method loops over the pixels of the image and separates it into reg green and blue. For each of those we call a function to obtain the next bit from the file we are reading. Whenever that function returns -1, we add the terminator and write the image to a file. If we never get -1 and we run out of pixels, the size of the file was too big so we output a warning and save the image.
The decode method does the same, except that instead of reading a bit from a file, we use a function to output the last bit of red green and blue to another file. We stop looping over the pixels when we encounter the terminator.

[Finish]
Finished all requirements

[Questions&Answers]

[Question 1]
Comparing your original and modified images carefully, can you detect *any* difference visually (that is, in the appearance of the image)?

[Answer 1]
Even after detailed inspection there is not difference in the images discernable by the human eye.


[Question 2]
Can you think of other ways you might hide the message in image files (or in other types of files)?

[Answer 2]
One could make only certain known pixels hold bits of interest so even if one knew that there was a message in the image, they still couldn't decode the message without knowing which pixels were meant for what. In a text file, one could make an encryption that only checks certain bytes in the text file and compiles the bytes into the actual secret message.


[Question 3]
Can you invent ways to increase the bandwidth of the channel?

[Answer 3]
One could choose a very HD photo so that there are more pixels to encode a message in. One could also use the alpha parameter that affects transparency. Decreasing or increasing that value by 1 would make very little of a difference in the photo.


[Question 4]
Suppose you were tasked to build an "image firewall" that would block images containing hidden messages. Could you do it? How might you approach this problem?

[Answer 4]
We would have to know how the message was encoded for us to check. If they used our encryption method we could attempt to decrypt it with our program and see if it returned anything readable. This is a shot in the dark but you could use Google's "search for this image" feature to find like photos then download the original photo (with the same resolution) and compare the RGB values to see if they are the same.


[Question 5]
Does this fit our definition of a covert channel? Explain your answer.

[Answer 5]
No. Wikipedia's definition of a covert channel is " a type of computer security attack that creates a capability to transfer information objects between processes that are not supposed to be allowed to communicate". If we're sending the image to another location to be decrypted than the two processes are allowed to communicate already. The purpose of this program is just to make the message difficult to read by anyone who might intercept the message.
