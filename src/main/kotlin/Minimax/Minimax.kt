package Minimax

import Estructuras.ArbolBinario
import Minimax.Mesa.Tablero
import java.util.Random
import kotlin.random.nextInt

class Minimax() : ArbolBinario<Tablero>() {

   constructor(tablero : Tablero) : this() {
       val clon = tablero.clone()
       raiz = nuevoVertice(clon)
       generaMinimax(raiz, clon, 0)
       println("Minimax Generado")
   }



    private fun generaMinimax(nodo : Vertice, tablero : Tablero, indie : Int){

        nodo.elemento = tablero

        val minmax = tablero.darSigMinimax() ?: return

        //Escape si ya se perdio
        //Limite para el arbol
        if(indie == 10){

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
                    nodo.elemento.minimax = Math.max(nodo.izquierdo.elemento.minimax,nodo.derecho.elemento.minimax)
                } else if(nodo.elemento.minimaxMayorMenor() == -1)
                    nodo.elemento.minimax = Math.min(nodo.izquierdo.elemento.minimax,nodo.derecho.elemento.minimax)
                return
            }

        }
        if(minmax[0].invalido){
            nodo.izquierdo = null
            nodo.derecho = Vertice(minmax[1])
            generaMinimax(nodo.derecho, minmax[1], indie + 1)
            nodo.elemento.minimax = nodo.derecho.elemento.minimax
            return
        }
        else if(minmax[1].invalido){
            nodo.derecho = null
            nodo.izquierdo =  Vertice(minmax[0])
            generaMinimax(nodo.izquierdo, minmax[0], indie + 1)
            nodo.elemento.minimax = nodo.izquierdo.elemento.minimax
            return
        }
        else {
            nodo.izquierdo = Vertice(minmax[0])
            nodo.derecho = Vertice(minmax[1])
            generaMinimax(nodo.izquierdo, minmax[0], indie + 1)
            generaMinimax(nodo.derecho, minmax[1], indie + 1)
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
        if(raiz.izquierdo.elemento.minimax < raiz.izquierdo.elemento.minimax) return 0
        if(raiz.izquierdo.elemento.minimax > raiz.izquierdo.elemento.minimax) return 1
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