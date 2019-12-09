import React, {useEffect, useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {A, useRoutes} from "hookrouter";
import Home from "./Home/Home";
import Login from "./Login";
import {Singup} from "./Register";
import NotFound from "./Erros/NotFound";
import Administrator from "./Administrator/Administrator";
import {UserType} from "../api";
import Logout from './Logout';
import {ClientDashboard} from "./Client/ClientDashboard";
import {User} from "./User/User";
import {REDIRECT} from "../constants/actionTypes";
import VetMainPage from "./Vet/VetMainPage";
import VetAppointement from "./Vet/VetAppointments";


const routes = {
    "/": () => <Home/>,
    "/login": () => <Login/>,
    "/register": () => <Singup/>,
    "/logout": () => <Logout/>,
    "/users": () => <User/>,
    "/admin/:username": ({id}: any) => <Administrator id={id}/>,
    "/cliend/:username": ({username}: any) => <ClientDashboard username={username}/>,
    "/administrators/:id": () => <VetMainPage/>,
    "/veterinarians/:id/appointments": ({id}: any) => <VetAppointement id={id}/>,
    "/veterinarians/:id": ({id}: any) => <VetMainPage id={id}/>,
    "/client/:id": ({id}: any) => <VetMainPage id={id}/>,

};

const LoggedOutView = (props: any) => {
    if (!props.isLoggedIn) {
        return <>
            <ul className="nav navbar-nav pull-xs-right">

                <li className="nav-item">
                    <A href="/" className="nav-link">
                        Home
                    </A>
                </li>

                <li className="nav-item">
                    <A href="/login" className="nav-link">
                        Login
                    </A>
                </li>

                <li className="nav-item">
                    <A href="/register" className="nav-link">
                        Sign up
                    </A>
                </li>
            </ul>
        </>;
    }
    return null;
};

const LoggedInView = (props: any) => {
    const currentUser = props.currentUser;
    const userType = props.userType;
    const isLoggedIn = props.isLoggedIn;
    const [display, setDisplay] = useState(<></>)
    const dispatch = useDispatch();


    const onClickRedirect = (to: any) => {
        dispatch({type: REDIRECT, redirectTo: to})
    }


    useEffect(() => {
            if (isLoggedIn && currentUser) {

                const d = userType === UserType.CLIENT ? <>
                    <li className="nav-item">
                        <A href="/" className="nav-link">
                            Pets
                        </A>
                    </li>
                    <li className="nav-item">
                        <A href="/" className="nav-link">
                            Appointments
                        </A>
                    </li>
                </> : userType === UserType.VETERINARIAN ? <>
                    <li className="nav-item">
                        <A href={'/veterinarians/' + currentUser.id} className="nav-link">
                            Veterinarian Page
                        </A>
                    </li>
                    <li className="nav-item">
                        <A href={'/veterinarians/' + currentUser.id + '/appointments'} className="nav-link">
                            Veterinarian Appointments
                        </A>
                    </li>
                </> : userType === UserType.ADMINISTRATOR ? <>
                    <li className="nav-item">
                        <a onClick={() => onClickRedirect("users")} className="nav-link">
                            See All User
                        </a>
                    </li>
                </> : <></>;
                setDisplay(d)
            }

        }, [isLoggedIn, currentUser]
    )

    if (isLoggedIn) {


        if (currentUser) {
            return <>
                <ul className="nav navbar-nav pull-xs-right">
                    {display}
                    <li className="nav-item">
                        <A href="/" className="nav-link">
                            Home
                        </A>
                    </li>

                    <li className="nav-item">
                        <A href="/logout" className="nav-link">
                            Logout
                        </A>
                    </li>
                    <li className="nav-item">
                        <A
                            href={`${currentUser.role}/:${currentUser.id}`}
                            className="nav-link">
                            {currentUser.name}
                        </A>
                    </li>
                </ul>
            </>;
        }

        return <>
            <ul className="nav navbar-nav pull-xs-right">
                {display}
                <li className="nav-item">
                    <A href="/" className="nav-link">
                        Home
                    </A>
                </li>

                <li className="nav-item">
                    <A href="/logout" className="nav-link">
                        Logout
                    </A>
                </li>
            </ul>
        </>;
    }

    return <></>;
};

function Header(props: any) {

    const routeResult = useRoutes(routes);
    const appName = useSelector((state: any) => state.common.appName);
    const userType = useSelector((state: any) => state.common.userType);
    const token = useSelector((state: any) => state.common.token);
    const currentUser = useSelector((state: any) => state.common.currentUser);
    const isLoggedIn = useSelector((state: any) => state.common.isLoggedIn);


    return <>
        <nav className="navbar navbar-light">
            <div className="container">
                <A href="/" className="navbar-brand">{appName}</A>
                <LoggedOutView isLoggedIn={isLoggedIn}/>
                {token ? <> <LoggedInView currentUser={currentUser} userType={userType}
                                          isLoggedIn={isLoggedIn}/> </> : <></>}

            </div>
        </nav>
        {routeResult || <NotFound/>}
    </>
}

export default Header;
