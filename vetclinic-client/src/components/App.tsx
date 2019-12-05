import agent from '../agent';
import Header from './Header';
import React from 'react';
import {connect} from 'react-redux';
import {APP_LOAD, REDIRECT} from '../constants/actionTypes';
import {Route, Switch} from 'react-router-dom';

import Home from './Home';
import Login from './Login';
import Register from './Register';
import Accounts from './Accounts';
import {store} from '../store';
import {push} from 'react-router-redux';

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

class App extends React.Component<any, any> {

    constructor(props: any) {
        super(props);
    }

    componentWillReceiveProps(nextProps: any) {
        if (nextProps.redirectTo) {
            store.dispatch(push(nextProps.redirectTo));
            this.props.onRedirect();
        }
    }

    componentWillMount() {
        const token = window.localStorage.getItem('jwt');
        if (token) {
            agent.setToken(token);
        }

    this.props.onLoad(token ? token : null, token);
  }

    render() {
        if (this.props.appLoaded) {
            return (
                <div>
                    <Header
                        appName={this.props.appName}
                        currentUser={this.props.currentUser}/>
                    <Switch>
                        <Route exact path="/" component={Home}/>
                        <Route path="/login" component={Login}/>
                        <Route path="/register" component={Register}/>
                        <Route path="/account" component={Accounts}/>
                    </Switch>
                </div>
            );
        }
        return (
            <div>
                <Header
                    appName={this.props.appName}
                    currentUser={this.props.currentUser}/>
            </div>
        );
    }
}

// App.contextTypes = {
//   router: PropTypes.object.isRequired
// };

export default connect(mapStateToProps, mapDispatchToProps)(App);
