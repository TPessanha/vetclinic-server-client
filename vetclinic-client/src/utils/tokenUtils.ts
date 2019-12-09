import TokenInfo from "../model/TokenInfo";

export function tokenToInfo(token: string): TokenInfo {
    return parseToken(token);
}

export function parseToken(token: string) {
    var base64Url = token.split(".")[1];
    var base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
    var jsonPayload = decodeURIComponent(
        atob(base64)
            .split("")
            .map(function(c) {
                return "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2);
            })
            .join("")
    );

    return JSON.parse(jsonPayload);
}

export function getTokenInfo(): TokenInfo {
    return tokenToInfo(localStorage.getItem("jwt") as string);
}