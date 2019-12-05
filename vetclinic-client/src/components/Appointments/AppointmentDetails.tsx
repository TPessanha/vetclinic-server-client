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