package lab.main.pong

import android.view.MotionEvent
import android.view.View

class ButtonTouchListener(
	private val ballMovingThread: PaddleMovingThread,
	private val sign: Int
) :
	View.OnTouchListener {
	override fun onTouch(view: View, event: MotionEvent): Boolean {
		when(event.action) {
			MotionEvent.ACTION_DOWN -> {
				ballMovingThread.restart(sign)
			}
			MotionEvent.ACTION_UP -> {
				ballMovingThread.stop()
			}
		}
		return true
	}
}