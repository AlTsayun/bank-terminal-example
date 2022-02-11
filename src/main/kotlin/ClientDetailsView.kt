import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.*
import model.Client
import ui.component.TextField

@Composable
fun ClientDetailsView(
    value: Client,
//    onValueChange: (Client) -> Unit,
) {
    ValidatableWidget(
        value = value,
        onValueChanged = {},
        validate = { false },
        content = @Composable { operationalValue, onOperationalValueChanged, isValid ->
            Column {
                SurnameTextField(
                    value = operationalValue.surname,
                    onValueChange = { onOperationalValueChanged(value.copy(surname = it)) }
                )
            }
        }
    )
}

/**
 * Widget, which calls onValueChanged() only if user changed value to valid, value can also be updated from calling
 * code.
 */
@Composable
fun <T> ValidatableWidget(
    value: T,
    onValueChanged: (T) -> Unit,
    validate: (T) -> Boolean,
    content: @Composable (operationalValue: T, onOperationalValueChanged: (T) -> Unit, isValid: Boolean) -> Unit
) {
    // value that is edited by content, may be invalid
    var operationalValue by remember { mutableStateOf(value) }
    // variable used to determine, that value is changed from outside
    var lastPassedValue by remember { mutableStateOf(value) }
    // passed to content to chow invalid state
    var isValid by remember { mutableStateOf(validate(value)) }
    
    if (lastPassedValue != value) {
        // updated value is passed so need to update inner state
        operationalValue = value
        lastPassedValue = value
        isValid = validate(value)
    }

    val onOperationalValueChanged = { newOperationalValue: T ->
        operationalValue = newOperationalValue
        isValid = validate(operationalValue)
        if (isValid)
        {
            onValueChanged(newOperationalValue)
        }
    }
    content(operationalValue, onOperationalValueChanged, isValid)
}

@Composable
fun SurnameTextField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    ValidatableWidget(
        value = value,
        onValueChanged = onValueChange,
        validate = { it.length % 2 == 1 }) @Composable { operationalValue, onOperationalValueChanged, isValid ->

        TextField(
            value = operationalValue,
            onValueChange = onOperationalValueChanged,
            isError = !isValid,
            errorMessage = {
                Text("This is an error")
            },
            helperMessage = {
                Text(text = "This is a helper!")
            },
            label = { Text("Surname") },
            singleLine = true
        )
    }
}