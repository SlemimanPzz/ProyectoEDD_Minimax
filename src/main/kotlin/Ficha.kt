class Ficha (val id: Int, private val dueno: Actor){
    override fun toString(): String {
        return "%01d $dueno".format(id)
    }
}