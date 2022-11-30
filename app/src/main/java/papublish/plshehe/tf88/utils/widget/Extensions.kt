package papublish.plshehe.tf88.utils.widget

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.glideImage(image: Any?) = Glide.with(this).load(image).into(this)