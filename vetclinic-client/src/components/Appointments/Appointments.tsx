import React from 'react';
import ReactDOM from 'react-dom';

class Appointments extends React.Component {
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
      <div style={{ backgroundColor: 'beije'}}className="AppointmentsContainer">
        <h1 style={{ backgroundColor: 'darkTurquoise', borderStyle:'solid', textAlign: 'center' }}>Appointments</h1>
        <div style={{borderColor: 'black', borderStyle: 'dotted'}} className="ChangeInfoForm">
        <form onSubmit={this.mySubmitHandler}>
        <h3 >Add Appointment</h3>
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
      <div className="AppointmentDetailsAndList">
       <div style={{float: 'left', width: '65%'}} className="AppointmentDetails">
        <h4>Appointment Details</h4>
        <p>Pet:</p>
        <p>Date:</p>
        <p>Client: </p>
        <p>Veterinarian: </p>
        <p>Description:</p>
        </div>
        <div style={{float: 'left',  width: '25%'}} className="AppointmentList">
        <h3>Appointment List</h3>
         <ul>
        <li>Appointment 1</li>
        <li>Appointment 2</li>
        <li>Appointment 3</li>
        <li>Appointment 4</li>
         </ul>
        </div>
       </div>
      </div>
    );
  }
}