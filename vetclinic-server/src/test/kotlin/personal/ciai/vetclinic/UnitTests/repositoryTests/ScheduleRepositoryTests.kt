package personal.ciai.vetclinic.UnitTests.repositoryTests

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import personal.ciai.vetclinic.repository.ScheduleRepository
import personal.ciai.vetclinic.repository.VeterinarianRepository
import personal.ciai.vetclinic.utils.ScheduleUtils.`list of schedules`
import personal.ciai.vetclinic.utils.ScheduleUtils.`schedule 1`
import personal.ciai.vetclinic.utils.ScheduleUtils.assertScheduleEquals
import personal.ciai.vetclinic.utils.VeterinarianUtils.`veterinarian 1`

@ExtendWith(SpringExtension::class)
@SpringBootTest
class ScheduleRepositoryTests {
    @Autowired
    lateinit var `schedules repository`: ScheduleRepository

    @Autowired
    lateinit var `veterinarian repository`: VeterinarianRepository

    @Test
    @Transactional
    fun `Test - Find All Schedule is Empty`() {
        assertTrue(`schedules repository`.findAll().toList().size >= 0)
    }

    @Test
    @Transactional
    fun `Test - Save and Delete a Schedule`() {
        `veterinarian repository`.save(`veterinarian 1`)

        val vet = `veterinarian repository`.getByUsername(`veterinarian 1`.username)
        `schedule 1`.veterinarian = vet
        `schedules repository`.save(`schedule 1`)

        val schedules = `schedules repository`.findAll().toList()
        assertTrue(schedules.size > 1)

        assertTrue(`schedules repository`.findById(schedules.first().id).isPresent())

        assertScheduleEquals(`schedules repository`.findById(schedules.first().id).get(), `schedule 1`)

        `schedules repository`.delete(schedules.first())

        assertTrue(`schedules repository`.findAll().toList().size >= 0)

        // Reset
        `schedule 1`.veterinarian = `veterinarian 1`
        `veterinarian repository`.delete(vet)
    }

    @Test
    @Transactional
    fun `Test - Save and Delete a List of Schedule`() {
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
    fun `Test - Get Schedule by Veterinarian Id`() {
        `veterinarian repository`.save(`veterinarian 1`)

        val vet = `veterinarian repository`.getByUsername(`veterinarian 1`.username)
        `schedule 1`.veterinarian = vet
        `schedules repository`.save(`schedule 1`)

        val schedules = `schedules repository`.findAll().toList()
        assertTrue(schedules.size >= 1)

        assertTrue(`schedules repository`.findById(schedules.first().id).isPresent())

        assertScheduleEquals(`schedules repository`.findById(schedules.first().id).get(), `schedule 1`)

        assertScheduleEquals(`schedules repository`.findByVetId(vet.id).first(), `schedule 1`)

        `schedules repository`.delete(schedules.first())

        assertTrue(`schedules repository`.findAll().toList().size >= 0)

        // Reset
        `schedule 1`.veterinarian = `veterinarian 1`
        `veterinarian repository`.delete(vet)
    }

    @Test
    @Transactional
    fun `Test - Get Schedule by Veterinarian Id and Date`() {
        val vet01 = `veterinarian repository`.save(`veterinarian 1`)

        `schedule 1`.veterinarian = vet01
        val sche = `schedules repository`.save(`schedule 1`)

        val schedules = `schedules repository`.findAll().toList()
        assertTrue(schedules.size >= 1)

        assertScheduleEquals(
            `schedules repository`.findByVetIdAndYearAndMonth(vet01.id, 2019,11 ).get(), sche
        )

        `schedules repository`.delete(schedules.first())
        assertTrue(`schedules repository`.findAll().toList().isNotEmpty())

        // Reset
        `schedule 1`.veterinarian = `veterinarian 1`
        `veterinarian repository`.delete(vet01)
    }
}
