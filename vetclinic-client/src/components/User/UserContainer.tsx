import React, {useEffect, useState} from "react";
import {useSelector} from "react-redux";
import {UserList} from "./UserList";

export const UserContainer = () => {
    const loadedUsers = useSelector((state: any) => state.user.users);



    return loadedUsers ? <>
        <div>
            <UserList users={loadedUsers || []}/>
        </div>
    </> : <></>

};
