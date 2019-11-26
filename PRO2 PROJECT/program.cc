/**
 * @mainpage Laboratorio PRO2. Caso de estudio: Simulación del sistema de gestón de tareas

Queremos simular el sistema de gestión de tareas de una arquitectura mutiprocesador y multi-usuario tipo NUMA, donde cada procesador trabaja exclusivamente con su propia memoria y puede ejecutar más de un proceso simultáneamente.

En este programa se introducen las clases <em>Cluster</em>, <em>Cjt_Usuarios</em>, <em>Usuario</em>, <em>Procesador</em>  i <em>Proceso</em>.
*/

/** @file program.cc
    @brief Programa principal de la simulacion del Sistema NUMA.
*/

#include "Cluster.hh"

#ifndef NO_DIAGRAM
#include <string>
#include <iostream>
#endif
using namespace std;

/** @brief Programa principal de la simulacion del Sistema NUMA.
 */

int main() {
    Cluster c;
    string ins;
    cin >> ins;
    while(ins != "acabar"){
            if (ins == "configurar_cluster") {
                int nproc;
                cin >> nproc;
                c.configurar_cluster(nproc);
            }
            else if (ins == "poner_usuario") {
                string uid;
                cin >> uid;
                c.poner_usuario(uid);
            }
            else if (ins == "quitar_usuario") {
                string uid;
                cin >> uid;
                c.quitar_usuario(uid);
            }
            else if (ins == "poner_proceso_en_procesador") {
                int id;
                Proceso proc;
                cin >> id;
                proc.leer_proceso();
                c.poner_proceso_en_procesador(id, proc);
            }
            else if (ins == "quitar_proceso_de_procesador") {
                int id, pid;
                cin >> id >> pid;
                c.quitar_proceso_de_procesador(id, pid);
            }
            else if (ins == "enviar_proceso_a_usuario") {
                Proceso proc;
                proc.leer_proceso();
                c.enviar_proceso_a_usuario(proc);
            }
            else if (ins == "consultar_usuario") {
                string uid;
                cin >> uid;
                c.consultar_usuario(uid);
            }
            else if (ins == "consultar_procesador") {
                int id;
                cin >> id;
                c.consultar_procesador(id);
            }
            else if (ins == "avanzar_tiempo") {
                int tmp;
                cin >> tmp;
                c.avanzar_tiempo(tmp);
            }
            else if (ins  == "enviar_procesos_a_cluster") {
                int nprocesos;
                cin >> nprocesos;
                c.enviar_procesos_a_cluster(nprocesos);
            }
            cin >> ins;	
    }
}
