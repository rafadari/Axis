package Repositorio;

import AxisModel.Linha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinhaRepositorio extends JpaRepository<Linha, String> {
    // Linha usa String (VARCHAR) como PK
}
