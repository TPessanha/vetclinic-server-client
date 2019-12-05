import React from 'react';
import {Link} from 'react-router-dom';
import agent from '../../agent';
import {connect} from 'react-redux';
import {GET_ADMINISTRATOR} from '../../constants/actionTypes';

const mapDispatchToProps = (dispatch: any) => ({
    onClick: (administrator: any) => dispatch({
        type: GET_ADMINISTRATOR,
        payload: agent.Administrator.get(administrator.id)
    }),
});

const AdministratorPreview = (props: any) => {
    const administrator = props.administrator;

    const handleClick = (event: any) => {
        event.preventDefault();

    };

    return (
        <div className="administrator-preview">
            <div className="administrator-details">
                <Link to={`/@${administrator.administrator.username}`}>
                    <img src={administrator.administrator.image} alt={administrator.administrator.username}/>
                </Link>

                <div className="info">
                    <Link className="author" to={`/@${administrator.administrator.username}`}>
                        {administrator.administrator.username}
                    </Link>

                </div>
            </div>
        </div>
    );
}

export default connect(() => ({}), mapDispatchToProps)(AdministratorPreview);
