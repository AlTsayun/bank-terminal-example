package ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.YearMonth
import java.util.*

@Composable
fun DatePicker(
    value: Date,
    onValueChange: (Date) -> Unit,
    from: Date,
    to: Date
) {

    fun getCalendarForDate(date: Date) : Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar
    }

    val fromCalendar = getCalendarForDate(from)
    val toCalendar = getCalendarForDate(to)
    val operationalCalendar = getCalendarForDate(value)

    // utilities methods for handy date access
    fun getYear(calendar: Calendar) = calendar.get(Calendar.YEAR)
    fun getMonth(calendar: Calendar) = calendar.get(Calendar.MONTH) + 1
    fun getDayOfMonth(calendar: Calendar) = calendar.get(Calendar.DAY_OF_MONTH)
    fun getFromYear() = getYear(fromCalendar)
    fun getFromMonth() = getMonth(fromCalendar)
    fun getFromDayOfMonth() = getDayOfMonth(fromCalendar)
    fun getToYear() = getYear(toCalendar)
    fun getToMonth() = getMonth(toCalendar)
    fun getToDayOfMonth() = getDayOfMonth(toCalendar)
    fun getOperationalYear() = getYear(operationalCalendar)
    fun getOperationalMonth() = getMonth(operationalCalendar)
    fun getOperationalDayOfMonth() = getDayOfMonth(operationalCalendar)

    fun getAvailableYears(): IntRange = (getFromYear() ..getToYear())
    fun getAvailableMonths(): IntRange {
        var firstAvailable = 1
        var lastAvailable = 12

        if (getOperationalYear() == getFromYear()) {
            firstAvailable = getFromMonth()
        }
        if (getOperationalYear() == getToYear()) {
            lastAvailable = getToMonth()
        }

        return (firstAvailable..lastAvailable)
    }
    fun getAvailableDayOfMonth(): IntRange {
        var firstAvailable = 1
        var lastAvailable = YearMonth.of(getOperationalYear(), getOperationalMonth()).lengthOfMonth()

        if (getOperationalYear() == getFromYear() && getOperationalMonth() == getFromMonth()) {
            firstAvailable = getFromDayOfMonth()
        }
        if (getOperationalYear() == getToYear() && getOperationalMonth() == getToMonth()) {
            lastAvailable = getToDayOfMonth()
        }

        return (firstAvailable..lastAvailable)
    }

    fun getCalendarForDate(
        year: Int,
        month: Int,
        dayOfMonth: Int
    ) : Calendar {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, dayOfMonth)
        return calendar
    }

    fun onOperationalTimeChange(
        year: Int = getOperationalYear(),
        month: Int = getOperationalMonth(),
        dayOfMonth: Int = getOperationalDayOfMonth()
    ) {
        val calendar = getCalendarForDate(year, month, dayOfMonth)
        onValueChange(calendar.time)
    }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row {
            Text("Year:")
            Spacer(modifier = Modifier.width(8.dp))
            DatePickerItemDropdownMenu(
                value = getOperationalYear(),
                itemRange = getAvailableYears(),
                onValueChange = {
                    onOperationalTimeChange(year = it)
                }
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Row {
            Text("Month:")
            Spacer(modifier = Modifier.width(8.dp))
            DatePickerItemDropdownMenu(
                value = getOperationalMonth(),
                itemRange = getAvailableMonths(),
                onValueChange = {

                    // if month with fewer than operationalDayOfMonth days selected need to adjust dayOfMonth
                    val lastAvailableDayOfMonth = YearMonth
                        .of(getOperationalYear(), it)
                        .lengthOfMonth()
                    val operationalDayOfMonth = getOperationalDayOfMonth()
                    val dayOfMonth = if (operationalDayOfMonth > lastAvailableDayOfMonth) {
                        lastAvailableDayOfMonth
                    } else {
                        operationalDayOfMonth
                    }

                    onOperationalTimeChange(month = it, dayOfMonth = dayOfMonth)
                }
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Row {
            Text("Date:")
            Spacer(modifier = Modifier.width(8.dp))
            DatePickerItemDropdownMenu(
                value = getOperationalDayOfMonth(),
                itemRange = getAvailableDayOfMonth(),
                onValueChange = {
                    onOperationalTimeChange(dayOfMonth = it)
                }
            )
        }
    }
}

@Composable
private fun DatePickerItemDropdownMenu(
    value: Int,
    itemRange: IntRange,
    onValueChange: (Int) -> Unit,
) {
    val (isMenuExpanded, setMenuExpanded) = remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = {
            setMenuExpanded(true)
        })
    ) {
        Text(value.toString())
        Spacer(Modifier.width(4.dp))
        Icon(
            imageVector = Icons.Filled.KeyboardArrowDown,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
    }
    DropdownMenu(
        expanded = isMenuExpanded,
        onDismissRequest = {
            setMenuExpanded(false)
        }
    ) {
        itemRange.forEach {
            DropdownMenuItem(onClick = {
                setMenuExpanded(false)
                onValueChange(it)
            }) {
                Text(
                    text = it.toString()
                )
            }
        }
    }
}

@Composable
fun DatePicker(
    value: Date,
    onValueChange: (Date) -> Unit,
    modifier: Modifier = Modifier,
    from: Date,
    to: Date,
    helperMessage: @Composable (() -> Unit)? = null,
    errorMessage: @Composable (() -> Unit)? = null,
    isError: Boolean,
) {

    Column(
        modifier = modifier
    ) {
        DatePicker(value, onValueChange, from, to)
        Box(
            modifier = Modifier
                .requiredHeight(16.dp)
                .padding(start = 16.dp, end = 12.dp)
        ) {
            CompositionLocalProvider(
                LocalTextStyle provides LocalTextStyle.current.copy(
                    fontSize = 12.sp,
                    color = if (isError) MaterialTheme.colors.error else LocalTextStyle.current.color
                )
            ) {
                if (isError) {
                    if (errorMessage != null) {
                        errorMessage()
                    }
                } else {
                    if (helperMessage != null) {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.medium
                        ) {
                            helperMessage()
                        }
                    }
                }
            }
        }
    }
}