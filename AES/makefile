default:
	clear;
	javac *.java;
clean:
	rm -f *.class;
	rm -f *.enc*
turnin:
	zip jdt2725_et7226_program5.zip *
love:
	java AES e keyFile PrideAndPrejudice
	# cat PrideAndPrejudice.enc
babies:
	java AES d keyFile PrideAndPrejudice.enc
	# cat PrideAndPrejudice.enc.dec
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