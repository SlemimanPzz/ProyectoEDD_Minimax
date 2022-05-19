import Estructuras.Lista
import minimax.actores.Computadora
import minimax.actores.Jugador
import minimax.mesa.Ficha
import minimax.mesa.ModoJuego
import minimax.mesa.Nodo
import minimax.mesa.Tablero
import kotlin.io.readln


fun main() {
    println("Bienvenido al Juego.")
    println("Dime tu nombre:")
    var s = ""
    while(s.length < 3){
        s = readln()
        if(s.length < 3) println("Tu nombre debe tener 3 o mas caracteres.")
    }
    println("Tu nombre sera $s.")
    Thread.sleep(600)
    println("Recuerda en cualquier momento puedes Cambiar de Modo con x")
    Thread.sleep(800)
    var i = 0
    println("Con que modo deseas empezar [1.Minimax|2.Random]")
    var mode  = ModoJuego.RANDOM
    while ( i !in 1 .. 2){
        try {
            i = readln().toInt()
            if(i !in 1 .. 2) println("Ingresa un numero valido")
            mode = when(i){
                1 -> ModoJuego.MINIMAX
                2 -> ModoJuego.RANDOM
                else -> ModoJuego.RANDOM
            }
        } catch (nfe : NumberFormatException){
            println("Ingresa un numero valido")
        }
    }
    println("Escogiste $mode, lo puedes cambiar después presionando x.")

    val humano  = Jugador(s, Lista<Nodo>(), null)
    val comp = Computadora(Lista<Nodo>(), humano,)
    humano.oponente = comp
    val fichas = arrayOf(Ficha(1, humano), Ficha(2, humano), Ficha(3, comp), Ficha(4, comp))
    val tablero = Tablero(fichas, humano, comp, mode, humano)
    tablero.estableceOrden()

    println(tablero)

    var x = true
    while (x){
        tablero.mueveSiguiente()
        Thread.sleep(1000)
        println(tablero)
        if(tablero.equals(null)) x = false
    }
}