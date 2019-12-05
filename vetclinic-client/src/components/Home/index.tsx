import Logo from './Logo';
import Main from './MainView';
import React from 'react';
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

class Home extends React.Component<any, any> {

    constructor(props: any) {
        super(props)
    }

    componentWillMount() {
        const container = this.props.token ? 'session' : 'default';
        const userPromise = this.props.token ?
            agent.User.type :
            agent.Employee.all;

        this.props.onLoad(container, userPromise, agent.Employee.all());
    }

    componentWillUnmount() {
        this.props.onUnload();
    }

    render() {
        return (
            <div className="home-page">

                <Logo token={this.props.token} appName={this.props.appName}></Logo>

                <div className="container page">
                    <div className="row">
                        <Main/>

                        <div className="col-md-3">

                        </div>
                    </div>
                </div>

            </div>
        );
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Home);

