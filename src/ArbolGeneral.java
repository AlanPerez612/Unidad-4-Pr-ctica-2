/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GX505DT
 */
public class ArbolGeneral {
    NodoGeneral raiz;
    
    public ArbolGeneral(){
        raiz=null;
    }
    
    public boolean insertarNodoGeneral(String path, char valor){
        if(path.isEmpty()){
            if(raiz==null){
                raiz = new NodoGeneral(valor);
                return true;
            }
            return false;
        }
        
        NodoGeneral padre = buscarNodo(path);
        if(padre == null){
            return false;
        }
        
        NodoGeneral buscarhijo = buscarNodo(path+"/"+valor);
        if(buscarhijo != null){
            return false;
        }
        buscarhijo = null;
        
        NodoGeneral hijo = new NodoGeneral(valor);
        if(hijo == null){
            return false;
        }
        
        return padre.insertarLiga(hijo);
    }
    
    public NodoGeneral buscarNodo(String path){
        //considerar que el path enviado es /m   /m/w   /m/w/a/f
        if(path.isEmpty()){
            return null;
        }
        
        path = path.substring(1);
        // m/w/a/f
        String[] vector = path.split("/");
        
        if(raiz.valor == vector[0].charAt(0)){
            if(vector.length == 1){
                return raiz;
            }
            for(NodoLiga temp = raiz.ini; temp!=null; temp = temp.sig){
                if(temp.direccion.valor == vector[1].charAt(0)){
                    if(vector.length == 2){
                        return temp.direccion;
                    }
                    return buscarNodo(temp.direccion, path.substring(3));
                }
            }
        }
        
        return null;
    }
    
    public boolean eliminarNodoGeneral(String path){
        NodoGeneral hijo = buscarNodo(path);
        if(hijo==null){
            return false;
        }
        
        if(hijo==raiz){
            if(raiz.esHoja()){
                raiz = null;
                return true;
            }
            return false;
        }
        
        String pathPadre = obtenerPathPadre(path);
        //    /g/w/R/a    buscar ultima coincidencia de /
        
        NodoGeneral padre = buscarNodo(pathPadre);
        if(padre==null){
            return false;
        }
        
        if(hijo.esHoja()){
            return padre.eliminarLiga(hijo);
        }
        return false;
    }
    
    public NodoGeneral buscarNodo(NodoGeneral nodoEncontrado, String path){
        //CASO BASE
        if(path.isEmpty()){
            return nodoEncontrado;
        }
        
        //   /w/a/f/r   /a/f   /f
        
        path = path.substring(1);
        String vector[];
        if(path.length()==1){
            vector = new String[1];
            vector[0] = path;
        } else{
            vector = path.split("/");
        }
        
        for(NodoLiga temp = nodoEncontrado.ini; temp!=null; temp = temp.sig){
            if(temp.direccion.valor==vector[0].charAt(0)){
                buscarNodo(temp.direccion, path.substring(1));
            }
        }
        
        return null;
    }
    
    public String obtenerPathPadre(String pathHijo){
        int posicionUltimaDiagonal = pathHijo.lastIndexOf("/")-1;
        return pathHijo.substring(0, posicionUltimaDiagonal);
    }
}
