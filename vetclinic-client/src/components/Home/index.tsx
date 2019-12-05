import Logo from './Logo';
import Main from './MainView';
import React, {useEffect} from 'react';
import {connect} from 'react-redux';
import {HOME_PAGE_LOADED, HOME_PAGE_UNLOADED} from '../../constants/actionTypes';
import agent from "../../agent";


const mapStateToProps = (state: any) => ({
    ...state.home,
    appName: state.common.appName,
    token: state.common.token
});

const mapDispatchToProps = (dispatch: any) => ({
    onLoad: (tab: string, pager: string, payload: string) =>
        dispatch({type: HOME_PAGE_LOADED, tab, pager, payload}),
    onUnload: () =>
        dispatch({type: HOME_PAGE_UNLOADED})
});

function Home(props: any) {
    useEffect(() => {
        const container = props.token ? 'session' : 'default';
        const userPromise = props.token ?
            agent.User.type :
            agent.Employee.all;

        props.onLoad(container, userPromise, agent.Employee.all());
        return (() => {
            props.onUnload();
        })
    });


    return <>
        <div className="home-page">

            <Logo token={props.token} appName={props.appName}/>

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

export default connect(mapStateToProps, mapDispatchToProps)(Home);

