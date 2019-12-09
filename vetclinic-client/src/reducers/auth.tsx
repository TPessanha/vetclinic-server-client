import {
    ASYNC_START,
    LOGIN,
    LOGIN_PAGE_UNLOADED,
    LOGOUT_PAGE_UNLOADED,
    REGISTER_PAGE_UNLOADED,
    SINGUP,
    UPDATE_FIELD_AUTH
} from '../constants/actionTypes';

export default (state = {}, action: any) => {
    switch (action.type) {
        case LOGIN:
        case SINGUP:
            console.log(state)
            return {
                ...state,
                isLoading: false,
                errors: action.error ? action.payload.errors : null
            };
        case LOGIN_PAGE_UNLOADED:
        case LOGOUT_PAGE_UNLOADED:
        case REGISTER_PAGE_UNLOADED:
            return {};
        case ASYNC_START:
            if (action.subtype === LOGIN || action.subtype === SINGUP) {
                return {...state, isLoading: true};
            }
            break;
        case UPDATE_FIELD_AUTH:
            return {...state, [action.key]: action.value};
        default:
            return state;
    }

    return state;
};
