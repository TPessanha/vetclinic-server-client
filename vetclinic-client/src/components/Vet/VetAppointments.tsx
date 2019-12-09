import React, {useEffect} from "react";
import VetAppointmentList from "./VetAppointmentList";

import {useDispatch, useSelector} from "react-redux";
import {vetActions} from "../../actions/VetActions";
import VetAppointmentDetails from "./VetAppointmentDetails";


const VetAppointments = (props: any) => {
    const id = useSelector((state: any) => state.common.currentUser.id);
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(vetActions.load_list(id as string));
    });
    if (id) {
        return (
            <div
                style={{
                    display: "flex",
                    flexDirection: "row"
                }}
            >
                <VetAppointmentList/>
                <VetAppointmentDetails id={id}/>
            </div>
        );

    }
    return <></>
};

export default VetAppointments;
