default:
	clear;
	javac *.java;
clean:
	rm -f *.class;
	rm -f *out*;
	rm -f *-steg.bmp;
babies:
	java Steganography -E inputImage.bmp description
	java Steganography -D inputImage-steg.bmp description.out
love:
	java Steganography -E inputImage.bmp nina2.jpg
	java Steganography -D inputImage-steg.bmp nina2.out.jpg