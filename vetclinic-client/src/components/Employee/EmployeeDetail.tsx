import {Link} from "react-router-dom";
import React from "react";

export const EmployeeDetail = (props: any) => {
    const employee = props.employee;

    return employee ? <>
            <div className="card-header"> {employee.name}
                <div className="card-img">
                    <Link
                        to={`/@${employee.image}`}
                        className="element-user">
                        <img src={employee.image} className="element-user-img" alt={employee.username}/>
                    </Link>
                </div>
                <div className="card-body">
                    <div className="card-block">
                        <p className="card-text">Email: </p>
                    </div>
                    <div className="card-block">
                        <p className="card-text">Phone Number: </p>
                    </div>


                    <div className="card-footer">
                        &nbsp;
                        <Link
                            to={`/@${employee.username}`}
                            className="element-user">
                            {employee.username}
                        </Link>

                        {/*<DeleteButton show={show} slug={props.slug} commentId={comment.id} />*/}
                    </div>
                </div>
            </div>

        </>
        : <></>;
};


