import React from 'react';
import agent from '../../agent';
import {connect} from 'react-redux';
import {CREATE_ADMINISTRATOR} from '../../constants/actionTypes';

const mapDispatchToProps = (dispatch: any) => ({
    onSubmit: (payload: any) =>
        dispatch({type: CREATE_ADMINISTRATOR, payload})
});


class AdministratorInput extends React.Component<any, any>{


    constructor(props: any) {
        super(props);

        this.handleSubminit = this.handleSubminit.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);

        this.state = {
            name: '',
            username: '',
            phoneNumber: '',
            address: '',
            email: '',
            password: ''
        };

    }

    set state(state) {
        this.state = state;
    }

    get state(): any {
        return this.state;
    }

    handleInputChange = (event: any) => {
        const target = event.target;
        const value = target.value;
        const name = target.name;

        this.setState({
            [name]: value
        });
    };


    handleSubminit = (event: any) => {
        event.preventDefault();
        const payload = agent.Administrator.create({body: this.state});
        this.setState({
                name: '',
                username: '',
                phoneNumber: '',
                address: '',
                email: '',
                password: ''
            }
        );

        // TODO ()
        // this.props.onSubmit(payload);
    };

    render() {
        return (
            <form className="card administrator-form" onSubmit={this.handleSubminit}>
                <div className="card-block">
                    <fieldset className="form-group">
                        Name:
                        <input
                            className="form-control form-control-lg"
                            type="text"
                            placeholder="Insert the Name..."
                            value={this.props.password}
                            onChange={this.handleInputChange}/>
                    </fieldset>
                    <label>

                        <input className="form-control"
                               placeholder="Insert the Name..."
                               type="text"
                               value={this.state.value}
                               onChange={this.handleInputChange}/>
                    </label>
                    <label>
                        UserName:
                        <input className="form-control"
                               placeholder="Insert the User Name..."
                               type="text"
                               value={this.state.value}
                               onChange={this.handleInputChange}/>
                    </label>
                    <label>
                        Phone Number:
                        <input className="form-control"
                               placeholder="Insert the Phone Number..."
                               type="text"
                               value={this.state.value}
                               onChange={this.handleInputChange}/>
                    </label>
                    <label>
                        Email:
                        <input className="form-control"
                               placeholder="Insert the Email..."
                               type="text"
                               value={this.state.value}
                               onChange={this.handleInputChange}/>
                    </label>
                    <label>
                        Address:
                        <input className="form-control"
                               placeholder="Insert the Name..."
                               type="text"
                               value={this.state.value}
                               onChange={this.handleInputChange}/>
                    </label>
                    <label>
                        Password:
                        <input className="form-control"
                               placeholder="Insert the Name..."
                               type="text"
                               value={this.state.value}
                               onChange={this.handleInputChange}/>
                    </label>

                </div>
                <div className="card-footer">
                    <button
                        className="btn btn-sm btn-primary"
                        type="submit">
                        Add New Administrator
                    </button>
                </div>
            </form>
        );
    }
}

export default connect(() => ({}), mapDispatchToProps)(AdministratorInput);
