import React, {useEffect} from 'react';
import {APP_LOAD, GET_USER, REDIRECT} from '../constants/actionTypes';
import api from "../api";
import {useDispatch, useSelector} from "react-redux";
import Header from "./Header";
import {store} from "../store";
import {push} from "react-router-redux";
import {ClientDashboard} from "./Client/ClientDashboard";


const App = () => {
    const dispatch = useDispatch();
    const appLoaded = useSelector((state: any) => state.common.appLoaded);
    const appName = useSelector((state: any) => state.common.appName);
    const currentUser = useSelector((state: any) => state.common.currentUser);
    const redirectTo = useSelector((state: any) => state.common.redirectTo);
    const token = useSelector((state: any) => state.common.token);

    const onLoad = (payload: any, token: any) => dispatch({type: APP_LOAD, payload, token, skipTracking: true});
    const onRedirect = () => dispatch({type: REDIRECT});
    const onGetUser = () => {
        return dispatch({type: GET_USER, payload: api.Auth.current()});
    };

    useEffect(() => {
        const tokens = window.localStorage.getItem('token');
        if (tokens) {
            api.setToken(tokens);
            onGetUser();
        }

        onLoad(tokens ? api.Auth.current() : null, tokens)
    });

    useEffect(() => {
        if (token && !currentUser) {
            onGetUser()
        }

        if (redirectTo) {
            // this.context.router.replace(nextProps.redirectTo);
            store.dispatch(push(redirectTo));
            onRedirect();
        }
    }, []);


    if (appLoaded) {
        return <>
            <div>
                <Header
                    appName={appName}
                    currentUser={currentUser}/>
            </div>
            <div> <ClientDashboard></ClientDashboard></div>
        </>
    }

    return <>
        <div>
            <Header
                appName={appName}
                currentUser={currentUser}/>
        </div>
    </>


};

export default App