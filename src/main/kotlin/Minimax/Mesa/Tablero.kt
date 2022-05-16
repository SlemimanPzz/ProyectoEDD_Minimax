package Minimax.Mesa

import Estructuras.Lista
import Minimax.Actores.Actor
import Minimax.Actores.CambiaModo
import Minimax.Actores.Computadora
import Minimax.Actores.Jugador
import Minimax.Minimax
import java.util.Arrays
import kotlin.system.exitProcess

/**
 * El tablero de juego.
 *
 * @param fichas las fichas que serán asignadas a los nodos
 * @property jugador1 Minimax.Actores.Jugador humano de la partida.
 * @property nodosTablero Los nodos del tablero.
 * @property nodosJugador Los nodos pertenecientes a [jugador1] en ese instante.
 * @constructor Se crean todas las vecindades y asignamos los nodos de los [Actor] en el juego.
 */
@Suppress("UNREACHABLE_CODE")
class Tablero(private val fichas: Array<Ficha>, private val jugador1: Jugador, private val computadora : Computadora, private var mode  :ModoJuego, primero : Actor) {

    private var nodosTablero: Array<Nodo> = Array(5) { i -> Nodo(i, null) }
    private var nodosJugador: Lista<Nodo> = Lista<Nodo>()
    private var nodosComputa: Lista<Nodo> = Lista<Nodo>()

    private var siguienteJugador: Actor = primero
    var minimax : Int = 0
    var invalido : Boolean = false
    private var arbolMinimax : Minimax? = null


    /**
     * Establece el orden en el tablero.
     */
     fun estableceOrden() {
        println("Decidamos quien sera el primer jugador")
        println("¿Quien sera el primer jugador 1.$jugador1 o 2.$computadora?")
        var i = 0
        var porDecidir  = true
        while(porDecidir) {
            try {
                i = readln().toInt()
                if(i == 1 || i == 2)
                porDecidir = false
                else println("Ingresa un numero valido")
            } catch (nfe: NumberFormatException) {
                println("Ingresa un numero valido")
            }
        }
         siguienteJugador = if(i == 1) jugador1
         else computadora
    }

    init {

        //Añadimos la vecindades a cada nodo
        for(i in 1..3)
        nodosTablero[0].vecindad.add(nodosTablero[i])

        nodosTablero[1].vecindad.add(nodosTablero[0])
        nodosTablero[1].vecindad.add(nodosTablero[2])
        nodosTablero[1].vecindad.add(nodosTablero[4])


        for(i in 0..4)
        nodosTablero[2].vecindad.add(nodosTablero[i])

        nodosTablero[3].vecindad.add(nodosTablero[0])
        nodosTablero[3].vecindad.add(nodosTablero[2])

        nodosTablero[4].vecindad.add(nodosTablero[1])
        nodosTablero[4].vecindad.add(nodosTablero[2])


        //Colocamos las fichas
        nodosTablero[0].valor = fichas[0]
        nodosTablero[1].valor = fichas[3]
        nodosTablero[3].valor = fichas[2]
        nodosTablero[4].valor = fichas[1]

        //Ponemos los nodos del jugador1
        jugador1.nodosJugador.agregaFinal(nodosTablero[0])
        jugador1.nodosJugador.agregaFinal(nodosTablero[4])
        nodosJugador = jugador1.nodosJugador

        //Ponemos los nodos del jugador2
        computadora.nodosJugador.agregaFinal(nodosTablero[3])
        computadora.nodosJugador.agregaFinal(nodosTablero[1])
        nodosComputa = computadora.nodosJugador
    }

    fun mueveSiguiente(){
        if(mode == ModoJuego.MINIMAX) mueveSiguienteMinimax()
        else {
            try{
                mueveActor(siguienteJugador)
                siguienteJugador = when(siguienteJugador){
                    jugador1 ->computadora
                    computadora -> jugador1
                    else -> throw RuntimeException()
                }
            } catch (ne : CambiaModo){
                  mode = when(mode){
                      ModoJuego.MINIMAX -> ModoJuego.RANDOM
                      ModoJuego.RANDOM -> ModoJuego.MINIMAX
                  }
                println("Modo de juego Cambiado")
            }
        }
    }


    private fun mueveSiguienteMinimax(){
        arbolMinimax = Minimax(this)
        if(siguienteJugador == computadora){
            println("CPU: Decidiendo jugada optima.")
            computadora.piensa()
            val optima = arbolMinimax?.darOptimoComputadora()
            if (optima != null) {
                val x = computadora.mueveMinimax(optima)
                if(!x){
                    computadora.mueve()
                }
                siguienteJugador = jugador1
            } else{
                println("Algo salio mal =(")
                exitProcess(0)
            }
        }
        else{
            try {
                mueveActor(siguienteJugador)
                siguienteJugador = computadora
            } catch (cm : CambiaModo){
                mode = when(mode){
                    ModoJuego.MINIMAX -> ModoJuego.RANDOM
                    ModoJuego.RANDOM -> ModoJuego.MINIMAX
                }
                println("Modo de juego cambiado. Vuelve a mover")
            }

        }
    }

    private fun mueveSiguienteEspecifico(i  :Int) : Boolean{
        return if(siguienteJugador.mueveEspecifico(i)){
            siguienteJugador= when(siguienteJugador){
                jugador1 -> computadora
                computadora -> jugador1
                else -> throw RuntimeException()
            }
            true
        } else false
    }

    /**
     * Hace que el [actor] haga un movimiento
     *
     * @param actor Quien realizara el movimiento, tiene que ser un jugador en el tablero.
     *
     * @throws IllegalArgumentException Si el parameter no es un [actor] en el tablero
     */
    private fun mueveActor(actor : Actor){
        if(actor != jugador1 && actor != computadora) throw IllegalArgumentException("Tu no estas jugando -_-")
        actor.mueve()
    }

    fun darSigMinimax() : Array<Tablero>? {
        val tableroIzquier = this.clone()
        val tableroDerecho = this.clone()
        val sePudoMoverIzq = tableroIzquier.mueveSiguienteEspecifico(0)
        val sePudoMoverDer = tableroDerecho.mueveSiguienteEspecifico(1)
        if(!sePudoMoverDer && !sePudoMoverIzq){
            when(siguienteJugador){
                //Jugador 1 Pierde
                jugador1 -> minimax = Int.MIN_VALUE
                // Pierde computadora
                computadora -> minimax = Int.MAX_VALUE
            }
            return null
        }
        if(sePudoMoverDer && sePudoMoverIzq)
            return arrayOf(tableroIzquier, tableroDerecho)

        return if(sePudoMoverIzq) {
            tableroDerecho.invalido = true
            arrayOf(tableroIzquier, tableroDerecho)
        } else {
            tableroIzquier.invalido = true
            arrayOf(tableroIzquier, tableroDerecho)
        }
    }

    fun minimaxMayorMenor() : Int{
        return when(siguienteJugador){
            jugador1 -> 1                   //Queremos el maximo valor ya que el siguiente en jugar es el jugador
            computadora -> -1               //Queremos el minimo valor ya que el siguiente en jugar es la computadora
            else -> throw RuntimeException()
        }
    }


    fun clone() : Tablero{
        return if(siguienteJugador == jugador1){
            val clonJugador = jugador1.clone()
            val clonCompu = computadora.clone()
            val clon = Tablero(fichas, clonJugador, clonCompu, mode ,clonJugador)



            for(x in 0 .. 4){
                    clon.nodosTablero[x].valor = nodosTablero[x].valor
            }

            clonJugador.nodosJugador.limpia()
            clon.nodosTablero.forEach { if(it.valor?.id == 1) clonJugador.nodosJugador.agregaFinal(it)}
            clon.nodosTablero.forEach { if(it.valor?.id == 2) clonJugador.nodosJugador.agregaFinal(it)}

            clonCompu.nodosJugador.limpia()
            clon.nodosTablero.forEach { if(it.valor?.id == 3) clonCompu.nodosJugador.agregaFinal(it)}
            clon.nodosTablero.forEach { if(it.valor?.id == 4)clonCompu.nodosJugador.agregaFinal(it) }


            return clon
        } else{
            val clonCompu = computadora.clone()
            val clonJugador = jugador1.clone()
            val clon = Tablero(fichas, clonJugador, clonCompu, mode , clonCompu)


            for(x in 0 .. 4){
                clon.nodosTablero[x].valor = nodosTablero[x].valor
            }

            clonJugador.nodosJugador.limpia()
            clon.nodosTablero.forEach { if(it.valor?.id == 1) clonJugador.nodosJugador.agregaFinal(it)}
            clon.nodosTablero.forEach { if(it.valor?.id == 2) clonJugador.nodosJugador.agregaFinal(it)}

            clonCompu.nodosJugador.limpia()
            clon.nodosTablero.forEach { if(it.valor?.id == 3) clonCompu.nodosJugador.agregaFinal(it)}
            clon.nodosTablero.forEach { if(it.valor?.id == 4)clonCompu.nodosJugador.agregaFinal(it) }

            return clon
        }
    }

    /**
     * Nos da la representation en cadena del juego, con fichas colocadas
     * y a quien le pertenece cada ficha.
     *
     * @return El talero en [String]
     */
    override fun toString(): String {
        return """
            ${nodosTablero[0]}---${nodosTablero[1]}
              |    \ /    |
              |  ${nodosTablero[2]}  |
              |    / \    |
            ${nodosTablero[3]}   ${nodosTablero[4]}
            """.trimIndent()
    }

}