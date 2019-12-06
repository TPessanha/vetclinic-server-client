import React from 'react';
import ReactDOM from 'react-dom';

class ChangeInfoForm extends React.Component {
  constructor(props:any) {
    super(props);
    this.state = {
      email: '',
      phoneNumber: null,
    };
  }

  mySubmitHandler = (event:any) => {
    event.preventDefault();
    alert("Appointment booked.");
  }

  myChangeHandler = (event:any) => {
    let nam = event.target.name;
    let val = event.target.value;
    this.setState({[nam]: val});
  }
  render() {
    return (
      <div className="ChangeInfoContainer">
      <div className="ChangeInfoForm">
        <form onSubmit={this.mySubmitHandler}>
        <h1 style={{ borderColor: 'black', borderStyle:'solid' }}>Change your informtion</h1>
        <p>New email:</p>
        <input
          type='text'
          name='email'
          onChange={this.myChangeHandler}
        />
        <p>New phone number:</p>
        <input
          type='text'
          name='phoneNumber'
          onChange={this.myChangeHandler}
        />
        <button type="submit"> Confirm </button>
        </form>
        </div>
        <div className="ClientDetails">
        <h2>Client Details</h2>
        <p >Name:</p>
        <p>Email:</p>
        <p>Photo: </p>
        <p>Adress: </p>
        <p>Username:</p>
        </div>
      </div>
    );
  }
}