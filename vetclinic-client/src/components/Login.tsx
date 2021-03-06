import React, {useEffect} from 'react';
import api from '../api';
import {useDispatch, useSelector} from 'react-redux';
import {LOGIN, LOGIN_PAGE_UNLOADED, UPDATE_FIELD_AUTH} from '../constants/actionTypes';
import useForm from "react-hook-form";
import Home from "./Home/Home";
import {Singup} from "./Register";
import {A, useRoutes} from 'hookrouter';
import {Redirect} from 'react-router-dom';

const routes = {
    "/": () => <Home/>,
    "/register": () => <Singup/>,
};

function Login(props: any) {
    const dispatch = useDispatch();
    const {handleSubmit, register, errors} = useForm();
    const routeResult = useRoutes(routes);
    const isLoggedIn = useSelector((state: any) => state.common.isLoggedIn);

    const onChangeUserName = (value: any) => {
        dispatch({type: UPDATE_FIELD_AUTH, key: 'username', value: value.username})
    };

    const onChangePassword = (value: any) => {
        dispatch({type: UPDATE_FIELD_AUTH, key: 'password', value: value.password})
    };
    const onSubmit = (username: string, password: string) =>
        dispatch({type: LOGIN, payload: api.Auth.login(username, password)});

    const onUnload = () =>
        dispatch({type: LOGIN_PAGE_UNLOADED});

    const onHandleSubmit = (event: any) => {
        onSubmit(event.username, event.password)
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
                        <h1 className="text-xs-center">Login In</h1>
                        <p className="text-xs-center">
                            <A href="/register">
                                Need an Account?
                            </A>
                        </p>
                        {routeResult}
                        <form onSubmit={handleSubmit(onHandleSubmit)}>
                            <fieldset>

                                <fieldset className="form-group">
                                    <input
                                        className="form-control"
                                        name="username"
                                        type="text"
                                        ref={register({
                                            validate: value => value !== " "
                                        })}
                                        placeholder="User Name"
                                        value={props.username}
                                        onChange={handleSubmit(onChangeUserName)}/>
                                    {errors.username && errors.username.message}
                                </fieldset>
                                <fieldset className="form-group">
                                    <input
                                        className="form-control "
                                        type="password"
                                        name="password"
                                        ref={register({
                                            validate: value => value !== "12345678" || value.size < 8
                                        })}
                                        placeholder="Password"
                                        value={props.password}
                                        onChange={handleSubmit(onChangePassword)}/>
                                    {errors.password && errors.password.message}

                                </fieldset>
                                <button
                                    className="btn btn-lg btn-primary pull-xs-right"
                                    type="submit"
                                    disabled={props.isLoading}>
                                    Login
                                </button>

                            </fieldset>
                        </form>
                    </div>

                </div>
            </div>
        </div>
    </>;
}

export default Login;
