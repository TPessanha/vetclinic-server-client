/*baseado em AdministratorList*/


import {Link} from 'react-router-dom';
import React from 'react';

const AppointmentDetails = (props: any) => {
    const appointment = props.appointment;
    return (
        <div className="appoointment-details">

            <div className="administrator-info">
               
                <span className="pet">{appointment.pet}</span>
                <span className="date">{appointment.date}</span>
                <span className="client">{appointment.client}</span>
                <span className="vet">{appointment.veterinarian}</span>
                <span className="description">{appointment.description}</span>
            </div>
           
        </div>
    );
};

export default AppointmentDetails;

/*
const ArtistDetails = (props:{artistId:number}) => {
    const [ artist, setArtist] = useState<Artist | null>(null);
    getData<Artist>(`/artists/${props.artistId}`)
    .then( data => data && setArtist(data));
    return <>
    {artist &&
    <div>
    <p>First Name: {artist.firstName}</p>
    <p>Last Name: {artist.lastName}</p>
    <img src={artist.photo}/>
    </div>}
    { !artist && <div>No artist</div>}
    </>;
    }
*/
