import React, {useEffect} from "react";
import VetAppointmentList from "./VetAppointmentList";

import {useDispatch, useSelector} from "react-redux";
import VetAppointmentDetails from "./VetAppointmentDetails";
import api from "../../api";
import {VETERINARIAN_PAGE_LOADED} from "../../constants/actionTypes";


const VetAppointments = (props: any) => {
    const id = useSelector((state: any) => state.common.currentUser.id);
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch({type: VETERINARIAN_PAGE_LOADED, payload: api.Veterinarian.getVetAppointments(id)});
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
