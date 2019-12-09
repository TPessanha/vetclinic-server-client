import { Action } from "redux";
import Pet from "../model/Pet";

export const petActions = {
    get
};

export interface PetAction extends Action {
    pet: {};
}

export function get() {
    return new Pet(1, "nas");
}
