import React, {useEffect} from 'react';
import {APP_LOAD, REDIRECT} from '../constants/actionTypes';
import api from "../api";
import {useDispatch, useSelector} from "react-redux";
import Header from "./Header";
import {store} from "../store";
import {push} from "react-router-redux";
import Home from "./Home/Home";
import Login from "./Login";
import {Singup} from "./Register";
import Logout from "./Logout";
import {User} from "./User/User";
import Administrator from "./Administrator/Administrator";
import {ClientDashboard} from "./Client/ClientDashboard";


const App = () => {
    const dispatch = useDispatch();
    const appLoaded = useSelector((state: any) => state.common.appLoaded);
    const appName = useSelector((state: any) => state.common.appName);
    const currentUser = useSelector((state: any) => state.common.currentUser);
    const userType = useSelector((state: any) => state.common.userType);

    const onLoad = (payload: any, token: any, type: string | null) => dispatch({
        type: APP_LOAD,
        payload,
        token,
        userType: type,
        skipTracking: true
    });
    const onRedirect = () => dispatch({type: REDIRECT});


    useEffect(() => {
        const tokens = window.localStorage.getItem('token');
        const type = window.localStorage.getItem('userType');
        const username = window.localStorage.getItem('username');
        if (tokens && !currentUser) {
            api.setToken(tokens);
            onLoad(tokens && username ? api.User.getByUserName(username) : null, tokens, type)

        }

    });



    return <>
        <div>
            <Header
                appName={appName}
                currentUser={currentUser}/>
        </div>
    </>


};

export default App