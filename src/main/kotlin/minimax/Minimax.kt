package minimax

import Estructuras.ArbolBinario
import minimax.mesa.Tablero
import kotlin.random.nextInt


/**
 * Clase Minimax que extiende al [ArbolBinario], ya que como los jugadores tienen 2 fichas y estas
 * solo se puede mover a un nodo, con un [ArbolBinario] basta para represent todos los movimientos posibles.
 *
 */
class Minimax() : ArbolBinario<Tablero>() {


    /**
     * Constructor que emplea un tablero como base y de ahi desarrolla los siguientes 10
     * movimientos posibles, ya que si no se va al infinito, para tomar decisiones con base en Minimax.
     *
     * @param tablero Tablero base para generar los siguientes 10 movimientos posibles.
     */
    constructor(tablero : Tablero) : this() {
       val clon = tablero.clone()
       raiz = nuevoVertice(clon)
       generaMinimax(raiz, clon, 0)
       println("Minimax Generado")
   }


    /**
     * Genera el minimax a de un vertices tomando en cuenta un [Tablero] base y con un límite indicado
     * por el índice.
     *
     * @param nodo El nodo base del cual generar el minimax.
     * @param tablero El tablero base para generar el minimax.
     * @param indice Cuanta la profundidad de la construction actual.
     */
    private fun generaMinimax(nodo : Vertice, tablero : Tablero, indice : Int){

        nodo.elemento = tablero

        val minmax = tablero.darSigMinimax() ?: return


        if(indice == 10){

            if(minmax[0].invalido){
                nodo.izquierdo = null
                nodo.derecho = Vertice(minmax[1])
                nodo.elemento.minimax = nodo.derecho.elemento.minimax

                return
            }

            else if(minmax[1].invalido){
                nodo.derecho = null
                nodo.izquierdo = Vertice(minmax[0])
                nodo.elemento.minimax = nodo.izquierdo.elemento.minimax
                return
            }

            else{
                nodo.izquierdo = Vertice(minmax[0])
                nodo.derecho = Vertice(minmax[1])
                if(nodo.elemento.minimaxMayorMenor() == 1){
                    nodo.elemento.minimax = Math.max(nodo.izquierdo.elemento.minimax, nodo.derecho.elemento.minimax)
                } else if(nodo.elemento.minimaxMayorMenor() == -1)
                    nodo.elemento.minimax = Math.min(nodo.izquierdo.elemento.minimax, nodo.derecho.elemento.minimax)
                return
            }

        }
        if(minmax[0].invalido){
            nodo.izquierdo = null
            nodo.derecho = Vertice(minmax[1])
            generaMinimax(nodo.derecho, minmax[1], indice + 1)
            nodo.elemento.minimax = nodo.derecho.elemento.minimax
            return
        }
        else if(minmax[1].invalido){
            nodo.derecho = null
            nodo.izquierdo =  Vertice(minmax[0])
            generaMinimax(nodo.izquierdo, minmax[0], indice + 1)
            nodo.elemento.minimax = nodo.izquierdo.elemento.minimax
            return
        }
        else {
            nodo.izquierdo = Vertice(minmax[0])
            nodo.derecho = Vertice(minmax[1])
            generaMinimax(nodo.izquierdo, minmax[0], indice + 1)
            generaMinimax(nodo.derecho, minmax[1], indice + 1)
            nodo.elemento.minimax = when(nodo.elemento.minimaxMayorMenor()){
                1 -> nodo.izquierdo.elemento.minimax.coerceAtLeast(nodo.derecho.elemento.minimax)
                -1 -> nodo.izquierdo.elemento.minimax.coerceAtMost(nodo.derecho.elemento.minimax)
                else -> 0
            }
            return
        }
    }


    fun darOptimoComputadora() : Int{
        if(raiz.derecho == null && raiz.izquierdo == null){
            println("ERROR")
            return -2
        }
        if(raiz.derecho == null) return 0
        if(raiz.izquierdo == null) return 1
        if(raiz.izquierdo.elemento.minimax < raiz.derecho.elemento.minimax) return 0
        if(raiz.izquierdo.elemento.minimax > raiz.derecho.elemento.minimax) return 1
        else{
            return kotlin.random.Random.nextInt(0 .. 1000) % 2
        }
    }

    override fun iterator(): MutableIterator<Tablero> {
        throw  UnsupportedOperationException()
    }

    override fun delete(elemento: Tablero?): Boolean {
        throw  UnsupportedOperationException()
    }

    override fun add(elemento: Tablero?) {
        throw UnsupportedOperationException()
    }


}