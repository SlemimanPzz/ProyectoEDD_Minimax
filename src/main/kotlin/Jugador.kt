class Jugador(val nombre :String, var ficha1: Ficha?, var ficha2: Ficha?): Actor {
    override fun toString(): String {
        return nombre.substring(0 .. 2)
    }

}