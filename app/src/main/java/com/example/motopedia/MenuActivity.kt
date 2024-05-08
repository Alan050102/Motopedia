package com.example.motopedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import androidx.core.view.MenuProvider
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MenuActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)

        auth = Firebase.auth

        var conocer = findViewById<Button>(R.id.btnConocer)
        var ods = findViewById<Button>(R.id.btnODS)
        var mantenimiento = findViewById<Button>(R.id.btnMantenimiento)
        var reciclaje = findViewById<Button>(R.id.btnReciclaje)
        var moto = findViewById<Button>(R.id.btnMoto)
        var nosotros = findViewById<Button>(R.id.btnNosotros)

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
                        startActivity(Intent(this@MenuActivity, Acerca::class.java))
                        true
                    }
                    R.id.logout -> {
                        auth.signOut()
                        startActivity(Intent(this@MenuActivity, MainActivity::class.java))
                        finish()
                        true
                    }

                    else ->
                        false
                }
            }
        })

        conocer.setOnClickListener {
            startActivity(Intent(this, Conocer::class.java).putExtra("saludo", "Crear Cuenta"))
        }
        ods.setOnClickListener {
            startActivity(Intent(this, ODS::class.java).putExtra("saludo", "Crear Cuenta"))
        }
        mantenimiento.setOnClickListener {
            startActivity(Intent(this, Mantenimiento::class.java).putExtra("saludo", "Crear Cuenta"))
        }
        reciclaje.setOnClickListener {
            startActivity(Intent(this, Reciclaje::class.java).putExtra("saludo", "Crear Cuenta"))
        }
        moto.setOnClickListener {
            startActivity(Intent(this, Moto::class.java).putExtra("saludo", "Crear Cuenta"))
        }
        nosotros.setOnClickListener {
            startActivity(Intent(this, Nosotros::class.java).putExtra("saludo", "Crear Cuenta"))
        }

    }
}