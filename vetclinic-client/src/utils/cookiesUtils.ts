export function getCookie(name: string): string {
    const nameLenPlus = name.length + 1;
    return (
        document.cookie
            .split(";")
            .map(c => c.trim())
            .filter(cookie => {
                return cookie.substring(0, nameLenPlus) === `${name}=`;
            })
            .map(cookie => {
                return decodeURIComponent(cookie.substring(nameLenPlus));
            })[0] || ""
    );
}

export function deleteCookie(name: string) {
    const date = new Date();

    date.setTime(date.getTime() + -1 * 24 * 60 * 60 * 1000);

    document.cookie = name + "=; expires=" + date.toUTCString() + "; path=/";
}
