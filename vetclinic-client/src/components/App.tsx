import Home from './Home';
import React, {useEffect} from 'react';
import {APP_LOAD, REDIRECT} from '../constants/actionTypes';
import Login from "./Login";
import Register from "./Register";
import Accounts from "./Accounts";
import agent from "../agent";
import {store} from "../store";
import {push} from "react-router-redux";
import {connect} from "react-redux";
import Header from "./Header";
// @ts-ignore
import {Route, Switch} from 'react-router-dom';

const mapStateToProps = (state: any) => {
    return {
        appLoaded: state.common.appLoaded,
        appName: state.common.appName,
        currentUser: state.common.currentUser,
        redirectTo: state.common.redirectTo
    }
};

const mapDispatchToProps = (dispatch: any) => ({
    onLoad: (payload: any, token: string) =>
        dispatch({type: APP_LOAD, payload, token, skipTracking: true}),
    onRedirect: () =>
        dispatch({type: REDIRECT})
});


const App = (props: any) => {

    useEffect(() => {
        const token = window.localStorage.getItem('jwt');
        if (token) {
            agent.setToken(token);
        }
    }, []);


    useEffect(() => {
        if (props.location.pathname) {
            store.dispatch(push(props.location.pathname));
            props.onRedirect();
        }
    }, [props.location.pathname]);


    if (props.appLoaded) {
        return <>
            <div>
                <Header
                    appName={props.appName}
                    currentUser={props.currentUser}/>
                <Switch>
                    <Route exact path="/" component={Home}/>
                    <Route path="/login" component={Login}/>
                    <Route path="/register" component={Register}/>
                    <Route path="/account" component={Accounts}/>
                </Switch>
            </div>
        </>
    }

    return <>
        <div>
            <Header
                appName={props.appName}
                currentUser={props.currentUser}/>
        </div>
    </>


}


export default connect(mapStateToProps, mapDispatchToProps)(App);
