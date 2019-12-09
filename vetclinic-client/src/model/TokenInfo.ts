class TokenInfo {
    sub: string;
    exp: number;
    iat: number;
    authorities: string[];
    username: string;
    id: number;
    constructor(
        sub: string,
        exp: number,
        iat: number,
        authorities: string[],
        username: string,
        id: number
    ) {
        this.sub = sub;
        this.exp = exp;
        this.iat = iat;
        this.authorities = authorities;
        this.username = username;
        this.id = id;
    }

    isAdmin(): boolean {
        return this.authorities.includes("ROLE_ADMIN");
    }

    isVeterinarian(): boolean {
        return this.authorities.includes("ROLE_VET");
    }

    isClient(): boolean {
        return this.authorities.includes("ROLE_CLIENT");
    }
}

export default TokenInfo;
