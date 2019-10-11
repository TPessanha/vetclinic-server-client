package personal.ciai.vetclinic.model

import personal.ciai.vetclinic.dto.UserDTO

/**
 * Models a User.
 *
 * @property email the email of the User.
 * @property name the name of the User.
 * @property phoneNumber the phone number of this User.
 * @property username the username of the User.
 * @property password the username of the User.
 * @property address the adress of the User.
 * @constructor Creates a User DTO.
 */

abstract class User (val email:String, val name:String, val phoneNumber:Int, val username:String, val password:String, val address:String) {


}
