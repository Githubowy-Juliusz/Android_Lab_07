package lab.main.pong

class GameController {
	lateinit var uiRestart: () -> Unit
	var up = 0
	var down = 0
	fun restart(winner: String) {
		if(winner == "UP")
			up++
		else
			down++
		uiRestart()
	}
}