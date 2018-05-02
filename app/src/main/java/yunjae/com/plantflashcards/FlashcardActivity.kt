package yunjae.com.plantflashcards

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View

import kotlinx.android.synthetic.main.activity_flashcard.*
import kotlinx.android.synthetic.main.content_flashcard.*
import yunjae.com.plantflashcards.dao.NetworkDAO
import yunjae.com.plantflashcards.dto.Plant
import yunjae.com.plantflashcards.service.PlantService

class FlashcardActivity : AppCompatActivity() {

    val CAMEMA_ACTIVITY_REQUEST: Int = 10
    var allPlants: List<Plant> = ArrayList<Plant>()
    var correctAnswer: Int = 0
    var answerCorrectly = 0
    var answerIncorrectly = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcard)
        setSupportActionBar(toolbar)

        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/

        fab.setOnClickListener{
            val getPlantsTask = GetPlantsTask()
            getPlantsTask.execute("")
            button1.setBackgroundColor(Color.LTGRAY)
            button2.setBackgroundColor(Color.LTGRAY)
            button3.setBackgroundColor(Color.LTGRAY)
            button4.setBackgroundColor(Color.LTGRAY)
        }

    }

    private fun evalute(guess: Int) {
        when(correctAnswer) {
            0 -> button1.setBackgroundColor(Color.GREEN)
            1 -> button2.setBackgroundColor(Color.GREEN)
            2 -> button3.setBackgroundColor(Color.GREEN)
            3 -> button4.setBackgroundColor(Color.GREEN)
        }

        if (guess == correctAnswer) {
            txtStatus.setText("Correct!")
            answerCorrectly++;

            txtCorrectAnswers?.setText("$answerCorrectly")

        } else {
            val correct = allPlants.get(correctAnswer).toString()
            txtStatus.setText("Incorrect. The correct plant is : $correct")
            answerIncorrectly++;
            txtWrongAnswers.setText("$answerIncorrectly")
        }
    }

    fun onButton1Clicked(view: View) {
        /*var randomNumber = (Math.random() * 4).toInt() + 1

        when(randomNumber) {
            1 -> button1.setBackgroundColor(Color.GREEN)
            2 -> button2.setBackgroundColor(Color.GREEN)
            3 -> button3.setBackgroundColor(Color.GREEN)
            4 -> button4.setBackgroundColor(Color.GREEN)
        }*/

        evalute(0)
    }

    fun onButton2Clicked(view: View) {
        /*val allPlants = ArrayList<Plant>()
        val redbud = Plant(guid = 83, genus = "Cercis", species = "canadensis", cultivar = "", common = "Estern Redbud")
        allPlants.add(redbud)
        val pawpaw = Plant(guid = 100, genus = "Asimina", species = "triloba", cultivar = "Alleghany", common = "Alleghany pawpaw", height = 10)
        allPlants.add(pawpaw)
        val i = 1 + 1


        button1.setBackgroundColor(Color.LTGRAY)
        button2.setBackgroundColor(Color.LTGRAY)
        button3.setBackgroundColor(Color.LTGRAY)
        button4.setBackgroundColor(Color.LTGRAY)*/
        evalute(1)
    }

    fun onButton3Clicked(view: View) {
        /*val getPlantsActiviity = GetPlantsTask()
        // execute will create a new thread, and invoke doInBackground in that new thread.
        getPlantsActiviity.execute("1")*/

        evalute(2)
    }

    fun onButton4Clicked(view: View) {
        /*//create an implicit intent to invoke the camera
        val cameraActivityIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraActivityIntent, CAMEMA_ACTIVITY_REQUEST)*/
        evalute(3)
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

    inner class GetPlantsTask: AsyncTask<String, Int, List<Plant>>() {
        /**
         *
         * @param result List<Plant>?
         * @return Unit
         */
        override fun onPostExecute(result: List<Plant>?) {
            super.onPostExecute(result)
            var numberResults = result?.size ?: 0

            if (numberResults > 3) {
                button1.text = result?.get(0).toString()
                button2.text = result?.get(1).toString()
                button3.text = result?.get(2).toString()
                button4.text = result?.get(3).toString()

                correctAnswer = (Math.random() * 4).toInt()

                val getPhotoTask = GetPhotoTask()
                val bitmap = getPhotoTask.execute(result?.get(correctAnswer)?.photoName)
                imageView.setImageBitmap(bitmap.get())

                allPlants = result!!
            }


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

    inner class GetPhotoTask: AsyncTask<String, Int, Bitmap>() {

        override fun doInBackground(vararg picture: String?): Bitmap? {
            val networkDAO = NetworkDAO()
            val bitmap = networkDAO.populatePicture(picture[0])
            return bitmap
        }

    }


}
