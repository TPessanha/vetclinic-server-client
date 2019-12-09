import ReactDOM from 'react-dom';
import {Provider} from 'react-redux';
import React from 'react';
import {history, store} from './store';
import {Route, Router, Switch} from 'react-router-dom';

import App from './components/App';
import "./index.scss";
import VetMainPage from "./components/Vet/VetMainPage";


ReactDOM.render((

    <Provider store={store}>
        <Router history={history}>
            <Switch>
                <Route path="/" component={App}/>
            </Switch>
        </Router>
    </Provider>

), document.getElementById('root'));


