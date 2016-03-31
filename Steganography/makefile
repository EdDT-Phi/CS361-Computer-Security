default:
	clear;
	javac *.java;
clean:
	rm -f *.class;
	rm -f *.out*;
	rm -f *-steg.*;
turnin:
	zip jdt2725_et7226_program3.zip *
love:
	java Steganography -E overwerk.png nina.jpg
babies:
	java Steganography -D overwerk-steg.png nina.out.jpg
check:
	java Steganography -E overwerk.png PrideAndPrejudice
	java Steganography -D overwerk-steg.png PrideAndPrejudice.out
debug:
	java Steganography -E overwerk.png test
	java Steganography -D overwerk-steg.png test.out