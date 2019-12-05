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
    alert("Client Information updated");
  }

  myChangeHandler = (event:any) => {
    let nam = event.target.name;
    let val = event.target.value;
    this.setState({[nam]: val});
  }
  render() {
    return (
      <form onSubmit={this.mySubmitHandler}>
      <h1>Change your informtion</h1>
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
    );
  }
}