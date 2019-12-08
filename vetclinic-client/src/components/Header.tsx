import React, {useEffect, useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {A, useRoutes} from "hookrouter";
import Home from "./Home/Home";
import Login from "./Login";
import {Singup} from "./Register";
import NotFound from "./Erros/NotFound";
import Administrator from "./Administrator/Administrator";
import api, {UserType} from "../api";
import Logout from './Logout';
import {ClientDashboard} from "./Client/ClientDashboard";
import {GET_USER} from "../constants/actionTypes";


const routes = {
    "/": () => <Home/>,
    "/login": () => <Login/>,
    "/register": () => <Singup/>,
    "/logout": () => <Logout/>,
    "/admin/:username": ({id}: any) => <Administrator id={id}/>,
    "/cliend/:username": ({username}: any) => <ClientDashboard username={username}/>,
    // "/user/:username": ({username}: any) => <User username={username}/>,
    // "/vet/:id": ({id}: any) => <Veterinarian id={id}/>,
    // "/cli/:id": ({id}: any) => <Client id={id}/>
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
    const [display, setDisplay] = useState(<></>)

    useEffect(() => {
            if (props.isLoggedIn && currentUser) {

                const d = props.userType === UserType.CLIENT ? <>
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
                </> : props.userType === UserType.VETERINARIAN ? <>
                    <li className="nav-item">
                        <A href="/" className="nav-link">
                            Schedules
                        </A>
                    </li>
                    <li className="nav-item">
                        <A href="/" className="nav-link">
                            Appointments
                        </A>
                    </li>
                </> : props.userType === UserType.ADMINISTRATOR ? <>
                    <li className="nav-item">
                        <A href="/" className="nav-link">
                            See All User
                        </A>
                    </li>
                </> : <></>;
                setDisplay(d)
            }
        }, []
    )

    if (props.isLoggedIn) {


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
                            href={`user/${currentUser.username}`}
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
                {/*<li className="nav-item">*/}
                {/*    <A*/}
                {/*        href={`/${currentUser.username}`}*/}
                {/*        className="nav-link">*/}
                {/*        {currentUser.name}*/}
                {/*    </A>*/}
                {/*</li>*/}
            </ul>
        </>;
    }

    return <></>;
};

function Header(props: any) {
    const dispatch = useDispatch();
    const routeResult = useRoutes(routes);
    const appName = useSelector((state: any) => state.common.appName);
    const userType = useSelector((state: any) => state.common.userType);
    const currentUser = useSelector((state: any) => state.common.currentUser);
    const isLoggedIn = useSelector((state: any) => state.common.isLoggedIn);


    const onGetUsertype = () => {
        return dispatch({
            type: GET_USER, payload: Promise.all([
                api.Administrator.get(currentUser.id),
                api.Veterinarian.get(currentUser.id),
                api.Client.get(currentUser.id)
            ])
        });
    };

    useEffect(() => {
        if (currentUser && currentUser.id && !userType) {
            onGetUsertype()
        }
    }   );

    return <>
        <nav className="navbar navbar-light">
            <div className="container">
                <A href="/" className="navbar-brand">{appName}</A>
                <LoggedOutView isLoggedIn={isLoggedIn}/>
                <LoggedInView currentUser={currentUser} userType={userType} isLoggedIn={isLoggedIn}/>
            </div>
        </nav>
        {routeResult || <NotFound/>}
    </>
}

export default Header;
