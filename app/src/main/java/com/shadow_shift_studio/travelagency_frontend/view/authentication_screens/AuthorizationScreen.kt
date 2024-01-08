package com.shadow_shift_studio.travelagency_frontend.view.authentication_screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shadow_shift_studio.travelagency_frontend.AuthError
import com.shadow_shift_studio.travelagency_frontend.EnterLoginHint
import com.shadow_shift_studio.travelagency_frontend.EnterPasswordHint
import com.shadow_shift_studio.travelagency_frontend.ForgotPasswordText
import com.shadow_shift_studio.travelagency_frontend.LoginButtonText
import com.shadow_shift_studio.travelagency_frontend.RegistrationText
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_surfaceVariant
import com.shadow_shift_studio.travelagency_frontend.view.main_screens.HomeScreen
import com.shadow_shift_studio.travelagency_frontend.view_model.authentication.LoginViewModel
import com.shadow_shift_studio.travelagency_frontend.view_model.authentication.RegistrationViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Authorization(navController: NavController, onAuthorization: () -> Unit) {
    val navControllerAuthorization = rememberNavController()
    val coroutineScope = rememberCoroutineScope()

    NavHost(navController = navControllerAuthorization, startDestination = "main") {
        composable("main") {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                /*topBar = {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ){
                    IconButton(onClick = { navControllerAuthorization.navigate("homeScreen")  }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            "close"
                        )
                    }
                }},*/
                content = { AuthorizationContent(navController, onAuthorization) },
                bottomBar = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = RegistrationText,
                            textAlign = TextAlign.Justify,
                            color = md_theme_dark_surfaceVariant,
                            modifier = Modifier
                                .clickable { navControllerAuthorization.navigate("registrationScreen") }
                                .padding(bottom = 11.dp)
                        )
                    }
                }
            )
        }
        composable("registrationScreen") {
            Registration(navControllerAuthorization)
        }
        composable("homeScreen") {
            //HomeScreen(navControllerAuthorization,)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AuthorizationContent(navController: NavController, onAuthorization: () -> Unit) {
    val context = LocalContext.current
    val viewModelLogin: LoginViewModel = LoginViewModel(context)
    val bringIntoViewRequester = BringIntoViewRequester()
    val coroutineScope = rememberCoroutineScope()

    var isTextVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 23.dp, end = 23.dp),
        verticalArrangement = Arrangement.Center
    )
    {
        LoginTextField(viewModelLogin, bringIntoViewRequester)

        Spacer(modifier = Modifier.height(20.dp))

        PasswordTextField(EnterPasswordHint, viewModelLogin, bringIntoViewRequester)

        Spacer(modifier = Modifier.height(11.dp))

        Text(text = ForgotPasswordText,
            modifier = Modifier.clickable { /*TODO*/ }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .bringIntoViewRequester(bringIntoViewRequester),
            onClick = {
                coroutineScope.launch {
                    viewModelLogin.loginUser()

                    if(viewModelLogin.loginStatusLiveData.value == true) {
                        onAuthorization()
                    }
                    else
                        isTextVisible = true
                }
            },
            content = { Text(text = LoginButtonText, fontSize = 18.sp) }
        )
        if(isTextVisible) {
            Text(
                text = AuthError,
                //color = md_theme_light_error,
                fontSize = 12.sp,
                textAlign = TextAlign.Left
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LoginTextField(viewModelLogin: LoginViewModel,bringIntoViewRequester: BringIntoViewRequester) {
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    TextField(
        value = viewModelLogin.login.value,
        onValueChange = { newText -> viewModelLogin.login.value = newText },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .onFocusEvent { event ->
                if (event.isFocused) {
                    coroutineScope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            },
        placeholder = { Text(EnterLoginHint) },
        label = { Text(EnterLoginHint) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Phone),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(Hint: String, viewModelLogin: LoginViewModel, bringIntoViewRequester: BringIntoViewRequester) {
    var passwordVisability by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    TextField(
        value = viewModelLogin.password.value,
        onValueChange = { newText -> viewModelLogin.password.value = newText },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .onFocusEvent { event ->
                if (event.isFocused) {
                    coroutineScope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            },
        placeholder = { Text(Hint) },
        label = { Text(Hint) },
        trailingIcon = {
            IconButton(onClick = { passwordVisability = !passwordVisability }) {
                Icon(
                    if (passwordVisability) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    ""
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        ),
        visualTransformation = if (passwordVisability) VisualTransformation.None
        else PasswordVisualTransformation()
    )
}
