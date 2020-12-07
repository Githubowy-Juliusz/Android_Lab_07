package lab.main.pong

import android.view.View

class PaddleMover(
	val view: View,
	val rectWidth: Int,
	val rectHeight: Int,
	val topOffset: Int,
	val bottomOffset: Int
) : RectMover() {

	override fun move(x: Int, y: Int) {
		var l = x - rectWidth / 2
		var t = y - topOffset - rectHeight / 2
		var r = l + rectWidth
		var b = t + rectHeight
		if(t < 0) {
			t = 0
			b = rectHeight
			_yCollision = true
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
			_yCollision = true
		}
		view.layout(l, t, r, b)
	}
}