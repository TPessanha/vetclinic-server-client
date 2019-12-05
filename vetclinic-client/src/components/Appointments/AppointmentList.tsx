const ArtistListItem = (props:{artist:Artist}) =>
<li key={props.artist.id}> {props.artist.firstName} {props.artist.lastName}</li>;
const ArtistIndex = (props:{artists:Artist[]}) =>
<ul>
{ props.artists.map((artist:Artist) => <ArtistListItem artist={artist}/>)}
</ul>;