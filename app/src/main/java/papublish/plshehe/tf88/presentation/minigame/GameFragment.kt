package papublish.plshehe.tf88.presentation.minigame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.findNavController
import papublish.plshehe.tf88.R
import papublish.plshehe.tf88.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    private var _binding : FragmentGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var board: MutableMap<Int, String>
    private var isCircle = false
    private var moveNumber = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentGameBinding.inflate(inflater,container,false)
        hideSystemBars()

        setBoard()

        //region Call Button Click Listeners
        btn0OnClick()
        btn1OnClick()
        btn2OnClick()
        btn3OnClick()
        btn4OnClick()
        btn5OnClick()
        btn6OnClick()
        btn7OnClick()
        btn8OnClick()

        btnQuitGameOnClick()
        btnRestartGameOnClick()

        setTitle("Player X's turn!")
        return binding.root
    }


    private fun hideSystemBars() {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(requireActivity().window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    private fun setBoard() {
        board = mutableMapOf<Int, String>(0 to "-", 1 to "-", 2 to "-",
            3 to "-", 4 to "-", 5 to "-",
            6 to "-", 7 to "-", 8 to "-")
    }

    private fun setTitle(title: String) {
        binding.txtTitle.text = title
    }

    private fun updateBoard(index: Int, player: String) {
        board[index] = player
    }

    private fun switchTurns() {
        isCircle = !isCircle
        if (isCircle) {
            setTitle("Player O's turn!")
        } else {
            setTitle("Player X's turn!")
        }
    }

    private fun incrementMoveNumber() {
        moveNumber++
        binding.txtMoveNumber.text = "Move number: $moveNumber"
    }

    private fun checkWinner(board: Map<Int, String>): String {
        if (board[0] == board[1] && board[0] == board[2] && board[0] != "-") { // first row
            binding.btn0.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_win)
            binding.btn1.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_win)
            binding.btn2.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_win)
            disableAllButtons()
            return board[0]!!
        } else if (board[3] == board[4] && board[3] == board[5] && board[3] != "-") { // second row
            binding.btn3.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_win)
            binding.btn4.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_win)
            binding.btn5.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_win)
            disableAllButtons()
            return board[3]!!
        } else if (board[6] == board[7] && board[6] == board[8] && board[6] != "-") { // third row
            binding.btn6.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_win)
            binding.btn7.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_win)
            binding.btn8.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_win)
            disableAllButtons()
            return board[6]!!
        } else if (board[0] == board[3] && board[0] == board[6] && board[0] != "-") { // first column
            binding.btn0.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_win)
            binding.btn3.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_win)
            binding.btn6.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_win)
            disableAllButtons()
            return board[0]!!
        } else if (board[1] == board[4] && board[1] == board[7] && board[1] != "-") { // second column
            binding.btn1.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_win)
            binding.btn4.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_win)
            binding.btn7.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_win)
            disableAllButtons()
            return board[1]!!
        } else if (board[2] == board[5] && board[2] == board[8] && board[2] != "-") { // third column
            binding.btn2.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_win)
            binding.btn5.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_win)
            binding.btn8.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_win)
            disableAllButtons()
            return board[2]!!
        } else if (board[0] == board[4] && board[0] == board[8] && board[0] != "-") { // first diagonal
            binding.btn0.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_win)
            binding.btn4.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_win)
            binding.btn8.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_win)
            disableAllButtons()
            return board[0]!!
        } else if (board[2] == board[4] && board[2] == board[6] && board[2] != "-") { // second diagonal
            binding.btn2.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_win)
            binding.btn4.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_win)
            binding.btn6.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_win)
            disableAllButtons()
            return board[2]!!
        } else {
            return "-" // no winner
        }
    }

    private fun areSpotsLeft(board: Map<Int, String>): Boolean {
        for ((index, spot) in board) {
            if (spot == "-") {
                return true // is at least one spot left empty
            }
        }
        disableAllButtons()
        return false // not spots left empty
    }

    private fun btn0OnClick() {
        binding.btn0.setOnClickListener {
            if (isCircle) {
                binding.btn0.text = "O" // set button text
                updateBoard(0, "O") // update map
                binding.btn0.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_selected) // change button background
            } else {
                binding.btn0.text = "X" // set button text
                updateBoard(0, "X") // update map
                binding.btn0.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_selected) // change button background
            }
            binding.btn0.isEnabled = false // disable clicking this button

            // we check if there is a winner
            val winner = checkWinner(board)
            if (winner != "-") {
                if (isCircle) {
                    setTitle("O has won!")
                } else {
                    setTitle("X has won!")
                }
            } else if (!areSpotsLeft(board)) { // we check if it is a tie
                setTitle("It's a tie!")
            } else {
                switchTurns()
                incrementMoveNumber()
            }
        }
    }

    private fun btn1OnClick() {
        binding.btn1.setOnClickListener {
            if (isCircle) {
                binding.btn1.text = "O" // set button text
                updateBoard(1, "O") // update map
                binding.btn1.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_selected) // change button background
            } else {
                binding.btn1.text = "X" // set button text
                updateBoard(1, "X") // update map
                binding.btn1.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_selected) // change button background
            }
            binding.btn1.isEnabled = false // disable clicking this button

            // we check if there is a winner
            val winner = checkWinner(board)
            if (winner != "-") {
                showRestartButton()
                if (isCircle) {
                    setTitle("O has won!")
                } else {
                    setTitle("X has won!")
                }
            } else if (!areSpotsLeft(board)) { // we check if it is a tie
                setTitle("It's a tie!")
                showRestartButton()
            } else {
                switchTurns()
                incrementMoveNumber()
            }
        }
    }

    private fun btn2OnClick() {
        binding.btn2.setOnClickListener {
            if (isCircle) {
                binding.btn2.text = "O" // set button text
                updateBoard(2, "O") // update map
                binding.btn2.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.rounded_selected
                ) // change button background
            } else {
                binding.btn2.text = "X" // set button text
                updateBoard(2, "X") // update map
                binding.btn2.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.rounded_selected
                ) // change button background
            }
            binding.btn2.isEnabled = false

            // we check if there is a winner
            val winner = checkWinner(board)
            if (winner != "-") {
                showRestartButton()
                if (isCircle) {
                    setTitle("O has won!")
                } else {
                    setTitle("X has won!")
                }
            } else if (!areSpotsLeft(board)) { // we check if it is a tie
                setTitle("It's a tie!")
                showRestartButton()
            } else {
                switchTurns()
                incrementMoveNumber()
            }
        }
    }

    private fun btn3OnClick() {
        binding.btn3.setOnClickListener {
            if (isCircle) {
                binding.btn3.text = "O" // set button text
                updateBoard(3, "O") // update map
                binding.btn3.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.rounded_selected
                ) // change button background
            } else {
                binding.btn3.text = "X" // set button text
                updateBoard(3, "X") // update map
                binding.btn3.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.rounded_selected
                ) // change button background
            }
            binding.btn3.isEnabled = false

            // we check if there is a winner
            val winner = checkWinner(board)
            if (winner != "-") {
                showRestartButton()
                if (isCircle) {
                    setTitle("O has won!")
                } else {
                    setTitle("X has won!")
                }
            } else if (!areSpotsLeft(board)) { // we check if it is a tie
                setTitle("It's a tie!")
                showRestartButton()
            } else {
                switchTurns()
                incrementMoveNumber()
            }
        }
    }


    private fun btn4OnClick() {
        binding.btn4.setOnClickListener {
            if (isCircle) {
                binding.btn4.text = "O" // set button text
                updateBoard(4, "O") // update map
                binding.btn4.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.rounded_selected
                ) // change button background
            } else {
                binding.btn4.text = "X" // set button text
                updateBoard(4, "X") // update map
                binding.btn4.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.rounded_selected
                ) // change button background
            }
            binding.btn4.isEnabled = false

            // we check if there is a winner
            val winner = checkWinner(board)
            if (winner != "-") {
                showRestartButton()
                if (isCircle) {
                    setTitle("O has won!")
                } else {
                    setTitle("X has won!")
                }
            } else if (!areSpotsLeft(board)) { // we check if it is a tie
                setTitle("It's a tie!")
                showRestartButton()
            } else {
                switchTurns()
                incrementMoveNumber()
            }
        }
    }

    private fun btn5OnClick() {
        binding.btn5.setOnClickListener {
            if (isCircle) {
                binding.btn5.text = "O" // set button text
                updateBoard(5, "O") // update map
                binding.btn5.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.rounded_selected
                ) // change button background
            } else {
                binding.btn5.text = "X" // set button text
                updateBoard(5, "X") // update map
                binding.btn5.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.rounded_selected
                ) // change button background
            }
            binding.btn5.isEnabled = false

            // we check if there is a winner
            val winner = checkWinner(board)
            if (winner != "-") {
                showRestartButton()
                if (isCircle) {
                    setTitle("O has won!")
                } else {
                    setTitle("X has won!")
                }
            } else if (!areSpotsLeft(board)) { // we check if it is a tie
                setTitle("It's a tie!")
                showRestartButton()
            } else {
                switchTurns()
                incrementMoveNumber()
            }
        }
    }

    private fun btn6OnClick() {
        binding.btn6.setOnClickListener {
            if (isCircle) {
                binding.btn6.text = "O" // set button text
                updateBoard(6, "O") // update map
                binding.btn6.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.rounded_selected
                ) // change button background
            } else {
                binding.btn6.text = "X" // set button text
                updateBoard(6, "X") // update map
                binding.btn6.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.rounded_selected
                ) // change button background
            }
            binding.btn6.isEnabled = false

            // we check if there is a winner
            val winner = checkWinner(board)
            if (winner != "-") {
                showRestartButton()
                if (isCircle) {
                    setTitle("O has won!")
                } else {
                    setTitle("X has won!")
                }
            } else if (!areSpotsLeft(board)) { // we check if it is a tie
                setTitle("It's a tie!")
                showRestartButton()
            } else {
                switchTurns()
                incrementMoveNumber()
            }
        }
    }

    private fun btn7OnClick() {
        binding.btn7.setOnClickListener {
            if (isCircle) {
                binding.btn7.text = "O" // set button text
                updateBoard(7, "O") // update map
                binding.btn7.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.rounded_selected
                ) // change button background
            } else {
                binding.btn7.text = "X" // set button text
                updateBoard(7, "X") // update map
                binding.btn7.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.rounded_selected
                ) // change button background
            }
            binding.btn7.isEnabled = false

            // we check if there is a winner
            val winner = checkWinner(board)
            if (winner != "-") {
                showRestartButton()
                if (isCircle) {
                    setTitle("O has won!")
                } else {
                    setTitle("X has won!")
                }
            } else if (!areSpotsLeft(board)) { // we check if it is a tie
                setTitle("It's a tie!")
                showRestartButton()
            } else {
                switchTurns()
                incrementMoveNumber()
            }
        }
    }

    private fun btn8OnClick() {
        binding.btn8.setOnClickListener {
            if (isCircle) {
                binding.btn8.text = "O" // set button text
                updateBoard(8, "O") // update map
                binding.btn8.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.rounded_selected
                ) // change button background
            } else {
                binding.btn8.text = "X" // set button text
                updateBoard(8, "X") // update map
                binding.btn8.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.rounded_selected
                ) // change button background
            }
            binding.btn8.isEnabled = false

            // we check if there is a winner
            val winner = checkWinner(board)
            if (winner != "-") {
                showRestartButton()
                if (isCircle) {
                    setTitle("O has won!")
                } else {
                    setTitle("X has won!")
                }
            } else if (!areSpotsLeft(board)) { // we check if it is a tie
                setTitle("It's a tie!")
                showRestartButton()
            } else {
                switchTurns()
                incrementMoveNumber()
            }
        }
    }
    private fun btnQuitGameOnClick() {
        binding.btnQuitGame.setOnClickListener {
            findNavController().navigate(R.id.action_gameFragment_to_mainFragment)
        }
    }

    private fun btnRestartGameOnClick() {
        binding.btnRestartGame.setOnClickListener {
            moveNumber = 1 // rest move number
            enableAllButtons() // enable all the buttons again
            setDefaultButtons() // remove colors and X's and O's
            setBoard() // reset board to first state
            setTitle("Player X's turn!")
            binding.txtMoveNumber.text = "Move number: 1"
            hideRestartButton()
        }
    }
    private fun showRestartButton() {
        binding.btnRestartGame.visibility = View.VISIBLE
    }

    private fun hideRestartButton() {
        binding.btnRestartGame.visibility = View.GONE
    }


    private fun enableAllButtons() {
        binding.btn0.isEnabled = true
        binding.btn1.isEnabled = true
        binding.btn2.isEnabled = true
        binding.btn3.isEnabled = true
        binding.btn4.isEnabled = true
        binding.btn5.isEnabled = true
        binding.btn6.isEnabled = true
        binding.btn7.isEnabled = true
        binding.btn8.isEnabled = true
    }

    private fun disableAllButtons() {
        binding.btn0.isEnabled = false
        binding.btn1.isEnabled = false
        binding.btn2.isEnabled = false
        binding.btn3.isEnabled = false
        binding.btn4.isEnabled = false
        binding.btn5.isEnabled = false
        binding.btn6.isEnabled = false
        binding.btn7.isEnabled = false
        binding.btn8.isEnabled = false
    }

    private fun setDefaultButtons() {
        binding.btn0.text = ""
        binding.btn0.background = ContextCompat.getDrawable(requireContext(), R.drawable.custom_button)
        binding.btn1.text = ""
        binding.btn1.background = ContextCompat.getDrawable(requireContext(), R.drawable.custom_button)
        binding.btn2.text = ""
        binding.btn2.background = ContextCompat.getDrawable(requireContext(), R.drawable.custom_button)
        binding.btn3.text = ""
        binding.btn3.background = ContextCompat.getDrawable(requireContext(), R.drawable.custom_button)
        binding.btn4.text = ""
        binding.btn4.background = ContextCompat.getDrawable(requireContext(), R.drawable.custom_button)
        binding.btn5.text = ""
        binding.btn5.background = ContextCompat.getDrawable(requireContext(), R.drawable.custom_button)
        binding.btn6.text = ""
        binding.btn6.background = ContextCompat.getDrawable(requireContext(), R.drawable.custom_button)
        binding.btn7.text = ""
        binding.btn7.background = ContextCompat.getDrawable(requireContext(), R.drawable.custom_button)
        binding.btn8.text = ""
        binding.btn8.background = ContextCompat.getDrawable(requireContext(), R.drawable.custom_button)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}