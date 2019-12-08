import React from "react";
import {EmployeeDetail} from "./EmployeeDetail";

export const EmployeeList = (props: any) => {
    const employees = props.employees;
    const list = employees ? employees.map((employee: any) =>
        <EmployeeDetail employee={employee}/>) : null;

    return list ?
        <>
            <div className="row">
                {list}
            </div>
        </>
        : <></>

};