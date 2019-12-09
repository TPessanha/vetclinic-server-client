import {Redirect} from 'react-router-dom';

import React, {useEffect} from 'react';
import api from '../api';
import {useDispatch, useSelector} from 'react-redux';
import {REGISTER_PAGE_UNLOADED, SINGUP, UPDATE_FIELD_AUTH} from "../constants/actionTypes";
import useForm from "react-hook-form";
import {A, useRoutes} from 'hookrouter';
import Home from "./Home/Home";
import Login from './Login';

const routes = {
    "/login": () => <Login/>,
};


export const Singup = (props: any) => {
    const dispatch = useDispatch();
    const routeResult = useRoutes(routes);
    const username = useSelector((state: any) => state.auth.username);
    const email = useSelector((state: any) => state.auth.email);
    const password = useSelector((state: any) => state.auth.password);
    const isLoading = useSelector((state: any) => state.auth.isLoading);
    const {handleSubmit, register, errors} = useForm();
    const isLoggedIn = useSelector((state: any) => state.common.isLoggedIn);


    const onChangeEmail = (value: any) => {
        dispatch({type: UPDATE_FIELD_AUTH, key: 'email', value: value.email})
    };
    const onChangePassword = (value: any) => {
        dispatch({type: UPDATE_FIELD_AUTH, key: 'password', value: value.password})
    };
    const onChangeUsername = (value: any) => {
        dispatch({type: UPDATE_FIELD_AUTH, key: 'username', value: value.username})
    };
    const onSubmit = (username: string, email: string, password: string) => {
        dispatch({type: SINGUP, payload: api.Auth.signup(username, email, password)})
    };

    const onUnload = () => dispatch({type: REGISTER_PAGE_UNLOADED});


    const onHandleSubmit = (event: any) => {
        onSubmit(event.username, event.email, event.password)
    };

    useEffect(() => {


        return (() => {
            onUnload();
        })
    }, []);

    if (isLoggedIn) {
        return <>

            <Redirect to='/'/>
            <Home/>
        </>
            ;
    }

    return <>
        <div className="auth-page">
            <div className="container page">
                <div className="row">

                    <div className="col-md-6 offset-md-3 col-xs-12">
                        <h1 className="text-xs-center">Sign Up</h1>
                        <p className="text-xs-center">
                            <A href="/login">
                                Already Have an Account?
                            </A>
                        </p>
                        {routeResult}

                        <form onSubmit={handleSubmit(onHandleSubmit)}>
                            <fieldset>

                                <fieldset className="form-group">
                                    <input
                                        className="form-control "
                                        name="email"


                                        type="text"
                                        placeholder="Email"
                                        value={email}
                                        onChange={handleSubmit(onChangeEmail)}/>
                                    {errors.email && errors.email.message}
                                </fieldset>
                                <fieldset className="form-group">
                                    <input
                                        className="form-control "
                                        name="username"
                                        type="text"

                                        placeholder="User Name"
                                        value={username}
                                        onChange={handleSubmit(onChangeUsername)}/>
                                </fieldset>


                                <fieldset className="form-group">
                                    <input
                                        className="form-control "
                                        type="password"
                                        name="password"

                                        placeholder="Password"
                                        value={password}
                                        onChange={handleSubmit(onChangePassword)}/>

                                </fieldset>
                                <button
                                    className="btn btn-lg btn-primary pull-xs-right"
                                    type="submit"
                                    disabled={isLoading}>
                                    Sign up
                                </button>

                            </fieldset>
                        </form>
                    </div>

                </div>
            </div>
        </div>
    </>;
}

