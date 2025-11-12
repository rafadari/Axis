package AxisDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder // Ajuda a construir objetos complexos
public class ETAResponse {
    private String idVeiculo;
    private String nomeLinha;
    private String tempoRestante; // Ex: "5 minutos"
    private double distanciaEmMetros;
}
