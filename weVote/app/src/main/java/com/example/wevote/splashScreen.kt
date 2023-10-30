package com.example.wevote

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class splashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val backgroundImage: ImageView = findViewById(R.id.SplashScreenImage)
        val textView: TextView = findViewById(R.id.textView)

        val slideAnimation1 = AnimationUtils.loadAnimation(this, R.anim.logo)
        val slideAnimation2 = AnimationUtils.loadAnimation(this, R.anim.tagline)

        // Create an AnimationSet to play both animations together
        val animationSet = AnimationSet(true)
        animationSet.addAnimation(slideAnimation1)
        // Apply the second animation to the textView
        textView.startAnimation(slideAnimation2)
        backgroundImage.startAnimation(animationSet)


        val font = Typeface.createFromAsset(assets, "voiceinmyhead.otf")
        textView.typeface = font


        Handler().postDelayed({
            val intent = Intent(this, viewPager::class.java)
            startActivity(intent)
            finish()
        }, 3500)
    }
}