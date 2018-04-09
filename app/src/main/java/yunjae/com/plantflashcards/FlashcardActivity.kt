package yunjae.com.plantflashcards

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View

import kotlinx.android.synthetic.main.activity_flashcard.*
import kotlinx.android.synthetic.main.content_flashcard.*
import yunjae.com.plantflashcards.dto.Plant
import yunjae.com.plantflashcards.service.PlantService

class FlashcardActivity : AppCompatActivity() {

    val CAMEMA_ACTIVITY_REQUEST: Int = 10


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcard)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    fun onButton2Clicked(view: View) {
        val redbud = Plant(guid = 83, genus = "Cercis", species = "canadensis", cultivar = "", common = "Estern Redbud")
        val pawpaw = Plant(guid = 100, genus = "Asimina", species = "triloba", cultivar = "Alleghany", common = "Alleghany pawpaw", height = 10)
        val i = 1 + 1
    }

    fun onButton3Clicked(view: View) {
        val getPlantsActiviity = GetPlantsActiviity()
        getPlantsActiviity.execute("1")
    }

    fun onButton4Clicked(view: View) {
//        Toast.makeText(this, "You click me!", Toast.LENGTH_LONG)
//        var plant = Plant(1, "genus", "species", "cultivar" , "common", 5)

        //create an implicit intent to invoke the camera
        val cameraActivityIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraActivityIntent, CAMEMA_ACTIVITY_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMEMA_ACTIVITY_REQUEST) {
                // I'm hearing back from the camera
                val image = data?.extras?.get("data") as Bitmap

                imageView.setImageBitmap(image)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_flashcard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    inner class GetPlantsActiviity: AsyncTask<String, Int, List<Plant>>() {
        /**
         *
         * @param result List<Plant>?
         * @return Unit
         */
        override fun onPostExecute(result: List<Plant>?) {
            super.onPostExecute(result)
        }

        /**
         *
         * @param search String?
         * @return List<Plant>
         */
        override fun doInBackground(vararg search: String?): List<Plant> {
            val difficulty = search[0]
            val plantService = PlantService()

            return plantService.parsePlantsFromJsonData(difficulty)
        }

    }


}
