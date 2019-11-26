/** @file Proceso.hh
    @brief Especificación de la clase Proceso
*/

#ifndef _PROCESO_HH_
#define _PROCESO_HH_
#ifndef NO_DIAGRAM 
#include <string>
using namespace std;
#endif


/** @class Proceso
    @brief Representa un proceso
 */

class Proceso {
    
private:
    /** @brief Indica el identificador del proceso */
    int pid;
    /** @brief Indica la cantidad de memoria que ocupa */
    int umem;
    /** @brief Indica el tiempo de ejecución predefinido del proceso o el que le queda */
    int tej;
    /** @brief Indica el identificador del usuario propietario del proceso */
    string propuid;

public:
    
    /* Construtora */
    
    /** @brief Crear un proceso vacío
        \pre cierto
        \post El resultado es un proceso vacío.
     */
    Proceso();
    
    
    /* Destructora */
    
    /** @brief Destructora por defecto
        \pre Existe un proceso
        \post Destruye el proceso
     */
    ~Proceso();
    
    
    /* Consultoras */
    
    /** @brief Consultar la cantidad de memoria que ocupa el proceso
        \pre Proceso no vacío
        \post Devuelve la cantidad de memoria que ocupa el proceso
     */
    int consultar_umem();
    
    /** @brief Consultar el tiempo de ejecución restante del proceso
        \pre Proceso no vacío
        \post Devuelve el tiempo de ejecución restante del proceso
     */
    int consultar_tej();
    
    /** @brief Consultar el identificador del proceso
        \pre Proceso no vacío
        \post Devuelve el identificador del proceso
     */
    int consultar_pid();
    
    /** @brief Consultar el identificador del usuario propietario
        \pre Proceso no vacío
        \post Devuelve el identificador del usuario propietario
     */
    string consultar_propuid();
    
    
    /* Modificadoras */
    
    /** @brief Leer los datos del proceso
        \pre Cierto
        \post Datos del proceso leídos y asignados
     */
    void leer_proceso();
    
    /** @brief Restar el tiempo pasado por parámetro al tiempo de ejecución restante del proceso
        \pre Proceso no vacío
        \post El tiempo de ejecución resultante es el tiempo de ejecución previo menos el parámetro tmp
     */
    void restar_tiempo(int tmp);
};
#endif
