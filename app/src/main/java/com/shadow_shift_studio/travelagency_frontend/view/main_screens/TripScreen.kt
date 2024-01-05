package com.shadow_shift_studio.travelagency_frontend.view.main_screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shadow_shift_studio.travelagency_frontend.EnterLoginHint
import com.shadow_shift_studio.travelagency_frontend.Padding
import com.shadow_shift_studio.travelagency_frontend.data.singletone_object.AuthorizedUser
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_primary
import com.shadow_shift_studio.travelagency_frontend.view.cards.CartTour
import com.shadow_shift_studio.travelagency_frontend.view.cards.HomeTourPreviewCard
import com.shadow_shift_studio.travelagency_frontend.view.cards.PassportHintCard
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun TripScreen(navController: NavController){
    val listState = rememberScrollState()
    Column(modifier = Modifier
        .padding(Padding.dp)
        .verticalScroll(listState)) {
        Text("Оформление поездки",
            color = md_theme_light_primary,
            modifier = Modifier
                .padding(bottom = 15.dp),
            fontSize = 28.sp,
            fontFamily = FontFamily.Monospace)
        CartTour()
        Text("Паспортные данные",
            color = md_theme_light_primary,
            modifier = Modifier
                .padding(top = 15.dp, bottom = 15.dp),
            fontSize = 18.sp,
            fontFamily = FontFamily.Monospace)
        if(AuthorizedUser.isPassportSaved)
        {
            PassportHint()
        }
        Passport()
        IconButton(onClick = {  }) {
            Icon(Icons.Default.PersonAdd,"")
            Text("Добавить друга")
        }
    }
}
@Composable
fun PassportHint()
{
    val listState = rememberLazyListState()
    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(count = 1, key = null) { i ->
            PassportHintCard()
        }
    }
}

@Composable
fun Passport(){
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    val checkedState = remember { mutableStateOf(false) }

    var series: String = ""
    var number: String = ""

    var lastName: String = ""
    var name: String = ""
    var middleName: String = ""

    var issueDate: Date
    var whoIssued: String = ""
    var birthDate: Date

    Spacer(Modifier.height(10.dp))
    TextField(
        value = series,
        onValueChange = { newText -> series = newText },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        placeholder = { Text("Серия") },
        label = { Text("Cерия") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Phone),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )
    Spacer(Modifier.height(10.dp))
    TextField(
        value = number,
        onValueChange = { newText -> number = newText },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        placeholder = { Text("Номер") },
        label = { Text("Номер") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Phone),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )
    Spacer(Modifier.height(10.dp))
    TextField(
        value = lastName,
        onValueChange = { newText -> lastName = newText },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        placeholder = { Text("Фамилия") },
        label = { Text("Фамилия") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )
    Spacer(Modifier.height(10.dp))
    TextField(
        value = name,
        onValueChange = { newText -> name = newText },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        placeholder = { Text("Имя") },
        label = { Text("Имя") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )
    Spacer(Modifier.height(10.dp))
    TextField(
        value = middleName,
        onValueChange = { newText -> middleName = newText },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        placeholder = { Text("Отчество") },
        label = { Text("Отчество") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )
    Spacer(Modifier.height(10.dp))
    DateField("Дата рождения")
    Spacer(Modifier.height(10.dp))
    TextField(
        value = whoIssued,
        onValueChange = { newText -> whoIssued = newText },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        placeholder = { Text("Кем выдан") },
        label = { Text("Кем выдан") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )
    Spacer(Modifier.height(10.dp))
    DateField("Дата выдачи")
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()){
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it },
        )
        Text("Запомнить паспорт", fontSize = 16.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateField(headline: String){
    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Input
    )

    DatePicker(
        state = datePickerState,
        showModeToggle = false,
        title = null,
        headline = {Text(headline, fontSize = 10.sp)}
    )
}
