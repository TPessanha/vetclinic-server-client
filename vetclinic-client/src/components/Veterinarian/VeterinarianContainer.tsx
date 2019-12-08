import React from "react";
import {useSelector} from "react-redux";
import {VeterinarianList} from "./VeterinarianList";

export const VeterinarianContainer = () => {
    const veterinarians = useSelector((state: any) => state.employee.veterinarians);

    // useEffect(() => {
    //
    // });

    return veterinarians ? <>
        <div>
            <VeterinarianList veterinarians={veterinarians || []}/>
        </div>
    </> : <></>

};
