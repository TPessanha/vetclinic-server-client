import api from './api';
import {ASYNC_END, ASYNC_START, LOGIN, LOGOUT, SINGUP} from './constants/actionTypes';


const promiseMiddleware = (store: any) => (next: any) => (action: any) => {
    if (isPromise(action.payload)) {
        store.dispatch({type: ASYNC_START, subtype: action.type});

        const currentView = store.getState().viewChangeCounter;
        const skipTracking = action.skipTracking;

        action.payload.then(
            (res: any) => {
                const currentState = store.getState();
                if (!skipTracking && currentState.viewChangeCounter !== currentView) {
                    return
                }
                console.log('RESULT', res);
                if (!(res instanceof Array)) {

                    action.payload = res.body;
                    action.headers = res.headers;
                }else  action.payload = res
                store.dispatch({type: ASYNC_END, promise: action.payload});
                store.dispatch(action);
            },
            (error: any) => {
                const currentState = store.getState();
                if (!skipTracking && currentState.viewChangeCounter !== currentView) {
                    return
                }
                console.log('ERROR', error);
                action.error = true;
                action.payload = error;
                if (!action.skipTracking) {
                    store.dispatch({type: ASYNC_END, promise: action.payload});
                }
                store.dispatch(action);
            }
        );

        return;
    }

    next(action);
};

const localStorageMiddleware = (store: any) => (next: any) => (action: any) => {
    if (action.type === SINGUP || action.type === LOGIN) {
        if (!action.error) {
            window.localStorage.setItem('token', action.headers.authorization);
            window.localStorage.setItem('username', action.headers.username);
            window.localStorage.setItem('userType', action.headers.type);
            api.setToken(action.headers.authorization);
        }
    } else if (action.type === LOGOUT) {
        window.localStorage.setItem('token', '');
        api.setToken(null);
    }

    next(action);
};

function isPromise(action: any) {
    return action && typeof action.then === 'function';
}


export {promiseMiddleware, localStorageMiddleware}
