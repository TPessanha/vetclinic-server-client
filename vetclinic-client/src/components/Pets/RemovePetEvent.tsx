import React from 'react';
import ReactDOM from 'react-dom';

class RemovePetEvent extends React.Component {
  shoot = (a: any) => {
    alert(a);
  }
  render() {
    return (
      <button onClick={() => this.shoot("Pet removed")}>Remove</button>
    );
  }
}