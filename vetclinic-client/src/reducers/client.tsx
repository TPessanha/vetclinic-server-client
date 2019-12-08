import {APP_LOAD, ASYNC_START, CLIENT_PAGE_LOADED, CLIENT_PAGE_UNLOADED,} from '../constants/actionTypes';

export default (state = {}, action: any) => {
    switch (action.type) {
        case APP_LOAD:
            return {
                ...state,
                isLoading: false
            };
        case CLIENT_PAGE_LOADED:
            console.log(action);
            return {
                ...state,
                isLoading: false,
                client: action.value,
            };

        case ASYNC_START:
            if (action.subtype === CLIENT_PAGE_LOADED) {
                return {...state, isLoading: true};
            }
            return {...state};
        case CLIENT_PAGE_UNLOADED:
            return {};
        default:
            return {
                ...state,
                isLoading: false
            };
    }
};
