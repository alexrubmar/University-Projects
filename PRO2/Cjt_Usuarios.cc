/** @file Cjt_Usuarios.cc
    @brief Código para la clase Cjt_Usuarios
 */

#include "Cjt_Usuarios.hh"

Cjt_Usuarios::Cjt_Usuarios() {}

Cjt_Usuarios::~Cjt_Usuarios() {}

void Cjt_Usuarios::poner_us(string uid) {
    if (vuser.empty() or not existe_usuario(uid)) {
        Usuario user;
        user.leer_id_usuario(uid);
        vuser.push_back(user);
    }
}

void Cjt_Usuarios::quitar_us(string uid) {
    if (not vuser.empty() and existe_usuario(uid)) {
        list<Usuario>::iterator it;
        it = encontrar_usuario_con_id(uid);
        if ((*it).sin_procesos_pendientes()) {
            it = vuser.erase(it);
        }
    }
}

void Cjt_Usuarios::enviar_proc_a_us(Proceso proc) {
    string uid = proc.consultar_propuid();
    list<Usuario>::iterator it = encontrar_usuario_con_id(uid);
    (*it).proc_a_us(proc);
}

void Cjt_Usuarios::consultar_u(string uid) {
    if (existe_usuario(uid)) {
        list<Usuario>::iterator it = encontrar_usuario_con_id(uid);
        (*it).cons_u();
    }
}

Proceso Cjt_Usuarios::elegir_proceso_pendiente() {
    list<Usuario>::iterator it = vuser.begin();
    list<Usuario>::iterator escogido = it;
    ++it;
    while (it != vuser.end()) {
        if ((*it).consultar_num_proc_pend() > (*escogido).consultar_num_proc_pend()) {
            escogido = it;
        }
        else if ((*it).consultar_num_proc_pend() == (*escogido).consultar_num_proc_pend()) {
            if ((*it).consultar_uid() < (*escogido).consultar_uid()) {
                escogido = it;
            }
        }
        ++it;
    }
    Proceso proc;
    proc = (*escogido).consultar_proc_mas_antiguo();
    (*escogido).eliminar_proceso_mas_antiguo();
    return proc;
}

bool Cjt_Usuarios::existe_usuario(string uid) {
    list<Usuario>::iterator it;
    for (it = vuser.begin(); it != vuser.end(); ++it) {
        if ((*it).consultar_uid() == uid) return true;
    }
    return false;
}

list<Usuario>::iterator Cjt_Usuarios::encontrar_usuario_con_id(string uid) {
    list<Usuario>::iterator it;
    for (it = vuser.begin(); it != vuser.end(); ++it) {
        if ((*it).consultar_uid() == uid) return it;
    }
    return vuser.end();
}

bool Cjt_Usuarios::existen_procesos_pendientes() {
    list<Usuario>::iterator it;
    for (it = vuser.begin(); it != vuser.end(); ++it) {
        if (not (*it).sin_procesos_pendientes()) return true;
    }
    return false;
}

bool Cjt_Usuarios::existen_usuarios() {
    return not vuser.empty();
}

