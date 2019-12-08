import { createStore, combineReducers, applyMiddleware } from "redux";
import thunkMiddleware from "redux-thunk";
import logger from "redux-logger";
// import petsReducer from "../reducers/Pet";
import reducers from "../reducers";

const store = createStore(reducers, applyMiddleware(thunkMiddleware, logger));

export type RootState = ReturnType<typeof reducers>;
