import React, {useState} from 'react';

export const ChangeInfo = (props: any) => {

    const [state, setState] = useState();

    const mySubmitHandler = (event: any) => {
        event.preventDefault();
        alert("Appointment booked.");
    }

    const myChangeHandler = (event: any) => {
        let nam = event.target.name;
        let val = event.target.value;
        setState({[nam]: val});
    }


    return <>
        <div className="ChangeInfoContainer">
            <div className="ChangeInfoForm">
                <form onSubmit={mySubmitHandler}>
                    <h1 style={{backgroundColor: 'darkTurquoise', borderStyle: 'solid', textAlign: 'center'}}>Edit your
                        Information</h1>
                    <p>New email:</p>
                    <input
                        type='text'
                        name='email'
                        onChange={myChangeHandler}
                    />
                    <p>New phone number:</p>
                    <input
                        type='text'
                        name='phoneNumber'
                        onChange={myChangeHandler}
                    />
                    <button type="submit"> Confirm</button>
                </form>
            </div>
            <div className="ClientDetails">
                <h2>Client Details</h2>
                <p>Name:</p>
                <p>Email:</p>
                <p>Photo: </p>
                <p>Adress: </p>
                <p>Username:</p>
            </div>
        </div>
    </>
}