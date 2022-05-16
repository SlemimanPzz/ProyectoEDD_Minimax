package Minimax.Mesa

import Minimax.Actores.Actor

/**
 * Fichas dentro del juego, con su [id] y [propietario].
 *
 * @property id ID de la ficha.
 * @property propietario Propietario de la ficha.
 */
class Ficha (val id: Int, val propietario: Actor){

    /**
     * [toString] de ficha
     *
     * @return La representación en cadena de la ficha
     */
    override fun toString(): String {
        return "%01d $propietario".format(id)
    }
    fun clone(): Ficha {
        return Ficha(id, propietario)
    }

}