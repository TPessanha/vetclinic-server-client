import {Link} from 'react-router-dom';
import ListErrors from './ListErrors';
import React from 'react';
import agent from '../agent';
import {connect} from 'react-redux';
import {LOGIN, LOGIN_PAGE_UNLOADED, UPDATE_FIELD_AUTH} from '../constants/actionTypes';

const mapStateToProps = (state: any) => ({...state.auth});

const mapDispatchToProps = (dispatch: any) => ({
    onChangeEmail: (value:any) =>
        dispatch({type: UPDATE_FIELD_AUTH, key: 'email', value}),
    onChangePassword: (value:any) =>
        dispatch({type: UPDATE_FIELD_AUTH, key: 'password', value}),
    onSubmit: (email:string, password:string) =>
        dispatch({type: LOGIN, payload: agent.Auth.login(email, password)}),
    onUnload: () =>
        dispatch({type: LOGIN_PAGE_UNLOADED})
});

class Login extends React.Component<any, any> {
    private changeEmail: (event: any) => any;
    private changePassword: (event: any) => any;
    private submitForm: (username: string, password: string) => (event: any) => void;

    constructor(props: any) {
        super(props);

        this.changeEmail = (event: any) => this.props.onChangeEmail(event.target.value);
        this.changePassword = (event: any) => this.props.onChangePassword(event.target.value);
        this.submitForm = (username: string, password: string) => (event: any) => {
            event.preventDefault();
            this.props.onSubmit(username, password);
        };
    }

    componentWillUnmount() {
        this.props.onUnload();
    }

    render() {
        const username = this.props.email;
        const password = this.props.password;
        return (
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

                            <ListErrors errors={this.props.errors}/>

                            <form onSubmit={this.submitForm(username, password)}>
                                <fieldset>

                                    <fieldset className="form-group">
                                        <input
                                            className="form-control form-control-lg"
                                            type="text"
                                            placeholder="User Name"
                                            value={username}
                                            onChange={this.changeEmail}/>
                                    </fieldset>

                                    <fieldset className="form-group">
                                        <input
                                            className="form-control form-control-lg"
                                            type="password"
                                            placeholder="Password"
                                            value={password}
                                            onChange={this.changePassword}/>
                                    </fieldset>

                                    <button
                                        className="btn btn-lg btn-primary pull-xs-right"
                                        type="submit"
                                        disabled={this.props.inProgress}>
                                        Log in
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

export default connect(mapStateToProps, mapDispatchToProps)(Login);
