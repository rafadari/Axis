package AxisController;

import AxisDTO.ETAResponse;
import AxisModel.PosicaoVeiculo;
import Repositorio.PosicaoVeiculoRepositorio;
import AxisController.ETAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transporte")
public class PosicaoController {

    @Autowired
    private PosicaoVeiculoRepositorio vehiclePositionRepository;

    @Autowired
    private ETAService etaService;

    // Endpoint para o Frontend atualizar os ícones no mapa (dados em tempo real do MongoDB)
    @GetMapping("/veiculos/posicoes")
    public List<PosicaoVeiculo> getAllVehiclePositions() {
        return vehiclePositionRepository.findAll();
    }

    // Endpoint principal: Cálculo de ETA (Interliga MySQL e MongoDB)
    @GetMapping("/eta/{pontoId}")
    public ResponseEntity<ETAResponse> getEstimatedTimeOfArrival(@PathVariable Integer pontoId) {

        // Chama a camada de Serviço que contém a lógica de negócios
        ETAResponse response = etaService.calcularETA(pontoId);

        return ResponseEntity.ok(response);
    }

    // Exemplo de Inserção de Nova Posição (Para testes)
    @PostMapping("/admin/posicao")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<PosicaoVeiculo> recordPosition(@RequestBody PosicaoVeiculo position) {
        // Salva a nova posição no MongoDB (demonstra o uso de alta escrita do NoSQL)
        return ResponseEntity.ok(vehiclePositionRepository.save(position));
    }
}