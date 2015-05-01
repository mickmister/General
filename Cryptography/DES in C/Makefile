# Michael Kochell

all:	des

des:	main.o permutation.o keys.o sbox.o
	gcc -o des main.o permutation.o keys.o sbox.o -g -O3

main.o: main.c permutation.h keys.h sbox.h
	gcc -c main.c -g -O3

permutation.o: permutation.c permutation.h
	gcc -c permutation.c -g -O3

keys.o: keys.c keys.h
	gcc -c keys.c -g -O3

sbox.o: sbox.c sbox.h permutation.h
	gcc -c sbox.c -g -O3

clean:
	rm -f *.o des
