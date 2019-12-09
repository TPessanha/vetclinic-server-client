import React, {useEffect, useState} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {
    EMPLOYEE_ADMIN_LOADED,
    EMPLOYEE_VET_LOADED,
    HOME_PAGE_LOADED,
    HOME_PAGE_UNLOADED
} from '../../constants/actionTypes';
import api from "../../api";
import {User} from "../User/User";
import {useRoutes} from "hookrouter";
import {Employee} from "../Employee/Employee";


const routes = {
    "/users": () => <User/>,
};

function Home() {
    const dispatch = useDispatch();
    const routeResult = useRoutes(routes);
    const redirectTo = useSelector((state: any) => state.common.redirectTo);
    const [currentView, setCurrentView] = useState("employee")


    const onLoad = () =>
        dispatch({type: HOME_PAGE_LOADED});
    const onUnload = () =>
        dispatch({type: HOME_PAGE_UNLOADED});
    const onLoadVets = () => {
        const payload = api.Veterinarian.all();
        dispatch({type: EMPLOYEE_VET_LOADED, payload});
    };
    const onLoadAdmins = () => {
        const payload = api.Administrator.all();
        dispatch({type: EMPLOYEE_ADMIN_LOADED, payload});
    };

    useEffect(() => {
        onLoad();
        onLoadVets();
        onLoadAdmins();

    });

    useEffect(() => {

        return (() => {
            onUnload();
        })
    });

    useEffect(() => {

            if (redirectTo) {
                setCurrentView(redirectTo)
            }


            return (() => {
                onUnload();
            })
        }, [redirectTo]
    );


    return <>
        <div className="container page">

            <div className="logo">
                <div className="container center">
                    <p className="text-xs-center">Welcome to Veterinarian Manegment Clinic.</p>
                </div>
            </div>

            <div className="container page">
                <div className="row">
                    {currentView && currentView === "users" ? <User/> : <Employee/>}
                </div>
            </div>

        </div>
    </>;
}

export default Home