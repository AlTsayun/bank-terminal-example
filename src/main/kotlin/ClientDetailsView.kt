import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import model.*
import ui.component.*
import java.math.BigDecimal
import java.util.*

@Composable
fun ClientDetailsView(
    value: Client,
    onValueChange: (Client) -> Unit,
) {
    ValidatableWidget(
        value = value,
        onValueChange = onValueChange,
        validate = { validateClient(it) }
    ) { operationalValue, onOperationalValueChange, isValid ->
            LazyColumn (
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    SurnameTextField(
                        value = operationalValue.surname,
                        onValueChange = { onOperationalValueChange(value.copy(surname = it)) }
                    )
                }
                item {
                    NameTextField(
                        value = operationalValue.name,
                        onValueChange = { onOperationalValueChange(value.copy(name = it)) }
                    )
                }
                item {
                    PatronymicTextField(
                        value = operationalValue.patronymic,
                        onValueChange = { onOperationalValueChange(value.copy(patronymic = it)) }
                    )
                }
                item {
                    BirthDatePicker(
                        value = operationalValue.birthDate,
                        onValueChange = { onOperationalValueChange(value.copy(birthDate = it)) }
                    )
                }
                item {
                    SexPicker(
                        value = operationalValue.sex,
                        onValueChange = { onOperationalValueChange(value.copy(sex = it)) }
                    )
                }
                item {
                    PassportSeriesInput(
                        value = operationalValue.passportSeries,
                        onValueChange = { onOperationalValueChange(value.copy(passportSeries = it)) }
                    )
                }
                item {
                    PassportNumberInput(
                        value = operationalValue.passportNumber,
                        onValueChange = { onOperationalValueChange(value.copy(passportNumber = it)) }
                    )
                }
                item {
                    PassportAuthorityInput(
                        value = operationalValue.passportAuthority,
                        onValueChange = { onOperationalValueChange(value.copy(passportAuthority = it)) }
                    )
                }
                item {
                    PassportDateOfIssuePicker(
                        value = operationalValue.passportDateOfIssue,
                        onValueChange = { onOperationalValueChange(value.copy(passportDateOfIssue = it)) }
                    )
                }
                item {
                    PassportIdentificationNumberInput(
                        value = operationalValue.passportIdentificationNumber,
                        onValueChange = { onOperationalValueChange(value.copy(passportIdentificationNumber = it)) }
                    )
                }
                item {
                    PlaceOfBirthInput(
                        value = operationalValue.placeOfBirth,
                        onValueChange = { onOperationalValueChange(value.copy(placeOfBirth = it)) }
                    )
                }
                item {
                    LocalitiesOfResidenceList(operationalValue.localitiesOfResidence)
                }
                item {
                    ResidenceAddressInput(
                        value = operationalValue.residenceAddress,
                        onValueChange = { onOperationalValueChange(value.copy(residenceAddress = it)) }
                    )
                }
                item {
                    HomePhoneNumberInput(
                        value = operationalValue.homePhoneNumber,
                        onValueChange = { onOperationalValueChange(value.copy(homePhoneNumber = it)) }
                    )
                }
                item {
                    MobilePhoneNumberInput(
                        value = operationalValue.mobilePhoneNumber,
                        onValueChange = { onOperationalValueChange(value.copy(mobilePhoneNumber = it)) },
                    )
                }
                item {
                    EmailInput(
                        value = operationalValue.email,
                        onValueChange = { onOperationalValueChange(value.copy(email = it)) },
                    )
                }
                item {
                    MaritalStatusPicker(
                        value = operationalValue.maritalStatus,
                        onValueChange = { onOperationalValueChange(value.copy(maritalStatus = it)) },
                    )
                }
                item {
                    CitizenshipPicker(
                        value = operationalValue.citizenship,
                        onValueChange = { onOperationalValueChange(value.copy(citizenship = it)) },
                    )
                }
                item {
                    DisabilityStatusPicker(
                        value = operationalValue.disabilityStatus,
                        onValueChange = { onOperationalValueChange(value.copy(disabilityStatus = it)) },
                    )
                }
                item {
                    IsRetireeCheckBox(
                        value = operationalValue.isRetiree,
                        onValueChange = { onOperationalValueChange(value.copy(isRetiree = it)) },
                    )
                }
                item {
                    MonthlyIncomeInput(
                        value = operationalValue.monthlyIncome,
                        onValueChange = { onOperationalValueChange(value.copy(monthlyIncome = it)) },
                    )
                }
                item {
                    IsMilitaryServiceSubjectCheckBox(
                        value = operationalValue.isMilitaryServiceSubject,
                        onValueChange = { onOperationalValueChange(value.copy(isMilitaryServiceSubject = it)) },
                    )
                }
            }
        }
}

@Composable
fun NullToEmptyStringWrapper(
    value: String?,
    onValueChange: (String?) -> Unit,
    content: @Composable (operationalValue: String, onOperationValueChange: (String) -> Unit) -> Unit
) {
    content.invoke(
        (value ?: "")
    ) {
        onValueChange.invoke(if (it == "") { null } else { it } )
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
    value: java.sql.Date,
    onValueChange: (java.sql.Date) -> Unit
) {
    ValidatableWidget<Date>(
        value = value,
        onValueChange = { onValueChange(java.sql.Date(it.time)) },
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
            isError = !isValid,
            label = { Text("Birth date:") }
        )
    }
}

@Composable
fun SexPicker(
    value: Sex,
    onValueChange: (Sex) -> Unit
) {
    DropdownSelectionButton(
        value = value,
        onOptionClicked = onValueChange,
        options = Sex.values().toList(),
        label = { Text("Sex:") }
    ) {
        Text(it.toString())
    }
}

@Composable
fun PassportSeriesInput(
    value: String,
    onValueChange: (String) -> Unit
) {
    ValidatableTextField(
        value = value,
        onValueChange = onValueChange,
        validate = { validatePassportSeries(it) },
        errorMessage = { Text("Must be of two capital letters") },
        helperMessage = { Text("Enter two letters of passport number") },
        label = { Text("Passport Series") },
    )
}

@Composable
fun PassportNumberInput(
    value: String,
    onValueChange: (String) -> Unit
) {
    ValidatableTextField(
        value = value,
        onValueChange = onValueChange,
        validate = { validatePassportNumber(it) },
        errorMessage = { Text("Must be of 7 digits") },
        helperMessage = { Text("Enter 7 digits of passport number") },
        label = { Text("Passport Number") },
    )
}

@Composable
fun PassportAuthorityInput(
    value: String,
    onValueChange: (String) -> Unit
) {
    ValidatableTextField(
        value = value,
        onValueChange = onValueChange,
        validate = { validatePassportAuthority(it) },
        errorMessage = { Text("Must be not empty") },
        helperMessage = { Text("Enter passport authority") },
        label = { Text("Passport Authority") },
    )
}

@Composable
fun PassportDateOfIssuePicker(
    value: java.sql.Date,
    onValueChange: (java.sql.Date) -> Unit
) {
    ValidatableWidget<Date>(
        value = value,
        onValueChange = { onValueChange(java.sql.Date(it.time)) },
        validate =  { validatePassportDateOffIssue(it) }
    ) { operationalValue, onOperationalValueChange, isValid ->
        DatePicker(
            value = operationalValue,
            onValueChange = onOperationalValueChange,
            from = Date(100, 0, 1),
            to = Date(),
            errorMessage = {
                Text("Must be before now")
            },
            isError = !isValid,
            label = { Text("Passport date of issue:") }
        )
    }
}

@Composable
fun PassportIdentificationNumberInput(
    value: String,
    onValueChange: (String) -> Unit
) {
    ValidatableTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Passport Identification Number") },
        validate = { validatePassportIdentificationNumber(it) },
        errorMessage = { Text("Must be of format 1234567 A 123 AA 1") },
        helperMessage = {
            Text(
                buildAnnotatedString {
                    append("Enter ")

                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("identification")
                    }
                    append(" number of passport")
                }
            )
        },
    )
}

@Composable
fun PlaceOfBirthInput(
    value: String,
    onValueChange: (String) -> Unit
) {
    ValidatableTextField(
        value = value,
        onValueChange = onValueChange,
        validate = { validatePlaceOfBirth(it) },
        errorMessage = { Text("Must be not empty") },
        helperMessage = { Text("Enter place of birth") },
        label = { Text("Place of birth") },
    )
}

@Composable
fun LocalitiesOfResidenceList(
    value: List<String>
) {
    DropdownButton(
        content = {
            Text("Localities of residence")
        },
        options = value,
    ) {
        Text(it)
    }
}

@Composable
fun ResidenceAddressInput(
    value: String,
    onValueChange: (String) -> Unit
) {
    ValidatableTextField(
        value = value,
        onValueChange = onValueChange,
        validate = { validateResidenceAddress(it) },
        errorMessage = { Text("Must be not empty") },
        helperMessage = { Text("Enter address of current residence") },
        label = { Text("Residence Address") },
    )
}

@Composable
fun PhoneNumberInput(
    value: String?,
    onValueChange: (String?) -> Unit,
    validate: (String?) -> Boolean,
    helperMessage: @Composable () -> Unit,
    label: @Composable () -> Unit,
) {
    NullToEmptyStringWrapper(
        value = value,
        onValueChange = onValueChange,
    ) { str, onStrChange ->
        ValidatableTextField(
            value = str,
            onValueChange = onStrChange,
            validate = validate,
            errorMessage = { Text("Must be of format 375XXXXXXXXX") },
            helperMessage = helperMessage,
            label = label,
        )

    }
}
@Composable
fun HomePhoneNumberInput(
    value: String?,
    onValueChange: (String?) -> Unit
) {
    PhoneNumberInput(
        value = value,
        onValueChange = onValueChange,
        validate = { validateHomePhoneNumber(it) },
        helperMessage = { Text("Enter home phone number") },
        label = { Text("Mobile Phone") },
    )
}

@Composable
fun MobilePhoneNumberInput(
    value: String?,
    onValueChange: (String?) -> Unit
) {
    PhoneNumberInput(
        value = value,
        onValueChange = onValueChange,
        validate = { validateMobilePhoneNumber(it) },
        helperMessage = { Text("Enter mobile phone number") },
        label = { Text("Mobile Phone") },
    )
}

@Composable
fun EmailInput(
    value: String?,
    onValueChange: (String?) -> Unit
) {
    NullToEmptyStringWrapper(
    value = value,
    onValueChange = onValueChange,
    ) { str, onStrChange ->
        ValidatableTextField(
            value = str,
            onValueChange = onStrChange,
            validate = { validateEmail(it) },
            errorMessage = { Text("Must be of email format? like example@mail.com") },
            helperMessage = { Text("Enter active email address") },
            label = { Text("Email") },
        )
    }
}

@Composable
fun MaritalStatusPicker(
    value: MaritalStatus,
    onValueChange: (MaritalStatus) -> Unit
) {
    DropdownSelectionButton(
        value = value,
        onOptionClicked = onValueChange,
        options = MaritalStatus.values().toList(),
        label = { Text("Marital status:") }
    ) {
        Text(it.toString())
    }
}

@Composable
fun CitizenshipPicker(
    value: Citizenship,
    onValueChange: (Citizenship) -> Unit
) {
    DropdownSelectionButton(
        value = value,
        onOptionClicked = onValueChange,
        options = Citizenship.values().toList(),
        label = { Text("Citizenship:") }
    ) {
        Text(it.toString())
    }
}

@Composable
fun DisabilityStatusPicker(
    value: DisabilityStatus,
    onValueChange: (DisabilityStatus) -> Unit
) {
    DropdownSelectionButton(
        value = value,
        onOptionClicked = onValueChange,
        options = DisabilityStatus.values().toList(),
        label = { Text("Disability status:") }
    ) {
        Text(it.toString())
    }
}

@Composable
fun IsRetireeCheckBox(
    value: Boolean,
    onValueChange: (Boolean) -> Unit
) {
    LabelledCheckBox(
        checked = value,
        onCheckedChange = onValueChange,
        label = { Text("Retired") }
    )
}

@Composable
fun MonthlyIncomeInput(
    value: BigDecimal?,
    onValueChange: (BigDecimal?) -> Unit
) {
    ValidatableWidget(
        value = value,
        onValueChange = onValueChange,
        validate = { validateMonthlyIncome(it) }
    ) { convertedValue, onConvertedValueChange, isConvertedValueValid ->

        NullToEmptyStringWrapper(
            value = convertedValue?.toString(),
            onValueChange = { onConvertedValueChange(it?.let { BigDecimal(it) }) },
        ) { str, onStrChange ->

            ValidatableTextField(
                value = str,
                label = { Text("Monthly income") },
                onValueChange = onStrChange,
                validate = { isConvertedValueValid && validateMonthlyIncomeString(it) },
                errorMessage = { Text("Must be of format 1234.00") },
                helperMessage = { Text("Enter monthly income amount") },
            )
        }
    }
}

@Composable
fun IsMilitaryServiceSubjectCheckBox(
    value: Boolean,
    onValueChange: (Boolean) -> Unit
) {
    LabelledCheckBox(
        checked = value,
        onCheckedChange = onValueChange,
        label = { Text("Military service subject") }
    )
}
