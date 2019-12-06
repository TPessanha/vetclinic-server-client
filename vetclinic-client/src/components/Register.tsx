import {Link} from 'react-router-dom';

import React, {useEffect} from 'react';
import agent from '../agent';
import {connect, useDispatch, useSelector} from 'react-redux';
import {REGISTER_PAGE_UNLOADED, SINGUP, UPDATE_FIELD_AUTH} from "../constants/actionTypes";
import useForm from "react-hook-form";

const mapStateToProps = (state: any) => ({...state.auth});

const mapDispatchToProps = (dispatch: any) => ({});

function Singup(props: any) {
    const dispatch = useDispatch();
    const auth = useSelector((state: any) => state.auth);
    const {handleSubmit, register, errors} = useForm();


    const onChangeEmail = (value: any) => {
        dispatch({type: UPDATE_FIELD_AUTH, key: 'email', value})
    };
    const onChangePassword = (value: any) => {
        dispatch({type: UPDATE_FIELD_AUTH, key: 'password', value})
    };
    const onChangeUsername = (value: any) => {
        dispatch({type: UPDATE_FIELD_AUTH, key: 'username', value})
    };
    const onSubmit = (username: string, email: string, password: string) => {
        const payload = agent.Auth.signup(username, email, password);
        dispatch({type: SINGUP, payload})
    };

    const onUnload = () => dispatch({type: REGISTER_PAGE_UNLOADED});


    const onHandleSubmit = (event: any) => {
        onSubmit(event.username, event.email, event.password)
    };

    useEffect(() => {


        return (() => {
            onUnload();
        })
    },[]);


    const email = props.email;
    const password = props.password;
    const username = props.username;

    return <>
        <div className="auth-page">
            <div className="container page">
                <div className="row">

                    <div className="col-md-6 offset-md-3 col-xs-12">
                        <h1 className="text-xs-center">Sign Up</h1>
                        <p className="text-xs-center">
                            <Link to="/login">
                                Already Have an Account?
                            </Link>
                        </p>


                        <form onSubmit={handleSubmit(onHandleSubmit)}>
                            <fieldset>

                                <fieldset className="form-group">
                                    <input
                                        className="form-control "
                                        name="username"
                                        type="text"
                                        ref={register({
                                            validate: value => value !== "admin" || "Nice try!"
                                        })}
                                        placeholder="User Name"
                                        value={props.username}
                                        onChange={handleSubmit(onChangeUsername)}/>
                                    {errors.username && errors.username.message}
                                </fieldset>

                                <fieldset className="form-group">
                                    <input
                                        className="form-control "
                                        name="email"
                                        ref={register({
                                            required: 'Required',
                                            pattern: {
                                                value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i,
                                                message: "invalid email address"
                                            }
                                        })}

                                        type="email"
                                        placeholder="Email"
                                        value={props.email}
                                        onChange={handleSubmit(onChangeEmail)}/>
                                    {errors.email && errors.email.message}
                                </fieldset>

                                <fieldset className="form-group">
                                    <input
                                        className="form-control "
                                        type="password"
                                        placeholder="Password"
                                        value={props.password}
                                        onChange={handleSubmit(onChangePassword)}/>
                                    {errors.password && errors.password.message}

                                </fieldset>
                                <button
                                    className="btn btn-lg btn-primary pull-xs-right"
                                    type="submit"
                                    disabled={props.inProgress}>
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


export default connect(mapStateToProps, mapDispatchToProps)(Singup);
