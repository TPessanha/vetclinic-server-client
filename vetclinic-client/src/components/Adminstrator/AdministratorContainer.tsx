import CommentInput from './AdministratorInput';
import AdministratorList from './AdministratorList';
import {Link} from 'react-router-dom';
import React from 'react';
import AdministratorInput from "./AdministratorInput";
import ListErrors from "../ListErrors";

const AdministratorContainer = (props: any) => {
    if (props.currentUser) {

        return (
            <div className="col-xs-12 col-md-8 offset-md-2">
                <div>
                    <ListErrors errors={props.errors}></ListErrors>
                    <AdministratorInput administrator={props.administrator} currentUser={props.currentUser}/>
                </div>

                <AdministratorList
                    administrator={props.administrator}
                    currentUser={props.currentUser}/>
            </div>
        );
    } else {
        return (
            <div className="col-xs-12 col-md-8 offset-md-2">
                <p>
                    <Link to="/login">Login in</Link>
                    &nbsp;or&nbsp;
                    <Link to="/register">sign up</Link>
                    &nbsp;to proceed.
                </p>

                <AdministratorList
                    administrator={props.administrator}
                    currentUser={props.currentUser}/>
            </div>
        );
    }
};

export default AdministratorContainer;
