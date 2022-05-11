class Tablero(private val fichas: Array<Ficha>, val jugador: Jugador) {


    private val nodosTablero: Array<Nodo> = Array(5) { i -> Nodo(i, null) }

    private class Nodo(val i : Int, var valor : Ficha?, val vecindad: Lista<Nodo> = Lista<Nodo>()){

        override fun toString(): String {
            if(valor != null) return "|$valor|"
            if(i == 2) return " |(2)| "
            return "| ($i) |"
        }

    }

    init {

        //Añadimos la vecindades a cada nodo
        nodosTablero[0].vecindad.add(nodosTablero[1])
        nodosTablero[0].vecindad.add(nodosTablero[2])
        nodosTablero[0].vecindad.add(nodosTablero[3])


        nodosTablero[1].vecindad.add(nodosTablero[0])
        nodosTablero[1].vecindad.add(nodosTablero[2])
        nodosTablero[1].vecindad.add(nodosTablero[4])

        nodosTablero[2].vecindad.add(nodosTablero[0])
        nodosTablero[2].vecindad.add(nodosTablero[1])
        nodosTablero[2].vecindad.add(nodosTablero[3])
        nodosTablero[2].vecindad.add(nodosTablero[4])

        nodosTablero[3].vecindad.add(nodosTablero[0])
        nodosTablero[3].vecindad.add(nodosTablero[2])

        nodosTablero[4].vecindad.add(nodosTablero[1])
        nodosTablero[4].vecindad.add(nodosTablero[2])


        nodosTablero[0].valor = fichas[0]
        nodosTablero[1].valor = fichas[3]
        nodosTablero[3].valor = fichas[2]
        nodosTablero[4].valor = fichas[1]


    }

    private fun atrapado(n : Nodo?): Boolean {
        if(n == null) return  false
        n.vecindad.forEach { if(it.valor == null)  return  false}
        return true
    }

    fun mueveJugador(){
        println("A donde quieres mover")
        var porMover = true
        while(porMover){
            val i = readln().toInt()
            if(nodosTablero[i].valor != null){
                println("Mocviste")
                porMover = false
            } else{
                println("No se puede hacer ese moviemiento")
            }

        }
    }
    override fun toString(): String {
        val str = """
            ${nodosTablero[0]}---${nodosTablero[1]}
              |    \ /    |
              |  ${nodosTablero[2]}  |
              |    / \    |
            ${nodosTablero[3]}   ${nodosTablero[4]}
            """.trimIndent()
        return str
    }
}