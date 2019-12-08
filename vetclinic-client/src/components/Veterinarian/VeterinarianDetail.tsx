import {Link} from "react-router-dom";
import React from "react";

export const VeterinarianDetail = (props: any) => {
    const veterinarian = props.veterinarian;

    return veterinarian ? <>
            <div className="card-header"> {veterinarian.name}
                <div className="card-img">
                    <Link
                        to={`/@${veterinarian.image}`}
                        className="element-user">
                        <img src={veterinarian.image} className="element-user-img" alt={veterinarian.username}/>
                    </Link>
                </div>
                <div className="card-body">
                    <div className="card-block">
                        <p className="card-text">Email:veterinarian.email </p>
                    </div>
                    <div className="card-block">
                        <p className="card-text">Phone Number:veterinarian.phonenumber </p>
                    </div>


                    <div className="card-footer">
                        &nbsp;
                        <Link
                            to={`/@${veterinarian.username}`}
                            className="element-user">
                            {veterinarian.username}
                        </Link>

                    </div>
                </div>
            </div>

        </>
        : <></>;
};


