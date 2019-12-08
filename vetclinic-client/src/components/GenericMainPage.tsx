import React from "react";
import NavBar from "./NavBar";
import { Router, Switch, Route } from "react-router-dom";
import VetMainPage from "./VetMainPage";
import { history } from "../utils/history";

const GenericMainPage = () => {
    return (
        <Router history={history}>
            <div>
                <NavBar />
                <Switch>
                    <Route path="/administrators">
                        <VetMainPage />
                    </Route>
                    <Route path="/veterinarians">
                        <VetMainPage />
                    </Route>
                    <Route path="/client">
                        <VetMainPage />
                    </Route>
                </Switch>
            </div>
        </Router>
    );
};

export default GenericMainPage;
