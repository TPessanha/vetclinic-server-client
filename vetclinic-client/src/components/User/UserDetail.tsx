import {Link} from "react-router-dom";
import React from "react";

export const UserDetail = (props: any) => {
    const user = props.user;

    return user ? <>
            <div className="card-header"> {user.name}
                {/*<div className="card-img">*/}
                {/*    <Link*/}
                {/*        to={`/@${user.image}`}*/}
                {/*        className="element-user">*/}
                {/*        <img src={user.image} className="element-user-img" alt={user.username}/>*/}
                {/*    </Link>*/}
                {/*</div>*/}
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
                            to={`/@${user.username}`}
                            className="element-user">
                            {user.username}
                        </Link>

                        {/*<DeleteButton show={show} slug={props.slug} commentId={comment.id} />*/}
                    </div>
                </div>
            </div>

        </>
        : <></>;
};


