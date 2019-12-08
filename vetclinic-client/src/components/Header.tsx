import React, {useState} from 'react';
import {useSelector} from "react-redux";
import {A, useRoutes} from "hookrouter";
import Home from "./Home/Home";
import Login from "./Login";
import {Singup} from "./Register";
import NotFound from "./Erros/NotFound";
import Administrator from "./Administrator/Administrator";
import {UserType} from "../api";
import Logout from './Logout';


const routes = {
    "/": () => <Home/>,
    "/login": () => <Login/>,
    "/register": () => <Singup/>,
    "/logout": () => <Logout/>,
    "/admin/:id": ({id}: any) => <Administrator id={id}/>,
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

    const [user, setUser] = useState()

    if (props.isLoggedIn) {

        const display = props.userType === UserType.CLIENT ? <>
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


        return <>
            <ul className="nav navbar-nav pull-xs-right">
                {display}
                <li className="nav-item">
                    <A href="/" className="nav-link">
                        Home
                    </A>
                </li>
                {/*<li className="nav-item">*/}
                {/*    <A*/}
                {/*        href={`/@${props.username}`}*/}
                {/*        className="nav-link">*/}
                {/*        <img src={props.image} className="user-pic" alt={props.username}/>*/}
                {/*        {props.username}*/}
                {/*    </A>*/}
                {/*</li>*/}
                <li className="nav-item">
                    <A href="/logout" className="nav-link">
                        Logout
                    </A>
                </li>
            </ul>
        </>;
    }

    return null;
};

function Header(props: any) {
    const routeResult = useRoutes(routes);
    const appName = useSelector((state: any) => state.common.appName);
    const userType = useSelector((state: any) => state.common.userType);
    const isLoggedIn = useSelector((state: any) => state.common.isLoggedIn);

    return <>
        <nav className="navbar navbar-light">
            <div className="container">
                <A href="/" className="navbar-brand">{appName}</A>
                <LoggedOutView isLoggedIn={isLoggedIn}/>
                <LoggedInView userType={userType} isLoggedIn={isLoggedIn}/>
            </div>
        </nav>
        {routeResult || <NotFound/>}
    </>
}

export default Header;
