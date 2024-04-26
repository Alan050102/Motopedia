package com.example.motopedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

enum class  ProviderType {
    BASIC,
    GOOGLE
}


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    companion object {
        private const val RC_SIGN_IN = 9001
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        auth = FirebaseAuth.getInstance()
        var login = findViewById<Button>(R.id.login)
        var crearcuenta = findViewById<Button>(R.id.crear)
        var recuperarcontra = findViewById<Button>(R.id.recupera)
        var loginGoogle = findViewById<Button>(R.id.autgoogle)
        var email = findViewById<EditText>(R.id.email_Text)
        var password = findViewById<EditText>(R.id.password_Text)


        login.setOnClickListener {
            if (email.text.toString() != "" && password.text.toString() != "") {
                auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, MenuActivity::class.java).putExtra("saludo", "Menu principal"))
                        finish()
                    } else {
                        Toast.makeText(this, "Error: " + task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Ingresar correo y password", Toast.LENGTH_LONG).show()
            }
        }

        crearcuenta.setOnClickListener {
            startActivity(Intent(this, AgregarUsuarios::class.java).putExtra("saludo", "Crear Cuenta"))
        }

        recuperarcontra.setOnClickListener {
            startActivity(Intent(this, RecuperarContra::class.java).putExtra("saludo", "Recuperar password"))
        }

        loginGoogle.setOnClickListener {
            // Create a Google Sign In client
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))  // Replace with your web client ID
                .requestEmail()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(this, gso)

            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN) // Replace RC_SIGN_IN with a unique request code
        }
    }

    // Handle Google Sign In result in onActivityResult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI
                            val user = auth.currentUser
                            startActivity(Intent(this, MenuActivity::class.java).putExtra("saludo", "Menu principal"))
                            finish()
                        } else {
                            // Sign in failed, display a message to the user
                            Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
            } catch (e: Exception) {
                // Google Sign In failed, update UI
                Toast.makeText(this, "Google Sign In Failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Toast.makeText(this, "No hay usuarios Autenticados", Toast.LENGTH_LONG).show()
        } else {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }
    }
}