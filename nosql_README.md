# Banco de Dados NoSQL — MongoDB (Sistema Axis)

Objetivo
O MongoDB complementa o MySQL armazenando dados dinâmicos e geoespaciais do sistema de mobilidade urbana Axis.

Coleções criadas
Coleção  Finalidade  Índices principais 
-`vehicle_positions`  Posições dos veículos em tempo real  `2dsphere`, `timestamp`, TTL 
- `viagens`  histórico de viagens dos veículos  `id_veiculo`, `id_linha` 
- `traffic_incidents`  Alertas e incidentes de trânsito  `2dsphere`, `ativo` 
-`rotas_cache`  Cache de trajetos de linhas  `id_linha` 

como executar:
1. Acesse o MongoDB (local ou Atlas)
2. Rode o script:
   ```bash
   mongo < 01_create_collections.js
   
exemplos
   `mongoimport --db axis_mongo --collection vehicle_positions --file 02_sample_data/vehicle_positions.json --jsonArray`

  # justificativa:
   O MongoDB foi escolhido por permitir:

alta taxa de escrita (telemetria de veículos)
consultas geoespaciais (pontos, rotas, incidentes)
expiração automática de dados (TTL)
integração simples com Java ou Node.js
assim, o MySQL mantém o núcleo transacional e o MongoDB guarda dados temporais e espaciais em tempo real
