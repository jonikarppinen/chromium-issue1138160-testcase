package fi.iki.jonik.issue1138160

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val url = "https://www.google.fi/" // The crash happens with any url

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initButtons()
    }

    private fun initButtons() {

        val crashButton = findViewById<Button>(R.id.crash_button)
        crashButton.setOnClickListener {
            // Executing this code crashes every time, on any Android version, if Chrome /
            // Android System WebView version is 86.0.4240.75 (or later).
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.parse(url), "text/html")
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            startActivity(intent)
        }


        val workaroundButton = findViewById<Button>(R.id.workaround_button)
        workaroundButton.setOnClickListener {
            // With "text/html" removed, this works fine.
            // Alternatively, without CATEGORY_BROWSABLE it also would not crash.
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            startActivity(intent)
        }

    }

}