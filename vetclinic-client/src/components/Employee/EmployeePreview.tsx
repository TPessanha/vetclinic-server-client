import React from 'react';
import {Link} from 'react-router-dom';
import agent from '../../agent';
import {connect} from 'react-redux';
import {GET_ADMINISTRATOR, GET_EMPLOYEE} from '../../constants/actionTypes';

const mapDispatchToProps = (dispatch: any) => ({
    onClick: (employee: any) => dispatch({
        type: GET_EMPLOYEE,
        payload: agent.Administrator.get(employee.id)
    }),
});

const AdministratorPreview = (props: any) => {
    const employee = props.employee;

    const handleClick = (event: any) => {
        event.preventDefault();

    };

    return (
        <div className="employee-preview">
            <div className="employee-details">
                <Link to={`/@${employee.employee.username}`}>
                    <img src={employee.employee.image} alt={employee.employee.username}/>
                </Link>

                <div className="info">
                    <Link className="author" to={`/@${employee.employee.username}`}>
                        {employee.employee.username}
                    </Link>
                </div>
            </div>
        </div>
    );
}

export default connect(() => ({}), mapDispatchToProps)(AdministratorPreview);
