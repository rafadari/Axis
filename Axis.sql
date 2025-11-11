CREATE DATABASE IF NOT EXISTS Axis;
USE Axis;

CREATE TABLE grupos_usuarios (
id_grupo INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
nome_grupo VARCHAR(50) UNIQUE NOT NULL, 
permissao_base VARCHAR(255) NOT NULL COMMENT 'Ex: READ_ONLY, CRUD_BASICO, ADMINISTRADOR'
);

CREATE TABLE usuarios (
    id_usuario VARCHAR(50) PRIMARY KEY NOT NULL,
    id_grupo INT NOT NULL,
    login VARCHAR(100) UNIQUE NOT NULL, 
    senha_hash VARCHAR(255) NOT NULL COMMENT 'Armazenar só a hash e não a senha pura',
    nome_completo VARCHAR(200) NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_grupo FOREIGN KEY (id_grupo) REFERENCES grupos_usuarios(id_grupo)
);

CREATE TABLE linhas (
id_linha VARCHAR(50) PRIMARY KEY NOT NULL,
nome_linha VARCHAR(100) UNIQUE NOT NULL,
descricao TEXT,
ativa BOOLEAN DEFAULT TRUE
);

CREATE TABLE pontos_parada (
id_ponto INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
Id_linha VARCHAR(50) NOT NULL,
nome_ponto VARCHAR(50) NOT NULL,
longitude DECIMAL(11, 8) NOT NULL,
latitude DECIMAL(10, 8) NOT NULL,
CONSTRAINT fk_linha FOREIGN KEY (Id_linha) REFERENCES linhas(id_linha)
);

CREATE TABLE veiculos (
id_veiculos VARCHAR(50) PRIMARY KEY NOT NULL,
placa VARCHAR(11) UNIQUE NOT NULL,
modelo VARCHAR(50),
capacidade INT
);

CREATE TABLE configuracao_sistema (
id_config INT PRIMARY KEY NOT NULL,
total_usuarios INT DEFAULT 0
);

INSERT INTO configuracao_sistema (id_config, total_usuarios) VALUES (1, 0);

DELIMITER $$
CREATE FUNCTION fn_id_linha() RETURNS VARCHAR(50)
BEGIN 
RETURN CONCAT('LINHA-', SUBSTRING(REPLACE(UUID(), '-', ''), 1, 15));
END $$
DELIMITER ;

DELIMITER $$ 
CREATE PROCEDURE sp_cadastrar_novo_usuario (
IN p_login VARCHAR(100),
IN p_senha_hash VARCHAR(255),
IN P_nome_completo VARCHAR(200)
)
BEGIN
DECLARE v_id_usuario VARCHAR(50);
SET v_id_usuario = REPLACE(UUID(), '-', '');

INSERT INTO usuarios (id_usuario, id_grupo, login, senha_hash, nome_completo) VALUES (v_id_usuario, 2, p_senha_hash, p_nome_completo);
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER trg_atualizar_usuarios_insert
AFTER INSERT ON usuarios
FOR EACH ROW
BEGIN
	UPDATE configuracao_sistema
	SET total_usuarios = total_usuarios + 1
	WHERE id_config = 1;
END $$

CREATE TRIGGER trg_atualizar_usuarios_delete
AFTER DELETE ON usuarios
FOR EACH ROW
BEGIN
	UPDATE configuracao_sistema
	SET total_usuarios = total_usuarios - 1
	WHERE id_config = 1;
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER trg_verificar_coordenadas
BEFORE UPDATE ON pontos_parada
FOR EACH ROW
BEGIN
	IF NEW.latitude < -90 OR NEW.latitude > 90 OR NEW.longitude < -180 OR NEW.longitude > 180 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'ERRO: Latitude ou longitude inválida para o ponto da parada.';
	END IF;
END $$
DELIMITER ;

CREATE VIEW vw_permissoes_login AS
SELECT
	u.id_usuario,
    u.login,
    u.senha_hash,
    g.nome_grupo,
    g.permissao_base
FROM 
	usuarios u
JOIN
	grupos_usuarios g ON u.id_grupo = g.id_grupo;
    
CREATE VIEW vw_pontos_por_linhas AS
SELECT
	l.nome_linha,
    p.id_linha,
    p.nome_ponto,
    p.latitude,
    p.longitude
FROM
	linhas 
JOIN
	pontos_parada p ON l.id_linha = p.id_linha
ORDER BY
	l.nome_linha, p.id_ponto;
    
    INSERT INTO grupos_usuarios (id_grupo, nome_grupo, permissao_base) VALUES (1, 'administrador', 'CRUD_TOTAL'), (2, 'cliente_padrao', 'READ_ONLY');
    
    CALL sp_cadastrar_novo_usuario('teste.user', 'hash_da_senha_de_teste', 'Usuario Teste Padrao');
    
    INSERT INTO usuarios (id_usuario, id_grupo, login, senha_hash, nome_completa) VALUES (REPLACE(UUID(), '-', ''), 1, 'admin', 'hash_do_admin123', 'Administrador do Sistema');