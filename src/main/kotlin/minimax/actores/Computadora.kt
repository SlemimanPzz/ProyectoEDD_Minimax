package minimax.actores

import Estructuras.Lista
import minimax.mesa.Nodo
import kotlin.random.Random
import kotlin.system.exitProcess


/**
 * Computer player unit capaz de jugar el juego en diversos modos.
 *
 * @property nodosJugador La [Lista] de nodos en el cual la computadora tiene sus fichas.
 * @property oponente Oponente de la computadora.
 */
class Computadora(override var nodosJugador: Lista<Nodo>, override var oponente: Actor?) :
    Actor {


    /**
     * Mueve una de sus fichas específicamente, si no es posible regresa `false`, utilizado en la generación de [minimax]
     * @return `true` si se logro el movimiento,  `false` si no fue posible.
     */
    override fun mueveEspecifico(i: Int): Boolean {
        if (nodosJugador.get(0).atrapado() && nodosJugador.get(1).atrapado()) return false
        else if (i == 0) {
            if (nodosJugador.get(0).atrapado()) return false
            val movido = nodosJugador.get(0).mueveFicha()
            nodosJugador.eliminaPrimero()
            nodosJugador.agregaInicio(movido)
            return true
        } else{
            if (nodosJugador.get(1).atrapado()) return false
            val movido = nodosJugador.get(1).mueveFicha()
            nodosJugador.eliminaUltimo()
            nodosJugador.agregaFinal(movido)
            return true
        }
    }

    /**
     * Movimiento utilizado durante el juego para mover conforme a minimax,
     * per bien este no corre el minimax, pero recibe la ficha que tiene que mover.
     *
     * @param i Ficha a mover.
     * @return Si se logro el moviemiento, se tiene medida de seguridad.
     */
    fun mueveMinimax(i :Int) : Boolean {
        if (nodosJugador.get(0).atrapado() && nodosJugador.get(1).atrapado()){
            println("Feliciades $oponente")
            Thread.sleep(1000)
            println("Ganaeste")
            Thread.sleep(1000)
            println("¿Como?")
            Thread.sleep(1000)
            println("(¬､¬)")
        }


        else if (i == 0) {
            if (nodosJugador.get(0).atrapado()) return false
            val movido = nodosJugador.get(0).mueveFicha()
            nodosJugador.eliminaPrimero()
            nodosJugador.agregaInicio(movido)
            println("$this: Se movio ${movido.valor}")
            return true
        } else if (i == 1) {
            if (nodosJugador.get(1).atrapado()) return false
            val movido = nodosJugador.get(1).mueveFicha()
            nodosJugador.eliminaUltimo()
            nodosJugador.agregaFinal(movido)
            println("$this: Se movio ${movido.valor}")
            return true
        }
        return false
    }


    /**
     * Clona la computadora.
     */
    override fun clone(): Computadora {
        return Computadora(Lista<Nodo>(), null )
    }


    /**
     * Método para darle emoción al asunto,
     */
    fun piensa(){
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
    override fun mueve() : Int{
        var puedesMover = true
        if(nodosJugador.get(0).atrapado() && nodosJugador.get(1).atrapado()) puedesMover = false
        else if(nodosJugador.get(1).atrapado()){
            val movido = nodosJugador.get(0).mueveFicha()
            nodosJugador.eliminaPrimero()
            nodosJugador.agregaInicio(movido)
            piensa()
            println("$this: Moviendo ficha ${movido.valor} automáticamente, unica opción posible")
            return 0
        }
        else if(nodosJugador.get(0).atrapado()){
            val movido = nodosJugador.get(1).mueveFicha()
            nodosJugador.eliminaUltimo()
            nodosJugador.agregaFinal(movido)
            piensa()
            println("$this:  ficha ${movido.valor} automáticamente, unica opción posible")
            return 1
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
        return  i
    }

    /**
     * Regresa una representación en cadena de la computadora.
     * @return una representación en cadena de la computadora.
     */
    override fun toString(): String {
        return "CPU"
    }
}