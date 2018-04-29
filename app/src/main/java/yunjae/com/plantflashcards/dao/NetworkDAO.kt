package yunjae.com.plantflashcards.dao

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection

/**
 * Created by jonesb on 4/24/2015.
 */
class NetworkDAO {

    /**
     * Execute the given URI, and return the data from that URI.
     * @param uri the universal resource indicator for a set of data.
     * *
     * @return the set of data provided by the uri
     */
    @Throws(IOException::class)
    fun request(uri: String): String {
        val sb = StringBuilder()

        val url = URL(uri)
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val bis = BufferedInputStream(urlConnection.inputStream)
            val bin = BufferedReader(InputStreamReader(bis))
            // temporary string to hold each line read from the reader.
            var inputLine: String?
            inputLine = bin.readLine()
            while (inputLine != null) {
                sb.append(inputLine)
                inputLine = bin.readLine()
            }
        } finally {
            // regardless of success or failure, we will disconnect from the URLConnection.
            urlConnection.disconnect()
        }
        return sb.toString()
    }

    fun populatePicture(pictureName: String?) : Bitmap? {
        // declare our return type.
        var bitmap:Bitmap? = null

        // compose a picture URI
        val pictureURI = PLANTPLACES_COM + "/photos/mini/$pictureName"
        val url = URL(pictureURI)
        val inputStream = url.openConnection().getInputStream()
        if (inputStream != null) {
            bitmap = BitmapFactory.decodeStream(inputStream)
        }

        return bitmap
    }

    companion object {
        val PLANTPLACES_COM = "http://www.plantplaces.com"
    }

}