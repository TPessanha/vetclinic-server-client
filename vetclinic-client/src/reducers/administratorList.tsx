import {HOME_PAGE_LOADED, HOME_PAGE_UNLOADED, SET_PAGE,} from '../constants/actionTypes';

export default (state = {}, action: any) => {
    switch (action.type) {
        case SET_PAGE:
            return {
                ...state,
                administrator: action.payload.administrator,
                currentPage: action.page
            };
        case HOME_PAGE_LOADED:
            return {
                ...state,
                pager: action.pager,
                administrator: action.payload[1].administrator,
            };
        case HOME_PAGE_UNLOADED:
            return {};
        default:
            return state;
    }
};
