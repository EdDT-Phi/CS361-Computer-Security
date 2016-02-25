UTEID: et7226; jdt2725; 
FIRSTNAME: Eddie; Justin; 
LASTNAME: Tribaldos; Tran;
CSACCOUNT: eddt; justint;
EMAIL: eddie.tribaldos@cs.utexas.edu; tran.d.justin@gmail.com;

Our code consists of multiple files for easy reading. Our SecureSystem file  holds the main function that takes in a file and reads it. Our parse function is in this file as well and tokenizes each line in the file and returns an InstructionObject which contains information on what type of command was read and its arguments. We also use try/catch to check if there are syntactically incorrect commands and print "Bad Instruction" if there is. The meat of our code is in the ReferenceMonitor file which handles all the relationships and necessary checks. It prints the command requested regardless if it actually happens. A check on the security levels is performed and if it checks out, the subject's/object's value can be changed. Objects and subjects are created here and stored in an objects hashmap and subjects hashmap respectively. This allows the PrintState function (also in this file) to iterate through the hashmaps and print their states. The SecurityLevel file stores constants for HIGH and LOW levels and has a dominates function that returns a boolean as to whether one security level dominates the other. The ssSubject and ssObject classes simply have a constructor that stores the name, security level, and values for the subject/object. The InstructionObject file also just stores constructors for an InstructionObject class.

(1) Finished all requirements

Test Cases:
5 test cases below

Input:
This instruction should fail
wRite Hal Hobj 42
write Lyle Hobj 99
READ Lyle Hobj
READ Hal Lobj

Output:
Bad Instruction
The current state is:
  Lobj has value: 0
  Hobj has value: 0
  Hal has recently read: 0
  Lyle has recently read: 0
Hal writes value 42 to Hobj
The current state is:
  Lobj has value: 0
  Hobj has value: 42
  Hal has recently read: 0
  Lyle has recently read: 0
Lyle writes value 99 to Hobj
The current state is:
  Lobj has value: 0
  Hobj has value: 99
  Hal has recently read: 0
  Lyle has recently read: 0
Lyle reads Hobj
The current state is:
  Lobj has value: 0
  Hobj has value: 99
  Hal has recently read: 0
  Lyle has recently read: 0
Hal reads Lobj
The current state is:
  Lobj has value: 0
  Hobj has value: 99
  Hal has recently read: 0
  Lyle has recently read: 0
