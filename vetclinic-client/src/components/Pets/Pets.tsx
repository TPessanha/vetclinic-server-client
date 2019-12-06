import React from "react";
import ReactDOM from "react-dom";

class Pets extends React.Component {
  constructor(props: any) {
    super(props);
    this.state = {
      email: "",
      phoneNumber: null
    };
  }

  mySubmitHandler = (event: any) => {
    event.preventDefault();
    alert("Appointment booked.");
  };

  myChangeHandler = (event: any) => {
    let nam = event.target.name;
    let val = event.target.value;
    this.setState({ [nam]: val });
  };
  render() {
    return (
      <div className="PetsContainer">
        <h1
          style={{
            backgroundColor: "darkTurquoise",
            borderStyle: "solid",
            textAlign: "center"
          }}
        >
          My Pets
        </h1>
        <div style={{}} className="PetListsAndNewPetForm">
          <div style={{ float: "left", width: "15%" }} className="PetList">
            <h3 style={{}}>Pet List</h3>
            <ul>
              <li>Pet 1</li>
              <li>Pet 2</li>
              <li>Pet 3</li>
              <li>Pet 4</li>
            </ul>
            <button style={{}} type="submit">
              {" "}
              Add Pet{" "}
            </button>
          </div>
          <div style={{ float: "right" }} className="AddPetForm">
            <form onSubmit={this.mySubmitHandler}>
              <h3>New Pet</h3>
              <p>Specie:</p>
              <input
                style={{}}
                type="text"
                name="specie"
                onChange={this.myChangeHandler}
              />
              <p>Age:</p>
              <input type="text" name="age" onChange={this.myChangeHandler} />
              <p>Description:</p>
              <input
                type="text"
                name="description"
                onChange={this.myChangeHandler}
              />
              <p>Health Status:</p>
              <input
                type="text"
                name="description"
                onChange={this.myChangeHandler}
              />
            </form>
            <button type="submit"> Confirm </button>
          </div>
        </div>
        <div
          style={{ position: "absolute", bottom: "0px" }}
          className="PetDetails"
        >
          <h2>Pet Details</h2>
          <p>Specie:</p>
          <p>Age:</p>
          <p>Owner: </p>
          <p>Description: </p>
          <p>Health Status:</p>
          <p>List of Appointments:</p>
        </div>
      </div>
    );
  }
}