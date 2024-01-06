package com.shadow_shift_studio.travelagency_frontend.view.main_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
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
import com.shadow_shift_studio.travelagency_frontend.model.entity.TourPreview
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_bottom_sheet_bottoms
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_onPrimary
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_dark_secondary
import com.shadow_shift_studio.travelagency_frontend.ui.theme.md_theme_light_primary
import com.shadow_shift_studio.travelagency_frontend.view.Custom.dateFormatter.DateTransformation
import com.shadow_shift_studio.travelagency_frontend.view.cards.CartTour
import com.shadow_shift_studio.travelagency_frontend.view.cards.HomeTourPreviewCard
import com.shadow_shift_studio.travelagency_frontend.view.cards.PassportHintCard
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripScreen(navController: NavController){

    val listState = rememberScrollState()
    var buttonClickCounter by remember { mutableStateOf(1) }
    val openDialog = remember { mutableStateOf(false) }

    var duration: Double = 5.0
    var touristCount: Int = 1
    val decimalFormat = DecimalFormat("#,###.##")
    var amount: Double = 150000.0

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
        Spacer(Modifier.height(15.dp))

        Column(modifier = Modifier
            .fillMaxWidth()
            .background(
                color = md_theme_dark_bottom_sheet_bottoms,
                shape = RoundedCornerShape(15.dp)
            )) {
            Text(
                "Паспортные данные",
                color = md_theme_light_primary,
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 15.dp, start = Padding.dp, end = Padding.dp),
                fontSize = 22.sp,
                fontFamily = FontFamily.Monospace
            )
            if (AuthorizedUser.isPassportSaved) {
                PassportHint()
            }
            Passport()
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { buttonClickCounter++ }) {
                    Icon(Icons.Default.PersonAdd, "")
                }
                Text("Добавить друга")
            }
        }

        Spacer(Modifier.height(15.dp))
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(
                color = md_theme_dark_bottom_sheet_bottoms,
                shape = RoundedCornerShape(15.dp)
            )) {
            Text(
                "Даты поездки",
                color = md_theme_light_primary,
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 15.dp, start = Padding.dp, end = Padding.dp),
                fontSize = 22.sp,
                fontFamily = FontFamily.Monospace
            )
            DatePicker(duration)

        }

        Spacer(Modifier.height(15.dp))
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(
                color = md_theme_dark_bottom_sheet_bottoms,
                shape = RoundedCornerShape(15.dp)
            )) {
            Text(
                "Итоговая сумма: ${multiplyDecimalFormat(decimalFormat, amount, touristCount)}" + "₽",
                color = md_theme_dark_onPrimary,
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 15.dp, start = Padding.dp, end = Padding.dp),
                fontSize = 26.sp,
                fontFamily = FontFamily.Monospace
            )
            Button( onClick = { openDialog.value = true },
                Modifier
                    .fillMaxWidth()
                    .padding(Padding.dp)) {
                Text("Забронировать")
            }
            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    }
                ){
                    Column(Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(15.dp))) {
                        Text(
                            text = "Тур забронирован!",
                            color = md_theme_light_primary,
                            modifier = Modifier
                                .padding(Padding.dp),
                            fontSize = 25.sp,
                            fontFamily = FontFamily.Monospace)
                        Text(
                            "Для уточнения деталей и оплаты с Вами свяжется оператор",
                            color = md_theme_light_primary,
                            modifier = Modifier
                                .padding(Padding.dp),
                            fontSize = 20.sp,
                            fontFamily = FontFamily.Monospace)
                        Button(
                            onClick = { openDialog.value = false },
                            modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = Padding.dp)
                        ) {
                            Text("Хорошо")
                        }
                    }
                }
            }
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
            .padding(start = Padding.dp)
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

    var series by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }

    var lastName by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var middleName by remember { mutableStateOf("") }

    var issueDate by remember { mutableStateOf("") }
    var whoIssued by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(start = Padding.dp, end = Padding.dp)) {
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
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Phone
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
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
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Phone
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
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
                onDone = { focusManager.clearFocus() }
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
                onDone = { focusManager.clearFocus() }
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
                onDone = { focusManager.clearFocus() }
            )
        )

        Spacer(Modifier.height(10.dp))
        DateField("Дата рождения", birthDate)

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
                onDone = { focusManager.clearFocus() }
            )
        )

        Spacer(Modifier.height(10.dp))
        DateField("Дата выдачи", issueDate)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it },
            )
            Text("Запомнить паспорт", fontSize = 16.sp)
        }
    }
}

@Composable
fun DateField(headline: String,text:String ){
    val maxChar = 8
    var text by remember { mutableStateOf("") }
    TextField(
        singleLine = true,
        value = text,
        onValueChange = {
            if (it.length <= maxChar) text = it
        },
        visualTransformation = DateTransformation(),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        placeholder = {Text(headline)},
        label = {Text(headline)}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(duration: Double) {
    val calendar = Calendar.getInstance()
    calendar.set(LocalDate.now().year, LocalDate.now().monthValue, LocalDate.now().dayOfMonth)

    var textIsVisible by remember { mutableStateOf(false) }

    var startDate by remember {
        mutableLongStateOf(calendar.timeInMillis)
    }

    // set the initial date
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = startDate
    )

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    startDate = datePickerState.selectedDateMillis!!
                    textIsVisible = true
                }) {
                    Text(text = "Выбрать")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDatePicker = false
                }) {
                    Text(text = "Отмена")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                modifier = Modifier
                    .height(height = 500.dp)
                    .padding(Padding.dp) ,
                title = {Text("Дата начала")}
            )
        }
    }

    Button(
        onClick = {
            showDatePicker = true
        },
        Modifier
            .fillMaxWidth()
            .padding(start = Padding.dp, end = Padding.dp, bottom = 15.dp)
    ) {
        Text(text = "Выбрать дату начала")
    }

    val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.ROOT)
    val endDate = startDate + duration.toInt() * 24 * 60 * 60 * 1000

    if(textIsVisible){
    Text(
        text = "Дата начала: ${formatter.format(Date(startDate))}, " +
                "\nДата окончания: ${formatter.format(Date(endDate))}",
        Modifier.padding(start = Padding.dp, end = Padding.dp, bottom = Padding.dp),
        color = Color.Black,
        fontSize = 18.sp,
        fontFamily = FontFamily.Monospace
    )}
}


fun multiplyDecimalFormat(decimalFormat: DecimalFormat, value: Double, multiplier: Int): String {
    val parsedValue = decimalFormat.parse(decimalFormat.format(value))?.toDouble() ?: 0.0

    val result = parsedValue * multiplier

    return decimalFormat.format(result)
}