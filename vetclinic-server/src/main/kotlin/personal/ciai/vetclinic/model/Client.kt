package personal.ciai.vetclinic.model

class Client<ClientDTO>(id: Int, name: String, username: String, password: String) : User<ClientDTO>(id = id, name, username,
    password
) {
    override fun toDTO(): ClientDTO {
        TODO("not implemented")
    }
}
