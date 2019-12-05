import AdministratorList from './Adminstrator/AdministratorList';
import React from 'react';
import {Link} from 'react-router-dom';
import agent from '../agent';
import {connect} from 'react-redux';
import {PROFILE_PAGE_LOADED, PROFILE_PAGE_UNLOADED} from '../constants/actionTypes';

const EditProfileSettings = (props: any) => {
    if (props.isUser) {
        return (
            <Link
                to="/settings"
                className="btn btn-sm btn-outline-secondary action-btn">
                <i className="ion-gear-a"></i> Edit Profile Settings
            </Link>
        );
    }
    return null;
};


const mapStateToProps = (state: any) => ({
    ...state.articleList,
    currentUser: state.common.currentUser,
    profile: state.profile
});

const mapDispatchToProps = (dispatch: any) => ({
    onLoad: (payload: any) => dispatch({type: PROFILE_PAGE_LOADED, payload}),
    onUnload: () => dispatch({type: PROFILE_PAGE_UNLOADED})
});

class Profile extends React.Component<any, any>{
    constructor(props: any) {
        super(props);
    }

    componentWillMount() {
        this.props.onLoad(Promise.all([
            // agent.Profile.get(this.props.match.params.username),
        ]));
    }

    componentWillUnmount() {
        this.props.onUnload();
    }

    renderTabs() {
        return (
            <ul className="nav nav-pills outline-active">
                <li className="nav-item">
                    <Link
                        className="nav-link active"
                        to={`/@${this.props.profile.username}`}>

                    </Link>
                </li>

            </ul>
        );
    }

    render() {
        const profile = this.props.profile;
        if (!profile) {
            return null;
        }

        const isUser = this.props.currentUser &&
            this.props.profile.username === this.props.currentUser.username;

        return (
            <div className="profile-page">

                <div className="user-info">
                    <div className="container">
                        <div className="row">
                            <div className="col-xs-12 col-md-10 offset-md-1">

                                <img src={profile.image} className="user-img" alt={profile.username}/>
                                <h4>{profile.name}</h4>
                                <p>{profile.email}</p>

                                <EditProfileSettings isUser={isUser}/>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        );
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Profile);
export {Profile, mapStateToProps};
