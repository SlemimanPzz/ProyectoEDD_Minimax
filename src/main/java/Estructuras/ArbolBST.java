package Estructuras;

import java.util.Iterator;

public class ArbolBST<T extends Comparable<T>> extends ArbolBinario<T>{

    /**
     * Contruye un arbol vacio
     */
    public ArbolBST(){
        super();
    }


    /**
     * Busca un elemento en el arbol
     * @param elemento elemento a buscar
     * @return Si se contiene el elemento
     */
    public boolean search(T elemento){
        Vertice u = raiz;

        while (u != null){
            if(elemento.equals(u.elemento)){
                return true;
            }
            if(elemento.compareTo(u.elemento) < 0){
                u = u.izquierdo;
            } else {
                u = u.derecho;
            }
        }
        return false;
    }

    /**
     * Busca en elemento en un arbol
     * @param nodo arbol donde buscar
     * @param u elemento a buscar
     * @return si fue encontrado
     */
    public boolean search(Vertice nodo, T u){
        if(nodo == null) return false;
        if(u.equals(nodo.elemento)) return true;
        if(u.compareTo(nodo.elemento) < 0) search(nodo.izquierdo, u);
        if(u.compareTo(nodo.elemento) > 0) search(nodo.derecho, u);
        return false;
    }

    /**
     * Elemina un elemento de un arbol
     * @param nodo arbol del que eliminar el elemento
     * @param u elemento a eleminars
     */
    public void delete(Vertice nodo, T u){
        deleteAux(nodo, u);
        elementos--;
    }

    /**
     * Delete para add, ya que no redude this.elementos
     * @param raiz arbol de donde eleminar
     * @param elemento elemento a eliminar
     */
    private void delete2(Vertice raiz, T elemento) {
        deleteAux(raiz, elemento);
    }


    /**
     * Delete recursivo que elimina el elemento del BST
     * @param nodo arbol de donde eliminar
     * @param u elemento a eliminar
     * @return el arbol con vertice elemento eliminado
     */
    private Vertice deleteAux(Vertice nodo, T u) {
        if(nodo == null) return nodo;
        if(u.equals(nodo.elemento)){
            //Si es hoja
            if(nodo.derecho == null && nodo.izquierdo == null) return null;
            //Si tiene solo 1 hoja
            if(nodo.izquierdo == null && nodo.derecho != null) return nodo.derecho;
            if(nodo.derecho == null && nodo.izquierdo != null) return nodo.izquierdo;

            //Tiene 2 hijos
            Vertice minDer = minDelete(nodo.derecho, nodo);
            nodo.elemento = minDer.elemento;

        } else if(nodo.elemento.compareTo(u) < 0) nodo.derecho = deleteAux(nodo.derecho, u);
        else {nodo.izquierdo = deleteAux(nodo.izquierdo, u);} 
        return nodo;
    }

    /**
     * Encientra el minimo y regresa ese vertice
     * @param nodo arbol del que se desea el minimo
     * @param padre el predesecor del minimo
     * @return el minimo
     */
    private Vertice minDelete(Vertice nodo, Vertice padre) {
        Vertice  pre = padre;
        Vertice i = nodo;
        while(i.izquierdo != null){
            pre = i;
            i = i.izquierdo;
        }
        delete(pre, i.elemento);
        return i;
    }

    /**
     * toString inOrder del arbol BST
     * @param root Arbol que se desea hacer el inOrder
     * @return cadena inOrder del BST
     */
    public String toString(Vertice root) {
        Lista<T> inOrder = new Lista<>();
        inOrder(root, inOrder);
        String s = "";
        for(T x : inOrder){
            s+= String.format("%s\t", x.toString());
        }
        return s;
    }
       

    /**
     * Hacemos que la lista que nos pase sea el recorido inOrder del arbol
     * @param nodo arbol del que queremos hacer inOrder
     * @param inOrder lista inOrder
     */
    private void inOrder(Vertice nodo, Lista<T> inOrder){
        if(nodo == null) return;
        inOrder(nodo.izquierdo, inOrder);
        inOrder.add(nodo.elemento);
        inOrder(nodo.derecho, inOrder);
    }
    
    
    /**
     * Interta un element en ese arbol
     * @param vertice Arbol en donde insertar
     * @param elemento Elemento a insertar
     */
    public void insert(Vertice vertice, T elemento){
        if(vertice == null){
            vertice = new Vertice(elemento);
        }
        inserAux(vertice, elemento);
        elementos++;
    }

    /**
     * Insetamos un elemento en el arbol
     * @param elemento elemento a insertar
     */
    public void insert(T elemento){
        if(isEmpty()) raiz = new Vertice(elemento);
        insert(this.raiz, elemento);
    }
    private void inserAux(Vertice nodo, T u){
        if(u.compareTo(nodo.elemento) < 0){
            if(nodo.izquierdo == null){
                nodo.izquierdo = new Vertice(u);
            } else {
                inserAux(nodo.izquierdo, u);
            }
        } else if( u.compareTo(nodo.elemento) > 0){
            if(nodo.derecho == null){
                nodo.derecho = new Vertice(u);
            } else{
                inserAux(nodo.derecho, u);
            }
        }
    }

    /**
     * Agrega un elemento al arbol
     * @param elemento elemento a agregar
     */
    @Override
    public void add(T elemento) {
        
        if(elemento == null){
            throw new IllegalArgumentException();
        }
        Vertice a = nuevoVertice(elemento);
        elementos++;
        if (isEmpty()) {
            raiz = a;
        }

        inserAux(raiz, elemento);
        
    }


    /**
     * Construye un arbol en base a una lista desordenada
     * @param lista lista desordenada de la que se desea el BST
     */
    public void buildUnsorted(Lista<T> lista) {
        this.buildSorted(lista.copia().mergeSort(((e1, e2) -> e1.compareTo(e2))));
    }

    /**
     * Creacion del arbol en base a una lista ordenada
     * @param lista Una lista ordena
     */
    public void buildSorted(Lista<T> lista) {
        IteradorLista<T> iter = lista.iteradorLista();
        this.raiz = buildSortedAux(lista.getLongitud(), iter);
       
    }

    /**
     * Contruye un arbol BST a aprtir de una collecion donde se itere ordenadamente
     * @param collection
     */
    public void buildSorted(Collection<T> collection) {
        this.raiz = buildSortedAux(collection.size(), collection.iterator());
    }


    /**
     * Auxiliar de build sorted
     * @param n 
     * @return regresamos el nodo creado
     */
    private Vertice buildSortedAux( int n, Iterator<T> iter){

        //Si n es 0 nada que hacer
        if(n <= 0) return null;

        //Creamos el vertice que sera el izquierdo recusrivamente
        Vertice izq = buildSortedAux(n / 2, iter);
        
        //Nodo actual tendra que ser la cabeza, o el menor de los elementos aun no agregados
        Vertice nodo = new Vertice(iter.next());

        //Ya que creamos el nodo podemos asignar si izquierdo
        nodo.izquierdo = izq;

        //Creamos el nodo derecho con el resto de la lista
        nodo.derecho = buildSortedAux(n - n/2 - 1, iter);

        return  nodo;


    }

    /**
     * Hace un arbol BST en base al arbol parametro
     * @param arbol arbol del que se desea hacer el BST
     */
    public void convertBST(ArbolBinario<T> arbol) {
        Lista<T> lista = new Lista<>();
        inOrder(arbol.raiz, lista);
        lista.mergeSort((e1, e2) -> e1.compareTo(e2));
        buildSortedAux(lista.getLongitud(), lista.iterator());
    }


    /**
     * Balancea a partir de cierto nodo
     * @param nodo
     */
    public void balanced(Vertice nodo){
        Lista<T> inOrder = new Lista<>();
        inOrder(nodo, inOrder);
        nodo = buildSortedAux(inOrder.getLongitud(), inOrder.iterator());
    }

    /**
     * Balancea todo el arbol
     */
    public void balanced(){
        balanced(this.raiz);
    }



    /**
     * Elimina el elemento de arbol
     * @param elemento elemento a eliminar
     * @return terminado
     */
    @Override
    public boolean delete(T elemento) {
        delete2(this.raiz, elemento);
        elementos--;
        return true;
    }

    /**
     * Iterador inOrder del arbol BST
     */
    @Override
    public Iterator<T> iterator() {
        Lista<T> inOrder = new Lista<>();
        inOrder(raiz, inOrder);
        return inOrder.iterator();
    }

    
    
}
