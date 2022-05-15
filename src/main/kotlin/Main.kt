import Estructuras.Lista
import Minimax.Actores.Computadora
import Minimax.Actores.Jugador
import Minimax.Mesa.Ficha
import Minimax.Mesa.ModoJuego
import Minimax.Mesa.Nodo
import Minimax.Mesa.Tablero
import Minimax.Minimax
import  kotlin.io.readln

fun main() {
    println("Bienvenido al Juego.")
    println("Dime tu nombre:")
    var s = ""
    while(s.length < 3){
        s = readln()
        if(s.length < 3) println("Tu nombre debe tener 3 o mas caracteres.")
    }
    println("Tu nombre sera $s.")

    val humano  = Jugador(s, Lista<Nodo>(), null)
    val comp = Computadora(Lista<Nodo>(), humano, ModoJuego.MINIMAX)
    humano.oponente = comp
    val fichas = arrayOf(Ficha(1, humano), Ficha(2, humano), Ficha(3, comp), Ficha(4, comp))
    val tablero = Tablero(fichas, humano, comp, ModoJuego.MINIMAX, humano)
    tablero.estableceOrden()

    val arbolMinimax = Minimax(tablero)

    println(tablero)

    var x = true
    while (x){
        tablero.mueveSiguiente()
        Thread.sleep(1000)
        println(tablero)
        if(tablero.equals(null)) x = false
    }
}