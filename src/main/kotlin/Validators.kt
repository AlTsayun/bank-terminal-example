import model.Client
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.ParseException
import java.text.ParsePosition
import java.util.*
import java.util.regex.Pattern

val EMAIL_ADDRESS_PATTERN = Pattern.compile(
    "[a-zA-Z0-9+._%\\-]{1,256}" +
            "@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)

val DECIMAL_FORMAT = DecimalFormat("0.00").apply {
    isParseBigDecimal = true
}

fun validateSurname(str: String): Boolean {
    return str.length > 1 && str.all { it.isLetter() } && str[0].isUpperCase()
}

fun validateName(str: String): Boolean {
    return str.length > 1 && str.all { it.isLetter() } && str[0].isUpperCase()
}

fun validatePatronymic(str: String): Boolean {
    return str.length > 1 && str.all { it.isLetter() } && str[0].isUpperCase()
}

fun validateBirthDate(date: Date): Boolean {
    return date.before(Calendar.getInstance().time)
}

fun validatePassportSeries(str: String): Boolean {
    return str.length == 2 && str.all { it.isLetter() } && str.all { it.isUpperCase() }
}

fun validatePassportNumber(str: String): Boolean {
    return str.length == 7 && str.all { it.isDigit() }
}

fun validatePassportAuthority(str: String): Boolean {
    return str.isNotBlank()
}

fun validatePassportDateOffIssue(issueDate: Date): Boolean {
    return issueDate.before(Calendar.getInstance().time)
}

fun validatePassportIdentificationNumber(str: String): Boolean {
    return str.length == 14
            && str.substring(0..6).all { it.isDigit() }
            && str[7].isLetter()
            && str.substring(8..10).all { it.isDigit() }
            && str.substring(11..12).all { it.isLetter() }
            && str[13].isDigit()
}

fun validatePlaceOfBirth(str: String): Boolean {
    return str.length > 1
}

fun validateResidenceAddress(str: String): Boolean {
    return str.length > 1
}

fun validatePhoneNumber(str: String): Boolean {
    return str.length == 12
            && str.substring(0..2) == "375"
            && str.substring(3..11).all { it.isDigit() }
}

fun validateHomePhoneNumber(str: String?): Boolean {
    return str == null || validatePhoneNumber(str)
}

fun validateMobilePhoneNumber(str: String?): Boolean {
    return str == null || validatePhoneNumber(str)
}

fun validateEmail(str: String?): Boolean {
    return str == null || EMAIL_ADDRESS_PATTERN.matcher(str).matches()
}

fun validateMonthlyIncome(value: BigDecimal?): Boolean {
    return value == null || value >= BigDecimal.ZERO
}

fun validateMonthlyIncomeString(value: String): Boolean {
    return try {
        val position = ParsePosition(0)
        DECIMAL_FORMAT.parse(value, position)
        return position.index == value.length && position.errorIndex == -1
    } catch (e: ParseException) {
        false
    }
}

fun validateClient(value: Client): Boolean {
    return value.birthDate.before(value.passportDateOfIssue)
}