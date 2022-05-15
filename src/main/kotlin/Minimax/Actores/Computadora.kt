package Minimax.Actores

import Estructuras.Lista
import Minimax.Mesa.ModoJuego
import Minimax.Mesa.Nodo
import kotlin.random.Random
import kotlin.system.exitProcess


/**
 * Computer player unit capaz de jugar el juego en diversos modos.
 *
 * @property nodosJugador La [Lista] de nodos en el cual la computadora tiene sus fichas.
 * @property oponente Oponente de la computadora.
 * @property mode Modo de juego definido en [ModoJuego] en el que la computadora actuara.
 */
class Computadora(override var nodosJugador: Lista<Nodo>, override var oponente: Actor?, private val mode : ModoJuego) :
    Actor {

    /**
     * Mueve dependiendo del modo de juego que esté la computadora.
     */
    override fun mueve() {
       when(mode){
           ModoJuego.RANDOM -> mueveRandom()
           ModoJuego.MINIMAX -> mueveMinimax()
       }
    }

    override fun mueveEspecifico(i: Int): Boolean {
        return if(nodosJugador.get(i).atrapado()){
            false
        } else {
            nodosJugador.get(i).mueveFicha()
            true
        }
    }

    override fun clone(): Computadora {
        return Computadora(Lista<Nodo>(), null, mode)
    }

    /**
     * Movimiento utilizando Minimax
     */
    private fun mueveMinimax() {
        TODO("Not yet implemented")
    }

    private fun piensa(){
        print("CPU: Pensando")
        for (x in 1 .. 3){
            Thread.sleep(700)
            print(".")
        }
        Thread.sleep(500)
        println()
    }

    /**
     * Movimiento aleatorio
     */
    private fun mueveRandom() {
        var puedesMover = true
        if(nodosJugador.get(0).atrapado() && nodosJugador.get(1).atrapado()) puedesMover = false
        else if(nodosJugador.get(1).atrapado()){
            val movido = nodosJugador.get(0).mueveFicha()
            nodosJugador.eliminaPrimero()
            nodosJugador.agregaInicio(movido)
            piensa()
            println("$this: Moviendo ficha ${movido.valor} automáticamente, unica opción posible")
            return
        }
        else if(nodosJugador.get(0).atrapado()){
            val movido = nodosJugador.get(1).mueveFicha()
            nodosJugador.eliminaUltimo()
            nodosJugador.agregaFinal(movido)
            piensa()
            println("$this:  ficha ${movido.valor} automáticamente, unica opción posible")
            return
        }


        if(!puedesMover){
            println("El CPU ya no puede moverse.")
            Thread.sleep(400)
            println("¡¡¡Haz ganado, $oponente!!!")
            println("El ganador es: $oponente")
            exitProcess(0)
        }

        val i = Random.nextInt() % 2
        val movido : Nodo
        piensa()
        if(i == 0){
            movido = nodosJugador.primero.mueveFicha()
            nodosJugador.eliminaPrimero()
            nodosJugador.agregaInicio(movido)
            println("CPU: Se he movido aleatoriamente la ficha ${movido.valor}")
        } else {
            movido = nodosJugador.ultimo.mueveFicha()
            nodosJugador.eliminaUltimo()
            nodosJugador.agregaFinal(movido)
            println("CPU: Se he movido aleatoriamente la ficha ${movido.valor}")
        }
    }

    override fun toString(): String {
        return "CPU"
    }
}