package com.example.composejoyride.data.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import com.example.composejoyride.ui.theme.composables.BottomNavigationBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun signInAnonymously(onSuccess: () -> Unit, onError: (String) -> Unit, isAdmin: Boolean) {
    FirebaseAuth.getInstance()
        .signInAnonymously()
        .addOnCompleteListener { task ->
            val db = Firebase.firestore
            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                val userMap = mapOf(
                    "uid" to it.uid,
                    "email" to it.email,
                    "isAnonymous" to it.isAnonymous,
                    "timestamp" to System.currentTimeMillis(),
                    "isAdmin" to isAdmin
                )
                db.collection("users").document(it.uid).set(userMap)
            }
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onError(task.exception?.message ?: "Ошибка авторизации")
            }
        }
}

fun onSuccess(navController: NavController, isBottomBarVisible: MutableState<Boolean>) {
    isBottomBarVisible.value = true
    navController.navigate(NoteGraph.MAIN_SCREEN)
}

fun onDisableFirebase(
    navController: NavController,
    isBottomBarVisible: MutableState<Boolean>,
    disableFirebase: MutableState<Boolean>
) {
    disableFirebase.value = true
    isBottomBarVisible.value = true
    navController.navigate(NoteGraph.MAIN_SCREEN)
}

fun onError(error: String, context: Context) {
    Toast.makeText(context, "Ошибка входа: $error", Toast.LENGTH_LONG).show()
}
