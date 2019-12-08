import { Action, Dispatch } from "redux";
import { userService } from "../services/userService";

export const userConstants = {
    LOGIN_REQUEST: "LOGIN_REQUEST",
    LOGOUT: "LOGOUT",
    LOGIN_FAILURE: "LOGIN_FAILURE",
    LOGIN_SUCCESS: "LOGIN_SUCCESS"
};

export const userActions = {
    login,
    logout
};

export interface UserAction extends Action {
    token: string;
}

function login(username: string, password: string) {
    return (dispatch: Dispatch<any>) => {
        dispatch(request(username));

        userService.login(username, password).then(
            token => {
                if (token) {
                    console.log("Dispatching login for: " + username);
                    console.log("Received token: " + token);
                    localStorage.setItem("jwt", token);

                    dispatch(success(token));
                }
                // history.push("/");
            },
            error => {
                dispatch(failure(error.toString()));
                // dispatch(alertActions.error(error.toString()));
            }
        );
    };

    function request(user: string) {
        return { type: userConstants.LOGIN_REQUEST, user };
    }
    function success(token: string) {
        return { type: userConstants.LOGIN_SUCCESS, token };
    }
    function failure(error: any) {
        return { type: userConstants.LOGIN_FAILURE, error };
    }
}

function logout() {
    userService.logout();
    return {
        type: userConstants.LOGOUT
    };
}
