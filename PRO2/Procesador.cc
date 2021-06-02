/** @file Procesador.cc
    @brief Código para la clase Procesador
 */

#include "Procesador.hh"

Procesador::Procesador() {}

Procesador::~Procesador() {}

void Procesador::leer_memoria() {
    cin >> memsize;
    memfree = memsize;
}

void Procesador::leer_id(int n) {
    id = n;
}

bool Procesador::proc_en_procesador(Proceso proc) {
    int memproc = proc.consultar_umem();
    if (memoria.empty() and memproc <= memsize) {
        memoria.insert(pair<int, Proceso> (0, proc));
        memfree -= proc.consultar_umem();
        return true;
    }
    else if (not memoria.empty()) {
        int posini, posfinal;
        int mem_min;
        bool encontrado = false;
        map<int, Proceso>::iterator it = memoria.begin();
        int pos_elegida;
        posini = it->first;
        if (posini > 0 and memproc <= posini) {
            encontrado = true;
            mem_min = posini;
            pos_elegida = 0;
        }
        posfinal = posini + (it->second).consultar_umem();
        ++it;
        while (it != memoria.end()) {
            posini = it->first;
            if (memproc <= (posini-posfinal) and (not encontrado or mem_min > (posini-posfinal))) {
                encontrado = true;
                mem_min = posini-posfinal;
                pos_elegida = posfinal;
            }
            posfinal = posini + (it->second).consultar_umem();
            ++it;
        }
        if (posfinal < memsize) {
            if (memproc <= (memsize-posfinal) and (not encontrado or mem_min > (memsize-posfinal))) {
                encontrado = true;
                mem_min = memsize - posfinal;
                pos_elegida = posfinal;
            }
        }
        if (encontrado) {
            memoria.insert(pair<int, Proceso> (pos_elegida, proc));
            memfree -= proc.consultar_umem();
            return true;
        }
        return false;
    }
    return false;
}

void Procesador::quitar_proceso(int pid) {
    bool encontrado = false;
    map<int, Proceso>::iterator it = memoria.begin();
    while (not encontrado and it != memoria.end()) {
        if (pid == (it->second).consultar_pid()) {
            memfree += (it->second).consultar_umem();
            it = memoria.erase(it);
            encontrado = true;
        }
        if (not encontrado) ++it;
    }
}

void Procesador::escribir_datos_memoria() {
    if (not memoria.empty()) {
        map<int, Proceso>::iterator it;
        for (it = memoria.begin(); it != memoria.end(); ++it) {
            cout << "  " << it->first << " " << (it->second).consultar_propuid() << " " << (it->second).consultar_pid() << " " << (it->second).consultar_umem() << " " << (it->second).consultar_tej() << endl;
        }
    }
}

void Procesador::avanzar_tmp(int tmp) {
    if (not memoria.empty()) {
        map<int, Proceso>::iterator it = memoria.begin();
        bool eliminado = false;
        while (it != memoria.end()) {
            (it->second).restar_tiempo(tmp);
            if ((it->second).consultar_tej() <= 0) {
                memfree += (it->second).consultar_umem();
                it = memoria.erase(it);
                eliminado = true;
            }
            if (not eliminado) ++it;
            else eliminado = false;
        }
    }
}

bool Procesador::cabe(int mem_proc) {
    if (memoria.empty() and memsize >= mem_proc) {
        return true;
    }
    else if (not memoria.empty()) {
        int posini, posfinal;
        map<int, Proceso>::iterator it = memoria.begin();
        posini = it->first;
        if (posini > 0 and mem_proc <= posini) return true;
        posfinal = posini + (it->second).consultar_umem();
        ++it;
        while (it != memoria.end()) {
            posini = it->first;
            if (mem_proc <= (posini-posfinal)) return true;
            posfinal = posini + (it->second).consultar_umem();
            ++it;
        }
        if (posfinal < memsize) {
            if (mem_proc <= (memsize - posfinal)) return true;
        }
        return false;
    }
    return false;
}

int Procesador::consultar_memfree() {
    return memfree;
}
