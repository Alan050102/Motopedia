package com.example.motopedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Conocer : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conocer)

        auth = Firebase.auth

        var toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId)
                {
                    R.id.info -> {
                        startActivity(Intent(this@Conocer, Acerca::class.java))
                        true
                    }
                    R.id.logout -> {
                        auth.signOut()
                        startActivity(Intent(this@Conocer, MainActivity::class.java))
                        finish()
                        true
                    }

                    else ->
                        false
                }
            }
        })

    }
}