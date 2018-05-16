package thevoid.iam.imeswithertest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import thevoid.iam.imeswitcher.*

class MainActivity : AppCompatActivity(), OnImeActionListener {

    lateinit var tv: TextView

    override fun onImeAction() {
        tv.text = "Done"
    }

    private val imeSwitcher = ImeSwitcher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val et1 = findViewById<EditText>(R.id.id1)
        val et2 = findViewById<EditText>(R.id.id2)
        val et3 = findViewById<EditText>(R.id.id3)
        val et4 = findViewById<EditText>(R.id.id4)
        val et5 = findViewById<EditText>(R.id.id5)
        val et6 = findViewById<EditText>(R.id.id6)

        tv = findViewById(R.id.id7)

        imeSwitcher.onCreate(
                email(et1),
                simple(et2),
                simple(et3),
                email(et4),
                simple(et5),
                custom(et6, { s -> isInt(s) }),
                callback = this)
    }

    private fun isInt(s: CharSequence): Boolean {
        val str = s.toString()
        var res = false
        try {
            str.toInt()
            res = true
        } catch (e: Exception) {

        }

        return res
    }
}
