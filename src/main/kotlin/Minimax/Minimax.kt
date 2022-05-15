package Minimax

import Estructuras.ArbolBinario
import Minimax.Mesa.Tablero

class Minimax() : ArbolBinario<Tablero>() {

   constructor(tablero : Tablero) : this() {
       val clon = tablero.clone()
       raiz = nuevoVertice(clon)
       generaMinimax(raiz, clon, 0)
       println("Minimax Generado")
   }

    private fun generaMinimax(nodo : Vertice, tablero : Tablero, indie : Int){
        nodo.elemento = tablero

        val minmax = tablero.darSigMinimax()

        if(minmax == null){
            nodo.elemento.minimax = nodo.elemento.minimax
            return
        }

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
                nodo.elemento.minimax = when(nodo.elemento.minimaxMayorMenor()){
                    1 -> nodo.izquierdo.elemento.minimax.coerceAtLeast(nodo.derecho.elemento.minimax)
                    -1 -> nodo.izquierdo.elemento.minimax.coerceAtMost(nodo.derecho.elemento.minimax)
                    else -> 0
                }
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

    override fun iterator(): MutableIterator<Tablero> {
        TODO("Not yet implemented")
    }

    override fun delete(elemento: Tablero?): Boolean {
        TODO("Not yet implemented")
    }

    override fun add(elemento: Tablero?) {
        TODO("Not yet implemented")
    }


}