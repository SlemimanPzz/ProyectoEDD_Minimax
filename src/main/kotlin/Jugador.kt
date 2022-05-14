import Estructuras.Lista
import kotlin.system.exitProcess

/**
 * Jugador humano del juego.
 *
 * @property nombre Nombre del jugador.
 * @property nodosJugador La [Lista] de nodos ocupados actualmente por el jugador.
 * @property oponente Oponente del jugador.
 */
class Jugador(private val nombre:String, override var nodosJugador: Lista<Nodo>, override var oponente: Actor?): Actor {

    /**
     * Mueve el jugador su ficha, o en su defecto mueve automáticamente si solo queda una opción o
     * termina el juego si este pierde.
     */
    override fun mueve(){

        var puedesMover = true
        if(nodosJugador.get(0).atrapado() && nodosJugador.get(1).atrapado()) puedesMover = false
        else if(nodosJugador.get(1).atrapado()){
            println("Solo puedes mover ${nodosJugador.get(0).valor?.id}")
            println("Presiona enter para continuar o cualquier tecla para salir")
            if(readLine() != "") exitProcess(1)
            val movido = nodosJugador.get(0).mueveFicha()
            val estatico = nodosJugador.get(1)
            nodosJugador.limpia()
            nodosJugador.agregaFinal(movido)
            nodosJugador.agregaFinal(estatico)
            Thread.sleep(400)
            println("$this: Moviendo ficha ${movido.valor?.id} automáticamente")
            return
        }
        else if(nodosJugador.get(0).atrapado()){
            println("Solo puedes mover ${nodosJugador.get(1).valor?.id}")
            println("Presiona enter para continuar o cualquier tecla para salir")
            if(readLine() != "") exitProcess(1)
            val movido = nodosJugador.get(1).mueveFicha()
            val estatico = nodosJugador.get(0)
            nodosJugador.limpia()
            nodosJugador.agregaFinal(movido)
            nodosJugador.agregaFinal(estatico)
            Thread.sleep(400)
            println("$this:  ficha ${movido.valor?.id} automáticamente")
            return
        }

        if(!puedesMover){
            println("Perdiste  el juego =(")
            println("El ganador es: CPU")
            exitProcess(0)
        }

        println("¿Que ficha quieres mover, $this? [${nodosJugador.primero.valor?.id}|${nodosJugador.ultimo.valor?.id}]")
        var porMover = true
        var i= 0
        while (porMover) {
            try {
                i = readln().toInt()
            } catch (nfe: NumberFormatException) {
                println("Ingresa un numero")
                continue
            }
            if (i != nodosJugador.get(0).valor?.id && i != nodosJugador.get(1).valor?.id ) {
                println("Escoge una ficha valida [${nodosJugador.primero.valor?.id}|${nodosJugador.ultimo.valor?.id}]")
                continue
            }
            porMover = false
        }
        val movido : Nodo
        if(i == nodosJugador.primero.valor?.id){
            movido = nodosJugador.primero.mueveFicha()
            val quieto = nodosJugador.ultimo
            nodosJugador.limpia()
            nodosJugador.agregaFinal(quieto)
            nodosJugador.agregaFinal(movido)
            Thread.sleep(400)
            println("$this: Ficha ${movido.valor} movida")
        }
        else {
            movido = nodosJugador.ultimo.mueveFicha()
            val quito = nodosJugador.primero
            nodosJugador.limpia()
            nodosJugador.agregaFinal(movido)
            nodosJugador.agregaFinal(quito)
            Thread.sleep(400)
            println("$this: Ficha ${movido.valor} movida")
        }

    }

    override fun toString(): String {
        return nombre.substring(0 .. 2)
    }

}