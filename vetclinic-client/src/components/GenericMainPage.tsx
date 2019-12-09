import React from "react";
import NavBar from "./NavBar";
import { Router, Switch, Route } from "react-router-dom";
import VetMainPage from "./Vet/VetMainPage";
import { history } from "../utils/history";
import VetAppointement from "./Vet/VetAppointments";

const GenericMainPage = () => {
    const home = ("/" + localStorage.getItem("home")) as string;

    return (
        <Router history={history}>
            <div>
                <NavBar home={home} />
                <Switch>
                    <Route path="/administrators/:id">
                        <VetMainPage />
                    </Route>
                    <Route path="/veterinarians/:id/appointments">
                        <VetAppointement />
                    </Route>
                    <Route path="/veterinarians/:id">
                        <VetMainPage />
                    </Route>
                    <Route path="/client/:id">
                        <VetMainPage />
                    </Route>
                </Switch>
            </div>
        </Router>
    );
};

export default GenericMainPage;
