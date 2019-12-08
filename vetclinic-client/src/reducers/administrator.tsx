import {
    ADMINISTRATOR_PAGE_LOADED,
    ADMINISTRATOR_PAGE_UNLOADED,
    CREATE_ADMINISTRATOR,
    DELETE_ADMINISTRATOR
} from '../constants/actionTypes';

export default (state: any, action: any) => {
    switch (action.type) {
        case ADMINISTRATOR_PAGE_LOADED:
            return {
                ...state,
                administrator: action.value,
            };
        case ADMINISTRATOR_PAGE_UNLOADED:
            return {};
        case CREATE_ADMINISTRATOR:
            return {
                ...state,
                administratorErrors: action.error ? action.payload.errors : null,
                administrator: action.error ?
                    null :
                    (state.administrator || []).concat([action.payload.administrator])
            };
        case DELETE_ADMINISTRATOR:
            const id = action.administrator.id;
            return {
                ...state,
                administrator: state.administrators.filter((administrator: any) => administrator.id !== id)
            };
        default:
            return state;
    }
};
