package com.example.composejoyride.ui.screens

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.R
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.data.utils.VengButtonType
import com.example.composejoyride.data.utils.onDisableFirebase
import com.example.composejoyride.data.utils.onError
import com.example.composejoyride.data.utils.onSuccess
import com.example.composejoyride.data.utils.sharedViewModel
import com.example.composejoyride.data.utils.signInAnonymously
import com.example.composejoyride.ui.theme.LocalTheme
import com.example.composejoyride.ui.theme.TheFont
import com.example.composejoyride.ui.theme.composables.VengButton
import com.example.composejoyride.ui.theme.composables.VengOutlinedTextField
import com.example.composejoyride.ui.theme.composables.rememberFirebaseAuthLauncher
import com.example.composejoyride.ui.viewModels.SettingsViewModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AuthScreen(
    navController: NavController,
    isBottomBarVisible: MutableState<Boolean>,
    disableFirebase: MutableState<Boolean>
) {
    val backgroundColor = MaterialTheme.colorScheme.background
    val textColor = MaterialTheme.colorScheme.tertiary
    val font = TheFont
    val viewmodel: SettingsViewModel = sharedViewModel(navController)

    isBottomBarVisible.value = false
    val showAdminDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val launcher = rememberFirebaseAuthLauncher(
        onAuthComplete = { result ->
            val db = Firebase.firestore
            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                val userMap = mapOf(
                    "uid" to it.uid,
                    "email" to it.email,
                    "isAnonymous" to it.isAnonymous,
                    "timestamp" to System.currentTimeMillis()
                )
                db.collection("users").document(it.uid).set(userMap)
            }

            if (result.resultCode == RESULT_OK) {
                onSuccess(navController, isBottomBarVisible)
            }
        },
        onAuthError = {
            onError(it?.message ?: "Неизвестно", context)
        }
    )

    val signInIntent = remember {
        val themeId = if (!LocalTheme.value) R.style.FirebaseUI_Dark else R.style.FirebaseUI_Light
        AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(
                listOf(
                    AuthUI.IdpConfig.EmailBuilder().build()
                )
            )
            .setTheme(themeId)
            .setLogo(R.drawable.alfa_logo)
            .build()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.app_name),
                textAlign = TextAlign.Center,
                fontFamily = font,
                color = textColor,
                fontSize = 48.sp
            )

            VengButton(
                onClick = { launcher.launch(signInIntent) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                image = Icons.Default.Email,
                text = "Войти через Email",
                buttonType = VengButtonType.Liquid
            )

            VengButton(
                onClick = {
                    signInAnonymously(
                        onSuccess = { onSuccess(navController, isBottomBarVisible) },
                        onError = { onError(it, context) },
                        false
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                image = Icons.Default.PersonOutline,
                text = "Войти как гость",
                buttonType = VengButtonType.Liquid
            )

            VengButton(
                onClick = {
                    showAdminDialog.value = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                image = Icons.Default.AdminPanelSettings,
                text = "Войти как администратор",
                buttonType = VengButtonType.Liquid
            )

            VengButton(
                onClick = { onDisableFirebase(navController, isBottomBarVisible, disableFirebase) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                image = Icons.Default.SkipNext,
                text = "Войти без Firebase",
                buttonType = VengButtonType.Liquid
            )

            if (showAdminDialog.value) {
                var login by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }

                AlertDialog(
                    onDismissRequest = { showAdminDialog.value = false },
                    title = {
                        Text(
                            "Вход администратора",
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    },
                    containerColor = MaterialTheme.colorScheme.background,
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            VengOutlinedTextField(
                                value = login,
                                onValueChange = { login = it },
                                label = {
                                    Text(
                                        "Логин",
                                        color = MaterialTheme.colorScheme.tertiary
                                    )
                                },
                                singleLine = true,
                            )
                            VengOutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                label = {
                                    Text(
                                        "Пароль",
                                        color = MaterialTheme.colorScheme.tertiary
                                    )
                                },
                                visualTransformation = PasswordVisualTransformation(),
                                singleLine = true,
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                if (login == "admin" && password == "admin") {
                                    showAdminDialog.value = false
                                    signInAnonymously(
                                        onSuccess = {
                                            onSuccess(
                                                navController,
                                                isBottomBarVisible
                                            )
                                        },
                                        onError = { onError(it, context) },
                                        true
                                    )
                                    viewmodel.fetchAdminStatus()
                                    navController.navigate(NoteGraph.ADMIN_SCREEN)
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Неверный логин или пароль",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.secondary)
                        ) {
                            Text("Войти", color = MaterialTheme.colorScheme.tertiary)
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showAdminDialog.value = false },
                            colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.secondary)
                        ) {
                            Text("Отмена", color = MaterialTheme.colorScheme.tertiary)
                        }
                    }
                )
            }

        }
    }
}