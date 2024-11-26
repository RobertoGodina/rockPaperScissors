export class RefreshTokenDTO {
  username: string;
  refreshApiToken: string;

  constructor(
    username: string,
    refreshApiToken: string
  ) {
    this.username = username;
    this.refreshApiToken = refreshApiToken;
  }
}
