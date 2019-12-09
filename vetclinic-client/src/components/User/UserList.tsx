import React from "react";
import {UserDetail} from "./UserDetail";

export const UserList = (props: any) => {
    const users = props.users;
    const list = users ? users.map((user: any) =>
        <UserDetail employee={user}/>) : null;

    return list ?
        <>
            <div className="row">
                {list}
            </div>
        </>
        : <></>

};