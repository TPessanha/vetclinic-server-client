import React from "react";
import {EmployeeDetail} from "./EmployeeDetail";

export const EmployeeList = (props: any) => {
    const employees = props.employees;
    const list = employees.map((employee: any) => <EmployeeDetail employee={employee}/>);

    return employees ?
        <>
            <div className="row">
                {list}
            </div>
        </>
        : <></>

};