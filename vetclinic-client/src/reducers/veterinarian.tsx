import {APP_LOAD, ASYNC_START, VETERINARIAN_PAGE_LOADED, VETERINARIAN_PAGE_UNLOADED,} from '../constants/actionTypes';

export default (state = {}, action: any) => {
    switch (action.type) {
        case APP_LOAD:
            return {
                ...state,
                isLoading: false
            };
        case VETERINARIAN_PAGE_LOADED:
            console.log(action);
            return {
                ...state,
                isLoading: false,
                veterinarian: action.value,
            };

        case ASYNC_START:
            if (action.subtype === VETERINARIAN_PAGE_LOADED) {
                return {...state, isLoading: true};
            }
            return {...state};
        case VETERINARIAN_PAGE_UNLOADED:
            return {};
        default:
            return {
                ...state,
                isLoading: false
            };
    }
};
