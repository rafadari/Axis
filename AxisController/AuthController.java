package AxisController;

import AxisDTO.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        try {
            // Tenta autenticar o usuário usando as credenciais e o PasswordEncoder configurado
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            // Define o contexto de segurança para manter o usuário logado
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Mapeia o grupo do usuário para informar o Frontend
            String grupos = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));

            return ResponseEntity.ok("Login bem-sucedido! Permissões: " + grupos);

        } catch (Exception e) {
            // Retorna erro de credenciais inválidas (401 Unauthorized)
            return ResponseEntity.status(401).body("Credenciais inválidas. Verifique usuário e senha.");
        }
    }

    // Endpoint para verificar o status de login (útil para o frontend)
    @GetMapping("/status")
    public ResponseEntity<String> checkStatus(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok("Usuário logado: " + authentication.getName());
        }
        return ResponseEntity.status(401).body("Nenhum usuário logado.");
    }
}