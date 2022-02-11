import model.*
import java.sql.Date
import java.util.*

fun getClient(id: Int): Client {
    return Client(
//        id = UUID.fromString("00000000-0000-0000-0000-000000000000"),
        id = UUID.randomUUID(),
        surname = "Surname $id",
        name = "Name $id",
        patronymic = "Patronymic $id",
        birthDate = Date.valueOf("2000-01-01"),
        sex = Sex.MALE,
        passportSeries = "MP",
        passportNumber = "1234567",
        passportAuthority = "MINISTRY OF INTERNAL AFFAIRS",
        passportDateOfIssue = Date.valueOf("2020-01-01"),
        passportIdentificationNumber = "0000000A000AA0",
        placeOfBirth = "REPUBLIC OF BELARUS",
        localitiesOfResidence = listOf("Brest", "Vitebsk", "Gomel", "Grodno", "Minsk", "Mogilev"),
        residenceAddress = "residence address",
        homePhoneNumber = "+375(00)000-00-00",
        mobilePhoneNumber = "+375(00)000-00-00",
        maritalStatus = MaritalStatus.SINGLE,
        email = "email@mail.com",
        citizenship = Citizenship.BELARUS,
        disabilityStatus = DisabilityStatus.UNLIMITED,
        isRetiree = false,
        monthlyIncome = "50000",
        isMilitaryServiceSubject = false
    )
}