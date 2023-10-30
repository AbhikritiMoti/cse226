package com.example.wevote

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout

    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fabAdd: FloatingActionButton


    private val fragmentManager: FragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomAppBar = findViewById(R.id.bottomAppBar)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        fabAdd = findViewById(R.id.fabAdd)

        // Set up the default fragment to be displayed
        replaceFragment(HomeBlankFragment())

        // Set click listeners
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(HomeBlankFragment())
                R.id.vote -> replaceFragment(ongoingBlankFragment())
                R.id.create -> replaceFragment(createBlankFragment())
                R.id.exit -> showExitConfirmationDialog()
            }
            true
        }

        fabAdd.setOnClickListener {
            // Handle FAB click
            // You can open a new fragment or perform any action you need here
        }

        val username = intent.getStringExtra("username")

        if (username != null) {
            // Pass the username as an argument to the HomeBlankFragment
            val homeFragment = HomeBlankFragment()
            val bundle = Bundle()
            bundle.putString("username", username)
            homeFragment.arguments = bundle

            // Replace the HomeBlankFragment in your FrameLayout
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, homeFragment)
                .commit()
        }

        // Store the username in shared preferences
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.apply()


        supportActionBar?.hide()

        drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)






    }
    private fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> replaceFragment(HomeBlankFragment())
            R.id.rate -> replaceFragment(rateBlankFragment())
            R.id.share -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "https://github.com/abhikritimoti/")
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
                true
            }
            R.id.git -> replaceFragment(webBlankFragment())
            R.id.newuser -> {

                val intent = Intent(this, Signup::class.java)
                startActivity(intent)

                val vg: ViewGroup? = findViewById(R.id.custom_toast)
                val inflater = layoutInflater
                val layout: View = inflater.inflate(R.layout.custom_toast, vg)
                val tv = layout.findViewById<TextView>(R.id.txtVw)
                tv.text = "Register here"
                val toast = Toast(applicationContext)
                toast.setGravity(Gravity.BOTTOM, 0, 300)
                toast.setView(layout)
                toast.duration = Toast.LENGTH_SHORT
                toast.show()
            }

            R.id.signout -> {

                val intent = Intent(this, Login::class.java)
                startActivity(intent)

                val vg: ViewGroup? = findViewById(R.id.custom_toast)
                val inflater = layoutInflater
                val layout: View = inflater.inflate(R.layout.custom_toast, vg)
                val tv = layout.findViewById<TextView>(R.id.txtVw)
                tv.text = "Signed Out"
                val toast = Toast(applicationContext)
                toast.setGravity(Gravity.BOTTOM, 0, 300)
                toast.setView(layout)
                toast.duration = Toast.LENGTH_SHORT
                toast.show()


            }

            R.id.exit -> showExitConfirmationDialog()

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showExitConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Exit Confirmation")
        builder.setMessage("Are you sure you want to exit")
        builder.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
            finishAffinity() // Close the app
        }
        builder.setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
            val vg: ViewGroup? = findViewById(R.id.custom_toast)
            val inflater = layoutInflater
            val layout: View = inflater.inflate(R.layout.custom_toast, vg)
            val tv = layout.findViewById<TextView>(R.id.txtVw)
            tv.text = "Aborted!"
            val toast = Toast(applicationContext)
            toast.setGravity(Gravity.BOTTOM, 0, 300)
            toast.setView(layout)
            toast.duration = Toast.LENGTH_SHORT
            toast.show()
        }
        builder.show()
    }
}