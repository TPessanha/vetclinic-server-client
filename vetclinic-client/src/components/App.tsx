import React, {useEffect} from 'react';
import {APP_LOAD, REDIRECT} from '../constants/actionTypes';
import Login from "./Login";
import Register from "./Register";
import agent from "../agent";
import {useDispatch, useSelector} from "react-redux";
import Header from "./Header";
// @ts-ignore
import {useLocation} from 'react-router-dom';
import {useRoutes} from "hookrouter";
import Home from "./Home/Home";

const mapStateToProps = (state: any) => {
    return {}
};

const mapDispatchToProps = (dispatch: any) => ({});

const routes = {
    "/": () => <Home/>,
    "/login": () => <Login/>,
    "/register": () => <Register/>
};

const App = () => {
    const dispatch = useDispatch();
    const routeResult = useRoutes(routes)
    const appLoaded = useSelector((state: any) => state.common.appLoaded);
    const appName = useSelector((state: any) => state.common.appName);
    const currentUser = useSelector((state: any) => state.common.currentUser);
    const redirectTo = useLocation();


    const onLoad = (payload: any, token: string) => dispatch({type: APP_LOAD, payload, token, skipTracking: true})
    const onRedirect = () => dispatch({type: REDIRECT});

    useEffect(() => {
        const token = window.localStorage.getItem('jwt');
        if (token) {
            agent.setToken(token);
        }
    }, []);


    if (appLoaded) {
        return <>
            <div>
                <Header
                    appName={appName}
                    currentUser={currentUser}/>
            </div>
        </>
    }

    return <>
        <div>
            <Header
                appName={appName}
                currentUser={currentUser}/>
        </div>
    </>


}
//
//
// export default connect(mapStateToProps, mapDispatchToProps)(App);

export default App