export default class Appointment {
    id: number;
    year: number;
    month: number;
    startTime: number;
    endTime: number;
    veterinarian: number;
    pet: number;
    client: number;
    description: string;
    status: number;
    justification: string;

    constructor(
        id: number,
        year: number,
        month: number,
        startTime: number,
        endTime: number,
        veterinarian: number,
        pet: number,
        client: number,
        description: string,
        status: number,
        justification: string
    ) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.startTime = startTime;
        this.endTime = endTime;
        this.veterinarian = veterinarian;
        this.pet = pet;
        this.client = client;
        this.description = description;
        this.status = status;
        this.justification = justification;
    }
}

export function getEmpty() {
    return new Appointment(-1, 0, 0, 0, 0, 0, 0, 0, "", 0, "");
}
