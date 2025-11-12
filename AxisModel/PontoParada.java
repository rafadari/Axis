package AxisModel;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pontos_parada")
@Data
public class PontoParada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ponto") // Chave INT
    private Integer idPonto;

    // CRÍTICO: Mapeia para Id_linha (com 'I' maiúsculo)
    @Column(name = "Id_linha", length = 50, nullable = false)
    private String idLinha;

    // Outra opção: Relacionamento ManyToOne para Linha
    /*
    @ManyToOne
    @JoinColumn(name = "Id_linha", referencedColumnName = "id_linha")
    private Linha linha;
    */

    @Column(name = "nome_ponto", length = 50, nullable = false)
    private String nomePonto;

    @Column(name = "longitude", precision = 11, scale = 8, nullable = false)
    private Double longitude;

    @Column(name = "latitude", precision = 10, scale = 8, nullable = false)
    private Double latitude;
}
