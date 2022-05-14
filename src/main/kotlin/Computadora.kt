import Estructuras.Lista
import kotlin.random.Random
import kotlin.system.exitProcess

class Computadora(override var nodosJugador: Lista<Nodo>, override var oponente: Actor?, val mode : ModoJuego) : Actor {
    override fun mueve() {
       when(mode){
           ModoJuego.RANDOM -> mueveRandom()
           ModoJuego.MINIMAX -> mueveMinimax()
       }
    }

    private fun mueveMinimax() {
        TODO("Not yet implemented")
    }

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