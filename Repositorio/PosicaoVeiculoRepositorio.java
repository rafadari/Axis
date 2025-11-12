package Repositorio;

import AxisModel.PosicaoVeiculo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosicaoVeiculoRepositorio extends MongoRepository<PosicaoVeiculo, String> {

    // Método usado no ETAService para simular o veículo mais recente
    static PosicaoVeiculo findFirstByIdVeiculoOrderByTimestampDesc(String idVeiculo);

    // Você também pode adicionar métodos GeoSpatial aqui (findNear, etc.)
}
