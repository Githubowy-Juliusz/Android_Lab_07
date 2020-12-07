package lab.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import lab.main.pong.PongFragment

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		val pongFragment = PongFragment(::runOnUiThread)


		supportFragmentManager.beginTransaction().apply {
			replace(R.id.frameLayout, pongFragment)
			commit()
		}
	}
}