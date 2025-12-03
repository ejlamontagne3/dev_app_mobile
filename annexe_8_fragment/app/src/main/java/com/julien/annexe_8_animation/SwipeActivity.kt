package com.julien.annexe_8_animation

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class SwipeActivity : AppCompatActivity() {

    lateinit var pager : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_swipe)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        pager = findViewById(R.id.pager)
        pager.setPageTransformer(MonPageTransformer())
        pager.adapter = MonAdapter(this)


    }

    inner class MonPageTransformer : ViewPager2.PageTransformer{
        override fun transformPage(page: View, position: Float) {
            page.setAlpha(0.9f - abs(position.toFloat())* 0.5f)
        }

    }

    inner class MonAdapter ( fragmentActivity: FragmentActivity) : FragmentStateAdapter ( fragmentActivity){
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            //ici que l'on détermine quel fragment afficher en fonction de la position
            when ( position )
            {
                0 -> return Fragment1()
                1 -> return Fragment2()
                else -> return Fragment3()
            }
        }


    }
}