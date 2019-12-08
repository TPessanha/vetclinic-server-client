import { userConstants, UserAction } from "../actions/user";
// import UserInfo from "../model/UserInfo";
// import { tokenToInfo } from "../utils/tokenUtils";
// import TokenInfo from "../model/Token";

let initialState: boolean;
// const emptyTokenInfo = new TokenInfo("", 0, 0, [], "");
// const emtpyUserInfo = new UserInfo(false, "", emptyTokenInfo);

if (localStorage.getItem("jwt")) {
    initialState = true;
} else {
    initialState = false;
}

function signInReducer(state = initialState, action: UserAction): boolean {
    switch (action.type) {
        case userConstants.LOGIN_REQUEST:
            return false;
        case userConstants.LOGIN_SUCCESS:
            return true;
        case userConstants.LOGOUT:
            return false;
        case userConstants.LOGIN_FAILURE:
            return false;
        default:
            return state;
    }
}

export default signInReducer;
