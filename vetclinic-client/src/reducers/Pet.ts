import Pet from "../model/Pet";
import { PetAction, petActions } from "../actions/pet";

function petsReducer(state = [] as Pet[], action: PetAction): Pet[] {
    switch (action.type) {
        case petActions.get:
            return [] as Pet[];

        default:
            return state;
    }
}

export default petsReducer;
