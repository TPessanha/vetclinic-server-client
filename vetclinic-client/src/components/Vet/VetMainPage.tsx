import React, {useState} from "react";
import {vetService} from "../../services/VetService";
import Veterinarian from "../../model/Veterinarian";
import VetDetailsForm from "./VetDetails";
import {useHistory} from "react-router-dom";
import {Button} from "react-bootstrap";

const VetMainPage = (props: any) => {
    const {id} = props.id;
    const history = useHistory();
    const [vetDetails, setVetDetails] = useState<Veterinarian | null>(null);
    const [errorMsg, setErrorMsg] = useState("");
    if (!vetDetails)
        vetService.getVetDetails(id as string).then(
            vet => {
                console.log(vet);
                vet && setVetDetails(vet);
            },
            (err: Error) => {
                console.log(err);
                setErrorMsg(err.message);
            }
        );

    return (
        <>
            {vetDetails && (
                <div>
                    <div
                        style={{
                            width: "100%",
                            justifyContent: "center",
                            alignItems: "center",
                            display: "flex"
                        }}
                    >
                        <div>
                            <VetDetailsForm vet={vetDetails}/>
                        </div>
                    </div>
                    <div
                        style={{
                            margin: "15px 15px 15px 15px"
                        }}
                    >
                        <Button
                            onClick={() => history.push(`${id}/appointments`)}
                        >
                            Appointments
                        </Button>
                    </div>
                </div>
            )}
            {!vetDetails && (
                <div>
                    <p>No Veterinarian to show</p>
                    <p>Reason: {errorMsg}</p>
                </div>
            )}
        </>
    );
};

export default VetMainPage;
