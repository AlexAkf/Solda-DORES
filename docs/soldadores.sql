CREATE DATABASE soldaDORES CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;    -- Cria o DB e faz ele aceitar acentos
USE soldaDORES;

-- INÍCIO DAS TABELAS

-- EMPRESAS
CREATE TABLE empresas(
    id INT AUTO_INCREMENT PRIMARY KEY,  -- INT pra poupar memória
    nome VARCHAR(250) NOT NULL,
    cnpj VARCHAR(18) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    condicao BOOLEAN NOT NULL DEFAULT TRUE,   -- Para conta ativa/inativa ("status" é reservado). No DB TRUE = 1 e FALSE = 0
    telefone VARCHAR(27) NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Data de criação no BD, vai ser útil no relatório
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- USUÁRIOS
CREATE TABLE usuarios(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(250) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    login VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(500) NOT NULL,    -- Lembrando que no DB só se salva o hash da senha
    senha_padrao BOOLEAN NOT NULL DEFAULT TRUE,
    cargo ENUM('gestor', 'supervisor', 'soldador') NOT NULL,
    condicao BOOLEAN NOT NULL DEFAULT TRUE,
    perfil ENUM('adm', 'restrito', 'comum') NOT NULL DEFAULT 'comum',
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- SOLDADOR
    id_supervisor INT,
    sinete VARCHAR(50) UNIQUE,
    validade_certificado DATE,  -- Lembrando que no DB o formato é AAAA/MM/DD
    ultima_solda DATE,  -- Para validar o certificado
    
    -- RESTRIÇÕES
    CONSTRAINT fk_supervisor FOREIGN KEY(id_supervisor) REFERENCES usuarios(id) ON DELETE SET NULL,
    CONSTRAINT ck_validade CHECK((cargo = 'soldador' AND validade_certificado IS NOT NULL) OR (cargo <> 'soldador' AND validade_certificado IS NULL)), -- Garante validade em soldador
    CONSTRAINT ck_solda CHECK((cargo = 'soldador' AND ultima_solda IS NOT NULL) OR (cargo <> 'soldador' AND ultima_solda IS NULL)), -- Garante solda em soldador
    CONSTRAINT ck_sinete CHECK((cargo = 'soldador' AND sinete IS NOT NULL) OR (cargo <> 'soldador' AND sinete IS NULL))    -- Garante sinete em soldador
);

-- PROJETOS
CREATE TABLE projetos(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(250) NOT NULL,
    fk_empresa INT NOT NULL,
    FOREIGN KEY(fk_empresa) REFERENCES empresas(id) ON DELETE CASCADE,  -- Se empresa for deletada deleta projeto
    fk_supervisor INT,
    FOREIGN KEY(fk_supervisor) REFERENCES usuarios(id) ON DELETE SET NULL,
    inicio DATE NOT NULL DEFAULT (CURRENT_DATE),
    prazo DATE,
    descricao TEXT,
    condicao ENUM('ativo', 'avaliacao', 'finalizado', 'cancelado') NOT NULL DEFAULT 'ativo',
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- EQUIPAMENTOS
CREATE TABLE equipamentos(
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(100) NOT NULL UNIQUE,
    modelo VARCHAR(250) NOT NULL,
    marca VARCHAR(250) NOT NULL,
    condicao ENUM('estoque', 'emprestado', 'estragado') NOT NULL DEFAULT 'estoque',
    situacao BOOLEAN NOT NULL DEFAULT TRUE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- JUNTAS
CREATE TABLE juntas(
    id INT AUTO_INCREMENT PRIMARY KEY,
    fk_projeto INT NOT NULL,
    FOREIGN KEY(fk_projeto) REFERENCES projetos(id) ON DELETE CASCADE,
    condicao ENUM('nao_realizado', 'em_andamento', 'concluido', 'refazer') NOT NULL DEFAULT 'nao_realizado',
    comprimento DECIMAL(10, 2),
    codigo VARCHAR(100) NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- RESTRIÇÕES
    CONSTRAINT unq_codigo UNIQUE (fk_projeto, codigo)    -- Código é exclusivo dentro de projeto
);

-- RELATÓRIOS
CREATE TABLE relatorios(
    id INT AUTO_INCREMENT PRIMARY KEY,
    fk_gestor INT NOT NULL,
    FOREIGN KEY(fk_gestor) REFERENCES usuarios(id) ON DELETE RESTRICT,  -- Não deixa deletar gestor se houver backup
    nome VARCHAR(250) NOT NULL,
    descricao TEXT,
    caminho VARCHAR(500),
    condicao BOOLEAN NOT NULL DEFAULT FALSE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- BACKUPS
CREATE TABLE backups(
    id INT AUTO_INCREMENT PRIMARY KEY,
    hora TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tipo ENUM('manual', 'automatico') NOT NULL,
    condicao BOOLEAN NOT NULL DEFAULT FALSE,
    caminho VARCHAR(500),
    nome VARCHAR(250),
    fk_gestor INT,
    FOREIGN KEY(fk_gestor) REFERENCES usuarios(id) ON DELETE RESTRICT,
    descricao TEXT
) ENGINE=InnoDB;

-- FIM DAS TABELAS



-- INÍCIO DOS RELACIONAMENTOS

-- EMPRESTIMOS
CREATE TABLE emprestimos(
    id INT AUTO_INCREMENT PRIMARY KEY,
    fk_equipamento INT NOT NULL,
    FOREIGN KEY(fk_equipamento) REFERENCES equipamentos(id) ON DELETE CASCADE,
    fk_soldador INT NOT NULL,
    FOREIGN KEY(fk_soldador) REFERENCES usuarios(id) ON DELETE CASCADE,
    emprestimo DATE NOT NULL DEFAULT (CURRENT_DATE),
    devolucao DATE
);

-- TRABALHOS
CREATE TABLE trabalhos(
    id INT AUTO_INCREMENT PRIMARY KEY,
    fk_projeto INT NOT NULL,
    FOREIGN KEY(fk_projeto) REFERENCES projetos(id) ON DELETE CASCADE,
    fk_soldador INT NOT NULL,
    FOREIGN KEY(fk_soldador) REFERENCES usuarios(id) ON DELETE CASCADE,
    entrada DATE NOT NULL DEFAULT (CURRENT_DATE),
    
    -- RESTRIÇÕES
    CONSTRAINT unq_soldador UNIQUE (fk_projeto, fk_soldador) -- Soldador único por projeto
);

-- SOLDAGENS
CREATE TABLE soldagens(
    id INT AUTO_INCREMENT PRIMARY KEY,
    fk_junta INT NOT NULL,
    FOREIGN KEY(fk_junta) REFERENCES juntas(id) ON DELETE CASCADE,
    fk_soldador INT NOT NULL,
    FOREIGN KEY(fk_soldador) REFERENCES usuarios(id) ON DELETE RESTRICT,
    fk_equipamento INT,
    FOREIGN KEY(fk_equipamento) REFERENCES equipamentos(id) ON DELETE SET NULL,
    condicao ENUM('aprovado', 'reprovado'),
    descricao TEXT,
    comprimento DECIMAL(10, 2),
    processo VARCHAR(100),
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- FIM DOS RELACIONAMENTOS



-- INÍCIO DOS ÍNDICES

CREATE INDEX idx_validadeCertificado ON usuarios (validade_certificado);
CREATE INDEX idx_soldagensData ON soldagens (criado_em);
CREATE INDEX idx_juntasCondicao ON juntas (condicao);
CREATE INDEX idx_soldagensCondicao ON soldagens (condicao);
CREATE INDEX idx_usuariosNome ON usuarios (nome);
CREATE INDEX idx_empresasNome ON empresas (nome);
CREATE INDEX idx_projetosCondicao ON projetos (fk_empresa, condicao);
CREATE INDEX idx_equipamentosCondicao ON equipamentos (condicao);

-- FIM DOS ÍNDICES



-- INÍCIO DOS VISUALIZADORES

CREATE OR REPLACE VIEW vw_relatorioSoldagens AS
    SELECT
        soldagens.id AS id_soldagem,
        soldagens.comprimento,
        soldagens.descricao,
        soldagens.condicao AS condicao_soldagem,
        soldagens.criado_em,
        soldagens.processo,
        projetos.nome AS nome_projeto,
        empresas.nome AS nome_empresa,
        juntas.codigo AS codigo_junta,
        equipamentos.codigo AS codigo_equipamento,
        usuarios.sinete AS sinete_soldador,
        supervisor.nome AS nome_supervisor
    FROM soldagens
        JOIN juntas ON soldagens.fk_junta = juntas.id
        JOIN projetos ON juntas.fk_projeto = projetos.id
        JOIN empresas ON projetos.fk_empresa = empresas.id
        JOIN usuarios ON soldagens.fk_soldador = usuarios.id
        LEFT JOIN equipamentos ON soldagens.fk_equipamento = equipamentos.id
        LEFT JOIN usuarios AS supervisor ON usuarios.id_supervisor = supervisor.id;
    
CREATE OR REPLACE VIEW vw_resumoProjetos AS
    SELECT
        projetos.id,
        projetos.nome,
        empresas.nome AS nome_empresa,
        usuarios.nome AS nome_supervisor,
        projetos.condicao,
        COUNT(DISTINCT juntas.id) AS total_juntas,
        COUNT(DISTINCT soldagens.id) AS total_soldagens,
        projetos.criado_em
    FROM projetos
        JOIN empresas ON projetos.fk_empresa = empresas.id
        LEFT JOIN usuarios ON projetos.fk_supervisor = usuarios.id
        LEFT JOIN juntas ON projetos.id = juntas.fk_projeto
        LEFT JOIN soldagens ON juntas.id = soldagens.fk_junta
    GROUP BY projetos.id;
    
CREATE OR REPLACE VIEW vw_equipamentoUso AS
    SELECT
        equipamentos.id AS id_equipamento,
        equipamentos.codigo,
        equipamentos.marca,
        equipamentos.modelo,
        equipamentos.situacao,
        emprestimos.id AS id_emprestimo,
        usuarios.nome AS nome_soldador,
        emprestimos.emprestimo,
        emprestimos.devolucao,
        CASE
            WHEN emprestimos.devolucao IS NULL THEN 'Em uso'
            ELSE 'Devolvido'
        END AS condicao_uso
    FROM equipamentos
        LEFT JOIN emprestimos ON equipamentos.id = emprestimos.fk_equipamento
        LEFT JOIN usuarios ON emprestimos.fk_soldador = usuarios.id;
        
CREATE OR REPLACE VIEW vw_validadeSoldador AS
    SELECT
        usuarios.id AS id_soldador,
        usuarios.nome AS nome_soldador,
        usuarios.sinete,
        usuarios.validade_certificado,
        CASE
            WHEN usuarios.validade_certificado < CURRENT_DATE THEN 'Vencido'
            ELSE 'Válido'
        END AS situacao_certificado,
        usuarios.ultima_solda
    FROM usuarios
    WHERE usuarios.cargo = 'soldador';

-- FIM DOS VISUALIZADORES



-- INÍCIO DA AUTOMOÇÃO

DELIMITER $$

CREATE TRIGGER tgr_atualizacaoSoldagem
    AFTER INSERT ON soldagens
    FOR EACH ROW BEGIN
        UPDATE usuarios SET ultima_solda = NEW.criado_em WHERE id = NEW.fk_soldador;
    END;
$$


CREATE TRIGGER tgr_atualizacaoEmprestimo
	AFTER INSERT ON emprestimos
		FOR EACH ROW BEGIN
			UPDATE equipamentos 
			SET condicao = 'emprestado' 
			WHERE id = NEW.fk_equipamento;
		END;
$$

CREATE TRIGGER tgr_verificarEmprestimo
	BEFORE INSERT ON emprestimos
	FOR EACH ROW BEGIN
		IF EXISTS(
			SELECT 1 FROM emprestimos
			WHERE fk_equipamento = NEW.fk_equipamento AND devolucao IS NULL
		) THEN
			SIGNAL SQLSTATE '45000' 
			SET MESSAGE_TEXT = "Este equipamento ainda não foi devolvido";
		END IF;
	END;
$$

CREATE TRIGGER tgr_devolucao
	AFTER UPDATE ON emprestimos
	FOR EACH ROW BEGIN
		IF OLD.devolucao IS NULL AND NEW.devolucao IS NOT NULL THEN
			UPDATE equipamentos 
			SET condicao = 'estoque' 
			WHERE id = NEW.fk_equipamento;
		END IF;
	END;
$$

DELIMITER ;

-- FIM DA AUTOMOÇÃO



-- INÍCIO DOS DADOS

-- DADOS DE EMPRESAS
INSERT INTO empresas (nome, cnpj, email, telefone) VALUES
('Metalúrgica Aço Forte', '63.646.640/0001-93', 'contato@acoforte.com.br', '(11) 93000-1000'),
('Construtora Rocha Viva', '15.392.393/0001-01', 'adm@rochaviva.com.br', '(21) 94000-2000'),
('Indústria de Equipamentos Alfa', '52.560.068/0001-29', 'rh@industriaalfa.com.br', '(31) 95000-3000'),
('Naval Soluções', '98.812.498/0001-91', 'comercial@navalsolucoes.com.br', '(41) 96000-4000'),
('Engenharia Total', '19.014.946/0001-35', 'suporte@engenhariatotal.com.br', '(51) 97000-5000'),
('Soldas Premium Ltda', '27.558.315/0001-05', 'vendas@soldaspremium.com', '(61) 98000-6000'),
('Manutenção Pesada ME', '09.056.821/0001-14', 'contabil@manutencaopesada.com', '(71) 99000-7000');

-- DADOS DE USUÁRIOS
INSERT INTO usuarios (nome, cpf, email, login, senha, senha_padrao, cargo, perfil, id_supervisor, sinete, validade_certificado, ultima_solda) VALUES
('Carlos Silva', '859.642.470-91', 'carlos.silva@acoforte.com.br', 'carlos.silva@adm', 'inicial', TRUE, 'gestor', 'adm', NULL, NULL, NULL, NULL),
('Ana Souza', '139.375.810-06', 'ana.souza@acoforte.com.br', 'ana.souza@res', 'inicial', TRUE, 'supervisor', 'restrito', NULL, NULL, NULL, NULL),
('Bruno Ferreira', '748.430.730-08', 'bruno.ferreira@acoforte.com.br', 'bruno.ferreira@com', 'inicial', TRUE, 'soldador', 'comum', 2, 'BFR', '2026-10-30', '2024-05-15'),
('Daniel Martins', '296.855.040-32', 'daniel.martins@acoforte.com.br', 'daniel.martins@com', 'inicial', TRUE, 'soldador', 'comum', 2, 'DMR', '2025-01-20', '2024-10-25'),
('Eduarda Lima', '768.046.390-57', 'eduarda.lima@acoforte.com.br', 'eduarda.lima@com', 'inicial', TRUE, 'soldador', 'comum', 2, 'ELM', '2024-11-05', '2023-11-01'),
('Felipe Gomes', '528.410.330-07', 'felipe.gomes@rochaviva.com.br', 'felipe.gomes@adm', 'inicial', TRUE, 'gestor', 'adm', NULL, NULL, NULL, NULL),
('Giovana Reis', '271.138.350-40', 'giovana.reis@rochaviva.com.br', 'giovana.reis@res', 'inicial', TRUE, 'supervisor', 'restrito', NULL, NULL, NULL, NULL),
('Henrique Costa', '422.194.840-09', 'henrique.costa@rochaviva.com.br', 'henrique.costa@com', 'inicial', TRUE, 'soldador', 'comum', 7, 'HCR', '2025-07-01', '2024-09-20'),
('Isabela Alves', '509.993.310-60', 'isabela.alves@industriaalfa.com.br', 'isabela.alves@adm', 'inicial', TRUE, 'gestor', 'adm', NULL, NULL, NULL, NULL),
('Juliana Melo', '683.343.700-91', 'juliana.melo@industriaalfa.com.br', 'juliana.melo@res', 'inicial', TRUE, 'supervisor', 'restrito', NULL, NULL, NULL, NULL),
('Kauan Nunes', '528.328.160-45', 'kauan.nunes@industriaalfa.com.br', 'kauan.nunes@com', 'inicial', TRUE, 'soldador', 'comum', 10, 'KNS', '2027-03-15', '2024-10-01'),
('Laura Pires', '352.274.150-13', 'laura.pires@navalsolucoes.com.br', 'laura.pires@res', 'inicial', TRUE, 'supervisor', 'restrito', NULL, NULL, NULL, NULL),
('Marcelo Rocha', '908.787.820-60', 'marcelo.rocha@navalsolucoes.com.br', 'marcelo.rocha@com', 'inicial', TRUE, 'soldador', 'comum', 12, 'MRL', '2025-12-01', '2024-09-29'),
('Natália Santos', '004.851.070-07', 'natalia.santos@engenhariatotal.com.br', 'natalia.santos@adm', 'inicial', TRUE, 'gestor', 'adm', NULL, NULL, NULL, NULL),
('Otávio Barros', '965.005.630-00', 'otavio.barros@engenhariatotal.com.br', 'otavio.barros@com', 'inicial', TRUE, 'soldador', 'comum', NULL, 'OBS', '2026-04-10', '2024-08-01'),
('Administrador', 'Não informado', 'Não informado', '1', '1', FALSE, 'gestor', 'adm', NULL, NULL, NULL, NULL);


-- DADOS DE PROJETOS
INSERT INTO projetos (nome, fk_empresa, fk_supervisor, inicio, prazo, descricao, condicao) VALUES
('Estrutura Galpão Principal', 1, 2, '2024-09-01', '2025-03-30', 'Montagem da estrutura metálica para o novo galpão.', 'ativo'),
('Ponte Estaiada Rio Sul', 2, 7, '2024-05-10', '2026-12-31', 'Construção da ponte estaiada sobre o Rio Sul.', 'ativo'),
('Reforma Unidade C', 1, 2, '2024-10-15', '2024-12-15', 'Reforma e reforço das vigas na Unidade C.', 'ativo'),
('Protótipo Máquina V8', 3, 10, '2024-01-01', '2024-10-30', 'Desenvolvimento do protótipo de nova máquina industrial.', 'avaliacao'),
('Navio Petroleiro Zeus', 4, 12, '2023-11-20', '2025-06-01', 'Soldagem do casco do novo navio petroleiro.', 'ativo'),
('Ampliação Fábrica', 5, NULL, '2024-07-01', '2025-05-01', 'Ampliação da área de produção da fábrica.', 'ativo'),
('Projeto de Férias', 1, 2, '2024-12-20', '2025-01-10', 'Pequeno projeto de manutenção durante o recesso.', 'ativo');

-- DADOS DE EQUIPAMENTOS
INSERT INTO equipamentos (codigo, modelo, marca, condicao) VALUES
('SM-001', 'MigMag 300A', 'Esab', 'estoque'),
('ET-105', 'Eletrodo 200A', 'Lincoln Electric', 'estoque'),
('TG-550', 'TIG AC/DC', 'Miller', 'estoque'),
('PL-020', 'Plasma Cutter 12mm', 'Hypertherm', 'estoque'),
('LM-007', 'Laser Weld 5kW', 'Trumpf', 'estoque');

-- DADOS DE JUNTAS
INSERT INTO juntas (fk_projeto, comprimento, codigo, condicao) VALUES
(1, 15.50, 'J-G-001', 'nao_realizado'),
(1, 8.20, 'J-G-002', 'em_andamento'),
(1, 22.00, 'J-G-003', 'concluido'),
(1, 10.75, 'J-G-004', 'refazer'),
(1, 5.00, 'J-G-005', 'nao_realizado'),
(2, 50.00, 'J-P-001', 'nao_realizado'),
(2, 35.80, 'J-P-002', 'em_andamento'),
(3, 3.10, 'J-R-001', 'concluido'),
(4, 1.50, 'J-M-001', 'nao_realizado'),
(5, 75.00, 'J-N-001', 'nao_realizado');

-- DADOS DE RELATÓRIOS
INSERT INTO relatorios (fk_gestor, nome, descricao, caminho, condicao) VALUES
(1, 'Relatório Mensal de Soldagens - Set/24', 'Resumo das soldagens aprovadas e reprovadas.', '/relatorios/set_2024.pdf', TRUE),
(6, 'Relatório de Não Conformidades - Out/24', 'Lista de juntas marcadas para refazer.', '/relatorios/nc_out_2024.pdf', TRUE),
(1, 'Relatório de Desempenho Soldadores - T3', 'Análise trimestral de produtividade dos soldadores da Aço Forte.', '/relatorios/desemp_t3_24.xlsx', FALSE),
(9, 'Inventário de Equipamentos', 'Situação e localização dos equipamentos de solda.', '/relatorios/inv_equip_3.pdf', TRUE),
(14, 'Backup Manual Setembro', 'Relatório de backup manual para fins de auditoria.', '/backups/manual_set24.zip', FALSE);


-- DADOS DE BACKUPS
INSERT INTO backups (hora, tipo, condicao, caminho, nome, fk_gestor, descricao) VALUES
('2024-10-29 02:00:00', 'automatico', TRUE, '/bkp/auto/20241029_0200.sql', 'BKP Automático 29/10', 1, 'Backup automático do DB soldaDORES.'),
('2024-10-01 10:00:00', 'manual', TRUE, '/bkp/manual/20241001_1000.sql', 'BKP Manual Início Mês', 6, 'Backup manual realizado pelo gestor Felipe Gomes.'),
('2024-10-28 02:00:00', 'automatico', TRUE, '/bkp/auto/20241028_0200.sql', 'BKP Automático 28/10', 1, 'Backup automático do DB soldaDORES.'),
('2024-09-15 14:30:00', 'manual', TRUE, '/bkp/manual/20240915_1430.sql', 'BKP Manual Meio Mês', 14, 'Backup manual da Engenharia Total.'),
('2024-10-30 02:00:00', 'automatico', FALSE, '/bkp/auto/20241030_0200.sql', 'BKP Automático 30/10', 1, 'Backup automático (falha de conexão).');


-- DADOS DE TRABALHOS
INSERT INTO trabalhos (fk_projeto, fk_soldador, entrada) VALUES
(1, 3, '2024-09-05'),
(1, 4, '2024-09-05'),
(1, 5, '2024-09-15'),
(2, 8, '2024-05-10'),
(5, 13, '2024-09-25'),
(3, 3, '2024-10-15');

-- DADOS DE SOLDAGENS
INSERT INTO soldagens (fk_junta, fk_soldador, fk_equipamento, condicao, descricao, comprimento, processo) VALUES
(3, 3, 1, 'aprovado', 'Solda da viga principal V-101.', 12.00, 'MIG/MAG'),
(3, 4, 1, 'aprovado', 'Solda da viga principal V-102.', 10.00, 'MIG/MAG'),
(4, 3, 3, 'reprovado', 'Solda de reforço na coluna C-5.', 10.75, 'TIG'),
(8, 4, 1, 'aprovado', 'Solda da viga secundária V-200.', 3.10, 'MIG/MAG'),
(2, 4, 3, 'aprovado', 'Primeira passada da viga V-103.', 8.20, 'TIG'),
(1, 3, 1, 'aprovado', 'Teste de soldagem para qualificação.', 0.50, 'MIG/MAG'),
(1, 3, 1, 'reprovado', 'Teste de soldagem com erro de penetração.', 0.50, 'MIG/MAG'),
(1, 4, 3, 'aprovado', 'Teste de qualificação TIG.', 0.50, 'TIG');

-- FIM DOS DADOS