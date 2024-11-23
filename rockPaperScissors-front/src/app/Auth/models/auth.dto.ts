export class AuthDTO {
  username: string;
  password: string;
  apiToken: string;
  refreshApiToken: string;


  constructor(
    username: string,
    password: string,
    apiToken: string,
    refreshApiToken: string
  ) {
    this.username = username;
    this.password = password;
    this.apiToken = apiToken;
    this.refreshApiToken = refreshApiToken;
  }
}
