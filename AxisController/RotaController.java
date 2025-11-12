package AxisController;

import Repositorio.PontoParadaRepositorio;
import AxisModel.Linha;
import AxisModel.PontoParada;
import Repositorio.LinhaRepositorio;
import Repositorio.PontoParadaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Requisito de Controle de Acesso
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rotas")
public class RotaController {

    @Autowired
    private LinhaRepositorio linhaRepository;

    @Autowired
    private PontoParadaRepositorio pontoParadaRepository;

    // Permite que qualquer usuário autenticado (incluindo CLIENTE_PADRAO) veja as linhas
    @GetMapping
    public List<Linha> getAllLinhas() {
        return linhaRepository.findAll();
    }

    // Retorna todos os pontos de uma linha específica
    @GetMapping("/{idLinha}/pontos")
    public List<PontoParada> getPontosByLinha(@PathVariable String idLinha) {
        // Assume que você criou um método findByIdLinha(String id) no PontoParadaRepository
        return pontoParadaRepository.findByIdLinha(idLinha);
    }

    // --- ENDPOINTS COM CONTROLE DE ACESSO (REQUISITO ACADÊMICO) ---

    // Apenas usuários com a ROLE_ADMINISTRADOR podem criar/alterar/excluir linhas
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/admin/linha")
    public ResponseEntity<Linha> createLinha(@RequestBody Linha linha) {
        // Simulação de uso da função fn_id_linha (se não for usada no INSERT)
        if (linha.getIdLinha() == null) {
            // Em Java, é mais prático gerar o ID aqui (ex: UUID.randomUUID().toString())
            // ou usar um Service que chame a sua Procedure SQL.
        }
        return ResponseEntity.ok(linhaRepository.save(linha));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/admin/linha/{id}")
    public ResponseEntity<Void> deleteLinha(@PathVariable String id) {
        linhaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
