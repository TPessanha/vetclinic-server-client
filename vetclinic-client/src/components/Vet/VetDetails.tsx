import React from "react";
import Veterinarian from "../../model/Veterinarian";

const VetDetails = (props: { vet: Veterinarian }) => {
    return (
        <div>
            <div>
                <label>
                    <h1>{props.vet.name}</h1>
                </label>
            </div>
            <div>
                <label>
                    <h2>Email: {props.vet.email}</h2>
                </label>
            </div>
            <div>
                <label>
                    <h2>Phone number: {props.vet.phoneNumber}</h2>
                </label>
            </div>
            <div>
                <label>
                    <h2>Address: {props.vet.address}</h2>
                </label>
            </div>
        </div>
    );
};

export default VetDetails;
