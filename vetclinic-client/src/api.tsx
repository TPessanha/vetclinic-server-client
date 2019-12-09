// @ts-ignore
import * as http from 'superagent';
// @ts-ignore
import * as requestPromise from 'superagent-promise';


const request = requestPromise(http, global.Promise);

const Api = 'http://localhost:8080';

const responseBody = (res: Response) => {
    return {
        body: res.body,
        headers: res.headers
    };
};

// @ts-ignore
let token: string = null;


const plugin = (req: any) => {
    if (token && token.length > 0) {
        req.set('authorization', `${token}`);
    }
    // req.set('Access-Control-Allow-Origin', 'http://localhost:3000');
};

export enum UserType {
    ADMINISTRATOR = "administrators", VETERINARIAN = "veterinarians", CLIENT = "clients", UNREGISTERED = ""
}

const requests = {
    delete: (url: string) =>
        request.del(`${url}`).use(plugin).then(responseBody),
    get: (url: string) =>
        request.get(`${url}`).use(plugin).then(responseBody),
    put: (url: string, body: any) =>
        request.put(`${url}`, JSON.stringify(body)).use(plugin).then(responseBody),
    post: (url: string, body: any) =>
        request.post(`${url}`, JSON.stringify(body)).use(plugin).then(responseBody)
};


const Auth = {
    login: (username: string, password: string) =>
        requests.post('/login', withUser(username, password)),
    signup: (username: string, email: string, password: string) =>
        requests.post('/signup', withUser(username, password, email))
};

const Administrator = {
    all: () =>
        requests.get(`/administrators`),
    current: () =>
        requests.get(`/administrators/`),
    delete: (id: number) =>
        requests.delete(`/administrators/${id}`),
    get: (id: number) =>
        requests.get(`/administrators/${id}`),
    update: (adminstrator: any) =>
        requests.put(`/administrators/${adminstrator.id}`, {adminstrator}),
    create: (adminstrator: any) =>
        requests.post('/administrators', {adminstrator})
};

const Veterinarian = {
    all: () =>
        requests.get(`/veterinarians`),
    get: (vetId: number) =>
        requests.get(`/veterinarians/${vetId}`),
    getVetAppointments: (vetId: any) =>
        requests.get(`/veterinarians/${vetId}/appointments`),
    current: () =>
        requests.get(`/veterinarians/`),
    delete: (vetId: string) =>
        requests.delete(`/veterinarians/${vetId}`),
    update: (veterinarian: any) =>
        requests.put(`/veterinarians/${veterinarian.id}`, {veterinarian: veterinarian}),
    create: (veterinarian: any) =>
        requests.post('/veterinarians', {veterinarian})
};

const Employee = {
    all: () => Promise.all([Administrator.all(), Veterinarian.all()])

}

const Client = {
    all: () =>
        requests.get(`/clients`),
    get: (clientId: number) =>
        requests.get(`/clients/${clientId}`),
    update: (client: any) =>
        requests.put(`/clients/${client.id}`, {Client: client}),
};


const User = {
    getByUserName: (username: string) => request.get(`/user/${username}`),
    all: () => Promise.all([Administrator.all(), Veterinarian.all(), Client.all()])

};


function withUser(username: string, password: string, email = "-") {

    return {

        address: "-",
        username: username,
        phoneNumber: "-1",
        id: "0",
        email: email,
        roles: [],
        name: "-",
        password: password

    };
}

export default {
    Administrator,
    Employee,
    Veterinarian,
    Client,
    User,
    Auth,
    setToken: (_token: any) => {
        token = _token;
    }
};
