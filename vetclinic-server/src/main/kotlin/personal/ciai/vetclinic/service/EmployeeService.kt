package personal.ciai.vetclinic.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import personal.ciai.vetclinic.exception.AccessForbiddenException
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.model.Employee
import personal.ciai.vetclinic.repository.EmployeeRepository

@Service
class EmployeeService(@Autowired private val employeeRepository: EmployeeRepository) {

    fun getAllEnabledEmployee(): List<Employee> = employeeRepository.findAllByEnabled(true)

    fun existEmployeeById(employeeId: Int): Boolean {
        if (employeeRepository.existsById(employeeId).not()) {
            throw AccessForbiddenException()
        }
        return true
    }

    fun deactivateEmployee(employeeId: Int) {
        val employee: Employee = getOneEmployeeEntity(employeeId)
        employee.enabled = false
        employeeRepository.save(employee)
    }

    private fun getOneEmployeeEntity(id: Int): Employee = employeeRepository.findById(id)
        .orElseThrow { NotFoundException("Employee account with Id $id not found") }
}
