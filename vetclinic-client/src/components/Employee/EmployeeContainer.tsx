import React, {useEffect, useState} from "react";
import {useSelector} from "react-redux";
import {EmployeeList} from "./EmployeeList";

export const EmployeeContainer = () => {
    const admin = useSelector((state: any) => state.employee.administrators);
    const vet = useSelector((state: any) => state.employee.veterinarians);
    const [employees, setEmployee] = useState();

    useEffect(() => {
        if (!employees && vet && admin) {
            try {
                setEmployee(vet.concat(admin))

            } catch (e) {
                console.log(e)
            }

        }
    });

    return employees ? <>
        <div>
            <EmployeeList employees={employees || []}/>
        </div>
    </> : <></>

};
