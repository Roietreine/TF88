package papublish.plshehe.tf88.utils

import android.os.Handler
import androidx.viewpager2.widget.ViewPager2
import java.util.*
import java.util.concurrent.ThreadLocalRandom

class Utils {

    companion object {

        fun setTimer(viewPager2: ViewPager2, totalPages: Int) {
            var currentPage = 0
            val DELAY_MS: Long = 500
            val PERIOD_MS: Long = 3000

            val handler = Handler()
            val update = Runnable {
                if (currentPage == totalPages) currentPage = 0
                viewPager2.setCurrentItem(currentPage++, true)
            }
            val timer = Timer() // This will create a new Thread
            timer.schedule(object : TimerTask() {
                override fun run() {
                    handler.post(update)
                }
            }, DELAY_MS, PERIOD_MS)
        }

        fun getRandomNumbers(from: Int, to: Int): Int {
            val random: Int = ThreadLocalRandom.current().nextInt(from, to)
            return random
        }

    }




}