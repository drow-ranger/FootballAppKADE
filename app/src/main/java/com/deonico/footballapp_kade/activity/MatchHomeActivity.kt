package com.deonico.footballapp_kade.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.deonico.footballapp_kade.R
import com.deonico.footballapp_kade.fragment.FavoriteMatchFragment
import com.deonico.footballapp_kade.fragment.NextMatchFragment
import com.deonico.footballapp_kade.fragment.PrevMatchFragment
import kotlinx.android.synthetic.main.activity_match_home.*
import org.jetbrains.anko.startActivity

class MatchHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_home)
        supportActionBar?.title = "Match Info"
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, PrevMatchFragment()).commit()

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_prev -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, PrevMatchFragment()).commit()
                    true
                }
                R.id.action_next -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, NextMatchFragment()).commit()
                    true
                }
                R.id.action_favorite_match -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, FavoriteMatchFragment()).commit()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_switch, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_team_app -> {
                startActivity<TeamHomeActivity>()
                finish()
            }
            R.id.action_match_app -> {
                startActivity<MatchHomeActivity>()
                finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, R.string.back, Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
}
