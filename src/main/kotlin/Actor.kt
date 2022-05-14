import Estructuras.Lista

interface Actor {

    var nodosJugador : Lista<Nodo>
    var oponente : Actor?
    fun mueve()
}