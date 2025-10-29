export interface AuthenticatedUserDto {
    email: string,
    name: string,
    token: string,
    userType: string
}