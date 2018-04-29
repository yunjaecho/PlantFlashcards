package yunjae.com.plantflashcards.service

import org.json.JSONObject
import yunjae.com.plantflashcards.dao.NetworkDAO
import yunjae.com.plantflashcards.dto.Plant

class PlantService {

    fun parsePlantsFromJsonData(difficulty: String?): List<Plant> {
        val allPlants = ArrayList<Plant>()

        val networkDAO = NetworkDAO()
        val plantJSONData = networkDAO.request("http://plantplaces.com/perl/mobile/flashcard.pl")

        val root = JSONObject(plantJSONData)
        val plantJSONArray = root.getJSONArray("values")

        var i = 0
        while(i < plantJSONArray.length()) {
            val plant = Plant()

            val plantJSON = plantJSONArray.getJSONObject(i)
            with(plantJSON) {
                plant.guid = getInt("plant")
                plant.genus = getString("genus")
                plant.species = getString("species")
                plant.cultivar = getString("cultivar")
                plant.common = getString("common")

                //"id":"24","plant":"9","genus":"Koelreuteria","species":"paniculata","cultivar":"","common":"Golden Rain Tree","picture_name":"Koelreuteria_paniculata_branch.JPG","description":"Branch of Koelreuteria paniculata","difficulty":"3","photo_credit":""
            }

            allPlants.add(plant)
            i++
        }

        return allPlants
    }
}