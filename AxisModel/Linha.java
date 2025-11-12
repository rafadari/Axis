package AxisModel;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "linhas")
@Data
public class Linha {

    @Id
    @Column(name = "id_linha", length = 50, nullable = false) // Chave VARCHAR
    private String idLinha;

    @Column(name = "nome_linha", length = 100, unique = true, nullable = false)
    private String nomeLinha;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "ativa")
    private Boolean ativa = true;

    // Métodos (se necessários) ou confie no Lombok
}
