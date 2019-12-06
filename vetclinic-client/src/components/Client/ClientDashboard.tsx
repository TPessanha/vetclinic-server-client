import React from 'react';
import ReactDOM from 'react-dom';

class ClientDashboard extends React.Component {
  constructor(props:any) {
    super(props);
    this.state = {
      email: '',
      phoneNumber: null,
    };
  }

  /*
  mySubmitHandler = (event:any) => {
    event.preventDefault();
    alert("Appointment booked.");
  }

  myChangeHandler = (event:any) => {
    let nam = event.target.name;
    let val = event.target.value;
    this.setState({[nam]: val});
  }
  */
  render() {
    return (
      <div style={{ backgroundColor: 'beije'}}className="ClientDashboardContainer">
        <h1 style={{ backgroundColor: 'darkTurquoise', borderStyle:'solid', textAlign: 'center' }}>Welcome to your Dashboard</h1>
      <div className="MyPets">
        <button style={{ margin: '4px 2px', fontSize: '16px'}} type="submit"> My Pets </button>
      </div>
      <div className="Appointments">
        <button style={{ margin: '4px 2px', fontSize: '16px'}} type="submit"> Appointments </button>
      </div>
      <div className="ChangeInfo">
        <button style={{ margin: '4px 2px', fontSize: '16px'}} type="submit"> Change Info </button>
      </div>
      </div>
    );
  }
}