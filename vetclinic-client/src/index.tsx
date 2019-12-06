import ReactDOM from 'react-dom';
import {Provider} from 'react-redux';
import React, {createContext} from 'react';
import {history, store} from './store';

import {Route, Router, Switch} from 'react-router-dom';

import App from './components/App';


ReactDOM.render((

    <Provider store={store}>
        <Router history={history}>
            <Switch>
                <Route path="/" component={App}/>
            </Switch>
        </Router>
    </Provider>

), document.getElementById('root'));


