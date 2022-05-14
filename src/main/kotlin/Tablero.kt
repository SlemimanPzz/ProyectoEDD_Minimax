import Estructuras.Lista
import kotlin.UnsupportedOperationException
import kotlin.system.exitProcess


/**
 * El tablero de juego.
 *
 * @param fichas las fichas que serán asignadas a los nodos
 * @property jugador1 Jugador humano de la partida.
 * @property nodosTablero Los nodos del tablero.
 * @property nodosJugador Los nodos pertenecientes a [jugador1] en ese instante.
 * @constructor Se crean todas las vecindades y asignamos los nodos de los [Actor] en el juego.
 */
class Tablero(private val fichas: Array<Ficha>, private val jugador1: Jugador, private val jugador2 : Actor) {

    private var nodosTablero: Array<Nodo> = Array(5) { i -> Nodo(i, null) }
    private var nodosJugador: Lista<Nodo> = Lista<Nodo>()
    private var nodosComputa: Lista<Nodo> = Lista<Nodo>()

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

    /**
     * Hace que el [actor] haga un movimiento
     *
     * @param actor Quien realizara el movimiento, tiene que ser un jugador en el tablero.
     *
     * @throws IllegalArgumentException Si el parameter no es un [actor] en el tablero
     */
    fun mueveActor(actor : Actor){
        if(actor != jugador1 && actor != jugador2) throw IllegalArgumentException("Tu no estas jugando -_-")
        actor.mueve()
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