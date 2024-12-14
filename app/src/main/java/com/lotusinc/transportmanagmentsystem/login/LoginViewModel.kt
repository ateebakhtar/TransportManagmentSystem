package com.lotusinc.transportmanagmentsystem.login

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun loginWithEmailAndPassword(
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    fetchUserType(firebaseAuth.currentUser?.uid ?: "") { userType ->
                        onResult(true, userType)
                    }

                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun fetchUserType(
        userId: String,
        onResult: (String?) -> Unit
    ) {
        val firestore = Firebase.firestore
        val userRef = firestore.collection("UserInfo").document(userId)

        userRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val userType = document.getString("type") // Fetch "type" field
                onResult(userType) // Pass the type back to the caller
            } else {
                onResult(null) // User document does not exist
            }
        }.addOnFailureListener {
            onResult(null)
        }
    }
}
