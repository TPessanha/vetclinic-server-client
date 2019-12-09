import React from "react";
import { RootState } from "../../store/store";
import {
    TypedUseSelectorHook,
    useSelector as useReduxSelector,
    useDispatch
} from "react-redux";
import { Button } from "react-bootstrap";
import { vetActions } from "../../actions/VetActions";
import { useParams } from "react-router";

export const useSelector: TypedUseSelectorHook<RootState> = useReduxSelector;

const VetAppointmentDetails = () => {
    const { id } = useParams();
    const dispatch = useDispatch();

    const a = useSelector(
        state => state.VetAppointementReducer.selectedAppointment
    );
    // eslint-disable-next-line
    if (a.id == -1) return <div>No appointment selected</div>;

    let CompletedBtn = (
        <Button
            onClick={() => dispatch(vetActions.complete_app(id as string, a))}
        >
            Complete
        </Button>
    );

    let AcceptBtn = (
        <Button
            onClick={() => dispatch(vetActions.accept_app(id as string, a))}
        >
            Accept
        </Button>
    );

    return (
        <div>
            <div>
                <label>ID: {a.id}</label>
                <label>Description: {a.description}</label>
                <label>Status: {a.status}</label>
            </div>
            <div>{CompletedBtn}</div>
            <div>{AcceptBtn}</div>
        </div>
    );
};

export default VetAppointmentDetails;
