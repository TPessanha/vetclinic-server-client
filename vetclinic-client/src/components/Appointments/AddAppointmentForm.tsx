import React from 'react';
import ReactDOM from 'react-dom';

class AddAppointmentForm extends React.Component {
  constructor(props:any) {
    super(props);
    this.state = {
      pet: '',
      slot: null,
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
      <form onSubmit={this.mySubmitHandler}>
      <h1>Book Appointment</h1>
      <p>Choose a Pet:</p>
      <input
        type='text'
        name='pet'
        onChange={this.myChangeHandler}
      />
      <p>Choose a slot:</p>
      <input
        type='text'
        name='slot'
        onChange={this.myChangeHandler}
      />
      <input
        type='submit'
      />
      </form>
    );
  }
}