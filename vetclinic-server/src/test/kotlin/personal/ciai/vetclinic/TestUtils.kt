package personal.ciai.vetclinic

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.net.URI
import java.util.Base64
import java.util.Date
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertAll
import personal.ciai.vetclinic.config.JWTSecret
import personal.ciai.vetclinic.model.Appointment
import personal.ciai.vetclinic.model.Client
import personal.ciai.vetclinic.model.Pet
import personal.ciai.vetclinic.model.TimeSlot
import personal.ciai.vetclinic.model.Veterinarian

object TestUtils {
    val clientExample1 = Client(0, "gaer@gmail.com", "Pedro", 412532, "Pedro123", "password", "Rua Pedro da cenas")
    val vetExample1 =
        Veterinarian(
            id = 0,
            email = "vetmail@das",
            name = "veterio",
            phoneNumber = 52345235,
            username = "vet123",
            password = "secret",
            address = "Rua dos vets",
            photo = URI("asdf"),
            enabled = true,
            appointments = arrayListOf(),
            employeeId = 12
        )

    val dogExample = Pet(
        0,
        "Bulldog",
        4,
        notes = "Happy dog",
        owner = clientExample1,
        physicalDescription = "Big Dog",
        medicalRecord = "Medical stuff",
        photo = null
    )
    val pigExample = Pet(
        0,
        "Pig",
        1,
        notes = "Cute",
        owner = clientExample1,
        physicalDescription = "Fluffy",
        medicalRecord = "Medical stuff",
        photo = null
    )
    val iguanaExample = Pet(
        0,
        "Iguana",
        2,
        notes = "Looks angry",
        owner = clientExample1,
        physicalDescription = "Green",
        medicalRecord = "Medical stuff",
        photo = null
    )
    val petList = listOf<Pet>(dogExample, pigExample, iguanaExample)

    val appointmentExample1 =
        Appointment(0, 10, 2019, TimeSlot(0, 2), vetExample1, dogExample, clientExample1)

    val appointmentList = listOf<Appointment>(appointmentExample1)

    val clientExample = Client(1, "diogo@gmail.com", "Diogo", 9143235, "Trisks", "345", "Rua das pedras")

    fun assertPetEquals(p1: Pet, p2: Pet) {
        assertAll("Is pet the same?",
//            { assertNotEquals(p1.id, p2.id) }, //ID changes because its auto-generated
            { assertEquals(p1.species, p2.species) },
            { assertEquals(p1.age, p2.age) },
            { assertEquals(p1.notes, p2.notes) },
            { assertEquals(p1.physicalDescription, p2.physicalDescription) },
            { assertEquals(p1.medicalRecord, p2.medicalRecord) },
            { assertEquals(p1.appointments, p2.appointments) }
        )
    }

    fun assertAppointmentEquals(a1: Appointment, a2: Appointment) {
        assertAll("Is appointment the same?",
//            { assertNotEquals(a1.id, a2.id) }, // the id is different because it is generated by Spring
            { assertEquals(a1.timeSlot.startDate, a2.timeSlot.startDate) },
            { assertEquals(a1.timeSlot.endDate, a2.timeSlot.endDate) },
            { assertEquals(a1.description, a2.description) },
            { assertPetEquals(a1.pet, a2.pet) }
        )
    }

    fun generateTestToken(username: String, authorities: List<String>): String {
        val passphrase = "este é um grande segredo que tem que ser mantido escondido"
        val KEY: String = Base64.getEncoder().encodeToString(passphrase.toByteArray())
        val SUBJECT = "JSON Web Token for CIAI 2019/20"
        val VALIDITY = 1000 * 60 * 60 * 10 // 10 minutes in microseconds

        val claims = HashMap<String, Any?>()
        claims["username"] = username
        claims["authorities"] = authorities.toString()

        val token = Jwts
            .builder()
            .setClaims(claims)
            .setSubject(JWTSecret.SUBJECT)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + JWTSecret.VALIDITY))
            .signWith(SignatureAlgorithm.HS256, JWTSecret.KEY)
            .compact()

        return "Bearer $token"
    }
}
