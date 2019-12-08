export default class Veterinarian {
    id: number;
    name: string;
    address: string;
    email: string;
    employeeId: number;
    enabled: boolean;
    password: string;
    phoneNumber: number;
    username: string;

    constructor(
        id: number,
        name: string,
        address: string,
        email: string,
        employeeId: number,
        enabled: boolean,
        password: string,
        phoneNumber: number,
        username: string
    ) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.employeeId = employeeId;
        this.enabled = enabled;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.username = username;
    }
}
