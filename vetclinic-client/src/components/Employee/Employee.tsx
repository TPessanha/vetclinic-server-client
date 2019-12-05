import React from 'react';
import agent from '../../agent';
import {connect} from 'react-redux';
import {EMPLOYEE_PAGE_LOADED, EMPLOYEE_PAGE_UNLOADED} from '../../constants/actionTypes';
import EmployeePreview from "./EmployeePreview";

const mapStateToProps = (state: any) => ({
    ...state.adminstrator,
    currentUser: state.common.currentUser
});

const mapDispatchToProps = (dispatch: any) => ({
    onLoad: (payload: any) =>
        dispatch({type: EMPLOYEE_PAGE_LOADED, payload}),
    onUnload: () =>
        dispatch({type: EMPLOYEE_PAGE_UNLOADED})
});


class Employee extends React.Component<any, any> {
    componentWillMount() {
        this.props.onLoad(Promise.all([
            agent.Employee.all()
        ]));
    }

    componentWillUnmount() {
        this.props.onUnload();
    }

    render() {
        if (!this.props.employee) {
            return null;
        }

        const canModify = this.props.currentUser
            && this.props.employee.username === this.props.currentUser.username;

        return (
            <div className="employee-page">

                <div className="logo">
                    <div className="container">

                        <h1>{this.props.employee.userName}</h1>
                        <EmployeePreview
                            article={this.props.employee}
                            canModify={canModify}/>

                    </div>
                </div>

                <div className="container page">

                    <div className="row employee-content">
                        <div className="col-xs-12">
                        </div>
                    </div>

                    <hr/>
                </div>
            </div>
        );
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Employee);
