import AdministratorAction from './AdministratorAction';
import {Link} from 'react-router-dom';
import React from 'react';

const AdministratorDetails = (props: any) => {
    const administrator = props.administrator;
    return (
        <div className="administrator-details">

            <Link to={`/@${administrator.username}`}>
                <img src={administrator.image} alt={administrator.username}/>
            </Link>

            <div className="administrator-info">
                <Link to={`/@${administrator.username}`} className="administrator">
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
