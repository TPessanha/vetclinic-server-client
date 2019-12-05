/*baseado em AdministratorList*/


import {Link} from 'react-router-dom';
import React from 'react';

const ClientInfoDetails = (props: any) => {
    const client = props.client;
    return (
        <div className="client-details">

            <div className="client-info">
               
                <span className="name">{client.name}</span>
                <span className="email">{client.email}</span>
                <span className="picture">{client.picture}</span>
                <span className="username">{client.username}</span>
                <span className="phoneNumber">{client.phoneNumber}</span>
                <span className="address">{client.adress}</span>
            </div>
           
        </div>
    );
};

export default ClientInfoDetails;