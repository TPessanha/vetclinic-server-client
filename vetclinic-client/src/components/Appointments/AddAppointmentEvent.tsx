import React from 'react';
import ReactDOM from 'react-dom';

class AddAppointmentEvent extends React.Component {
  shoot = (a: any) => {
    alert(a);
  }
  render() {
    return (
      <button onClick={() => this.shoot("Appointment Added.")}>Add Appointment</button>
    );
  }
}