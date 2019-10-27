package personal.ciai.vetclinic.UnitTests.repositoryTests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import personal.ciai.vetclinic.repository.SchedulesRepository
import personal.ciai.vetclinic.repository.VeterinarianRepository
import personal.ciai.vetclinic.utils.ScheduleUtils.`list of schedules`
import personal.ciai.vetclinic.utils.ScheduleUtils.`schedule 1`
import personal.ciai.vetclinic.utils.ScheduleUtils.assertScheduleEquals
import personal.ciai.vetclinic.utils.ScheduleUtils.generateMonth
import personal.ciai.vetclinic.utils.VeterinarianUtils.`veterinarian 1`

@ExtendWith(SpringExtension::class)
@SpringBootTest
class ScheduleRepositoryTests {
    @Autowired
    lateinit var `schedules repository`: SchedulesRepository

    @Autowired
    lateinit var `veterinarian repository`: VeterinarianRepository

    @Test
    @Transactional
    fun `Test - Find All Schedules is Empty`() {
        assertTrue(`schedules repository`.findAll().toList().isEmpty())
    }

    @Test
    @Transactional
    fun `Test - Save and Delete a Schedule`() {
        `veterinarian repository`.save(`veterinarian 1`)

        val vet = `veterinarian repository`.getByUsername(`veterinarian 1`.username)
        `schedule 1`.veterinarian = vet
        `schedules repository`.save(`schedule 1`)

        val schedules = `schedules repository`.findAll().toList()
        assertTrue(schedules.size == 1)

        assertTrue(`schedules repository`.findById(schedules.first().id).isPresent())

        assertScheduleEquals(`schedules repository`.findById(schedules.first().id).get(), `schedule 1`)

        `schedules repository`.delete(schedules.first())

        assertTrue(`schedules repository`.findAll().toList().isEmpty())

        // Reset
        `schedule 1`.veterinarian = `veterinarian 1`
        `veterinarian repository`.delete(vet)
    }

    @Test
    @Transactional
    fun `Test - Save and Delete a List of Schedules`() {
        `veterinarian repository`.save(`veterinarian 1`)
        val vet = `veterinarian repository`.getByUsername(`veterinarian 1`.username)
        `list of schedules`.map { it.veterinarian = vet }

        `schedules repository`.saveAll(`list of schedules`)
        val schedules = `schedules repository`.findAll().toList()
        assertTrue(schedules.size == `list of schedules`.size)

        `schedules repository`.deleteAll(schedules)
        assertTrue(`schedules repository`.findAll().toList().isEmpty())

        // Reset
        `list of schedules`.map { it.veterinarian = `veterinarian 1` }
        `veterinarian repository`.delete(vet)
    }

    @Test
    @Transactional
    fun `Test - Save and Delete a List of Generate Schedules`() {
        `veterinarian repository`.save(`veterinarian 1`)
        val vet = `veterinarian repository`.getByUsername(`veterinarian 1`.username)
        val `generated list of Schedules` = generateMonth(vet)

        `schedules repository`.saveAll(`generated list of Schedules`)
        val schedules = `schedules repository`.findAll().toList()
        assertTrue(schedules.size == `generated list of Schedules`.size)

        `schedules repository`.deleteAll(schedules)
        assertTrue(`schedules repository`.findAll().toList().isEmpty())

        // Reset
        `veterinarian repository`.delete(vet)
    }

    @Test
    @Transactional
    fun `Test - Get Schedule by Veterinarian Id`() {
        `veterinarian repository`.save(`veterinarian 1`)

        val vet = `veterinarian repository`.getByUsername(`veterinarian 1`.username)
        `schedule 1`.veterinarian = vet
        `schedules repository`.save(`schedule 1`)

        val schedules = `schedules repository`.findAll().toList()
        assertTrue(schedules.size == 1)

        assertTrue(`schedules repository`.findById(schedules.first().id).isPresent())

        assertScheduleEquals(`schedules repository`.findById(schedules.first().id).get(), `schedule 1`)

        assertScheduleEquals(`schedules repository`.findAllByVeterinarian(vet.id).get().first(), `schedule 1`)

        `schedules repository`.delete(schedules.first())

        assertTrue(`schedules repository`.findAll().toList().isEmpty())

        // Reset
        `schedule 1`.veterinarian = `veterinarian 1`
        `veterinarian repository`.delete(vet)
    }

    @Test
    @Transactional
    fun `Test - Get Schedule by Veterinarian Id and Start Date`() {
        `veterinarian repository`.save(`veterinarian 1`)

        val vet = `veterinarian repository`.getByUsername(`veterinarian 1`.username)
        `schedule 1`.veterinarian = vet
        `schedules repository`.save(`schedule 1`)

        val schedules = `schedules repository`.findAll().toList()
        assertTrue(schedules.size == 1)

        assertScheduleEquals(
            `schedules repository`.getVeterinarianAndStartDateIsEqual(
                vet.id,
                schedules.first().timeSlot.startDate
            ).get(), `schedule 1`
        )

        `schedules repository`.delete(schedules.first())
        assertTrue(`schedules repository`.findAll().toList().isEmpty())

        // Reset
        `schedule 1`.veterinarian = `veterinarian 1`
        `veterinarian repository`.delete(vet)
    }

    @Test
    @Transactional
    fun `Test - Get All Schedule by Veterinarian Id and Greater Than Start Date`() {
        `veterinarian repository`.save(`veterinarian 1`)
        val vet = `veterinarian repository`.getByUsername(`veterinarian 1`.username)
        val `generated list of Schedules` = generateMonth(vet)

        `schedules repository`.saveAll(`generated list of Schedules`)
        val schedules = `schedules repository`.findAll().toList()
        assertTrue(schedules.size == `generated list of Schedules`.size)

        assertEquals(
            `schedules repository`.findAllByVeterinarianAndStartDateIsGreaterThanEqual(
                vet.id,
                schedules.first().timeSlot.startDate
            ).size, `generated list of Schedules`.size
        )

        `schedules repository`.deleteAll(schedules)
        assertTrue(`schedules repository`.findAll().toList().isEmpty())

        // Reset
        `veterinarian repository`.delete(vet)
    }
}
