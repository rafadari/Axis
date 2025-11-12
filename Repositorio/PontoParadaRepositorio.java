package Repositorio;

import AxisModel.PontoParada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PontoParadaRepositorio extends JpaRepository<PontoParada, Integer> {
    // PontoParada usa Integer (INT) como PK

    // Método usado no RotaController
    List<PontoParada> findByIdLinha(String idLinha);
}
