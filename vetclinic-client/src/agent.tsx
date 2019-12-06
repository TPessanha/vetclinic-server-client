// @ts-ignore
import * as http from 'superagent';
// @ts-ignore
import * as requestPromise from 'superagent-promise';


const request = requestPromise(http, global.Promise);

const Api = 'http://localhost:8080';

const responseBody = (res: Body) => res.body;

// @ts-ignore
let token: string = null;


const tokenPlugin = (req: any) => {
    if (token) {
        req.set('authorization', `Token ${token}`);
    }
};

export enum UserType {
    ADMINISTRATOR, VETERINARIAN, CLIENT, UNREGISTERED
}

const requests = {
    delete: (url: string) =>
        request.del(`${Api}${url}`).use(tokenPlugin).then(responseBody),
    get: (url: string) =>
        request.get(`${Api}${url}`).use(tokenPlugin).then(responseBody),
    put: (url: string, body: any) =>
        request.put(`${Api}${url}`, body).use(tokenPlugin).then(responseBody),
    post: (url: string, body: any) =>
        request.post(`${Api}${url}`, body).use(tokenPlugin).then(responseBody)
};

const Auth = {
    login: (username: string, password: string) =>
        requests.post('/login', {CredentialsDTO: {username: username, password: password}}),
    signup: (username: string, email: string, password: string) =>
        requests.post('/signup', {ClientDTO: {username: username, email: email, password: password}}),
    save: (user: any) => {
        let type = User.type(user.id);

        if (UserType.ADMINISTRATOR === type) {
            return Administrator.update(user);
        }

        if (UserType.CLIENT === type) {
            return Client.update(user);
        }

        if (UserType.VETERINARIAN === type) {
            return Veterinarian.update(user);
        }
    }

};

const Administrator = {
    all: () =>
        requests.get(`/administrators`),
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
        requests.get(`/veterinarians?vetid=${vetId}`),
    delete: (vetId: string) =>
        requests.delete(`/veterinarians/${vetId}`),
    update: (veterinarian: any) =>
        requests.put(`/veterinarians/${veterinarian.id}`, {veterinarian: veterinarian}),
    create: (veterinarian: any) =>
        requests.post('/veterinarians', {veterinarian})
};

const Employee = {
    all: () => {
        let result = [];
        result.push(Administrator.all());
        result.push(Veterinarian.all());

        return result
    }
};
const Client = {
    get: (clientId: number) =>
        requests.get(`/client?id=${clientId}`),
    update: (client: any) =>
        requests.put(`/veterinarians/${client.id}`, {Client: client}),
};


const User = {
    type: (id: number) =>
        (Veterinarian.get(id)
            ? UserType.VETERINARIAN : (Administrator.get(id)
                ? UserType.ADMINISTRATOR : (Client.get(id)
                    ? UserType.CLIENT : UserType.UNREGISTERED)))
};


export default {
    Administrator,
    Employee,
    Veterinarian,
    Client,
    User,
    Auth,
    setToken: (_token: string) => {
        token = _token;
    }
};
