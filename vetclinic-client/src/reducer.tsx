import auth from './reducers/auth';
import {combineReducers} from 'redux';
import common from './reducers/common';
import home from './reducers/home';
import {routerReducer} from "react-router-redux";
import employee from "./reducers/employee";
import user from "./reducers/user";
import signedIn from "./reducers/SignIn";
import petReducer from "./reducers/Pet";
import {filter as vetFilter, VetAppointementReducer} from "./reducers/VetAppointementReducer";

export default combineReducers({
    // administrator,
    user,
    employee,
    auth,
    common,
    home,
    signedIn,
    petReducer,
    VetAppointementReducer,
    vetFilter,
    router: routerReducer
});
