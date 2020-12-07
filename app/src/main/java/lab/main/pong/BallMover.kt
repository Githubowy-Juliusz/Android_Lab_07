package lab.main.pong

import android.view.View

class BallMover(
	val ball: View,
	val rectWidth: Int,
	val rectHeight: Int,
	val topOffset: Int,
	val bottomOffset: Int,
	val downPaddle: View,
	val upPaddle: View,
	val paddleWidth: Int,
	val paddleHeight: Int,
	val controller: GameController
) : RectMover() {
	override fun move(x: Int, y: Int) {
		var l = x - rectWidth / 2
		var t = y - topOffset - rectHeight / 2
		var r = l + rectWidth
		var b = t + rectHeight
		val downPaddleLocation = IntArray(2)
		val upPaddleLocation = IntArray(2)
		downPaddle.getLocationOnScreen(downPaddleLocation)
		upPaddle.getLocationOnScreen(upPaddleLocation)
		val downPaddleL = downPaddleLocation[0]
		val downPaddleT = downPaddleLocation[1] - topOffset
		val downPaddleR = downPaddleL + paddleWidth
		val downPaddleB = downPaddleT + paddleHeight
		val upPaddleL = upPaddleLocation[0]
		val upPaddleT = upPaddleLocation[1] - topOffset
		val upPaddleR = upPaddleL + paddleWidth
		val upPaddleB = upPaddleT + paddleHeight
		if(t < 0) {
			t = 0
			b = rectHeight
			controller.restart("UP")
		}
		if(r > screenWidth) {
			r = screenWidth
			l = r - rectWidth
			_xCollision = true
		}
		if(l < 0) {
			l = 0
			r = rectWidth
			_xCollision = true
		}
		if(b > screenHeight - bottomOffset) {
			b = screenHeight - bottomOffset
			t = b - rectHeight
			controller.restart("DOWN")
		}
		if(r > upPaddleL && l < upPaddleR) {
			if(t < upPaddleB && t > upPaddleT) {
				t = upPaddleB
				b = rectHeight
				_yCollision = true
			}
		}
		if(r > downPaddleL && l < downPaddleR) {
			if(b > downPaddleT && b < downPaddleB) {
				b = downPaddleT
				t = b - rectHeight
				_yCollision = true
			}
		}
		ball.layout(l, t, r, b)
	}
}