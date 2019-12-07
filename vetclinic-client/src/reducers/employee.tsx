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
                inProgress: true
            };
        case EMPLOYEE_PAGE_LOADED:
            return {
                ...state,
                inProgress: false,
                employees: action.value,
            };
        case EMPLOYEE_VET_LOADED:
            return {
                ...state,
                inProgress: false,
                employees_Veterinarian: action.payload
            };
        case EMPLOYEE_ADMIN_LOADED:
            return {
                ...state,
                inProgress: false,
                employees_Administrator: action.payload
            };
        case ASYNC_START:
            if (action.subtype === EMPLOYEE_VET_LOADED || action.subtype === EMPLOYEE_ADMIN_LOADED || action.subtype === EMPLOYEE_VET_LOADED) {
                return {...state, inProgress: true};
            }
            break;
        case EMPLOYEE_PAGE_UNLOADED:
            return {};
        default:
            return state;
    }
};
