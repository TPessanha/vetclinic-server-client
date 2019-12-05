import React from 'react';
import ReactDOM from 'react-dom';

class NewPetForm extends React.Component {
  constructor(props:any) {
    super(props);
    this.state = {
      specie: '',
      age: null,
      health: '',
      description:''
    };
  }

  mySubmitHandler = (event:any) => {
    event.preventDefault();
    alert("New pet added");
  }

  myChangeHandler = (event:any) => {
    let nam = event.target.name;
    let val = event.target.value;
    this.setState({[nam]: val});
  }
  render() {
    return (
      <form onSubmit={this.mySubmitHandler}>
      <h1>Add Pet</h1>
      <p>Species:</p>
      <input
        type='text'
        name='specie'
        onChange={this.myChangeHandler}
      />
      <p>Age</p>
      <input
        type='text'
        name='age'
        onChange={this.myChangeHandler}
      />
      <p>Physical Description</p>
      <input
        type='text'
        name='decription'
        onChange={this.myChangeHandler}
      />
      <p>Health Notes</p>
      <input
        type='text'
        name='health'
        onChange={this.myChangeHandler}
      />
      <button type="submit"> Confirm </button>
      </form>
    );
  }
}