package AxisModel;

import jakarta.persistence.*;
import lombok.Data; // Usando Lombok para getters/setters/construtores

@Entity // Define que esta classe é mapeada para uma tabela
@Table(name = "grupos_usuarios")
@Data // Gera getters, setters, toString, equals, hashCode (Lombok)
public class GrupoUsuario {

    @Id // Define a Chave Primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT no MySQL
    @Column(name = "id_grupo")
    private Integer idGrupo; // Mapeia id_grupo

    @Column(name = "nome_grupo", unique = true, nullable = false)
    private String nomeGrupo;

    @Column(name = "permissao_base", nullable = false)
    private String permissaoBase;

    // Você não precisa de métodos explícitos se usar @Data do Lombok

}