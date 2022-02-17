import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.Client
import ui.component.DatePicker
import ui.component.TextField
import java.util.*

@Composable
fun ClientDetailsView(
    value: Client,
    onValueChange: (Client) -> Unit,
) {
    ValidatableWidget(
        value = value,
        // todo: replace empty strings with nulls before onValueChange
        onValueChange = onValueChange,
        // todo: validate dates consistency
        validate = { true }
    ) { operationalValue, onOperationalValueChange, isValid ->
            Column {
                Text(operationalValue.surname)
                SurnameTextField(
                    value = operationalValue.surname,
                    onValueChange = { onOperationalValueChange(value.copy(surname = it)) }
                )

                Spacer(modifier = Modifier.width(16.dp))

                NameTextField(
                    value = operationalValue.name,
                    onValueChange = { onOperationalValueChange(value.copy(name = it)) }
                )

                Spacer(modifier = Modifier.width(16.dp))

                PatronymicTextField(
                    value = operationalValue.patronymic,
                    onValueChange = { onOperationalValueChange(value.copy(patronymic = it)) }
                )

                Spacer(modifier = Modifier.width(16.dp))

                BirthDatePicker(
                    value = operationalValue.birthDate,
                    onValueChange = { onOperationalValueChange(value.copy(birthDate = java.sql.Date(it.time))) }
                )
            }
        }
}

/**
 * Widget, which calls onValueChanged() only if user changed value to valid, value can also be updated from calling
 * code.
 */
@Composable
fun <T> ValidatableWidget(
    value: T,
    onValueChange: (T) -> Unit,
    validate: (T) -> Boolean,
    content: @Composable (operationalValue: T, onOperationalValueChange: (T) -> Unit, isValid: Boolean) -> Unit
) {
    // value that is edited by content, may be invalid
    var operationalValue by remember { mutableStateOf(value) }
    // variable used to determine, that value is changed from outside
    var lastPassedValue by remember { mutableStateOf(value) }
    // passed to content to chow invalid state
    var isValid by remember { mutableStateOf(validate(value)) }
    
    if (lastPassedValue !== value) {
        // updated value is passed so need to update inner state
        operationalValue = value
        lastPassedValue = value
        isValid = validate(value)
    }

    val onOperationalValueChange = { newOperationalValue: T ->
        operationalValue = newOperationalValue
        isValid = validate(operationalValue)
        if (isValid)
        {
            onValueChange(newOperationalValue)
        }
    }
    content(operationalValue, onOperationalValueChange, isValid)
}

@Composable
fun ValidatableTextField(
    value: String,
    label: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    validate: (String) -> Boolean,
    errorMessage: @Composable (() -> Unit)? = null,
    helperMessage: @Composable (() -> Unit)? = null,
) {
    ValidatableWidget(
        value = value,
        onValueChange = onValueChange,
        validate = validate
    ) { operationalValue, onOperationalValueChange, isValid ->
        TextField(
            value = operationalValue,
            onValueChange = onOperationalValueChange,
            isError = !isValid,
            errorMessage = errorMessage,
            helperMessage = helperMessage,
            label = label,
            singleLine = true
        )
    }
}

@Composable
fun GenericValidatableNameTextField(
    value: String,
    label: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    validate: (String) -> Boolean,
) {
    ValidatableTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        validate = validate,
        errorMessage = {
            Text("Must contain only multiple letters and begin with capital")
        },
        helperMessage = {
            Text("Must contain only multiple letters and begin with capital")
        }
    )

}

@Composable
fun SurnameTextField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    GenericValidatableNameTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Surname") },
        validate = { validateSurname(it) }
    )
}

@Composable
fun NameTextField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    GenericValidatableNameTextField(
        value = value,
        label = { Text("Name") },
        onValueChange = onValueChange,
        validate = { validateName(it) }
    )
}

@Composable
fun PatronymicTextField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    GenericValidatableNameTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Patronymic") },
        validate = { validatePatronymic(it) }
    )
}

@Composable
fun BirthDatePicker(
    value: Date,
    onValueChange: (Date) -> Unit
) {
    ValidatableWidget(
        value = value,
        onValueChange = onValueChange,
        validate =  { validateBirthDate(it) }
    ) { operationalValue, onOperationalValueChange, isValid ->
        DatePicker(
            value = operationalValue,
            onValueChange = onOperationalValueChange,
            from = Date(0, 0, 1),
            to = Date(),
            errorMessage = {
                Text("Must be before now")
            },
            isError = !isValid
        )
    }
}
