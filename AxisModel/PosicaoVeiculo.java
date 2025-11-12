package AxisModel;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDateTime;

@Document(collection = "vehicle_positions") // Mapeia a collection
@Data
public class PosicaoVeiculo {

    // ID do MongoDB (_id)
    @Id
    private String id;

    @Field("id_veiculo")
    private String idVeiculo;

    // Array de coordenadas [longitude, latitude] para índice 2dsphere
    @Field("localizacao")
    private Double[] localizacao;

    @Field("timestamp")
    private LocalDateTime timestamp;

    // Outros campos relevantes (velocidade, direção, etc.)
}