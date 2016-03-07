default:
	clear;
	javac *.java;
clean:
	rm -f *.class;
	rm -f *.out;
	rm -f *-steg.bmp;
babies:
	java Steganography -E inputImage.bmp description
	java Steganography -D inputImage.bmp-steg.bmp description.out