import {
    ADMINISTRATOR_CREATED,
    ADMINISTRATOR_PAGE_UNLOADED,
    APP_LOAD,
    DELETE_ADMINISTRATOR,
    HOME_PAGE_UNLOADED,
    LOGIN,
    LOGIN_PAGE_UNLOADED,
    LOGOUT,
    REDIRECT,
    REGISTER_PAGE_UNLOADED,
    SINGUP
} from '../constants/actionTypes';

const defaultState = {
    appName: 'VetClinic',
    token: "",
    viewChangeCounter: 0
};

export default (state = defaultState, action: any) => {
    switch (action.type) {
        case APP_LOAD:
            return {
                ...state,
                token: action.token || "",
                appLoaded: true,
                currentUser: action.payload ? action.payload.user : null
            };
        case REDIRECT:
            return {...state, redirectTo: null};
        case LOGOUT:
            return {...state, redirectTo: '/', token: null, currentUser: null};
        case ADMINISTRATOR_CREATED:
            const redirectUrl = `/article/${action.payload.article.slug}`;
            return {...state, redirectTo: redirectUrl};

        case LOGIN:
        case SINGUP:
            return {
                ...state,
                redirectTo: action.error ? null : '/',
                token: action.error ? null : action.payload.user.token,
                currentUser: action.error ? null : action.payload.user
            };
        case DELETE_ADMINISTRATOR:
            return {...state, redirectTo: '/'};
        case ADMINISTRATOR_PAGE_UNLOADED:
        case HOME_PAGE_UNLOADED:
        case LOGIN_PAGE_UNLOADED:
        case REGISTER_PAGE_UNLOADED:
            return {...state, viewChangeCounter: state.viewChangeCounter + 1};
        default:
            return state;
    }
};
