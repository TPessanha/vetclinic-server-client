export function getData<type>(response: Response): Promise<type> {
    if (response.ok) {
        if (response.headers.get("Authorization"))
            localStorage.setItem(
                "jwt",
                response.headers.get("Authorization") as string
            );
        return response.json();
    } else {
        throw new Error(`Error: ${response.status}: ${response.statusText}`);
    }
}

export function putData<type>(response: Response, val: type): type {
    if (response.ok) {
        if (response.headers.get("Authorization"))
            localStorage.setItem(
                "jwt",
                response.headers.get("Authorization") as string
            );
        return val;
    } else {
        throw new Error(`Error: ${response.status}: ${response.statusText}`);
    }
}
