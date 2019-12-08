export const alertActions = {
    success,
    error
};

const alertConstants = {
    SUCCESS: "ALERT_SUCCESS",
    ERROR: "ALERT_ERROR"
};

function success(msg: String) {
    return { type: alertConstants.SUCCESS, msg };
}

function error(msg: String) {
    return { type: alertConstants.ERROR, msg };
}
