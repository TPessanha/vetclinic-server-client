/*baseado em AdministratorList*/


import {Link} from 'react-router-dom';
import React from 'react';

const PetDetails = (props: any) => {
    const pet = props.client;
    return (
        <div className="pet-details">

            <div className="pet-info">
               
                <span className="chipNumber">{pet.id}</span>
                <span className="species">{pet.species}</span>
                <span className="age">{pet.age}</span>
                <span className="owner">{pet.owner}</span>
                <span className="listOfAppointments">{pet.listOfAppointments}</span>
                <span className="description">{pet.description}</span>
                <span className="healthStatus">{pet.healthStatus}</span>
            </div>
           
        </div>
    );
};

export default PetDetails;