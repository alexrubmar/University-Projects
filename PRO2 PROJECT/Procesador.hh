/** @file Procesador.hh
    @brief Especificación de la clase Procesador
 */

#ifndef _PROCESADOR_HH_
#define _PROCESASOR_HH_
#include "Proceso.hh"
#ifndef NO_DIAGRAM
#include <map>
#include <iostream>
using namespace std;
#endif


/** @class Procesador
    @brief Representa un procesador del Cluster y su memoria asociada
 */

class Procesador {
    
private:
    
    /** @brief Representa el identificador del procesador */
    int id;
    /** @brief Representa la capacidad de memoria que contiene el procesador */
    int memsize;
    /** @brief Representa la cantidad de unidades de memoria libre que contiene el procesador */
    int memfree;
    /** @brief Representa la memoria, dónde en cada elemento del map hay un proceso que contiene la memoria y su posición inicial */
    map<int, Proceso> memoria;
    
public:
    
    /* Constructorea */
    
    /** @brief Crear procesador vacío.
        \pre cierto
        \post El resultado es un procesador vacío.
     */
    Procesador();
    
    
    /* Destructora */
    
    /** @brief Destructora por defecto
        \pre Existe un procesador
        \post Destruye el procesador
     */
    ~Procesador();
    
    
    /* Consultoras */
    
    /** @brief Consultar la cantidad de memoria libre del procesador
        \pre Cierto
        \post Devuelve el numero de unidades de memoria libres
     */
    int consultar_memfree();
        
    /** @brief Escribe la representación de la memoria del procesador
        \pre Cierto
        \post Escribe la posición inicial y los datos del proceso de cada uno de los procesos en orden de posición ascendente
     */
    void escribir_datos_memoria();
    
    /** @brief Devuelve booleano de si la cantidad de memoria pasada por parámetro cabe en el procesador
        \pre mem_proc > 0
        \post Si en la memoria hay espacio consecutivo mayor o igual a mem_proc devuelve 'true', en caso contrario devuelve 'false'
     */
    bool cabe(int mem_proc);
    
    
    /* Modificadoras */
    
    /** @brief Leer la capacidad de memoria del procesador
        \pre Cierto
        \post Se ha leído la memoria y se le ha asignado a memsize y memfree
     */
    void leer_memoria();
    
    /** @brief Leer el identificador
        \pre n > 0
        \post Se le ha asignado a id el entero n pasado por parámetro
     */
    void leer_id(int n);
    
    /** @brief Introduce el proceso pasado por parámetro en la memoria del procesador
        \pre Propietario de proc existe
        \post Si el proceso cabe en memoria, ha sido colocado en la posición correspondiente y devuelve 'true'. En caso contrario devuelve 'false'
     */
    bool proc_en_procesador(Proceso proc);
    
    /** @brief Elimina el proceso con el identificador pasado por prámetro de la memoria del procesador
        \pre Cierto
        \post Si existía un proceso con el identificador pid, ha sido eliminado. En caso contrario no se hace nada
     */
    void quitar_proceso(int pid);

    
    /** @brief Avanza el tiempo transcurrido del procesador
        \pre tmp > 0
        \post Si no tiene procesos activos no hace nada. En caso contrario, resta el parámetro tmp al tiempo de ejecución restante de cada proceso. El proceso que termina su tiempo de ejecución se elimina del procesador
     */
    void avanzar_tmp(int tmp);
    
};
#endif
