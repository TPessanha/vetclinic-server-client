import React, {useEffect} from "react";
import {useDispatch} from "react-redux";
import {USER_LIST_PAGE_LOADED, USER_LIST_PAGE_UNLOADED} from "../../constants/actionTypes";
import api from "../../api";
import {UserContainer} from "./UserContainer";


export const User = () => {
    // const isLoading = useSelector((state: any) => state.employee.isLoading);
    const dispatch = useDispatch();

    const onLoad = () => {
        dispatch({
            type: USER_LIST_PAGE_LOADED,
            payload : Promise.all([api.Administrator.all(), api.Veterinarian.all(), api.Client.all()])
        });
    }
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
            <p className="text-xs-center">List Of User</p>
            <UserContainer/>
        </div>

    </>;
    // !isLoading ? <></> :

};