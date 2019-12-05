import React from 'react';
import agent from '../../agent';
import {connect} from 'react-redux';
import {ADMINISTRATOR_PAGE_LOADED, ADMINISTRATOR_PAGE_UNLOADED} from '../../constants/actionTypes';
import AdministratorContainer from "./AdministratorContainer";
import AdministratorDetails from "./AdminstratorDetails";

const mapStateToProps = (state: any) => ({
    ...state.adminstrator,
    currentUser: state.common.currentUser
});

const mapDispatchToProps = (dispatch: any) => ({
    onLoad: (payload: any) =>
        dispatch({type: ADMINISTRATOR_PAGE_LOADED, payload}),
    onUnload: () =>
        dispatch({type: ADMINISTRATOR_PAGE_UNLOADED})
});



class Administrator extends React.Component<any, any>{
    componentWillMount() {
        this.props.onLoad(Promise.all([
            agent.Administrator.get(this.props.administrator.id)
        ]));
    }

    componentWillUnmount() {
        this.props.onUnload();
    }

    render() {
        if (!this.props.administrator) {
            return null;
        }

        const canModify = this.props.currentUser
            && this.props.administrator.username === this.props.currentUser.username;

        return (
            <div className="administrator-page">

                <div className="banner">
                    <div className="container">

                        <h1>{this.props.administrator.userName}</h1>
                        <AdministratorDetails
                            article={this.props.administrator}
                            canModify={canModify}/>

                    </div>
                </div>

                <div className="container page">

                    <div className="row administrator-content">
                        <div className="col-xs-12">
                        </div>
                    </div>

                    <hr/>

                    <div className="administrator-actions">
                    </div>

                    <div className="row">
                        <AdministratorContainer
                            comments={this.props.comments || []}
                            errors={this.props.commentErrors}
                            slug={this.props.match.params.id}
                            currentUser={this.props.currentUser}/>
                    </div>
                </div>
            </div>
        );
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Administrator);
