package AxisModel;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    // ID Próprio (VARCHAR) - Não usamos @GeneratedValue
    @Id
    @Column(name = "id_usuario", length = 50, nullable = false)
    private String idUsuario; // Mapeia id_usuario (Gerado pela sua Procedure ou no Java)

    @ManyToOne // Define o relacionamento: Muitos Usuários para Um Grupo
    @JoinColumn(name = "id_grupo", nullable = false) // <-- CORREÇÃO: Garantir que o nome é "id_grupo"
    private GrupoUsuario grupo; // Objeto que representa o grupo de permissão

    @Column(name = "login", unique = true, nullable = false, length = 100)
    private String login;

    @Column(name = "senha_hash", nullable = false, length = 255)
    private String senhaHash;

    @Column(name = "nome_completo", length = 200)
    private String nomeCompleto;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro; // Mapeia o TIMESTAMP (MySQL)

}