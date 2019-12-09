import React, {useEffect} from 'react';
import {useDispatch} from 'react-redux';
import {LOGOUT_PAGE_LOADED, LOGOUT_PAGE_UNLOADED} from '../constants/actionTypes';
import Home from "./Home/Home";
import {useRoutes} from 'hookrouter';
import api from "../api";

const routes = {
    "/": () => <Home/>,
};

function Logout(props: any) {
    const dispatch = useDispatch();
    const routeResult = useRoutes(routes);


    const onUnload = () =>
        dispatch({type: LOGOUT_PAGE_UNLOADED});

    const onLoad = () =>
        dispatch({type: LOGOUT_PAGE_LOADED});


    useEffect(() => {

        const tokens = window.localStorage.getItem('token');
        if (tokens) {
            window.localStorage.clear()

            api.setToken(null);
        }
        onLoad()
        onUnload();
        // return (() => {
        //     onUnload();
        // })
    }, []);


    return <>

        {routeResult}

        </>;
}

export default Logout;
