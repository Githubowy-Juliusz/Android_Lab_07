package lab.main.pong

import android.util.Log
import android.view.View
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.random.Random

class BallMovingThread(
	private val view: View,
	private val speed: Float,
	private val mover: RectMover,
	private val runOnUiThread: (action: Runnable) -> Unit
) {
	private val thread = Thread(Runnable {
		threadLoop()
	})
	private val token: AtomicBoolean = AtomicBoolean(true)
	private val frameTime = 16L
	fun restart() {
		token.set(false)
		thread.join()
		token.set(true)
		thread.start()
	}

	fun stop() {
		token.set(false)
		thread.join()
	}

	private fun threadLoop() {
		Thread.sleep(1000)
		val location = IntArray(2)
		view.getLocationOnScreen(location)
		var x = location[0].toFloat()
		var y = location[1].toFloat()

		val xmovement = Random.nextFloat()
		val ymovement = 1f
		var xsign = Random.nextInt(0, 2) * 2 - 1
		var ysign = Random.nextInt(0, 2) * 2 - 1

		while(token.get()) {
			x += speed * (frameTime / 1000f) * xmovement * xsign
			y += speed * (frameTime / 1000f) * ymovement * ysign
			val dx = (x + mover.rectWidth / 2f).toInt()
			val dy = (y + mover.rectHeight / 2f).toInt()
			runOnUiThread(Runnable { mover.move(dx, dy) })
			Thread.sleep(frameTime)
			if(mover.xCollision) {
				xsign *= -1
				view.getLocationOnScreen(location)
				x = location[0].toFloat()
			}
			if(mover.yCollision) {
				Log.d("Hello", "yCollision, $dy, $ysign, $ymovement")
				ysign *= -1
				view.getLocationOnScreen(location)
				y = location[1].toFloat()
				if(y > 1000)
					y -= 20
			}
		}
	}
}