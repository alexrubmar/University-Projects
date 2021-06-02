/** @file Cjt_Usuarios.hh
    @brief Especificación de la clase Cjt_Usuarios
 */

#ifndef _CONJUSUARIOS_HH_
#define _CONJUSUARIOS_HH_

#include "Usuario.hh"
#ifndef NO_DIAGRAM
#include <list>
#include <iostream>
#endif

/** @class Cjt_Usuarios
    @brief Representación del conjunto de usuarios asociados al Cluster
 */

class Cjt_Usuarios {

private:
    /** @brief Lista de usuarios asociados al Cluster */
    list<Usuario> vuser;
    
public:
    
    /* Constructora */
    
    /** @brief Constructora por defecto
        \pre Cierto
        \post El resultado es un Cjt_Usuarios vacío
     */
    Cjt_Usuarios();
    
    
    /* Destructora */
    
    /** @brief Destructora por defecto
        \pre Existe un Cjt_Usuarios
        \post Destruye el  Cjt_Usuarios
     */
    ~Cjt_Usuarios();
    
    
    /* Consultoras */
    
    /** @brief Consultar datos del usuario
        \pre Cierto
        \post Si no existe usuario con uid no se hace nada, en caso contrario se escribe cuantos procesos tiene y el identificador del más antiguo. Si no tiene procesos pendientes solo se escribe 0.
     */
    void consultar_u(string uid);
    
    /** @brief Consultar si el usuario con el identificador pasado por parámetro existe en el conjunto
        \pre Cierto
        \post Si existe un usuario con identificador igual al parámetro uid devuelve 'true', si no existe devuelve 'false'
     */
    bool existe_usuario(string uid);
    
    /** @brief Consultar si existe al menos un usuario en el conjunto
        \pre Cierto
        \post Si vuser está vacío devuelve 'false', en caso contrario devuelve 'true'
     */
    bool existen_usuarios();
    
    /** @brief Consulta si existe un usuario con al menos un proceso pendientes
        \pre Existe al menos un usuario 
        \post Si se encuentra un usuario con al menos un proceso pendiente devuelve 'true', en caso contrario devuelve 'false'
     */
    bool existen_procesos_pendientes();
    
    /** @brief Devuelve el proceso pendiente más antiguo del usuario con menor identificador que tenga más procesos pendientes
        \pre Existe al menos un usuario con al menos un proceso pendiente
        \post Devuelve el proceso pendiente más antiguo del usuario con menor identificador que tenga más procesos pendientes
     */
    Proceso elegir_proceso_pendiente();
    
    
    /* Modificadoras */
    
    /** @brief Añade usuario nuevo a la lista de usuarios.
        \pre Cierto
        \post Si ya existe usuario con identificador uid no se hace nada. Si no existe se añade usuario a listUs.
     */
    void poner_us(string uid);
    
    /** @brief Quitar usuario de la lista de usuarios
        \pre Cierto
        \post Si no existe usuario con identificador igual al parámetro uid no se hace nada, en caso contrario se el elimina dicho usuario de la lista
     */
    void quitar_us(string uid);
    
    /** @brief Envia el proceso pasado por parametro al usuario propietario del proceso
        \pre Usuario propietario del proceso existe
        \post El proceso pasa a ser el proceso pendiente más reciente del usuario
     */
    void enviar_proc_a_us(Proceso proc);
    
private:
    
    /** @brief Devuelve el iterador de la lista de usuarios que apunta al usuario con identificador igual al pasado por parámetro
        \pre Existe al menos un usuario
        \post Si encuentra usuario con identificador igual al parámetro uid devuelve el iterador que apunta al usuario en cuestión, en caso contrario devuelve la posición .end()
     */
    list<Usuario>::iterator encontrar_usuario_con_id(string uid);
    
};
#endif

