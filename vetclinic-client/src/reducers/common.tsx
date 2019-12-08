import {
    ADMINISTRATOR_CREATED,
    ADMINISTRATOR_PAGE_UNLOADED,
    APP_LOAD,
    GET_USER,
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
    appName: 'VetClinic',
    token: "",
    viewChangeCounter: 0,
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
                isLoggedIn: action.token ? true : false
            };
        case GET_USER:
            return {
                ...state,
                currentUser: !action.error ? action.payload : state.currentUser,
                userType: action.payload ? action.payload.userType : null,
            };
        case REDIRECT:
            return {...state, redirectTo: null};
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
