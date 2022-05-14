import Estructuras.Lista
import kotlin.random.Random
import kotlin.system.exitProcess


/**
 * Computer player unit capaz de jugar el juego en diversos modos.
 *
 * @property nodosJugador La [Lista] de nodos en el cual la computadora tiene sus fichas.
 * @property oponente Oponente de la computadora.
 * @property mode Modo de juego definido en [ModoJuego] en el que la computadora actuara.
 */
class Computadora(override var nodosJugador: Lista<Nodo>, override var oponente: Actor?, private val mode : ModoJuego) : Actor {

    /**
     * Mueve dependiendo del modo de juego que esté la computadora.
     */
    override fun mueve() {
       when(mode){
           ModoJuego.RANDOM -> mueveRandom()
           ModoJuego.MINIMAX -> mueveMinimax()
       }
    }

    /**
     * Movimiento utilizando Minimax
     */
    private fun mueveMinimax() {
        TODO("Not yet implemented")
    }

    /**
     * Movimiento aleatorio
     */
    private fun mueveRandom() {
        var puedesMover = true
        if(nodosJugador.get(0).atrapado() && nodosJugador.get(1).atrapado()) puedesMover = false
        else if(nodosJugador.get(1).atrapado()){
            val movido = nodosJugador.get(0).mueveFicha()
            val estatico = nodosJugador.get(1)
            nodosJugador.limpia()
            nodosJugador.agregaFinal(movido)
            nodosJugador.agregaFinal(estatico)
            println("$this: Moviendo ficha ${movido.valor?.id} automáticamente")
            return
        }
        else if(nodosJugador.get(0).atrapado()){
            val movido = nodosJugador.get(1).mueveFicha()
            val estatico = nodosJugador.get(0)
            nodosJugador.limpia()
            nodosJugador.agregaFinal(movido)
            nodosJugador.agregaFinal(estatico)
            println("$this:  ficha ${movido.valor?.id} automáticamente")
            return
        }


        if(!puedesMover){
            println("El CPU ya no puede moverse.")
            println("Haz ganado, $oponente!!!")
            println("El ganador es: $oponente")
            exitProcess(0)
        }

        val i = Random.nextInt() % 2
        val movido : Nodo
        if(i == 0){
            movido = nodosJugador.primero.mueveFicha()
            val quieto = nodosJugador.ultimo
            nodosJugador.limpia()
            nodosJugador.agregaFinal(quieto)
            nodosJugador.agregaFinal(movido)
            println("CPU: Se he movido aleatoriamente la ficha ${movido.valor}")
        } else {
            movido = nodosJugador.ultimo.mueveFicha()
            val quito = nodosJugador.primero
            nodosJugador.limpia()
            nodosJugador.agregaFinal(movido)
            nodosJugador.agregaFinal(quito)
            println("CPU: Se he movido aleatoriamente la ficha ${movido.valor}")
        }
    }

    override fun toString(): String {
        return "CPU"
    }
}