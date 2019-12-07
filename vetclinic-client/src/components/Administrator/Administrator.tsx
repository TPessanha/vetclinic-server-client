import React, {useEffect} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {ADMINISTRATOR_PAGE_LOADED, ADMINISTRATOR_PAGE_UNLOADED} from '../../constants/actionTypes';


function Administrator(props: any) {
    const dispatch = useDispatch();

    const token = useSelector((state: any) => state.auth.token);
    const currentUser = useSelector((state: any) => state.common.currentUser);


    const onLoad = (page: any, payload: any) =>
        dispatch({type: ADMINISTRATOR_PAGE_LOADED, page, payload});
    const onUnload = () =>
        dispatch({type: ADMINISTRATOR_PAGE_UNLOADED});

    useEffect(() => {
        const container = props.token ? 'admin' : 'default';
        // onLoad(container, agent.Administrator.get(currentUser.id));

        return (() => {
            onUnload();
        })
    }, []);


    return <>
        <div className="page">
            <div className="container page">
                <div className="row">
                    {/*TODO (SHOW WHAT ADMIN CAN DO)*/}
                    <div className="col-md-6 offset-md-3 col-xs-12">
                        <h1 className="text-xs-center">Administrator</h1>

                    </div>

                </div>
            </div>
        </div>
    </>;
}

export default Administrator