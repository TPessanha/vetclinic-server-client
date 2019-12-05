import React from 'react';
import {Link} from 'react-router-dom';

const LoggedOutView = (props: any) => {
    if (!props.currentUser) {
        return <>
            <ul className="nav navbar-nav pull-xs-right">

                <li className="nav-item">
                    <Link to="/" className="nav-link">
                        Home
                    </Link>
                </li>

                <li className="nav-item">
                    <Link to="/login" className="nav-link">
                        Login
                    </Link>
                </li>

                <li className="nav-item">
                    <Link to="/register" className="nav-link">
                        Sign up
                    </Link>
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
                    <Link to="/" className="nav-link">
                        Home
                    </Link>
                </li>
                <li className="nav-item">
                    <Link to="/editor" className="nav-link">
                        <i className="ion-compose"></i>&nbsp;Action
                    </Link>
                </li>
                <li className="nav-item">
                    <Link to="/settings" className="nav-link">
                        <i className="ion-gear-a"></i>&nbsp;Account
                    </Link>
                </li>
                <li className="nav-item">
                    <Link
                        to={`/@${props.currentUser.username}`}
                        className="nav-link">
                        <img src={props.currentUser.image} className="user-pic" alt={props.currentUser.username}/>
                        {props.currentUser.username}
                    </Link>
                </li>

            </ul>
        </>;
    }

    return null;
};

function Header(props: any) {
    return <>
        <nav className="navbar navbar-light">
            <div className="container">
                <Link to="/" className="navbar-brand">{props.appName}</Link>
                <LoggedOutView currentUser={props.currentUser}/>
                <LoggedInView currentUser={props.currentUser}/>
            </div>
        </nav>
    </>
}

export default Header;
