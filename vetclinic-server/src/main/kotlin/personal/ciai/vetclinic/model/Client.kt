package personal.ciai.vetclinic.model

class Client<ClientDTO>(id: Int) : User<ClientDTO>(id) {
    override fun toDTO(): ClientDTO {
        TODO("not implemented")
    }
}
