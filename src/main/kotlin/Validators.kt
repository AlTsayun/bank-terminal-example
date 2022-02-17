import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import model.Sex
import java.util.*


//
//interface ValidationProperty<T> {
//    fun validate(value: T): Boolean
//    fun getPropertyId(): String
//}
//
//interface ExValidationProperty<T> {
//    fun validate(value: T): Boolean
//    fun getErrorMessage(): @Composable (() -> Unit)?
//    fun getHelperMessage(): @Composable (() -> Unit)?
//    fun getPropertyId(): String
//}
//
//fun getNameValidator(): ExValidationProperty<String> {
//    return object : ExValidationProperty<String> {
//        override fun validate(value: String): Boolean {
//            return value.length > 1 && value.all { it.isLetter() } && value[0].isUpperCase()
//        }
//
//        override fun getHelperMessage(): () -> Unit = {
//            Text("Must contain only multiple letters and begin with capital")
//        }
//
//        override fun getErrorMessage(): () -> Unit = {
//            Text("Must contain only multiple letters and begin with capital")
//        }
//    }
//}
//
//fun getSurnameValidator(): ExValidationProperty<String> {
//    return getNameValidator()
//}
//
//fun getPatronymicValidator(): ExValidationProperty<String> {
//    return getNameValidator()
//}

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
fun validateSex(sex: Sex): Boolean {
    return true
}
fun validatePassportSeries(str: String): Boolean {
    return true
}
fun validatePassportNumber(str: String): Boolean {
    return true
}
fun validatePassportAuthority(str: String): Boolean {
    return true
}
fun validatePassportDateOffIssue(issueDate: Date, birthDate: Date): Boolean {
    return issueDate.before(Calendar.getInstance().time) && issueDate.after(birthDate)
}
fun validatePassportIdentificationNumber(str: String): Boolean {
    //todo: add passportIdentificationNumber validation
    return true
}
fun validatePlaceOfBirth(str: String): Boolean {
    return str.length > 1 && str.all { it.isLetter() } && str[0].isUpperCase()
}
fun validateLocalitiesOfResidence(str: String): Boolean {
    //todo: add localitiesOfResidence validation
    return true
}
fun validateResidenceAddress(str: String): Boolean {
    //todo: add localitiesOfResidence validation
    return true
}
fun validateHomePhoneNumber(str: String?): Boolean {
    //todo: add homePhoneNumber validation
    return true
}
fun validateMobilePhoneNumber(str: String?): Boolean {
    //todo: add mobilePhoneNumber validation
    return true
}
