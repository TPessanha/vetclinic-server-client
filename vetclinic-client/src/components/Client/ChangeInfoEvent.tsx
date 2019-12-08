import React from 'react';
import ReactDOM from 'react-dom';

export class ChangeInfoEvent extends React.Component {
  shoot = (a: any) => {
    alert(a);
  }
  render() {
    return (
      <button onClick={() => this.shoot("Provide the data to be changed")}>Change Info</button>
    );
  }
}