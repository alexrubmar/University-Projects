/** @file Cluster.cc
    @brief Código de la clase Cluster
 */

#include "Cluster.hh"

Cluster::Cluster() {}

Cluster::~Cluster() {}

void Cluster::configurar_cluster(int nproc) {
    while (not vproc.empty()) vproc.pop_back();
    leer_arbol(arbol, nproc);
    for (int i = 0; i < nproc; ++i) {
        Procesador cpu;
        cpu.leer_memoria();
        cpu.leer_id(i+1);
        vproc.push_back(cpu);
    }
}

void Cluster::poner_usuario(string uid) {
    listUs.poner_us(uid);
}

void Cluster::quitar_usuario(string uid) {
    listUs.quitar_us(uid);
}

void Cluster::poner_proceso_en_procesador(int id, Proceso proc) {
    if (id <= vproc.size() and id > 0) {
        string uid = proc.consultar_propuid();
        if (listUs.existe_usuario(uid)) {
            bool cabe;
            cabe = vproc[id-1].proc_en_procesador(proc);
            if (not cabe) {
                listUs.enviar_proc_a_us(proc);
            }
        }
    }
}

void Cluster::quitar_proceso_de_procesador(int id, int pid) {
    if (id <= vproc.size() and id > 0) {
        vproc[id-1].quitar_proceso(pid);
    }
}

void Cluster::enviar_proceso_a_usuario(Proceso proc) {
    string uid = proc.consultar_propuid();
    if (listUs.existe_usuario(uid)) {
        listUs.enviar_proc_a_us(proc);
    }
}

void Cluster::consultar_usuario(string uid) {
    cout << "Usuario " << uid << endl;
    listUs.consultar_u(uid);
}

void Cluster::avanzar_tiempo(int tmp) {
    for (int i = 0; i < vproc.size(); ++i) {
        vproc[i].avanzar_tmp(tmp);
    }
}

void Cluster::consultar_procesador(int id) {
    if (id <= vproc.size() and id > 0) {
        cout << "Procesador " << id << endl;
        vproc[id-1].escribir_datos_memoria();
    }
}

void Cluster::enviar_procesos_a_cluster(int nprocesos) {
    if (listUs.existen_usuarios()) {
        queue<Proceso> rechazados;
        bool finish = false;
        int i = 0;
        while (i < nprocesos and not finish) {
            if (listUs.existen_procesos_pendientes()) {
                Proceso proc = listUs.elegir_proceso_pendiente();
                int id = elegir_procesador(arbol, proc.consultar_umem());
                if (id == 0) rechazados.push(proc);
                else {
                    vproc[id-1].proc_en_procesador(proc);
                    ++i;
                }
            }
            else finish = true;
        }
        while (not rechazados.empty()) {
            listUs.enviar_proc_a_us(rechazados.front());
            rechazados.pop();
        }
    }
}

void Cluster::leer_arbol(BinTree<int>& a, int n) {
    int x;
    cin >> x;
    if (x != 0 and x <= n){
        BinTree<int> l;
        leer_arbol(l,n);
        BinTree<int> r;
        leer_arbol(r,n);
        a=BinTree<int>(x,l,r);
    }
}

int Cluster::elegir_procesador(const BinTree<int>& a, int mem_proc) {
    if (not a.empty()) {
        int mem_max = 0, id = 0;
        queue<BinTree<int> > colaProcesadores;
        colaProcesadores.push(a);
        while(not colaProcesadores.empty()){
            BinTree<int> aux(colaProcesadores.front());
            int memaux = vproc[aux.value()-1].consultar_memfree();
            bool cabe = vproc[aux.value()-1].cabe(mem_proc);
            if(cabe and memaux > mem_max){
                mem_max = memaux;
                id = aux.value();
            }
            if (not aux.left().empty()) colaProcesadores.push(aux.left());
            if (not aux.right().empty()) colaProcesadores.push(aux.right());
            colaProcesadores.pop();
        }
        return id;
    }
    return 0;
}

