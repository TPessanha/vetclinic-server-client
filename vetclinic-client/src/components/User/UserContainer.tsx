import React, {useEffect, useState} from "react";
import {useSelector} from "react-redux";
import {UserList} from "./UserList";

export const UserContainer = () => {
    const loadedUsers = useSelector((state: any) => state.users.users);
    const [users, setUser] = useState();

    useEffect(() => {
        if (!users && loadedUsers) {
            try {
                setUser(loadedUsers)

            } catch (e) {
                console.log(e)
            }

        }
    });

    return users ? <>
        <div>
            <UserList users={users || []}/>
        </div>
    </> : <></>

};
