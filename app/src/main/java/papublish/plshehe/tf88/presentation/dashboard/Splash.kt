package papublish.plshehe.tf88.presentation.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import papublish.plshehe.tf88.R
import papublish.plshehe.tf88.presentation.webview.WebviewActivity

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                startActivity(WebviewActivity.getStartIntent(this))
                finish()
            },1500
        )
    }
}