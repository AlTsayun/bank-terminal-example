import model.*
import java.math.BigDecimal
import java.sql.Date
import java.util.*

fun getClient(id: Int): Client {
    return Client(
        id = UUID.randomUUID(),
        surname = "Surname $id",
        name = "Name $id",
        patronymic = "Patronymic $id",
        birthDate = Date.valueOf("200$id-01-01"),
        sex = Sex.MALE,
        passportSeries = "MP",
        passportNumber = "1234567",
        passportAuthority = "MINISTRY OF INTERNAL AFFAIRS",
        passportDateOfIssue = Date.valueOf("2020-01-01"),
        passportIdentificationNumber = "0000000A000AA0",
        placeOfBirth = "REPUBLIC OF BELARUS",
        localitiesOfResidence = listOf("Brest", "Vitebsk", "Gomel", "Grodno", "Minsk", "Mogilev"),
        residenceAddress = "residence address",
        homePhoneNumber = "375000000000",
        mobilePhoneNumber = "375000000000",
        maritalStatus = MaritalStatus.SINGLE,
        email = "email@mail.com",
        citizenship = Citizenship.BELARUS,
        disabilityStatus = DisabilityStatus.UNLIMITED,
        isRetiree = false,
        monthlyIncome = BigDecimal("50000"),
        isMilitaryServiceSubject = false
    )
}

fun getClients(): MutableList<Client> {
    return (0..5).asIterable()
        .map { getClient(it) }
        .toMutableList()
}