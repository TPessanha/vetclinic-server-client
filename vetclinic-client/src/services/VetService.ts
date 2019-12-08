import { history } from "../utils/history";
import Veterinarian from "../model/Veterinarian";

export const vetService = {
    getVetDetails
};

function getVetDetails(): Promise<Veterinarian> {
    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Authorization", localStorage.getItem("jwt") as string);

    const requestOptions = {
        method: "GET",
        headers: myHeaders
    };

    return fetch(history.location.pathname, requestOptions).then(
        getVetDetailsHandler
    );
}

function getVetDetailsHandler(response: Response) {
    if (response.ok) {
        if (response.headers.get("Authorization"))
            localStorage.setItem(
                "jwt",
                response.headers.get("Authorization") as string
            );
        return response.json();
    } else {
        throw new Error(`Error: ${response.status}: ${response.statusText}`);
    }
}
