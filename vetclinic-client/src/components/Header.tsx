import React from 'react';
import {useSelector} from "react-redux";
import {A, useRoutes} from "hookrouter";
import Home from "./Home/Home";
import Login from "./Login";
import {Singup} from "./Register";
import NotFound from "./Erros/NotFound";
import Administrator from "./Administrator/Administrator";


const routes = {
    "/": () => <Home/>,
    "/login": () => <Login/>,
    "/register": () => <Singup/>,
    "/admin/:id": ({id}: any) => <Administrator id={id}/>,
    // "/vet/:id": ({id}: any) => <Veterinarian id={id}/>,
    // "/cli/:id": ({id}: any) => <Client id={id}/>
};

const LoggedOutView = (props: any) => {
    if (!props.currentUser) {
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
    if (props.currentUser) {
        return <>
            <ul className="nav navbar-nav pull-xs-right">
                <li className="nav-item">
                    <A href="/" className="nav-link">
                        Home
                    </A>
                </li>
                <li className="nav-item">
                    <A
                        href={`/@${props.currentUser.username}`}
                        className="nav-link">
                        <img src={props.currentUser.image} className="user-pic" alt={props.currentUser.username}/>
                        {props.currentUser.username}
                    </A>
                </li>

            </ul>
        </>;
    }

    return null;
};

function Header(props: any) {
    const routeResult = useRoutes(routes)
    const appName = useSelector((state: any) => state.common.appName);
    const currentUser = useSelector((state: any) => state.common.currentUser);

    return <>
        <nav className="navbar navbar-light">
            <div className="container">
                <A href="/" className="navbar-brand">{appName}</A>
                <LoggedOutView currentUser={currentUser}/>
                <LoggedInView currentUser={currentUser}/>
            </div>
        </nav>
        {routeResult || <NotFound/>}
    </>
}

export default Header;
