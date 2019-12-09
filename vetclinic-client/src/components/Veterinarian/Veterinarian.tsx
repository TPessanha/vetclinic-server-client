import React from "react";
import {VeterinarianContainer} from "./VeterinarianContainer";
import {useSelector} from "react-redux";


export const Veterinarian = () => {
    const isLoading = useSelector((state: any) => state.veterinarian.isLoading);

    return <>
        <div className="container center">
            <p className="text-xs-center">List Of Veterinaria</p>
            <VeterinarianContainer/>
        </div>

    </>;

};