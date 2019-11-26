/** @file Usuario.cc
    @brief Código para la clase Usuario
 */

#include "Usuario.hh"

Usuario::Usuario() {}

Usuario::~Usuario() {}

void Usuario::leer_id_usuario(string id) {
    uid = id;
}

bool Usuario::sin_procesos_pendientes() {
    return ProcsPend.empty();
}

string Usuario::consultar_uid() {
    return uid;
}

void Usuario::cons_u() {
    if (ProcsPend.empty()) cout << "  0" << endl;
    else {
        cout << "  " << ProcsPend.size() << " " << ProcsPend.front().consultar_pid() << endl;
    }
}

int Usuario::consultar_num_proc_pend() {
    return ProcsPend.size();
}

Proceso Usuario::consultar_proc_mas_antiguo() {
    return ProcsPend.front();
}

void Usuario::proc_a_us(Proceso proc) {
    ProcsPend.push(proc);
}

void Usuario::eliminar_proceso_mas_antiguo() {
    ProcsPend.pop();
}
