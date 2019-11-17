CREATE SCHEMA WAGMAR;


CREATE TABLE WAGMAR.CLIENTE (
  NR_CPF             DECIMAL(11)         NOT NULL    PRIMARY KEY,
  NM_CLIENTE         VARCHAR (256)       NOT NULL
);
CREATE UNIQUE INDEX nr_cpf_UNIQUE ON WAGMAR.CLIENTE (nr_cpf) ;


CREATE PROCEDURE autcredd.ac_confirma_carga(OUT cd_ret INTEGER, IN cd_emiss_cartao SMALLINT, IN cd_aplic_scont SMALLINT, IN nr_chip_cartao_sc DECIMAL(10, 0), IN nr_carga SMALLINT, IN vl_carga_efe DECIMAL(10, 2), IN nr_chip_cartao_cc char(20), IN dt_carga TIMESTAMP)
PARAMETER STYLE JAVA NO SQL LANGUAGE JAVA
EXTERNAL NAME 'com.examplo.Procedures.acConfirmaCarga';