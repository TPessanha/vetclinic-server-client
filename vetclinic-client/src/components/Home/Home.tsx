import React, {useEffect} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {
    EMPLOYEE_ADMIN_LOADED,
    EMPLOYEE_VET_LOADED,
    HOME_PAGE_LOADED,
    HOME_PAGE_UNLOADED
} from '../../constants/actionTypes';
import api from "../../api";
import {Employee} from "../Employee/Employee";


function Home() {
    const dispatch = useDispatch();

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


    return <>
        <div className="container page">

            <div className="logo">
                <div className="container center">
                    <p className="text-xs-center">Welcome to Veterinarian Manegment Clinic.</p>
                </div>
            </div>

            <div className="container page">
                <div className="row">
                    {/*<MainView/>*/}
                    <Employee/>
                </div>
            </div>

        </div>
    </>;
}

export default Home