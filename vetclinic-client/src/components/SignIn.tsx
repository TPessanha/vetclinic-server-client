import React, { ChangeEvent, FormEvent, useState, Dispatch } from "react";
import {
    useDispatch,
    useSelector as useReduxSelector,
    TypedUseSelectorHook
} from "react-redux";
import { userActions } from "../actions/user";
import { RootState } from "../store/store";
import "./SignIn.scss";
import GenericMainPage from "./GenericMainPage";

export const useSelector: TypedUseSelectorHook<RootState> = useReduxSelector;

async function performLogin(
    username: string,
    password: string,
    dispatch: Dispatch<any>
) {
    dispatch(userActions.login(username, password));
}

export function getData<T>(url: string, defaultValue: T): Promise<T> {
    let auth = {};
    let token = localStorage.getItem("jwt");
    if (token) auth = { Authorization: token };

    // sign out in case of unauthorized access (expired session)
    return fetch(url, {
        method: "GET",
        mode: "cors",
        cache: "no-cache",
        headers: {
            ...auth,
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            if (response.ok) return response.json();
            else {
                console.log(
                    `Error: ${response.status}: ${response.statusText}`
                );
                return new Promise<T>((resolve, reject) =>
                    resolve(defaultValue)
                );
            }
        })
        .catch(reason => {
            console.log(reason);
        });
}

const SignInForm: React.FC = () => {
    const dispatch = useDispatch();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const submitHandler = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        performLogin(username, password, dispatch);
        setUsername("");
        setPassword("");
    };

    const usernameChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
        setUsername(e.target.value);
    };

    const passwordChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value);
    };

    const signInForm = (
        <div className="wrapper fadeInDown">
            <div id="formContent">
                <form onSubmit={submitHandler}>
                    <label>
                        <h3>Login</h3>
                    </label>
                    <div>
                        <label>
                            <input
                                autoComplete="username"
                                type="text"
                                id="login"
                                className="fadeIn second"
                                value={username}
                                onChange={usernameChangeHandler}
                                placeholder="username"
                                name="login"
                            />
                        </label>
                    </div>
                    <div>
                        <label>
                            <input
                                autoComplete="current-password"
                                type="password"
                                id="password"
                                className="fadeIn third"
                                value={password}
                                onChange={passwordChangeHandler}
                                placeholder="password"
                                name="login"
                            />
                        </label>
                    </div>
                    <input
                        type="submit"
                        className="fadeIn fourth"
                        value="Sign In"
                    />
                </form>
            </div>
        </div>
    );

    const signedIn = useSelector(state => state.signedIn);

    return <> {signedIn ? GenericMainPage() : signInForm} </>;
    // add a message space for alerts (you were signed out, expired session)
};

export default SignInForm;
