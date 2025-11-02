import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Observable } from "rxjs";
import { environment } from "../../../../environments/environment";
import { AuthenticationService } from "../authentication.service";
import { Injectable } from "@angular/core";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(
        private AuthenticationService: AuthenticationService
    ){}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        console.log('requisicao interceptada' + request.url);

        if(request.url === environment.authentication_api_endpoint){
            return next.handle(request);
        }

        let authenticatedUser;
        try {
            authenticatedUser = this.AuthenticationService.getAuthenticateUser();
        } catch (error){
            console.error(error);
            return next.handle(request);
        }

        console.log(authenticatedUser)

        console.log('verficando a existencia do token');
        
        if(!authenticatedUser || !authenticatedUser.token){
            console.log('token nao existe, abortando');
            return next.handle(request);
        }

        const token = authenticatedUser.token;

        if(token){
            request = request.clone({
                setHeaders: {
                    Authorization: `Bearer ${token}`,           
                }
            })
        }

        return next.handle(request);

    }


}