import {Link} from 'react-router-dom';

import React from 'react';
import agent from '../agent';
import {connect} from 'react-redux';
import ListErrors from "./ListErrors";
import {REGISTER_PAGE_UNLOADED, SINGUP, UPDATE_FIELD_AUTH} from "../constants/actionTypes";

const mapStateToProps = (state: any) => ({...state.auth});

const mapDispatchToProps = (dispatch: any) => ({
    onChangeEmail: (value: any) =>
        dispatch({type: UPDATE_FIELD_AUTH, key: 'email', value}),
    onChangePassword: (value: any) =>
        dispatch({type: UPDATE_FIELD_AUTH, key: 'password', value}),
    onChangeUsername: (value: any) =>
        dispatch({type: UPDATE_FIELD_AUTH, key: 'username', value}),
    onSubmit: (username: string, email: string, password: string) => {
        const payload = agent.Auth.signup(username, email, password);
        dispatch({type: SINGUP, payload})
    },
    onUnload: () =>
        dispatch({type: REGISTER_PAGE_UNLOADED})
});

class Register extends React.Component<any, any>{
    private submitForm: (username: string, email: string, password: string) => (event: any) => void;
    private changeEmail: (event: any) => any;
    private changePassword: (event: any) => any;
    private changeUsername: (event: any) => any;
    constructor(props: any) {
        super(props);
        this.changeEmail = (event:any) => this.props.onChangeEmail(event.target.value);
        this.changePassword = (event:any) => this.props.onChangePassword(event.target.value);
        this.changeUsername = (event:any) => this.props.onChangeUsername(event.target.value);
        this.submitForm = (username: string, email: string, password: string) => (event:any) => {
            event.preventDefault();
            this.props.onSubmit(username, email, password);
        }
    }

    componentWillUnmount() {
        this.props.onUnload();
    }

    render() {
        const email = this.props.email;
        const password = this.props.password;
        const username = this.props.username;

        return (
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

                            <ListErrors errors={this.props.errors}/>

                            <form onSubmit={this.submitForm(username, email, password)}>
                                <fieldset>

                                    <fieldset className="form-group">
                                        <input
                                            className="form-control form-control-lg"
                                            type="text"
                                            placeholder="Username"
                                            value={this.props.username}
                                            onChange={this.changeUsername}/>
                                    </fieldset>

                                    <fieldset className="form-group">
                                        <input
                                            className="form-control form-control-lg"
                                            type="email"
                                            placeholder="Email"
                                            value={this.props.email}
                                            onChange={this.changeEmail}/>
                                    </fieldset>

                                    <fieldset className="form-group">
                                        <input
                                            className="form-control form-control-lg"
                                            type="password"
                                            placeholder="Password"
                                            value={this.props.password}
                                            onChange={this.changePassword}/>
                                    </fieldset>

                                    <button
                                        className="btn btn-lg btn-primary pull-xs-right"
                                        type="submit"
                                        disabled={this.props.inProgress}>
                                        Sign up
                                    </button>

                                </fieldset>
                            </form>
                        </div>

                    </div>
                </div>
            </div>
        );
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Register);
