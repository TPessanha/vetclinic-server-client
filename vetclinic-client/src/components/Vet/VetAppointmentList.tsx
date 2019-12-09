import React from "react";
import Appointment from "../../model/Appointment";
import { RootState } from "../../store/store";
import {
    TypedUseSelectorHook,
    useSelector as useReduxSelector,
    useDispatch
} from "react-redux";
import { vetActions } from "../../actions/VetActions";

export const useSelector: TypedUseSelectorHook<RootState> = useReduxSelector;

export const VetAppointmentList = () => {
    const appointments = useSelector(
        state => state.VetAppointementReducer.appointments
    );

    const selectedAppointment = useSelector(
        state => state.VetAppointementReducer.selectedAppointment
    );

    const hasElements = appointments != null && appointments.length > 0;

    if (!hasElements) return <div>Empty list</div>;

    return (
        <ul
            className="list-group"
            style={{
                margin: "15px 15px 15px 15px"
            }}
        >
            {appointments.map((app: Appointment) => (
                <VetAppointmentListItem
                    key={app.id}
                    appointment={app}
                    // eslint-disable-next-line
                    selected={(selectedAppointment as Appointment).id == app.id}
                />
            ))}
        </ul>
    );
};

export const VetAppointmentListItem = (props: {
    appointment: Appointment;
    selected: boolean;
}) => {
    const app = props.appointment;
    let classes = "list-group-item";
    if (props.selected) classes = "list-group-item active";

    const dispatch = useDispatch();

    return (
        <li
            className={classes}
            onClick={() => {
                dispatch(vetActions.select_app(app));
            }}
        >
            {app.client} | ({app.year}/{app.month} | {app.description})
        </li>
    );
};

export default VetAppointmentList;
