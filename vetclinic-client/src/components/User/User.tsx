import React, {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {USER_LIST_PAGE_LOADED, USER_LIST_PAGE_UNLOADED} from "../../constants/actionTypes";
import api from "../../api";
import {UserContainer} from "./UserContainer";


export const User = () => {
    // const isLoading = useSelector((state: any) => state.employee.isLoading);
    const dispatch = useDispatch();

    const onLoad = () =>
        dispatch({type: USER_LIST_PAGE_LOADED, payload: api.User.all()});
    const onUnload = () =>
        dispatch({type: USER_LIST_PAGE_UNLOADED});
    useEffect(() => {
        onLoad();
        return (() => {
            onUnload();
        })
    });

    return <>
        <div className="container center">
            <p className="text-xs-center">List Of Employees</p>
            <UserContainer/>
        </div>

    </>;
    // !isLoading ? <></> :

};