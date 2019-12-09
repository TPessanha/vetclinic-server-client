// import { Store } from "redux";
// import { userActions } from "../actions/user";
// import User from "../model/User";
import {history} from "../store";
import {getTokenInfo} from "../utils/tokenUtils";

export const userService = {
    login,
    logout
};

function login(username: String, password: String) {
    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    const requestOptions = {
        method: "POST",
        headers: myHeaders,
        body: JSON.stringify({username, password})
    };

    return fetch("/login", requestOptions).then(handleResponse);
}

function handleResponse(response: Response) {
    if (response.ok) {
        if (response.headers.get("redirect")) {
            const redirect = response.headers.get("redirect") as string;
            localStorage.setItem("home", redirect);
            history.push(redirect);
        }
        return response.headers.get("Authorization");
    } else {
        throw new Error(`Error: ${response.status}: ${response.statusText}`);
        // TODO
        // and add a message to the Ui: wrong password ?? other errors?
    }
}

function logout() {
    localStorage.removeItem("jwt");
    localStorage.removeItem("home");
    history.push("/");
}

export function checkJWT() {
    const token = getTokenInfo();
    const time = new Date().getTime() / 1000;
    if (token && token.exp < time) logout();
}
