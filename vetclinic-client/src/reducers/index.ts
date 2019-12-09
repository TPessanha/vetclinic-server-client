import signedIn from "./SignIn";
import petReducer from "./Pet";
import {
    VetAppointementReducer,
    filter as vetFilter
} from "./VetAppointementReducer";

import { combineReducers } from "redux";

const reducers = combineReducers({
    signedIn,
    petReducer,
    VetAppointementReducer,
    vetFilter
});

export default reducers;
