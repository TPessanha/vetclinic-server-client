import Appointment from "./Appointment";

export default class appList {
    selectedAppointment: Appointment;
    appointments: Appointment[];
    loaded: boolean;
    constructor(
        selectedAppointment: Appointment,
        appointments: Appointment[],
        loaded: boolean
    ) {
        this.selectedAppointment = selectedAppointment;
        this.appointments = appointments;
        this.loaded = loaded;
    }
}
