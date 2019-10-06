package personal.ciai.vetclinic.model

data class Administrative(
    var id: Long? = null,
    val fullName: String? = null,
    val picture: String? = null,
    val email: String? = null,
    val phoneNumber: Int? = null,
    var address: String? = null
)
