#include "Player.hh"
#include <vector>
#include <queue>
#include <list>
#include <set>
#include <map>
#include <unordered_map>
#include <algorithm>


/**
 * Write the name of your player and save this file
 * with the same name and .cc extension.
 */
#define PLAYER_NAME alex.rubiano


struct PLAYER_NAME : public Player {

  /**
   * Factory: returns a new instance of this class.
   * Do not modify this function.
   */
  static Player* factory () {
    return new PLAYER_NAME;
  }

  /**
   * Types and attributes for your player can be defined here.
   */
  
 
    typedef queue<Pos> ColaPos;
    typedef vector<Pos> VecPos;
    typedef map<Pos,Pos> MapPosPos;
    typedef map<Pos,int> MapPosInt;
    typedef map<Pos, bool> MapPosBool;
    typedef vector<bool> VecBool;
    typedef vector<VecBool> MatrixBool;
    typedef vector<int> VecInt;
    typedef map<int,int> MapIntInt;
    
    //Atributos
    MapPosInt pos_reservadas;
    MapPosInt::iterator it;
    
    

  /**
   * Play method, invoked once per each round.
   */
  

  
  bool puede_mover_sold(Pos pos) {
        Cell c = cell(pos);
        CellType tipo = c.type;
        if (c.id != -1 and ant(c.id).player != me() and ant(c.id).type == Worker) return true;
        else if (c.id == -1 and tipo != Water) return true;
        return false;
  }
  
  bool puede_mover_tr_sin_bonus(Pos pos) {
      Cell c = cell(pos);
      CellType tipo = c.type;
      if (c.id == -1 and tipo != Water) return true;
      return false;
  }
  
  bool puede_mover_tr_con_bonus(Pos pos) {
      Cell c = cell(pos);
      CellType tipo = c.type;
      if (c.id == -1 and tipo != Water) return true;
      if (c.id != -1 and ant(c.id).player == me() and ant(c.id).type == Queen) return true;
      return false;
  }
  
  bool puede_mover_R(Pos pos) {
      Cell c = cell(pos);
      CellType tipo = c.type;
      if (tipo != Water) {
          if (c.id == -1) return true;
          else if (ant(c.id).player != me() and ant(c.id).type != Queen) return true;
      }
      return false;
  }
  
  void vecinos(Pos pos, VecPos& pos_disp, Ant hormiga) {
        for (int d = 0; d != 4; ++d) {
            Dir dir = Dir(d);
            Pos p = pos + dir;
            if (pos_ok(p) and hormiga.type == Soldier and puede_mover_sold(p)) pos_disp.push_back(p);
            else if (pos_ok(p) and hormiga.type == Worker and hormiga.bonus == None and puede_mover_tr_sin_bonus(p)) pos_disp.push_back(p);
            else if (pos_ok(p) and hormiga.type == Worker and hormiga.bonus != None and puede_mover_tr_con_bonus(p)) pos_disp.push_back(p);
            else if (pos_ok(p) and hormiga.type == Queen and puede_mover_R(p)) pos_disp.push_back(p);
            
        }
    }
    
    void puede_moverse(Pos pos, VecPos& pos_disp, Ant hormiga) {
        for (int d = 0; d != 4; ++d) {
            Dir dir = Dir(d);
            Pos p = pos + dir;
            if (pos_ok(p) and hormiga.type == Worker and puede_mover_tr_sin_bonus(p)) pos_disp.push_back(p);
            
        }
    }
  
  inline bool hay_trabajador(Cell c) {
      int id = c.id;
      if (id != -1) {
          return ant(id).player != me() and ant(id).type == Worker;
      }
      return false;
      _unreachable();
  }
  
  bool hay_bonus(Cell c) {
      int bonus = c.bonus;
      if (bonus != None) return true;
      return false;
  }
  
  bool hay_reina(Pos p) {
      VecInt reinas = queens(me());
      Ant reina = ant(reinas[0]);
      Pos pos_reina = reina.pos;
      
      if (pos_reina == p) return true;
      return false;
  }
  
  Dir bfs_soldado (Pos pos) {
        
        Ant soldado = ant(cell(pos).id); //Cambiar esto
        MapPosPos padres;
        ColaPos cola;
        MatrixBool visitado(board_rows(), VecBool(board_cols(), false));
        visitado[pos.i][pos.j] = true;
        cola.push(pos);
        
        bool encontrado = false;
        Pos p;
        
        while (not cola.empty() and not encontrado) {
            p = cola.front();
            Cell c = cell(p);
            if(hay_trabajador(c)) encontrado = true;
            else {
                cola.pop();
                VecPos pos_disp;
                vecinos(p, pos_disp, soldado);
                int nvec = pos_disp.size();
                int i = 0;
                while (i < nvec) {
                    Pos n = pos_disp[i];
                    if (not visitado[n.i][n.j]) {
                        cola.push(n);
                        padres[n] = p;
                        visitado[n.i][n.j] = true;
                    }
                    ++i;
                }
            }
        }
        
        while (p != pos and padres[p] != pos) p = padres[p];
        if (p == pos) return Dir(None);
        
        int d = 0;
        while (d < 4) {
            Dir dir = Dir(d);
            Pos npos = pos + dir;
            if (npos == p) {
                return dir;
            }
            ++d;
        }
        
        _unreachable();
        
  }
  
   Dir bfs_trabajadora(Pos pos, bool tiene_bonus) {
        
        Ant trabajadora = ant(cell(pos).id);
        MapPosPos padres;
        ColaPos cola;
        MatrixBool visitado(board_rows(), VecBool(board_cols(), false));
        visitado[pos.i][pos.j] = true;
        cola.push(pos);
        
        bool encontrado = false;
        Pos p;
        
        while (not cola.empty() and not encontrado) {
            p = cola.front();
            Cell c = cell(p);
            if(not tiene_bonus and hay_bonus(c) and not comida_de_reina(pos)) encontrado = true;
            else if (tiene_bonus and hay_reina(p)) encontrado = true;
            else {
                cola.pop();
                VecPos pos_disp;
                vecinos(p, pos_disp, trabajadora);
                int nvec = pos_disp.size();
                int i = 0;
                while (i < nvec) {
                    Pos n = pos_disp[i];
                    if (not visitado[n.i][n.j]) {
                        cola.push(n);
                        padres[n] = p;
                        visitado[n.i][n.j] = true;
                    }
                    ++i;
                }
            }
        }
        
        while (p != pos and padres[p] != pos) p = padres[p];
        
        if (p == pos) return Dir(None);
        
        int d = 0;
        while (d < 4) {
            Dir dir = Dir(d);
            Pos npos = pos + dir;
            if (npos == p) {
                return dir;
            }
            ++d;
        }
        
        _unreachable();
        
  }
  
    Dir bfs_reina(Pos pos) {
        
        
        Ant reina = ant(cell(pos).id);
        MapPosPos padres;
        ColaPos cola;
        MatrixBool visitado(board_rows(), VecBool(board_cols(), false));
        visitado[pos.i][pos.j] = true;
        cola.push(pos);
        
        bool encontrado = false;
        Pos p;
        
        while (not cola.empty() and not encontrado) {
            p = cola.front();
            Cell c = cell(p);
            if(hay_bonus(c)) encontrado = true;
            else {
                cola.pop();
                VecPos pos_disp;
                vecinos(p, pos_disp, reina);
                int nvec = pos_disp.size();
                int i = 0;
                while (i < nvec) {
                    Pos n = pos_disp[i];
                    if (not visitado[n.i][n.j]) {
                        cola.push(n);
                        padres[n] = p;
                        visitado[n.i][n.j] = true;
                    }
                    ++i;
                }
            }
        }
        
        while (p != pos and padres[p] != pos) p = padres[p];
        
        if (p == pos) return Dir(None);
        
        int d = 0;
        while (d < 4) {
            Dir dir = Dir(d);
            Pos npos = pos + dir;
            if (npos == p) {
                return dir;
            }
            ++d;
        }
        
        _unreachable();
        
  }
  
  
  
  bool reina_cerca(Pos pos) {
      for (int d = 0; d < 4; ++d) {
          Dir dir = Dir(d);
          Pos p = pos + dir;
          if (hay_reina(p)) return true;
      }
      return false;
  }
  
  bool comida_de_reina(Pos pos) {
      for (int i = pos.i - 3; i <= pos.i + 3; ++i) {
          for (int j = pos.j - 3; j <= pos.j + 3; ++j) {
              if (pos_ok(i,j)) {
                Cell c = cell(i,j);
                if (c.id != -1 and ant(c.id).player == me() and ant(c.id).type == Queen) return true;
              }
          }
      }
      return false;
  }
  
  void mover_trabajadora(int id) {
        Ant trabajadora = ant(id);
        bool tiene_bonus = (trabajadora.bonus != None);
        Cell c = cell(trabajadora.pos);
        if (not tiene_bonus and c.bonus != None and not comida_de_reina(trabajadora.pos)) {
            take(id);
            return;
        }
        if (tiene_bonus and reina_cerca(trabajadora.pos) and c.bonus == None) {
            leave(id);
            return;
        }
        
        Dir dirTr = bfs_trabajadora(trabajadora.pos, trabajadora.bonus != None);
        
        it = pos_reservadas.find(trabajadora.pos + Dir(dirTr));
        if (it == pos_reservadas.end()) {
            pos_reservadas[trabajadora.pos+Dir(dirTr)] = id;
            
            VecPos pos_disp;
            puede_moverse(trabajadora.pos, pos_disp, trabajadora);
            Pos pos_final = trabajadora.pos + dirTr;
            int npos = pos_disp.size();
            for (int i = 0; i < npos; ++i) {
                if (pos_final == pos_disp[i]) {
                    move(id, dirTr);
                    return;
                }
            }
        }
  }
  
  void mover_soldado(int id) {
        Ant soldado = ant(id);
        Dir dirSol = bfs_soldado(soldado.pos);
        
        it = pos_reservadas.find(soldado.pos + Dir(dirSol));
        if (it == pos_reservadas.end()) {
            pos_reservadas[soldado.pos+Dir(dirSol)] = id;
            
            VecPos pos_disp;
            vecinos(soldado.pos, pos_disp, soldado);
            Pos pos_final = soldado.pos + dirSol;
            int npos = pos_disp.size();
            for (int i = 0; i < npos; ++i) {
                if (pos_final == pos_disp[i]) {
                    move(id, dirSol);
                    return;
                }
            }
        }
  }
  
  bool enemeigo_cerca(Pos pos, Dir & dir) {
      for (int i = 0; i < 4; ++i) {
          Pos p = pos + Dir(i);
          if (pos_ok(p) and cell(p).id != -1 and ant(cell(p).id).player != me() and ant(cell(p).id).type == Soldier) {
              dir = Dir(i);
              return true;
          }
      }
      return false;
  }
  
  void mover_reina(int id) {
        Ant reina = ant(id);
        
        Dir dirR;
        
        if (enemeigo_cerca(reina.pos, dirR)) {
            move(id, dirR);
            return;
        }
        
        VecInt soldados = soldiers(me());
        VecInt trabajadoras = workers(me());
        if (round() < 200 and ((soldados.size() < 2 and trabajadoras.size() >= 1) or (soldados.size() < 3 and trabajadoras.size() > 2))) {
            if (reina.reserve[0] >=3 and reina.reserve[1] >=3 and reina.reserve[2] >=3){
                for (int i = 0; i < 4; ++i) { 
                    Pos pos = reina.pos + Dir(i);
                    if (pos_ok(pos) and cell(pos).id == -1 and cell(pos).type != Water){
                        lay(id, Dir(i), Soldier);
                        return;
                    }
                }
            }
        }
        else {
            if (reina.reserve[0] >=1 and reina.reserve[1] >=1 and reina.reserve[2] >=1){
                for (int i = 0; i < 4; ++i) { 
                    Pos pos = reina.pos + Dir(i);
                    if (pos_ok(pos) and cell(pos).id == -1 and cell(pos).type != Water){
                        lay(id, Dir(i), Worker);
                        return;
                    }
                }
            }
        }
        
        dirR = bfs_reina(reina.pos);
        
        it = pos_reservadas.find(reina.pos + Dir(dirR));
        if (it == pos_reservadas.end()) {
            pos_reservadas[reina.pos+Dir(dirR)] = id;
        
            VecPos pos_disp;
            vecinos(reina.pos, pos_disp, reina);
            Pos pos_final = reina.pos + dirR;
            int npos = pos_disp.size();
            for (int i = 0; i < npos; ++i) {
                if (pos_final == pos_disp[i]) {
                    move(id, dirR);
                    return;
                }
            }
        }
  }
  
  virtual void play () {
        
        
        //Mover trabajadoras
        VecInt trabajadoras = workers(me());
        for (int id : trabajadoras) {
            mover_trabajadora(id);
        }
        
        //Mover soldados
        VecInt soldados = soldiers(me());
        for (int id : soldados) {
            mover_soldado(id);
        }
        
        //Mover reina
        VecInt reinas = queens(me());
        if (not reinas.empty() and round() % queen_period() == 0) {
            mover_reina(reinas[0]);
        }
        
        pos_reservadas.clear();
  }

};


/**
 * Do not modify the following line.
 */
RegisterPlayer(PLAYER_NAME);
