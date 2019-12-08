import React, {useState} from 'react';

export const ChangeInfoEvent = (props: any) => {

    const [shoot, setShoot] = useState();
    return (
        <button onClick={() => setShoot("Provide the data to be changed")}>Change Info</button>
    );
};