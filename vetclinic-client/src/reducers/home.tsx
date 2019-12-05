import {HOME_PAGE_LOADED, HOME_PAGE_UNLOADED} from '../constants/actionTypes';

export default (state = {}, action: any) => {
    switch (action.type) {
        case HOME_PAGE_LOADED:
            return {
                ...state,

                employee: action.payload[0].employee
            };
        case HOME_PAGE_UNLOADED:
            return {};
        default:
            return state;
    }
};
