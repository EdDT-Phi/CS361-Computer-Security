UTEID: jdt2725; et7226;
FIRSTNAME: Justin; Eddie;
LASTNAME: Tran; Tribaldos;
CSACCOUNT: justint; eddt;
EMAIL: tran.d.justin@gmail.com; eddie.tribaldos@cs.utexas.edu;

[Program 5]
[Description]
There is only 1 java file: In AES.java, we predefined arrays byteSub and invByteSub so we could access when substituting bytes. We included your code of predefined log tables. We predefined an rcon table as well for creating the extended key. Justin implemented the main method which included handling input from the command line using the all to familiar scanner, filewriter, and bufferedwriter. Justin also wrote the simple methods of rotate and shiftRows which respectively rotates the bottom byte to the top and shifts the rows of the array as demonstrated in the graphic shown previously. Eddie wrote subBytes, XOR, encode and decode which where parallels of each other. Encode simply added the round key, subsituted bytes, shifted the rows, and mixed the columns. Decode just did the reverse. One of the key methods was keyExpansion which expanded upon the previous methods mentioned before. Key expansion rotated the current column, substituted its bytes, and XOR'd it with rcon and the column previous. To compile our program you should run "make clean", followed by "make", followed by "make love" to encrypt. "make babies" decrypts.

[Finish]
(1) Finished all requirements




[Test Case 1]

[Command line]
  make test1

[Timing Information]
  FileSize: 16000 bytes

  Encoding took: 133 milliseconds
  Throughput: 0.120248 MB/sec

  Decoding took: 112 milliseconds
  Throughput: 0.141959 MB/sec

[Input Filenames]
  bigger

[Output Filenames]
  bigger.enc
  bigger.enc.dec




[Test Case 2]

[Command line]
  make test2

[Timing Information]
  FileSize: 6400 bytes

  Encoding took: 114 milliseconds
  Throughput: 0.055875 MB/sec

  Decoding took: 67 milliseconds
  Throughput: 0.094195 MB/sec

[Input Filenames]
  big

[Output Filenames]
  big.enc
  big.enc.dec




[Test Case 3]

[Command line]
  make test3

[Timing Information]
  FileSize: 3200 bytes

  Encoding took: 54 milliseconds
  Throughput: 0.058463 MB/sec

  Decoding took: 47 milliseconds
  Throughput: 0.066939 MB/sec

[Input Filenames]
  small

[Output Filenames]
  small.enc
  small.enc.dec




[Test Case 4]

[Command line]
  make test4

[Timing Information]
  FileSize: 1600 bytes

  Encoding took: 32 milliseconds
  Throughput: 0.049849 MB/sec

  Decoding took: 25 milliseconds
  Throughput: 0.063577 MB/sec

[Input Filenames]
  smaller

[Output Filenames]
  smaller.enc
  smaller.enc.dec
