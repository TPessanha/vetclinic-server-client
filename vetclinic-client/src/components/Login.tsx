import {Link} from 'react-router-dom';
import React, {useEffect} from 'react';
import agent from '../agent';
import {connect} from 'react-redux';
import {LOGIN, LOGIN_PAGE_UNLOADED, UPDATE_FIELD_AUTH} from '../constants/actionTypes';
import useForm from "react-hook-form";

const mapStateToProps = (state: any) => ({...state.auth});

const mapDispatchToProps = (dispatch: any) => ({
    onChangeEmail: (value: any) =>
        dispatch({type: UPDATE_FIELD_AUTH, key: 'email', value}),
    onChangePassword: (value: any) =>
        dispatch({type: UPDATE_FIELD_AUTH, key: 'password', value}),
    onSubmit: (email: string, password: string) =>
        dispatch({type: LOGIN, payload: agent.Auth.login(email, password)}),
    onUnload: () =>
        dispatch({type: LOGIN_PAGE_UNLOADED})
});

function Login(props: any) {
    const {handleSubmit, register, errors} = useForm();

    const onSubmit = (values: any) => {
        console.log(values);
    };
    useEffect(() => {

        return (() => {
            props.onUnload();
        })
    });


    const password = props.password;
    const username = props.username;

    return <>
        <div className="auth-page">
            <div className="container page">
                <div className="row">

                    <div className="col-md-6 offset-md-3 col-xs-12">
                        <h1 className="text-xs-center">Login In</h1>
                        <p className="text-xs-center">
                            <Link to="/register">
                                Need an Account?
                            </Link>
                        </p>


                        <form onSubmit={handleSubmit(onSubmit)}>
                            <fieldset>

                                <fieldset className="form-group">
                                    <input
                                        className="form-control form-control-lg"
                                        name="username"
                                        type="text"
                                        ref={register({
                                            validate: value => value !== "admin" || "Nice try!"
                                        })}
                                        placeholder="User Name"
                                        value={props.username}
                                        onChange={handleSubmit(onSubmit)}/>
                                    {errors.username && errors.username.message}
                                </fieldset>
                                <fieldset className="form-group">
                                    <input
                                        className="form-control form-control-lg"
                                        type="password"
                                        placeholder="Password"
                                        value={props.password}
                                        onChange={handleSubmit(onSubmit)}/>
                                    {errors.password && errors.password.message}

                                </fieldset>
                                <button
                                    className="btn btn-lg btn-primary pull-xs-right"
                                    type="submit"
                                    disabled={props.inProgress}>
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

export default connect(mapStateToProps, mapDispatchToProps)(Login);
