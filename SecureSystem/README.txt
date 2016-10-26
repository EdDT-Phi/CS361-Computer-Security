UTEID: et7226; jdt2725; 
FIRSTNAME: Eddie; Justin; 
LASTNAME: Tribaldos; Tran;
CSACCOUNT: eddt; justint;
EMAIL: eddie.tribaldos@cs.utexas.edu; tran.d.justin@gmail.com;

#[Program 2]

Run: java CovertChannel [v] inputfile

##[Description]
There are 5 java files: The CovertChannel file is the most important. We have our covert channel here. It parses the input file and executes instructions based off the bits in the file, as indicated in the assignment instructions. When the Verbose flag is set we simply output what the instructions executed to log. SecureSystem is the the gateway between the Channel and the ReferenceMonitor. It takes in commands, parses them, and sends them as Instruction Objects. The Reference monitor evaluates the instructions and is where objects, subjects and the ObjectManager live. Only the ObjectManager has access to objects. We have a run fuction inside the Subject class which Lyle uses to evaluate what he has read and output a byte with FileOutputStream. This run function and main inside of CovertChannel are the most important functions. Eddie drove a slight majority of the time but Justin drove as well. Both contributed equally to the completion of this project.

##[Machine Information]
  machine type: Intel(R) Xeon(R) CPU E3-1270 V2 @ 3.50GHz (aka lab machine)
  clock speed: CPU MHz: 1600.000
  operating system: Linux

##[Source Description]
  Pride and Prejudice
  description: classic book by Jane Austen (required)
  source: http://www.gutenberg.org/files/1342/1342-pdf.pdf

  The Metamorphosis
  description: classic book by Franz Kafka (required)
  source: http://history-world.org/The_Metamorphosis_T.pdf

  Of Mice and Men
  description: classic book by John Steinbeck
  source: http://static1.1.sqspcdn.com/static/f/523476/26270956/1432918802250/Of_Mice_and_Men_-_Full_Text.pdf?token=Hi%2BbVnNJLEk9mHU8duhFGbcoufU%3D

  Test Text
  description: YoloSwagBlazeIt420 copy and pasted multiple times
  source: my mind and the mass usage of ctrlc + ctrlv

##[Finish]
  (1) Finished all requirements

##[Results Sumamry]
[No.]    [DocumentName]        [Size]    [Bandwidth]
  1     Pride and Prejudice    681.8kB   645 bits/ms
  2     The Metamorphosis      125.8kB   606 bits/ms
  3     Of Mice and Men        167.4kB   612 bits/ms
  4     Test Text              164.2kB   602 bits/ms
  