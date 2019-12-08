import {APP_LOAD, ASYNC_START, PET_PAGE_LOADED, PET_PAGE_UNLOADED,} from '../constants/actionTypes';

export default (state = {}, action: any) => {
    switch (action.type) {
        case APP_LOAD:
            return {
                ...state,
                isLoading: false
            };
        case PET_PAGE_LOADED:
            console.log(action);
            return {
                ...state,
                isLoading: false,
                pet: action.value,
            };

        case ASYNC_START:
            if (action.subtype === PET_PAGE_LOADED) {
                return {...state, isLoading: true};
            }
            return {...state};
        case PET_PAGE_UNLOADED:
            return {};
        default:
            return {
                ...state,
                isLoading: false
            };
    }
};
