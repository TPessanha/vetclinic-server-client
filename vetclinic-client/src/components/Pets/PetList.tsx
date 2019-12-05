/*
const ArtistListItem = (props:{artist:Artist}) =>
<li key={props.artist.id}> {props.artist.firstName} {props.artist.lastName}</li>;
const ArtistIndex = (props:{artists:Artist[]}) =>
<ul>
{ props.artists.map((artist:Artist) => <ArtistListItem artist={artist}/>)}
</ul>;

*/

const PetList = () =>
<div>
    <h2>Users</h2>
    <ul>
        <li>Pet 1</li>
        <li>Pet 2</li>
        <li>Pet 3</li>
        <li>Pet 4</li>
    </ul>
</div>