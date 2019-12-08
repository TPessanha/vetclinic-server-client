import React from "react";
import ReactDOM from "react-dom";
import "./index.scss";
import App from "./components/App";
import * as serviceWorker from "./serviceWorker";
import { createStore, applyMiddleware } from "redux";
import { Provider } from "react-redux";
import thunkMiddleware from "redux-thunk";
import { createLogger } from "redux-logger";
import reducers from "./reducers";
import { composeWithDevTools } from "redux-devtools-extension";
// import "bootstrap/dist/css/bootstrap.css";
// import "bootstrap/dist/css/bootstrap-theme.css";

const loggerMiddleware = createLogger();
const store = createStore(
    reducers,
    composeWithDevTools(applyMiddleware(thunkMiddleware, loggerMiddleware))
);
ReactDOM.render(
    <Provider store={store}>
        <App />
    </Provider>,
    document.getElementById("root")
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
