import AdministratorAction from './AdministratorAction';
import {Link} from 'react-router-dom';
import React from 'react';

const AdministratorDetails = (props: any) => {
    const client = props.client;
    return (
        <div className="client-details">

            <Link to={`/@${client.username}`}>
                <img src={client.image} alt={client.username}/>
            </Link>

            <div className="client-info">
                <Link to={`/@${client.username}`} className="client">
                    {administrator.username}
                </Link>
                <span className="name">{administrator.name}</span>
                <span className="email">{administrator.email}</span>
                <span className="phoneNumber">{administrator.phoneNumber}</span>
            </div>
            <AdministratorAction canModify={props.canModify} administrator={administrator}/>
        </div>
    );
};

export default AdministratorDetails;
