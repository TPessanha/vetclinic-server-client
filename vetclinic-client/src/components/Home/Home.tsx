import Main from './MainView';
import React, {useEffect} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {HOME_PAGE_LOADED, HOME_PAGE_UNLOADED} from '../../constants/actionTypes';
import agent from "../../agent";


function Home(props: any) {
    const dispatch = useDispatch();

    const token = useSelector((state: any) => state.common.token);
    const appName = useSelector((state: any) => state.common.appName);
    const currentUser = useSelector((state: any) => state.common.currentUser);


    const onLoad = (tab: any, pager: any, payload: any) =>
        dispatch({type: HOME_PAGE_LOADED, tab, pager, payload});
    const onUnload = () =>
        dispatch({type: HOME_PAGE_UNLOADED});

    useEffect(() => {
        const container = props.token != "" ? 'session' : 'default';
        const userPromise = props.token === "" ?
            agent.Employee.all : agent.User.type;

        onLoad(container, userPromise, agent.Employee.all());
        return (() => {
            onUnload();
        })
    }, []);


    return <>
        <div className="home-page">

            <div className="logo">
                <div className="container center">
                    <p className="text-xs-center">Welcome to Veterinarian Manegment Clinic.</p>
                </div>
            </div>

            <div className="container page">
                <div className="row">
                    <Main/>

                    <div className="col-md-3">

                    </div>
                </div>
            </div>

        </div>
    </>;
}

export default Home