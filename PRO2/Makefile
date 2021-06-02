OPTIONS = -D_JUDGE_ -D_GLIBCXX_DEBUG -O2 -Wall -Wextra -Werror -Wno-uninitialized -Wno-sign-compare -std=c++11

program.exe: program.o Cluster.o Procesador.o Cjt_Usuarios.o Usuario.o Proceso.o
	g++ -o program.exe *.o
	
program.o: program.cc Cluster.hh
	g++ -c program.cc $(OPTIONS)
	
Cluster.o: Cluster.cc Cluster.hh BinTree.hh Procesador.hh Cjt_Usuarios.hh
	g++ -c Cluster.cc $(OPTIONS)
	
Procesador.o: Procesador.cc Procesador.hh Proceso.hh
	g++ -c Procesador.cc $(OPTIONS)
	
Cjt_Usuarios.o: Cjt_Usuarios.cc Cjt_Usuarios.hh Usuario.hh
	g++ -c Cjt_Usuarios.cc $(OPTIONS)
	
Usuario.o: Usuario.cc Usuario.hh Proceso.hh
	g++ -c Usuario.cc $(OPTIONS)
	
Proceso.o: Proceso.cc Proceso.hh
	g++ -c Proceso.cc $(OPTIONS)
	
practica.tar: program.cc Cluster.cc Cluster.hh Procesador.cc Procesador.hh Cjt_Usuarios.cc Cjt_Usuarios.hh Usuario.cc Usuario.hh Proceso.cc Proceso.hh
	tar -cvf practica.tar program.cc Cluster.cc Cluster.hh Procesador.cc Procesador.hh Cjt_Usuarios.cc Cjt_Usuarios.hh Usuario.cc Usuario.hh Proceso.cc Proceso.hh Makefile html.zip
	
clean:
	rm *.o
	rm *.exe
