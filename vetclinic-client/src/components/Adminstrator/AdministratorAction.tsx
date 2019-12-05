import {
    CREATE_ADMINISTRATOR,
    DELETE_ADMINISTRATOR,
    GET_ADMINISTRATOR,
    UPDATE_ADMINISTRATOR
} from "../../constants/actionTypes";
import agent from "../../agent";
import {Link} from "react-router-dom";
import React from "react";
import {connect} from 'react-redux';

const mapDispatchToProps = (dispatch: any) => ({
    onClickGet: (payload: any) =>
        dispatch({type: GET_ADMINISTRATOR, payload}),
    onClickDelete: (payload: any) =>
        dispatch({type: DELETE_ADMINISTRATOR, payload}),
    onClickCreate: (payload: any) =>
        dispatch({type: CREATE_ADMINISTRATOR, payload}),
    onClickUpdate: (payload: any) =>
        dispatch({type: UPDATE_ADMINISTRATOR, payload})
});

const AdministratorActions = (props: any) => {
    const administrator = props.administrator;

    const get = () => {
        props.onClickGet(agent.Administrator.get(administrator.id))
    };
    const del = () => {
        props.onClickDelete(agent.Administrator.delete(administrator.id))
    };
    const update = () => {
        props.onClickUpdate(agent.Administrator.update(administrator))
    };
    const create = () => {
        props.onClickCreate(agent.Administrator.create(administrator))
    };

    let defaultAction = <>
        <button className="btn btn-outline-danger btn-sm" onClick={get}>
            <i className="ion-trash-a"/> Get Administrator
        </button>

        <button className="btn btn-outline-danger btn-sm" onClick={del}>
            <i className="ion-trash-a"/> Delete Administrator
        </button>

        <button className="btn btn-outline-danger btn-sm" onClick={create}>
            <i className="ion-trash-a"/> Add New Administrator
        </button>
    </>
    if (props.canModify) {
        return (
            <span>
                <Link to={`/administrator/${administrator.update()}`} className="btn btn-outline-secondary btn-sm">
                  <i className="ion-edit"/> Update Administrator
                </Link>
                {defaultAction}
            </span>
        );
    }


    return (<><span>
        {defaultAction}
    </span></>);
};


export default connect(() => ({}), mapDispatchToProps)(AdministratorActions);
