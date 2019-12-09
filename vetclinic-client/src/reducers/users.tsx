import {APP_LOAD, ASYNC_START, USER_LIST_PAGE_LOADED, USER_LIST_PAGE_UNLOADED,} from '../constants/actionTypes';

export default (state = {}, action: any) => {
    switch (action.type) {
        case APP_LOAD:
            return {
                ...state,
                isLoading: false
            };
        case USER_LIST_PAGE_LOADED:
            const users = action.payload;
            if (!users)
                return {...state};

            return {
                ...state,
                isLoading: false,
                users: action.payload ? action.payload() : null
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
