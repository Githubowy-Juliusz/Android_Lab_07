package lab.main.pong

import android.view.View
import java.util.concurrent.atomic.AtomicBoolean

class PaddleMovingThread(
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
	private var xsign = 1
	fun restart(xsign: Int) {
		token.set(false)
		thread.join()
		this.xsign = xsign
		token.set(true)
		thread.start()
	}

	fun stop() {
		token.set(false)
		thread.join()
	}

	private fun threadLoop() {
		val location = IntArray(2)
		view.getLocationOnScreen(location)
		var x = location[0].toFloat()
		val y = location[1]

		val xmovement = 1f

		while(token.get()) {
			x += speed * (frameTime / 1000f) * xmovement * xsign
			val dx = (x + mover.rectWidth / 2f).toInt()
			runOnUiThread(Runnable { mover.move(dx, y) })
			Thread.sleep(frameTime)
			if(mover.xCollision) {
				return
			}
		}
	}
}