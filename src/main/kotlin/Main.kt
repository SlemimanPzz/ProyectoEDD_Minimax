import Estructuras.Lista

fun main(args: Array<String>) {
    println("Hello World!")

    println("Dime tu nombre:")
    var s : String
    var yn : Int
    while (true){
        s = readln()
        println("Tu nombre sera $s, estas seguro que quieres ese nombre.Si(1)/No(0)")
        yn = readln().toInt()
        if(yn == 1) break
        println("Vuelve a ingresar tu nombre")
    }


    val humano  = Jugador(s)
    val compu = Computadora()
    val fichas = arrayOf(Ficha(1,humano), Ficha(2,humano), Ficha(3,compu), Ficha(4,compu))


    val tablero = Tablero(fichas)
    println(tablero)
    println("Hola")
}