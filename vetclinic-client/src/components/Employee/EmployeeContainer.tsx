import React, {useEffect, useState} from "react";
import {useSelector} from "react-redux";
import {EmployeeList} from "./EmployeeList";

export const EmployeeContainer = () => {
    const admin = useSelector((state: any) => state.employee.employees_Administrator);
    const vet = useSelector((state: any) => state.employee.employees_Veterinarian);
    const [employees, setEmployee] = useState();

    useEffect(() => {
        if (!employees && vet && admin) {
            console.log(vet)
            setEmployee(vet.concat(admin))
        }
    });

    return employees ? <>
        <div >
            <EmployeeList employees={employees || []}/>
        </div>
    </> : <></>

};
