import React, {useEffect} from 'react';
import {APP_LOAD, GET_USER, REDIRECT} from '../constants/actionTypes';
import api from "../api";
import {useDispatch, useSelector} from "react-redux";
import Header from "./Header";
import {store} from "../store";
import {push} from "react-router-redux";


const App = () => {
    const dispatch = useDispatch();
    const appLoaded = useSelector((state: any) => state.common.appLoaded);
    const appName = useSelector((state: any) => state.common.appName);
    const currentUser = useSelector((state: any) => state.common.currentUser);
    const userType = useSelector((state: any) => state.common.userType);
    const redirectTo = useSelector((state: any) => state.common.redirectTo);

    const onLoad = (payload: any, token: any) => dispatch({type: APP_LOAD, payload, token, skipTracking: true});
    const onRedirect = () => dispatch({type: REDIRECT});


    useEffect(() => {
        const tokens = window.localStorage.getItem('token');
        const username = window.localStorage.getItem('username');
        if (tokens && !currentUser) {
            api.setToken(tokens);
            onLoad(tokens && username ? api.User.getByUserName(username) : null, tokens)

        }

    });

    useEffect(() => {
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