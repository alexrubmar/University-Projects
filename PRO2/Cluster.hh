/** @file Cluster.hh
    @brief Especificación de la clase Cluster
*/

#ifndef _CLUSTER_HH_
#define _CLUSTER_HH_
#ifndef NO_DIAGRAM
#include <string>
#include <list>
#include <vector>
#include "BinTree.hh"
#endif
#include "Procesador.hh"
#include "Cjt_Usuarios.hh"
using namespace std;


/** @class Cluster
    @brief Representa un Cluster con arquitectura multiprocesador y multiusuario
 */

class Cluster {
    
private:
    
    /** @brief Representa la distribución en la que están organizados los procesadores */
    BinTree<int> arbol;
    /** @brief Representa el conjunto de procesadores ordenados por identificador */
    vector<Procesador> vproc;
    /** @brief Representa el conjunto de usuarios asociados al cluster */
    Cjt_Usuarios listUs;
    
public:
    
    /* Consutructora */
    
    /** @brief Creadora por defecto.
        \pre Cierto
        \post El resultado es un Cluster vacío.
    */
    Cluster();
    
    
    /* Destructora */
    
    /** @brief Destructora por defecto
        \pre Existe un cluster
        \post Destruye el cluster
     */
    ~Cluster();
    
    
    /* Consultoras */
    
    /** @brief Consultar datos del usuario
        \pre Cluster configurado
        \post Si existe usuario con el mismo identificador que el pasado por parámetro se escribe cuantos procesos pendientes tiene y el identificador del más antiguo, si no tiene procesos pendientes solo se escribe 0. Si no existe el usuario no se hace nada
     */
    void consultar_usuario(string uid);
    
    /** @brief Consultar datos de procesador
        \pre Cluster configurado
        \post Si no existe procesador con id no se hace nada, si existe escriben los datos de la memoria del procesador
     */
    void consultar_procesador(int id);
    
    
    /* Modificadoras */
    
    /** @brief Configura el cluster
        \pre nproc > 0
        \post El resultado es el Cluster con nproc procesadores distribuídos en forma de árbol y cada procesador con una cantidad de memoria determinada. En el caso de que ya hubiese estado configurado anteriormente los procesos activos desaparecen pero los pendientes se mantienen.
    */
    void configurar_cluster(int nproc);
    
    /** @brief Añade usuario nuevo a la lista de usuarios.
        \pre Cluster configurado
        \post Si ya existe usuario con identificador uid no se hace nada. Si no existe se añade usuario a listUs.
     */
    void poner_usuario(string uid);
    
    /** @brief Elimina usuario de la lista de usuarios.
        \pre Cluster configurado
        \post Si no existe usuario con identificador uid o al usuario con uid le quedan procesos pendientes, no se hace nada. En caso contrario, el usuario con uid se elimina.
     */
    void quitar_usuario(string uid);
    
    /** @brief Introducir un proceso en un procesador.
        \pre Cluster configurado. Porcesador con identificador id existe.
        \post Si el usuario propietario de proc no existe no se hace nada. Si existe y hay un hueco de memoria libre consecutiva en el procesador id dónde quepa proc pasa a ejecutarse en procesador id y la memoria que usa pasa a estar ocupada. En el caso de que existan varios huecos dónde proc quepa, se colocará en el hueco más ajustado y cercano a la posición inicial. Si proc no cabe pasa a ser el pendiente más reciente del usuario propietario.
     */
    void poner_proceso_en_procesador(int id, Proceso proc);
    
    /** @brief Eliminar un proceso de un procesador.
        \pre Cluster está configurado. Existe procesador con id.
        \post Si existe proceso con pid en procesador con id, la memoria que usa el proceso pasa a quedar libre y el proceso desaparece.
     */
    void quitar_proceso_de_procesador(int id, int pid);
    
    /** @brief Avanzar el tiempo transcurrido del sistema.
        \pre Cluster configurado.
        \post Transcurren tmp unidades de tiempo. Se eliminan todos los procesos activos que hayan acabado en ese intervalo de tiempo.
     */
    void avanzar_tiempo(int tmp);
    
    /** @brief Enviar un proceso al usuario.
        \pre Cluster configurado
        \post Si el usuario con identificador igual al del propietario del proceso existe el proceso pasa a ser el proceso pendiente más reciente del usuario adjudicado. En caso contrario no hace nada
     */
    void enviar_proceso_a_usuario(Proceso proc);
    
    /** @brief Enviar n procesos al cluster.
        \pre Cluster configurado. 
        \post Los procesos que cabían en algún procesador han sido introducidos al Cluster, los que no han sido devueltos al usuario propietario. Si no existían usuarios con procesos pendientes no hace nada.
     */
    void enviar_procesos_a_cluster(int nprocesos);
    
private:
    
    /** @brief Función de búsqueda del procesador adecuado para enviar el proceso
        \pre Cierto
        \post Devuelve el identificador del procesador adecuado para colocar el proceso
     */
    int elegir_procesador(const BinTree<int>& a, int mem_proc);
    
    /** @brief Función inmersiva para leer la distribución en forma de arbol de los procesadores
        \pre Cierto
        \post Se han distribuido los identificadores de los n procesadores.
     */
    void leer_arbol(BinTree<int>& a, int n);
    
};
#endif
