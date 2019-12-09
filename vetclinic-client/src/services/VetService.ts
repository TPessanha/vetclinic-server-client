import Veterinarian from "../model/Veterinarian";
import { getData, putData } from "../utils/apiUtils";
import Appointment from "../model/Appointment";
import { checkJWT } from "./userService";

export const vetService = {
    getVetDetails,
    getVetAppointments,
    updateAppointment
};

const apiVet = "/veterinarians";

function getVetDetails(id: string): Promise<Veterinarian> {
    checkJWT();

    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Authorization", localStorage.getItem("jwt") as string);

    const requestOptions = {
        method: "GET",
        headers: myHeaders
    };

    return fetch(`${apiVet}/${id}`, requestOptions).then(response => {
        return getData<Veterinarian>(response);
    });
}

function getVetAppointments(id: string): Promise<Appointment[]> {
    checkJWT();

    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Authorization", localStorage.getItem("jwt") as string);

    const requestOptions = {
        method: "GET",
        headers: myHeaders
    };

    return fetch(`${apiVet}/${id}/appointments`, requestOptions).then(
        response => {
            return getData<Appointment[]>(response);
        }
    );
}

function updateAppointment(
    vetId: string,
    appointment: Appointment
): Promise<Appointment> {
    checkJWT();

    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Authorization", localStorage.getItem("jwt") as string);

    const requestOptions = {
        method: "PUT",
        headers: myHeaders,
        body: JSON.stringify(appointment)
    };

    return fetch(
        `${apiVet}/${vetId}/appointments/${appointment.id}`,
        requestOptions
    ).then(response => {
        return putData<Appointment>(response, appointment);
    });
}
