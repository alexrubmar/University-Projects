/** @file Proceso.cc
    @brief Código de la clase Proceso
*/

#include "Proceso.hh"
#include <iostream>

Proceso::Proceso() {}

Proceso::~Proceso() {}

void Proceso::leer_proceso() {
    cin >> propuid >> pid >> umem >> tej;
}

int Proceso::consultar_umem() {
    return umem;
}

int Proceso::consultar_tej() {
    return tej;
}

int Proceso::consultar_pid() {
    return pid;
}

string Proceso::consultar_propuid() {
    return propuid;
}

void Proceso::restar_tiempo(int tmp) {
    tej = tej - tmp;
}


