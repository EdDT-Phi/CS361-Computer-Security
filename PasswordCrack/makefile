default:
	clear;
	javac *.java
clean:
	rm -f *.class
	rm -f *.enc*
turnin:
	zip jdt2725_et7226_program5.zip *
love:
	java PasswordCrack dictionary pass1
babies:
	java PasswordCrack dictionary pass2
all: love babies
test1:
	java AES e keyFile bigger
	java AES d keyFile bigger.enc
test2:
	java AES e keyFile big
	java AES d keyFile big.enc
test3:
	java AES e keyFile small
	java AES d keyFile small.enc
test4:
	java AES e keyFile smaller
	java AES d keyFile smaller.enc