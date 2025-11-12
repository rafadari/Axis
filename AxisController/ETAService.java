package AxisController;

import AxisDTO.ETAResponse;
import AxisModel.PosicaoVeiculo;
import Repositorio.PosicaoVeiculoRepositorio;
import AxisModel.PontoParada;
import Repositorio.PontoParadaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ETAService {

    @Autowired
    private PontoParadaRepositorio pontoParadaRepository;

    @Autowired
    private PosicaoVeiculoRepositorio vehiclePositionRepository;

    // Velocidade média em metros por minuto (ex: 20 km/h = 333.33 m/min)
    private static final double VELOCIDADE_MEDIA_M_MIN = 333.33;

    // Função principal de interligação dos bancos
    public ETAResponse calcularETA(Integer pontoId) {

        // 1. Busca o Ponto de Parada no MySQL (Dados Estáticos)
        PontoParada ponto = pontoParadaRepository.findById(pontoId)
                .orElseThrow(() -> new RuntimeException("Ponto de Parada não encontrado."));

        // 2. Simulação de busca do Veículo Mais Próximo (Dados Dinâmicos do MongoDB)
        // Em um projeto real, você faria uma Geo-Query no MongoDB:
        // vehiclePositionRepository.findNearestVehicle(ponto.getLatitude(), ponto.getLongitude());

        // --- SIMULAÇÃO ---
        PosicaoVeiculo veiculoMaisProximo = PosicaoVeiculoRepositorio.findFirstByIdVeiculoOrderByTimestampDesc("V101");

        if (veiculoMaisProximo == null) {
            return ETAResponse.builder()
                    .nomeLinha(ponto.getIdLinha())
                    .tempoRestante("N/A: Nenhum veículo ativo.")
                    .build();
        }
        // --- FIM SIMULAÇÃO ---

        // 3. Lógica de Cálculo de Distância (Fórmula de Haversine ou similar)
        double distancia = calcularDistanciaMetros(
                ponto.getLatitude(), ponto.getLongitude(),
                veiculoMaisProximo.getLocalizacao()[1], veiculoMaisProximo.getLocalizacao()[0] // [lat, lng]
        );

        // 4. Cálculo do Tempo (Distância / Velocidade)
        double tempoEmMinutos = distancia / VELOCIDADE_MEDIA_M_MIN;

        return ETAResponse.builder()
                .idVeiculo(veiculoMaisProximo.getIdVeiculo())
                .nomeLinha(ponto.getIdLinha())
                .distanciaEmMetros(distancia)
                .tempoRestante(String.format("%.0f minutos", tempoEmMinutos))
                .build();
    }

    // Simplificação da fórmula de Haversine para fins acadêmicos
    private double calcularDistanciaMetros(double lat1, double lon1, double lat2, double lon2) {
        final int RAIO_TERRA = 6371000; // Raio da Terra em metros
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RAIO_TERRA * c; // Distância em metros
    }
}
