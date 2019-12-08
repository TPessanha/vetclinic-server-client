import React from "react";
import {VeterinarianDetail} from "./VeterinarianDetail";

export const VeterinarianList = (props: any) => {
    const employees = props.employees;
    const list = employees && employees.size > 0 ? employees.map((employee: any) => <VeterinarianDetail
        employee={employee}/>) : [];

    return employees ?
        <>
            <div className="row">
                {list}
            </div>
        </>
        : <></>

};