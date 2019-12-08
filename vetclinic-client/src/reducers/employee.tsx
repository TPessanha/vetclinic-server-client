import {
    APP_LOAD,
    ASYNC_START,
    EMPLOYEE_ADMIN_LOADED,
    EMPLOYEE_PAGE_LOADED,
    EMPLOYEE_PAGE_UNLOADED,
    EMPLOYEE_VET_LOADED
} from '../constants/actionTypes';

export default (state = {}, action: any) => {
    switch (action.type) {
        case APP_LOAD:
            return {
                ...state,
                isLoading: false
            };
        case EMPLOYEE_PAGE_LOADED:
            console.log(action);
            return {
                ...state,
                isLoading: false,
                employee: action.value,
            };
        case EMPLOYEE_VET_LOADED:
            return {
                ...state,
                isLoading: false,
                veterinarians: action.payload
            };
        case EMPLOYEE_ADMIN_LOADED:
            return {
                ...state,
                isLoading: false,
                administrators: action.payload
            };
        case ASYNC_START:
            if (action.subtype === EMPLOYEE_VET_LOADED || action.subtype === EMPLOYEE_ADMIN_LOADED || action.subtype === EMPLOYEE_VET_LOADED) {
                return {...state, isLoading: true};
            }
            //return previous state
            return {...state};
        case EMPLOYEE_PAGE_UNLOADED:
            return {};
        default:
            return state;
    }
};
