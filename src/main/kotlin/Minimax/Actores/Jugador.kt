package Minimax.Actores

import Estructuras.Lista
import Minimax.Mesa.Nodo
import kotlin.system.exitProcess

/**
 * Minimax.Actores.Jugador humano del juego.
 *
 * @property nombre Nombre del jugador.
 * @property nodosJugador La [Lista] de nodos ocupados actualmente por el jugador.
 * @property oponente Oponente del jugador.
 */
class Jugador(private val nombre:String, override var nodosJugador: Lista<Nodo>, override var oponente: Actor?): Actor {

    var ultimoMovimiento : Int = -1
    /**
     * Mueve el jugador su ficha, o en su defecto mueve automáticamente si solo queda una opción o
     * termina el juego si este pierde.
     */
    override fun mueve() : Int{

        var puedesMover = true
        if(nodosJugador.get(0).atrapado() && nodosJugador.get(1).atrapado()) puedesMover = false
        else if(nodosJugador.get(1).atrapado()){
            println("Solo puedes mover ${nodosJugador.get(0).valor}")
            println("Presiona enter para continuar, x para cambiar de modo o cualquier tecla para salir")
            val bb = readln()
            if(bb == "x") throw CambiaModo()
            if(bb != "") exitProcess(1)
            val movido = nodosJugador.get(0).mueveFicha()
            nodosJugador.eliminaPrimero()
            nodosJugador.agregaInicio(movido)
            Thread.sleep(400)
            println("$this: Moviendo ficha ${movido.valor} automáticamente")
            ultimoMovimiento = 0
            return 0
        }
        else if(nodosJugador.get(0).atrapado()){
            println("Solo puedes mover ${nodosJugador.get(1).valor?.id}")
            println("Presiona enter para continuar, x para cambiar de modo o cualquier tecla para salir")
            val bb = readln()
            if(bb == "x") throw CambiaModo()
            if(bb != "") exitProcess(1)
            val movido = nodosJugador.get(1).mueveFicha()
            nodosJugador.eliminaUltimo()
            nodosJugador.agregaFinal(movido)
            Thread.sleep(400)
            println("$this:  ficha ${movido.valor?.id} automáticamente")
            ultimoMovimiento = 1
            return 1
        }

        if(!puedesMover){
            println("Perdiste  el juego =(")
            println("El ganador es: CPU")
            exitProcess(0)
        }

        println("¿Que ficha quieres mover, $this? [${nodosJugador.primero.valor?.id}|${nodosJugador.ultimo.valor?.id}]")
        var porMover = true
        var i : String
        var x = 0
        while (porMover) {
            try {
                i = readln()
                if(i == "x") throw CambiaModo()
                x = i.toInt()
            } catch (nfe: NumberFormatException) {
                println("Ingresa un numero")
                continue
            }
            if (x != 1 && x != 2 ) {
                println("Escoge una ficha valida [${nodosJugador.primero.valor?.id}|${nodosJugador.ultimo.valor?.id}]")
                continue
            }
            porMover = false
        }
        val movido : Nodo
        if(x - 1 == 0){
            movido = nodosJugador.primero.mueveFicha()
            nodosJugador.eliminaPrimero()
            nodosJugador.agregaInicio(movido)
            Thread.sleep(400)
            println("$this: Minimax.Mesa.Ficha ${movido.valor} movida")
        }
        else {
            movido = nodosJugador.ultimo.mueveFicha()
            nodosJugador.eliminaUltimo()
            nodosJugador.agregaFinal(movido)
            Thread.sleep(400)
            println("$this: Minimax.Mesa.Ficha ${movido.valor} movida")
        }
        ultimoMovimiento = x
        return  x - 1
    }

    override fun clone(): Jugador {
        return Jugador(nombre, Lista(), null)
    }

    override fun mueveEspecifico(i: Int): Boolean {
        if (nodosJugador.get(0).atrapado() && nodosJugador.get(1).atrapado()) return false
        else if (i == 0) {
            if (nodosJugador.get(0).atrapado()) return false
            val movido = nodosJugador.get(0).mueveFicha()
            nodosJugador.eliminaPrimero()
            nodosJugador.agregaInicio(movido)
            return true
        } else if (i == 1) {
            if (nodosJugador.get(1).atrapado()) return false
            val movido = nodosJugador.get(1).mueveFicha()
            nodosJugador.eliminaUltimo()
            nodosJugador.agregaFinal(movido)
            return true
        }
        return false
    }




    override fun toString(): String {
        return nombre.substring(0 .. 2)
    }

}