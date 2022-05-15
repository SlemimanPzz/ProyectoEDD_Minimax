package Minimax.Mesa

import Estructuras.Lista
import Minimax.Actores.Actor
import Minimax.Actores.Jugador
import Minimax.Minimax

/**
 * El tablero de juego.
 *
 * @param fichas las fichas que serán asignadas a los nodos
 * @property jugador1 Minimax.Actores.Jugador humano de la partida.
 * @property nodosTablero Los nodos del tablero.
 * @property nodosJugador Los nodos pertenecientes a [jugador1] en ese instante.
 * @constructor Se crean todas las vecindades y asignamos los nodos de los [Actor] en el juego.
 */
class Tablero(private val fichas: Array<Ficha>, private val jugador1: Jugador, private val jugador2 : Actor, private var mode  :ModoJuego, primero : Actor) {

    private var nodosTablero: Array<Nodo> = Array(5) { i -> Nodo(i, null) }
    private var nodosJugador: Lista<Nodo> = Lista<Nodo>()
    private var nodosComputa: Lista<Nodo> = Lista<Nodo>()

    private var siguienteJugador: Actor = primero
    var minimax : Int = 0
    var invalido : Boolean = false
    var ultimoMovimieno : Int = 0


     fun estableceOrden(): Actor {
        println("Decidamos quien sera el primer jugador")
        println("¿Quien sera el primer jugador 1.$jugador1 o 2.$jugador2?")
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
        return if(i == 1) jugador1
        else jugador2
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
        jugador2.nodosJugador.agregaFinal(nodosTablero[1])
        jugador2.nodosJugador.agregaFinal(nodosTablero[3])
        nodosComputa = jugador2.nodosJugador

    }

    fun mueveSiguiente(){
        mueveActor(siguienteJugador)
        siguienteJugador = when(siguienteJugador){
            jugador1 -> jugador2
            jugador2 -> jugador1
            else -> throw RuntimeException()
        }
    }

    private fun mueveSiguienteEspecifico(i  :Int) : Boolean{
        return if(siguienteJugador.mueveEspecifico(i)){
            ultimoMovimieno = i
            siguienteJugador= when(siguienteJugador){
                jugador1 -> jugador2
                jugador2 -> jugador1
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
        if(actor != jugador1 && actor != jugador2) throw IllegalArgumentException("Tu no estas jugando -_-")
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
                jugador1 -> minimax = -1
                //Jugador 2 Pierde
                jugador2 -> minimax = 1
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
            jugador1 -> 1
            jugador2 -> -1
            else -> 0
        }
    }


    fun clone() : Tablero{
        if(siguienteJugador == jugador1){
            val clon1 = jugador1.clone()
            return Tablero(fichas, clon1, jugador2.clone(), mode ,clon1)
        }
        else{
            val clon2 = jugador2.clone()
            return Tablero(fichas, jugador1.clone(), clon2,mode , clon2)
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