package com.example.motopedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

enum class  ProviderType {
    BASIC,
    GOOGLE
}

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        auth = Firebase.auth
        var login = findViewById<Button>(R.id.login)
        var crearcuenta = findViewById<Button>(R.id.crear)
        var recuperarcontra = findViewById<Button>(R.id.recupera)
        var loginGoogle = findViewById<Button>(R.id.autgoogle)
        var email = findViewById<EditText>(R.id.email_Text)
        var password = findViewById<EditText>(R.id.password_Text)


        login.setOnClickListener {
            if (email.text.toString()!= "" && password.text.toString()!= "")
            {
                auth.signInWithEmailAndPassword(email.text.toString(),password.text.toString()).addOnCompleteListener{
                        task ->

                    if(task.isSuccessful)
                    {
                        startActivity(Intent(this,MenuActivity::class.java).putExtra("saludo", "Menu principal"))
                        finish()
                    }
                    else
                    {
                        Toast.makeText(this,"Error: "+ task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }
            else
            {
                Toast.makeText(this,"Ingresar correo y password", Toast.LENGTH_LONG).show()
            }

        }


        crearcuenta.setOnClickListener {
            startActivity(Intent(this,AgregarUsuarios::class.java).putExtra("saludo", "Crear Cuenta"))
        }
        recuperarcontra.setOnClickListener {
            startActivity(Intent(this,RecuperarContra::class.java).putExtra("saludo", "Recuperar password"))
        }


    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser == null)
        {
            Toast.makeText(this,"No hay usuarios Autenticados", Toast.LENGTH_LONG).show()
        }
        else
        {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }
    }
}