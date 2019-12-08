import React, { useState } from "react";
import { vetService } from "../services/VetService";

import Veterinarian from "../model/Veterinarian";

const VetMainPage: React.FC = props => {
    const [vetDetails, setVetDetails] = useState<Veterinarian | null>(null);
    if (!vetDetails)
        vetService.getVetDetails().then(vet => {
            console.log(vet);
            vet && setVetDetails(vet);
        });

    return (
        <>
            {vetDetails && (
                <div>
                    <div style={{ width: "100%" }}>
                        <div style={{ float: "left", width: "40%" }}>
                            <p>Hello {vetDetails.name}</p>
                            <li>
                                <ul>1</ul>
                                <ul>3</ul>
                                <ul>4</ul>
                            </li>
                        </div>
                        <div style={{ float: "right" }}>
                            <div>Details of stuff</div>
                        </div>
                    </div>
                </div>
            )}
            {!vetDetails && <div>No Veterinarian</div>}
        </>
    );
};

export default VetMainPage;
