import {APP_LOAD, ASYNC_START, USER_LIST_PAGE_LOADED, USER_LIST_PAGE_UNLOADED,} from '../constants/actionTypes';


export default (state: any = {}, action: any) => {
    switch (action.type) {
        case APP_LOAD:
            return {
                ...state,
                isLoading: false
            };
        case USER_LIST_PAGE_LOADED:
            if (!action.error || !state || state.users)
                return {...state}
            return {
                ...state,
                isLoading: false,
                users: state.users ? state.users.concat(action.payload) : action.payload
            };

        case ASYNC_START:
            if (action.subtype === USER_LIST_PAGE_LOADED) {
                return {...state, isLoading: true};
            }
            return {...state};
        case USER_LIST_PAGE_UNLOADED:
            return {};
        default:
            return {
                ...state,
                isLoading: false
            };
    }
};
