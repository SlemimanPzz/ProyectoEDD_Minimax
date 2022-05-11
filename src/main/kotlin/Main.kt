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



    val humano  = Jugador(s, null, null)
    val compu = Computadora()
    val fichas = arrayOf(Ficha(1, humano), Ficha(2, humano), Ficha(3, compu), Ficha(4, compu))
    humano.ficha1 = fichas[0]
    humano.ficha2 = fichas[1]
    compu.ficha1 = fichas[2]
    compu.ficha2 = fichas[3]


    val tablero = Tablero(fichas, humano)

    println(tablero)

    tablero.mueveJugador()
    print(tablero)
}