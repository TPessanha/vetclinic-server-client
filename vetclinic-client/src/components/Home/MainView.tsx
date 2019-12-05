import React from 'react';
import agent from '../../agent';
import {connect} from 'react-redux';
import {CHANGE_TAB} from '../../constants/actionTypes';


const EmployeGetTab = (props: any) => {
    const clickHandler = (event: any) => {
        event.preventDefault();
        props.onEmployeeClick('all', agent.Employee.all, agent.Employee.all());
    };
    return (
        <li className="nav-item">
            <a
                href=""
                className={props.employee === 'all' ? 'nav-link active' : 'nav-link'}
                onClick={clickHandler}>
                All Employee
            </a>
        </li>
    );
};


const mapStateToProps = (state: any) => ({
    ...state.articleList,
    tags: state.home.tags,
    token: state.common.token
});

const mapDispatchToProps = (dispatch: any) => ({
    onEmployeeClick: (payload: any) =>
        dispatch({type: CHANGE_TAB, payload})
});

const MainView = (props: any) => {
    return (
        <div className="col-md-9">
            <div className="feed-toggle">
                <ul className="nav nav-pills outline-active">
                    <EmployeGetTab tab={props.tab} onTabClick={props.EmployeGetTab}/>
                </ul>
            </div>

        </div>
    );
};

export default connect(mapStateToProps, mapDispatchToProps)(MainView);
