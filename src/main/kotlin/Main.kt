import Estructuras.Lista
import  kotlin.io.readln

fun main() {
    println("Hello World!")

    println("Dime tu nombre:")
    var s = ""
    while(s.length < 3){
        s = readln()
        if(s.length < 3) println("Tu nombre debe tener 3 o mas caracteres.")
    }
    println("Tu nombre sera $s.")

    val humano  = Jugador(s, Lista<Nodo>(), null)
    val comp = Computadora(Lista<Nodo>(), humano, ModoJuego.RANDOM)
    humano.oponente = comp
    val fichas = arrayOf(Ficha(1, humano), Ficha(2, humano), Ficha(3, comp), Ficha(4, comp))

    val tablero = Tablero(fichas, humano, comp)

    println(tablero)

    var x = true
    while (x){
        tablero.mueveActor(humano)
        println(tablero)
        tablero.mueveActor(comp)
        println(tablero)
        if(tablero.equals(null)) x = false
    }
}