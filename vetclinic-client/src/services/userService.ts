// import { Store } from "redux";
// import { userActions } from "../actions/user";
// import User from "../model/User";
import { history } from "../utils/history";

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
        body: JSON.stringify({ username, password })
    };

    return fetch("/login", requestOptions).then(handleResponse);
}

function handleResponse(response: Response) {
    if (response.ok) {
        if (response.headers.get("redirect")) {
            const redirect = response.headers.get("redirect") as string;
            const id = redirect.substring(redirect.lastIndexOf("/"));
            localStorage.setItem("userid", id);
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
    history.push("/");
}
