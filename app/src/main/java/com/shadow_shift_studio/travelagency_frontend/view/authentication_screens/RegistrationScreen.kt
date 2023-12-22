package com.shadow_shift_studio.travelagency_frontend.view.authentication_screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RichTooltipBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberRichTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.shadow_shift_studio.travelagency_frontend.CreateLoginHint
import com.shadow_shift_studio.travelagency_frontend.EnterEmailHint
import com.shadow_shift_studio.travelagency_frontend.EnterPasswordHint
import com.shadow_shift_studio.travelagency_frontend.FillAllFields
import com.shadow_shift_studio.travelagency_frontend.InputErrorMessage
import com.shadow_shift_studio.travelagency_frontend.PasswordsDontMatch
import com.shadow_shift_studio.travelagency_frontend.RegisterButtonText
import com.shadow_shift_studio.travelagency_frontend.RepeatPasswordHint
import com.shadow_shift_studio.travelagency_frontend.passwordRules
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_onSurface
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_error
import com.shadow_shift_studio.travelagency_frontend.view_model.authentication.RegistrationViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Registration(navController: NavController) {
    val context = LocalContext.current
    val viewModelRegistration: RegistrationViewModel = RegistrationViewModel(context)
    var isTextVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val bringIntoViewRequester = BringIntoViewRequester()

    Column {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Default.ArrowBack, "", modifier = Modifier
                        .height(28.dp)
                        .width(28.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(11.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 23.dp, end = 23.dp),
            verticalArrangement = Arrangement.Top
        )
        {
            LoginTextField(viewModelRegistration, bringIntoViewRequester)

            Spacer(modifier = Modifier.height(20.dp))

            EmailTextField(viewModelRegistration, bringIntoViewRequester)

            Spacer(modifier = Modifier.height(20.dp))

            RegPasswordField(EnterPasswordHint, viewModelRegistration, bringIntoViewRequester)

            Spacer(modifier = Modifier.height(20.dp))

            RepeatPasswordField(RepeatPasswordHint, viewModelRegistration, bringIntoViewRequester)

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier.fillMaxWidth()
                    .bringIntoViewRequester(bringIntoViewRequester),
                onClick = {
                    coroutineScope.launch {
                        viewModelRegistration.registerUser()
                        if (viewModelRegistration.registerStatusLiveData.value == true) {
                            navController.popBackStack()
                        }
                    }
                    /*if(viewModelRegistration.isAllDataEntered()){
                        coroutineScope.launch {
                            viewModelRegistration.registerUser()
                            if(viewModelRegistration.registerStatusLiveData.value == true) {
                                navController.popBackStack()
                            }
                        }
                    }*/
                    //else isTextVisible = true},
                },
                content = { Text(text = RegisterButtonText, fontSize = 18.sp) }
            )

            if(isTextVisible) {
                Text(
                    text = FillAllFields,
                    fontSize = 12.sp,
                    color = md_theme_light_error,
                    textAlign = TextAlign.Left
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginTextField(viewModelRegistration: RegistrationViewModel, bringIntoViewRequester: BringIntoViewRequester)
{
    var isLoginError by remember { mutableStateOf(false) }
    var loginErrorMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    TextField(
        value = viewModelRegistration.login.value,
        onValueChange = {
            viewModelRegistration.login.value = it
            /*var res = viewModelRegistration.isLoginValid(viewModelRegistration.login.value)
            if (res == LoginStates.VALID)
                isLoginError = false
            else if (res == LoginStates.INVALID_CHARACTERS) {
                isLoginError = true
                loginErrorMessage = loginErrors[LoginStates.INVALID_CHARACTERS.value]
            } else if (res == LoginStates.INVALID_LENGTH) {
                isLoginError = true
                loginErrorMessage = loginErrors[LoginStates.INVALID_LENGTH.value]
            }*/
        },
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
        placeholder = { Text(CreateLoginHint) },
        label = { Text(CreateLoginHint) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )
    if (isLoginError && !viewModelRegistration.login.value.isEmpty()) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = loginErrorMessage,
            fontSize = 12.sp,
            color = md_theme_light_error
        )
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmailTextField(viewModelRegistration: RegistrationViewModel, bringIntoViewRequester: BringIntoViewRequester)
{

    var isEmailError by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    TextField(
        value = viewModelRegistration.email.value,
        onValueChange = {
            viewModelRegistration.email.value = it
            isEmailError = !viewModelRegistration.isEmailValid(viewModelRegistration.email.value)},
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
        placeholder = { Text(EnterEmailHint) },
        label = { Text(EnterEmailHint) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )
    if (isEmailError && viewModelRegistration.email.value.isNotEmpty()) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = InputErrorMessage,
            fontSize = 12.sp,
            color = md_theme_light_error
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun RegPasswordField(Hint: String, viewModel: RegistrationViewModel, bringIntoViewRequester: BringIntoViewRequester)
{
    var passwordVisability by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    TextField(
        value = viewModel.password.value,
        onValueChange = {
            viewModel.password.value = it
            isPasswordError = !viewModel.isPasswordValid(viewModel.password.value)},
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
        trailingIcon =
        {
            val tooltipState = rememberRichTooltipState(isPersistent = true)
            val scope = rememberCoroutineScope()
            RichTooltipBox(
                text = {
                    Text(text = passwordRules,
                        modifier = Modifier.padding(10.dp))
                },
                tooltipState = tooltipState,
            ) {
                IconButton(
                    onClick = {  scope.launch { tooltipState.show() } },
                    modifier = Modifier.tooltipTrigger()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        tint = if(isPasswordError == true) md_theme_light_error else md_theme_dark_onSurface,
                        contentDescription = ""
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        ),
        visualTransformation = if (passwordVisability) VisualTransformation.None
        else PasswordVisualTransformation()
    )
    if (isPasswordError && viewModel.password.value.isNotEmpty()) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = InputErrorMessage,
            fontSize = 12.sp,
            color = md_theme_light_error
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RepeatPasswordField(Hint: String, viewModel: RegistrationViewModel, bringIntoViewRequester: BringIntoViewRequester)
{
    var passwordVisability by remember { mutableStateOf(false) }
    var isPasswordsEqual by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    TextField(
        value = viewModel.repeatPassword.value,
        onValueChange = { viewModel.repeatPassword.value = it
            isPasswordsEqual =viewModel.isPasswordsMatch()},
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
    if (isPasswordsEqual == false && viewModel.repeatPassword.value.isNotEmpty()) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = PasswordsDontMatch,
            fontSize = 12.sp,
            color = md_theme_light_error
        )
    }
}
