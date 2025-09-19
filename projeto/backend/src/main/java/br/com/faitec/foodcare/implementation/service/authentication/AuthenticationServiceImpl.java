package br.com.faitec.foodcare.implementation.service.authentication;

import br.com.faitec.foodcare.domain.UserModel;
import br.com.faitec.foodcare.port.dao.user.UserDao;
import br.com.faitec.foodcare.port.service.authentication.AuthenticationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserModel authenticate(String email, String password) {
        if(email == null || email.isEmpty()
            || password == null || password.isEmpty())
        {
            return null;
        }

        UserModel user = userDao.findByEmail(email);
        if(user == null) {
            return null;
        }
        
        // Verificar se é senha criptografada (BCrypt) ou texto simples
        boolean passwordMatch = false;
        if (user.getPassword().startsWith("$2a$") || user.getPassword().startsWith("$2b$")) {
            // Senha criptografada - usar BCrypt
            passwordMatch = passwordEncoder.matches(password, user.getPassword());
        } else {
            // Senha em texto simples - comparação direta
            passwordMatch = password.equals(user.getPassword());
        }
        
        if(passwordMatch) {
            // Verificar se beneficiário está aprovado
            if(user.getUserType() == UserModel.UserType.BENEFICIARY) {
                if(user.getAble() == null || !user.getAble()) {
                    return null; // Beneficiário não aprovado
                }
            }
            return user;
        }
        
        return null;
    }
}