package com.deonico.footballapp_kade.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.deonico.footballapp_kade.R
import com.deonico.footballapp_kade.fragment.FavoriteTeamFragment
import com.deonico.footballapp_kade.fragment.TeamsFragment
import kotlinx.android.synthetic.main.activity_team_home.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity

class TeamHomeActivity : AppCompatActivity(), AnkoLogger {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_home)
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, TeamsFragment()).commit()

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_teams -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, TeamsFragment()).commit()
                    true
                }
                R.id.action_favourites -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, FavoriteTeamFragment()).commit()
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
}

