package AxisDTO;

import lombok.Data;

@Data // Gera getters e setters
public class LoginRequest {
    private String username;
    private String password;
}