import {
    ADMINISTRATOR_CREATED,
    ADMINISTRATOR_PAGE_UNLOADED,
    APP_LOAD,
    HOME_PAGE_UNLOADED,
    LOGIN,
    LOGIN_PAGE_UNLOADED,
    LOGOUT,
    LOGOUT_PAGE_UNLOADED,
    REDIRECT,
    REGISTER_PAGE_UNLOADED,
    SINGUP
} from '../constants/actionTypes';

const defaultState = {
    appName: 'Pet Clinic',
    token: "",
    viewChangeCounter: 0,
    userType: null,
    currentUser: null
};

export default (state = defaultState, action: any) => {
    switch (action.type) {
        case APP_LOAD:
            return {
                ...state,
                token: action.token || "",
                appLoaded: true,
                currentUser: action.payload ? action.payload : null,
                isLoggedIn: action.token ? true : false,
                userType: action.userType
            };
        case REDIRECT:
            return {...state, redirectTo: action.redirectTo};
        case LOGOUT_PAGE_UNLOADED:
            return {...state, isLoggedIn: false};
        case LOGOUT:
            return {...state, redirectTo: '/', token: null, currentUser: null};
        case ADMINISTRATOR_CREATED:
            const redirectUrl = `/administrator/${action.payload.administrator}`;
            return {...state, redirectTo: redirectUrl};

        case LOGIN:
        case SINGUP:
            return {
                ...state,
                redirectTo: action.error ? null : '/',
                token: action.error ? null : action.headers.authorization,
                userType: action.error ? null : action.headers.type,
                currentUser: null,
            };
        case ADMINISTRATOR_PAGE_UNLOADED:
        case HOME_PAGE_UNLOADED:
        case LOGIN_PAGE_UNLOADED:
        case REGISTER_PAGE_UNLOADED:
            return {...state, viewChangeCounter: state.viewChangeCounter + 1};
        default:
            return state;
    }
};
