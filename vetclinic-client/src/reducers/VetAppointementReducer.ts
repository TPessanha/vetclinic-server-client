import {
    vetConstants,
    VetAction,
    VetAppListAction,
    vetAppListConstants,
    filterConstants
} from "../actions/VetActions";
import appList from "../model/AppointmentList";
import { getEmpty } from "../model/Appointment";

const initialState = new appList(getEmpty(), [], false);

export function VetAppointementReducer(
    state = initialState,
    action: VetAction
): appList {
    switch (action.type) {
        case vetConstants.SELECT_APP:
            return { ...state, selectedAppointment: action.appointment };
        case vetConstants.LOADING_LIST:
            return new appList(getEmpty(), [], false);
        case vetConstants.LOADED_LIST:
            return new appList(getEmpty(), action.appointments, true);
        case vetConstants.COMPLETE_APP_RESQUEST:
        case vetConstants.ACCEPT_APP_RESQUEST:
            return state;
        case vetConstants.COMPLETE_APP:
        case vetConstants.ACCEPT_APP:
            let newState = {
                ...state,
                selectedAppointment: action.appointment
            };
            newState.appointments.forEach(a => {
                // eslint-disable-next-line
                if (a.id == action.appointment.id) {
                    a.status = action.appointment.status;
                }
                console.log(a);
            });
            return newState;
        case vetConstants.FAILURE:
            return state;
        default:
            return state;
    }
}

export function filter(
    state = filterConstants.SHOW_ALL,
    action: VetAppListAction
) {
    switch (action.type) {
        case vetAppListConstants.SET_FILTER:
            return action.filter;
        default:
            return state;
    }
}

export default VetAppointementReducer;
