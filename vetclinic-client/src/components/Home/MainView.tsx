import React, {useState} from 'react';
import agent from '../../agent';
import {useDispatch, useSelector} from 'react-redux';


const Employees = (props: any) => {
    const dispatch = useDispatch();
    const [employee, setEmployee] = useState();

    const token = useSelector((state: any) => state.common.token);


    return (
        <li className="nav-item">
            <a
                href=""
                className={employee === 'all' ? 'nav-link active' : 'nav-link'}
                onClick={() => setEmployee(agent.Employee.all)}>
                See All Employee
            </a>
        </li>
    );
};


function MainView(props: any) {
    const token = useSelector((state: any) => state.common.token);

    return <>
        <div className="col-md-9">
            <div className="feed-toggle">
                <ul className="nav nav-pills outline-active">
                    <Employees token={token}/>
                </ul>
            </div>
        </div>
    </>;
}
export default MainView