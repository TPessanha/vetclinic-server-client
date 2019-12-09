import React, {useEffect} from "react";
import VetAppointmentList from "./VetAppointmentList";

import {useDispatch} from "react-redux";
import {useParams} from "react-router-dom";
import {vetActions} from "../../actions/VetActions";
import VetAppointmentDetails from "./VetAppointmentDetails";


const VetAppointments = () => {
    const {id} = useParams();
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(vetActions.load_list(id as string));
    });

    return (
        <div
            style={{
                display: "flex",
                flexDirection: "row"
            }}
        >
            <VetAppointmentList/>
            <VetAppointmentDetails/>
        </div>
    );
};

export default VetAppointments;
