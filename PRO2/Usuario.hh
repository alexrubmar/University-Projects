/** @file Usuario.hh
    @brief Especificación de la clase Usuario
 */

#ifndef _USUARIO_HH_
#define _USUARIO_HH_

#include "Proceso.hh"
#ifndef NO_DIAGRAM
#include <queue>
#include <string>
#include <iostream>
using namespace std;
#endif


/** @class Usuario
    @brief Representa un usuario asociado al cluster
 */

class Usuario {
    
private:
    /** @brief Indica el identificador del usuario */
    string uid;
    /** @brief Representa la cola de procesos pendientes del usuario */
    queue<Proceso> ProcsPend;
    
public:
    
    /* Constructora */
    
    /** @brief Crear usuario vacío
        \pre cierto
        \post El resultado es un usuario vacío.
     */
    Usuario();
    
    
    /* Destructora */
    
    /** @brief Destructora por defecto
        \pre Existe un usuario
        \post Destruye el usuario
     */
    ~Usuario();
    
    
    /* Consultoras */
    
    /** @brief Consultar identificador del usuario
        \pre Cierto
        \post Devuelve el identificador del usuario
     */
    string consultar_uid();
    
    /** @brief Consultar si el usuario no tiene procesos pendientes
        \pre Cierto
        \post Si la cola ProcsPend está vacía devuelve 'true', en caso contrario devuelve 'false'
     */
    bool sin_procesos_pendientes();
    
    /** @brief Consultar el numero de procesos pendientes que tiene el usuario
        \pre Cierto
        \post Devuelve el numero de procesos pendientes del usuario
     */
    int consultar_num_proc_pend();
    
    /** @brief Consultar el proceso pendiente más antiguo del usuario
        \pre El usuario tiene al menos un proceso pendiente
        \post Devuelve el proceso más antiguo de la cola de procesos pendientes
     */
    Proceso consultar_proc_mas_antiguo();
    
    /** @brief Consultar datos del usuario
        \pre Cierto
        \post Se escribe cuantos procesadores tiene y el identificador del más antiguo. Si no tiene procesos pendiente solo se escribe 0.
     */
    void cons_u();
    
    
    /* Modificadoras */
    
    /** @brief Asignar el identificador del usuario con el valor del parámetro
        \pre Cierto
        \post El identificador del usuario tiene el valor del pasado por parámetro
     */
    void leer_id_usuario(string id);
    
    /** @brief Poner el proceso pasado pasado por parámetro como el proceso pendiente más reciente del usuario
        \pre Cierto
        \post El proceso pasa a ser el más reciende de la cola de procesos pendientes
     */
    void proc_a_us(Proceso proc);
    
    /** @brief Elimina el proceso más antiguo de la cola de procesos pendintes
        \pre Cola de procesos pendientes no vacía
        \post El proceso más antiguo de los procesos pendientes queda eliminado 
     */
    void eliminar_proceso_mas_antiguo();
    
};
#endif
