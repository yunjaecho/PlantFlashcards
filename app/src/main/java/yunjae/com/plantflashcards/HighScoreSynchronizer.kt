package yunjae.com.plantflashcards

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class HighScoreSynchronizer: BroadcastReceiver() {

    var power = false

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(Intent.ACTION_POWER_CONNECTED)) {
            // We are here because the device is connected to power
            power = true
            synchronized(context)
        } else if (intent?.action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
            // We are here because the device is not connected to power
            power = false
            synchronized(context)
        }
    }

    private fun synchronized(context: Context?) {
        if (power) {
            Toast.makeText(context, "ACTION_POWER_CONNECTED", Toast.LENGTH_LONG).show()

        } else {
            Toast.makeText(context, "ACTION_POWER_DISCONNECTED", Toast.LENGTH_LONG).show()
        }
    }
}