export class UserDTO {
    id?: string;
    apiToken?: string;
    refreshApiToken?: string;
    username: string;
    email: string;
    password: string;

    constructor(
        username: string,
        apiToken: string,
        refreshApiToken: string,
        email: string,
        password: string
    ) {
        this.username = username;
        this.apiToken = apiToken;
        this.refreshApiToken = refreshApiToken;
        this.email = email;
        this.password = password;

    }
}
