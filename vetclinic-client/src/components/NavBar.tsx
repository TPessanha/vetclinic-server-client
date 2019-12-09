import React, { Dispatch, FormEvent } from "react";
import { Nav, Navbar, Button, NavItem } from "react-bootstrap";
import { userActions } from "../actions/user";
import { useDispatch } from "react-redux";

function performLogout(dispatch: Dispatch<any>) {
    dispatch(userActions.logout());
}

const NavBar = (props: { home: string }) => {
    const dispatch = useDispatch();

    const handlerLogout = (e: FormEvent<HTMLButtonElement>) => {
        performLogout(dispatch);
    };

    return (
        <Navbar bg="light" variant="light" expand="lg">
            <Navbar.Brand>Pet Clinic</Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="mr-auto">
                    <NavItem>
                        <Nav.Link href={props.home}>Home</Nav.Link>
                    </NavItem>
                </Nav>
                <Button onClick={handlerLogout}>Sign out</Button>
            </Navbar.Collapse>
        </Navbar>
    );
};

export default NavBar;
