package yunjae.com.plantflashcards.dto

class Plant(var guid: Int, var genus: String, var species: String,
            var cultivar: String, var common: String, var height: Int =0, var photoName: String = "") {
    constructor(): this(0, "", "", "", "")

    override fun toString(): String {
        return "Plant(guid=$guid, genus='$genus', species='$species', cultivar='$cultivar', common='$common', height=$height, photoName='$photoName')"
    }


}
