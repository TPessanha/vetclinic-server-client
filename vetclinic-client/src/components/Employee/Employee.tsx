import React from "react";
import {EmployeeContainer} from "./EmployeeContainer";
import {useSelector} from "react-redux";


export const Employee = () => {
    // const isLoading = useSelector((state: any) => state.employee.isLoading);

    return <>
        <div className="container center">
            <p className="text-xs-center">List Of Employees</p>
                <EmployeeContainer/>
        </div>

    </>;
    // !isLoading ? <></> :

};