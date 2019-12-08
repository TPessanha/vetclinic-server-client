import React, { Dispatch, FormEvent } from "react";
import { Nav, Navbar, Button } from "react-bootstrap";
import { userActions } from "../actions/user";
import { useDispatch } from "react-redux";
import { getTokenInfo } from "../utils/tokenUtils";

function performLogout(dispatch: Dispatch<any>) {
    dispatch(userActions.logout());
}

const NavBar = () => {
    const dispatch = useDispatch();
    const tokenInfo = getTokenInfo();

    const handlerLogout = (e: FormEvent<HTMLButtonElement>) => {
        performLogout(dispatch);
    };

    return (
        <Navbar bg="light" variant="light" expand="lg">
            <Navbar.Brand>Welcome {tokenInfo.username}</Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="mr-auto"></Nav>
                <Button onClick={handlerLogout}>Sign out</Button>
            </Navbar.Collapse>
        </Navbar>
    );
};

export default NavBar;
