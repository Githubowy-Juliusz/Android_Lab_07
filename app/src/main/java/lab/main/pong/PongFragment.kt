package lab.main.pong

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
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

		val downLeftButton = view.findViewById<Button>(R.id.pongDownLeftButton)
		val downRightButton = view.findViewById<Button>(R.id.pongDownRightButton)
		val upLeftButton = view.findViewById<Button>(R.id.pongUpLeftButton)
		val upRightButton = view.findViewById<Button>(R.id.pongUpRightButton)

		val ballMover = RectMover(ball, ballSize, ballSize, topOffset, bottomOffset)
		ballMovingThread =
			BallMovingThread(ball, 800f, ballMover, runOnUiThread)
		ballMovingThread.restart()
		val downPaddleMover =
			RectMover(downPaddle, paddleWidth, paddleHeight, topOffset, bottomOffset)
		val upPaddleMover =
			RectMover(upPaddle, paddleWidth, paddleHeight, topOffset, bottomOffset)
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
	}

	override fun onDestroyView() {
		super.onDestroyView()
		ballMovingThread.stop()
	}
}