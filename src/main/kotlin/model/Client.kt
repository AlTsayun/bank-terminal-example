package model

import java.math.BigDecimal
import java.sql.Date
import java.util.*

data class Client(
    val id: UUID,
    val surname: String,
    val name: String,
    val patronymic: String,
    val birthDate: Date,
    val sex: Sex,
    val passportSeries: String,
    val passportNumber: String,
    val passportAuthority: String,
    val passportDateOfIssue: Date,
    val passportIdentificationNumber: String,
    val placeOfBirth: String,
    val localitiesOfResidence: List<String>,
    val residenceAddress: String,
    val homePhoneNumber: String?,
    val mobilePhoneNumber: String?,
    val email: String?,
    val maritalStatus: MaritalStatus,
    val citizenship: Citizenship,
    val disabilityStatus: DisabilityStatus,
    val isRetiree: Boolean,
    val monthlyIncome: BigDecimal?,
    val isMilitaryServiceSubject: Boolean
)

enum class Sex {
    MALE, FEMALE
}

enum class MaritalStatus {
    MARRIED, SINGLE
}

enum class DisabilityStatus {
    DISABLED, UNLIMITED
}

enum class Citizenship {
    BELARUS, RUSSIA
}