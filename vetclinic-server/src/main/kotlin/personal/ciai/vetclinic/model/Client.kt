package personal.ciai.vetclinic.model

import javax.persistence.Entity

@Entity
class Client(email: String, name: String,
             phoneNumber: Int,
             username: String, password: String,
             address: String): User(email, name, phoneNumber, username, password, address) {
}
