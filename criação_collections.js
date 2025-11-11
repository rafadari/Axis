use axis_mongo;

// cria usuario de acesso ao banco (sem root)
db.createUser({
  user: "axis_user",
  pwd: "senha_segura_123",
  roles: [{ role: "readWrite", db: "axis_mongo" }]
});

// divisor das coleções: vehicle_positions, viagens, traffic_incidents, rotas_cache

db.createCollection("vehicle_positions");
db.vehicle_positions.createIndex({ localizacao: "2dsphere" });
db.vehicle_positions.createIndex({ id_veiculo: 1, timestamp: -1 });
db.vehicle_positions.createIndex({ timestamp: 1 }, { expireAfterSeconds: 172800 }); // TTL 48h


db.createCollection("viagens");
db.viagens.createIndex({ id_veiculo: 1 });
db.viagens.createIndex({ id_linha: 1 });
db.viagens.createIndex({ inicio: -1 });


db.createCollection("traffic_incidents");
db.traffic_incidents.createIndex({ localizacao: "2dsphere" });
db.traffic_incidents.createIndex({ ativo: 1 });
db.traffic_incidents.createIndex({ data_registro: -1 });


db.createCollection("rotas_cache");
db.rotas_cache.createIndex({ id_linha: 1 });
