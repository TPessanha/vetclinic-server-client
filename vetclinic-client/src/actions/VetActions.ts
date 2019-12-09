import { Action, Dispatch } from "redux";
import { vetService } from "../services/VetService";
import Appointment from "../model/Appointment";

export const vetActions = {
    select_app,
    load_list,
    complete_app,
    accept_app
};

export const vetConstants = {
    SELECT_APP: "SELECT_APP",
    LOADING_LIST: "LOADING_LIST",
    LOADED_LIST: "LOADED_LIST",
    FAILURE: "FAILURE",
    COMPLETE_APP: "COMPLETE_APP",
    COMPLETE_APP_RESQUEST: "COMPLETE_APP_RESQUEST",
    ACCEPT_APP: "ACCEPT_APP",
    ACCEPT_APP_RESQUEST: "ACCEPT_APP_RESQUEST"
};

export const vetAppListConstants = {
    SET_FILTER: "SET_FILTER"
};

export const filterConstants = {
    SHOW_ALL: "SHOW_ALL",
    SHOW_COMPLETED: "SHOW_COMPLETED",
    SHOW_PENDING: "SHOW_PENDING"
};

function complete_app(vetId: string, appointment: Appointment) {
    return (dispatch: Dispatch<any>) => {
        dispatch(requestComApp(appointment));

        vetService.updateAppointment(vetId, { ...appointment, status: 3 }).then(
            appointment => {
                if (appointment) {
                    dispatch(completed(appointment));
                }
                // history.push("/");
            },
            error => {
                dispatch(failure(error.toString()));
                // dispatch(alertActions.error(error.toString()));
            }
        );
    };
    function requestComApp(appointment: Appointment) {
        return { type: vetConstants.COMPLETE_APP_RESQUEST, appointment };
    }

    function failure(error: any) {
        return { type: vetConstants.FAILURE, error };
    }

    function completed(appointment: Appointment) {
        return { type: vetConstants.COMPLETE_APP, appointment };
    }
}

function accept_app(vetId: string, appointment: Appointment) {
    return (dispatch: Dispatch<any>) => {
        dispatch(requestAcceptApp(appointment));

        vetService.updateAppointment(vetId, { ...appointment, status: 2 }).then(
            appointment => {
                if (appointment) {
                    dispatch(accepted(appointment));
                }
                // history.push("/");
            },
            error => {
                dispatch(failure(error.toString()));
                // dispatch(alertActions.error(error.toString()));
            }
        );
    };
    function requestAcceptApp(appointment: Appointment) {
        return { type: vetConstants.ACCEPT_APP_RESQUEST, appointment };
    }

    function failure(error: any) {
        return { type: vetConstants.FAILURE, error };
    }

    function accepted(appointment: Appointment) {
        return { type: vetConstants.ACCEPT_APP, appointment };
    }
}

function select_app(appointment: Appointment) {
    return { type: vetConstants.SELECT_APP, appointment };
}

function load_list(vetId: string) {
    return (dispatch: Dispatch<any>) => {
        dispatch(loading());

        vetService.getVetAppointments(vetId).then(
            appointments => {
                if (appointments) {
                    dispatch(loaded(appointments));
                }
                // history.push("/");
            },
            error => {
                dispatch(failure(error.toString()));
                // dispatch(alertActions.error(error.toString()));
            }
        );
    };

    function failure(error: any) {
        return { type: vetConstants.FAILURE, error };
    }

    function loading() {
        const emptyList = [] as Appointment[];
        return { type: vetConstants.LOADING_LIST, emptyList };
    }

    function loaded(appointments: Appointment[]) {
        return { type: vetConstants.LOADED_LIST, appointments };
    }
}

export interface VetAction extends Action {
    appointment: Appointment;
    appointments: Appointment[];
}

export interface VetErrorAction extends Action {
    error: string;
}

export interface VetAppListAction extends Action {
    filter: string;
}
