package lab.main.pong

import android.content.res.Resources


abstract class RectMover {
	val xCollision: Boolean
		get() {
			if(_xCollision) {
				val tmp = _xCollision
				_xCollision = false
				return tmp
			}
			return _xCollision
		}
	val yCollision: Boolean
		get() {
			if(_yCollision) {
				val tmp = _yCollision
				_yCollision = false
				return tmp
			}
			return _yCollision
		}
	protected val screenWidth = Resources.getSystem().displayMetrics.widthPixels
	protected val screenHeight = Resources.getSystem().displayMetrics.heightPixels

	protected var _xCollision = false
	protected var _yCollision = false
	
	abstract fun move(x: Int, y: Int)
}
