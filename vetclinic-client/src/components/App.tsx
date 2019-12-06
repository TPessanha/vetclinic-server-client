import React, {useEffect} from 'react';
import {APP_LOAD, REDIRECT} from '../constants/actionTypes';
import agent from "../agent";
import {useDispatch, useSelector} from "react-redux";
import Header from "./Header";


const App = () => {
    const dispatch = useDispatch();
    const appLoaded = useSelector((state: any) => state.common.appLoaded);
    const appName = useSelector((state: any) => state.common.appName);
    const currentUser = useSelector((state: any) => state.common.currentUser);


    const onLoad = (payload: any, token: string) => dispatch({type: APP_LOAD, payload, token, skipTracking: true});
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


};

export default App