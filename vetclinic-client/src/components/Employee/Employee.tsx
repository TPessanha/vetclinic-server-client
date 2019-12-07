import React from "react";
import {EmployeeContainer} from "./EmployeeContainer";
import {useSelector} from "react-redux";


export const Employee = () => {
    const isLoading = useSelector((state: any) => state.employee.inProgress);

    return isLoading ? <></> : <>
        <div className="col-md-6 offset-md-3 col-xs-12">
            <EmployeeContainer/>
        </div>
    </>;

};