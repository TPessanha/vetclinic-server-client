package personal.ciai.vetclinic.model

import java.time.YearMonth
import java.util.BitSet
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import personal.ciai.vetclinic.dto.ScheduleDTO
import personal.ciai.vetclinic.exception.ConflictException
import personal.ciai.vetclinic.exception.PreconditionFailedException

@Entity
@Table(
    name = "schedules",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["month", "year", "vet_id"])
    ]
)
class Schedule(
    id: Int,

    @Column(nullable = false)
    var month: Int,

    @Column(nullable = false)
    var year: Int,

    @ManyToOne
    @JoinColumn(name = "vet_id")
    var veterinarian: Veterinarian,

    @Column(nullable = false)
    var availableBlocks: ByteArray,

    @Column(nullable = false)
    var bookedBlocks: ByteArray
) : IdentifiedEntity(id) {

    constructor(
        id: Int,
        yearMonth: YearMonth,
        veterinarian: Veterinarian,
        timeBlocks: ByteArray,
        bookedBlocks: ByteArray
    ) : this(id, yearMonth.monthValue, yearMonth.year, veterinarian, timeBlocks, bookedBlocks)

    init {
        if (month > 12 || month < 1)
            throw PreconditionFailedException("Months must be in range 1..12")
        checkConflicks(720, 1)
    }

    fun bookAppointment(timeSlot: TimeSlot) {
        val blocks = BitSet.valueOf(availableBlocks)
        val bookedBlocks = BitSet.valueOf(this.bookedBlocks)
        val toBook = BitSet(720)

        for (i in timeSlot.startDate until (timeSlot.endDate + 1))
            toBook.set(i)

        if (toBook.intersects(bookedBlocks))
            throw ConflictException("That time slot is already in use")

        val copy = BitSet.valueOf(toBook.toByteArray())
        copy.or(blocks)
        if (!copy.equals(blocks))
            throw ConflictException("Tried to book an appointment when veterinarian was not available")

        bookedBlocks.or(toBook)
        this.bookedBlocks = bookedBlocks.toByteArray()
    }

    fun checkConflicks(nBlocks: Int = 720, nBlocksHour: Int = 1, booked: BitSet = BitSet.valueOf(bookedBlocks)) {
        val blocks = BitSet.valueOf(availableBlocks)
        val copy = BitSet.valueOf(availableBlocks)

        copy.or(booked)
        if (!copy.equals(blocks))
            throw ConflictException("Booked appointment when veterinarian was not available")

        if (blocks.cardinality() > 160)
            throw PreconditionFailedException("Veterinarian can't work more than 160h each month")

        val w1 = getHoursInWeek(blocks, nBlocks, 0)
        val w2 = getHoursInWeek(blocks, nBlocks, 1)
        val w3 = getHoursInWeek(blocks, nBlocks, 2)
        val w4 = getHoursInWeek(blocks, nBlocks, 3)
        if ((w1 > 40) or (w2 > 40) or (w3 > 40) or (w4 > 40))
            throw PreconditionFailedException("Veterinarian can't work more than 40h each week")

        if (checkLonger12HourShifts(blocks, nBlocks))
            throw PreconditionFailedException("Veterinarian can't work more than 12h each shift")

        if (checkRestsConflict(blocks, nBlocks, nBlocksHour))
            throw PreconditionFailedException("Veterinarian needs to rest 8H after 6H of work")
    }

    fun checkRestsConflict(blocks: BitSet, nBlocks: Int, nBlocksHour: Int = 1): Boolean {
        val blocksIn8Hours = nBlocksHour * 8 + 1
        var counter = 0
        var i = 0
        do {
            if (!blocks[i])
                counter = 0
            else {
                counter++
                if ((counter >= 6) and (!blocks[i + 1])) {
                    val next = blocks.nextSetBit(i + 1)
                    if ((next != -1) and (next < (i + blocksIn8Hours)))
                        return true
                }
            }
        } while (i++ < nBlocks)
        return false
    }

    fun checkLonger12HourShifts(blocks: BitSet, nBlocks: Int): Boolean {
        var counter = 0
        var i = 0
        do {
            if (!blocks[i])
                counter = 0
            else {
                counter++
                if (counter >= 12)
                    return true
            }
        } while (i++ < nBlocks)
        return false
    }

    fun getHoursInWeek(blocks: BitSet, nBlocks: Int, week: Int): Int {
        var hours = 0
        val start = (nBlocks / 4) * week
        val end = start + (nBlocks / 4)
        for (i in start until end) {
            if (blocks[i])
                hours++
        }
        return hours
    }

    override fun toDTO() = ScheduleDTO(
        id = this.id,
        month = month,
        year = year,
        vetId = veterinarian.id,
        availableBlocks = availableBlocks.toList(),
        bookedBlocks = bookedBlocks.toList()
    )
}
