class Jugador(private val nombre :String): Actor  {
    override fun toString(): String {
        return nombre.substring(0 .. 2)
    }

}