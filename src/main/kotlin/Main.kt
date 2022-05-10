import  kotlin.io.readln


fun main() {
    println("Hello World!")

    println("Dime tu nombre:")
    var s : String  = ""
    while(s.length < 3){
        println("ingresa:")
        s = readln()
    }
    println("Tu nombre sera $s, estas seguro que quieres ese nombre.")



    val humano  = Jugador(s)
    val compu = Computadora()
    val fichas = arrayOf(Ficha(1, humano), Ficha(2, humano), Ficha(3, compu), Ficha(4, compu))


    val tablero = Tablero(fichas)
    println(tablero)
    println("Hola como estas?")

}