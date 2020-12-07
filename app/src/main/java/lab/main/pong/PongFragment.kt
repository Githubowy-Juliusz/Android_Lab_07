package lab.main.pong

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import lab.main.R

class PongFragment(
	private val runOnUiThread: (action: Runnable) -> Unit
) :
	Fragment(
		R.layout.pong_fragment
	) {
	private val ballSize = 50
	private val paddleWidth = 300
	private val paddleHeight = 50

	private val topOffset = 195
	private val bottomOffset = 220
	private lateinit var ballMovingThread: BallMovingThread
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val ball = view.findViewById<ImageView>(R.id.pongBall)
		val downPaddle = view.findViewById<ImageView>(R.id.pongDownPaddle)
		val upPaddle = view.findViewById<ImageView>(R.id.pongUpPaddle)

		val score = view.findViewById<TextView>(R.id.pongScore)

		val downLeftButton = view.findViewById<Button>(R.id.pongDownLeftButton)
		val downRightButton = view.findViewById<Button>(R.id.pongDownRightButton)
		val upLeftButton = view.findViewById<Button>(R.id.pongUpLeftButton)
		val upRightButton = view.findViewById<Button>(R.id.pongUpRightButton)

		val ballInitialLocation = IntArray(2)
		val upPaddleLocation = IntArray(2)
		val downPaddleLocation = IntArray(2)
		ball.getLocationOnScreen(ballInitialLocation)
		upPaddle.getLocationOnScreen(upPaddleLocation)
		downPaddle.getLocationOnScreen(downPaddleLocation)

		val controller = GameController()
		val ballMover = BallMover(
			ball,
			ballSize,
			ballSize,
			topOffset,
			bottomOffset,
			downPaddle,
			upPaddle,
			paddleWidth,
			paddleHeight,
			controller
		)
		ballMovingThread =
			BallMovingThread(ball, 800f, ballMover, runOnUiThread)
		ballMovingThread.restart()
		val downPaddleMover =
			PaddleMover(
				downPaddle,
				paddleWidth,
				paddleHeight,
				topOffset,
				bottomOffset
			)
		val upPaddleMover =
			PaddleMover(upPaddle, paddleWidth, paddleHeight, topOffset, bottomOffset)
		val downPaddleMovingThread =
			PaddleMovingThread(downPaddle, 400f, downPaddleMover, runOnUiThread)
		val upPaddleMovingThread =
			PaddleMovingThread(upPaddle, 400f, upPaddleMover, runOnUiThread)
		downLeftButton.setOnTouchListener(
			ButtonTouchListener(
				downPaddleMovingThread,
				-1
			)
		)
		downRightButton.setOnTouchListener(
			ButtonTouchListener(
				downPaddleMovingThread,
				1
			)
		)
		upLeftButton.setOnTouchListener(
			ButtonTouchListener(
				upPaddleMovingThread,
				-1
			)
		)
		upRightButton.setOnTouchListener(
			ButtonTouchListener(
				upPaddleMovingThread,
				1
			)
		)

		fun restart() {
			ballMovingThread.stop()
			var l = ballInitialLocation[0]
			var t = ballInitialLocation[1]
			var r = l + ballSize
			var b = t + ballSize
			ball.layout(l, t, r, b)
			l = downPaddleLocation[0]
			t = downPaddleLocation[1]
			r = l + paddleWidth
			b = t + paddleHeight
			downPaddle.layout(l, t, r, b)
			t = upPaddleLocation[1]
			b = t + paddleHeight
			upPaddle.layout(l, t, r, b)
			score.setText(controller.up.toString() + "\n\n\n\n" + controller.down.toString())
			ballMovingThread.restart()
		}
		controller.uiRestart = ::restart
	}

	override fun onDestroyView() {
		super.onDestroyView()
		ballMovingThread.stop()
	}
}