import signedIn from "./SignIn";
import petReducer from "./Pet";

import { combineReducers } from "redux";

const reducers = combineReducers({
    signedIn,
    petReducer
});

export default reducers;
