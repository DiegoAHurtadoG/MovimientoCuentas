-- ----------------------------
-- Sequence structure for movimiento_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."movimiento_id_seq";
CREATE SEQUENCE "public"."movimiento_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for persona_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."persona_id_seq";
CREATE SEQUENCE "public"."persona_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Table structure for cliente
-- ----------------------------
DROP TABLE IF EXISTS "public"."cliente";
CREATE TABLE "public"."cliente" (
  "contraseña" varchar(255) COLLATE "pg_catalog"."default" NOT NULL DEFAULT NULL,
  "estado" bool NOT NULL DEFAULT NULL,
  "id" int4 NOT NULL DEFAULT NULL
)
;

-- ----------------------------
-- Records of cliente
-- ----------------------------
INSERT INTO "public"."cliente" VALUES ('1234', 't', 1);
INSERT INTO "public"."cliente" VALUES ('5678', 't', 2);
INSERT INTO "public"."cliente" VALUES ('1245', 't', 3);
INSERT INTO "public"."cliente" VALUES ('789', 't', 4);

-- ----------------------------
-- Table structure for cuenta
-- ----------------------------
DROP TABLE IF EXISTS "public"."cuenta";
CREATE TABLE "public"."cuenta" (
  "numero_cuenta" varchar(255) COLLATE "pg_catalog"."default" NOT NULL DEFAULT NULL,
  "estado" bool NOT NULL DEFAULT NULL,
  "saldo_actual" numeric(19,2) NOT NULL DEFAULT NULL,
  "saldo_inicial" numeric(19,2) NOT NULL DEFAULT NULL,
  "tipo_cuenta" int4 NOT NULL DEFAULT NULL,
  "cliente_id" int4 NOT NULL DEFAULT NULL
)
;

-- ----------------------------
-- Records of cuenta
-- ----------------------------
INSERT INTO "public"."cuenta" VALUES ('585545', 't', 1000.00, 1000.00, 1, 1);
INSERT INTO "public"."cuenta" VALUES ('478758', 't', 1425.00, 2000.00, 0, 1);
INSERT INTO "public"."cuenta" VALUES ('225487', 't', 700.00, 100.00, 1, 2);
INSERT INTO "public"."cuenta" VALUES ('495878', 't', 250.00, 100.00, 0, 3);
INSERT INTO "public"."cuenta" VALUES ('496825', 't', 0.00, 540.00, 0, 2);
INSERT INTO "public"."cuenta" VALUES ('963', 't', 1000.00, 1000.00, 0, 3);

-- ----------------------------
-- Table structure for movimiento
-- ----------------------------
DROP TABLE IF EXISTS "public"."movimiento";
CREATE TABLE "public"."movimiento" (
  "id" int4 NOT NULL DEFAULT nextval('movimiento_id_seq'::regclass),
  "estado" bool NOT NULL DEFAULT NULL,
  "fecha" date NOT NULL DEFAULT NULL,
  "saldo_disponible" numeric(19,2) NOT NULL DEFAULT NULL,
  "tipo_movimiento" int4 NOT NULL DEFAULT NULL,
  "valor" numeric(19,2) NOT NULL DEFAULT NULL,
  "numero_cuenta" varchar(255) COLLATE "pg_catalog"."default" NOT NULL DEFAULT NULL
)
;

-- ----------------------------
-- Records of movimiento
-- ----------------------------
INSERT INTO "public"."movimiento" VALUES (1, 't', '2022-02-07', 1425.00, 0, 575.00, '478758');
INSERT INTO "public"."movimiento" VALUES (2, 't', '2022-02-08', 700.00, 1, 600.00, '225487');
INSERT INTO "public"."movimiento" VALUES (3, 't', '2022-02-09', 250.00, 1, 150.00, '495878');
INSERT INTO "public"."movimiento" VALUES (4, 't', '2022-02-10', 0.00, 0, 540.00, '496825');

-- ----------------------------
-- Table structure for persona
-- ----------------------------
DROP TABLE IF EXISTS "public"."persona";
CREATE TABLE "public"."persona" (
  "id" int4 NOT NULL DEFAULT nextval('persona_id_seq'::regclass),
  "direccion" varchar(255) COLLATE "pg_catalog"."default" NOT NULL DEFAULT NULL,
  "edad" int4 DEFAULT NULL,
  "genero" int4 DEFAULT NULL,
  "identificacion" varchar(255) COLLATE "pg_catalog"."default" NOT NULL DEFAULT NULL,
  "nombre" varchar(255) COLLATE "pg_catalog"."default" NOT NULL DEFAULT NULL,
  "telefono" varchar(255) COLLATE "pg_catalog"."default" NOT NULL DEFAULT NULL
)
;

-- ----------------------------
-- Records of persona
-- ----------------------------
INSERT INTO "public"."persona" VALUES (1, 'Otavalo sn y principal', NULL, NULL, '170987654321', 'Jose Lema', '098254785');
INSERT INTO "public"."persona" VALUES (2, 'Amazonas y NNUU', NULL, NULL, '110987654321', 'Marianela Montalvo', '097548965');
INSERT INTO "public"."persona" VALUES (3, '13 junio y Equinoccial', NULL, NULL, '150987654321', 'Juan Osorio', '098874587');
INSERT INTO "public"."persona" VALUES (4, 'La x', NULL, NULL, '789', 'Test 1', '789');

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."movimiento_id_seq"
OWNED BY "public"."movimiento"."id";
SELECT setval('"public"."movimiento_id_seq"', 5, true);
ALTER SEQUENCE "public"."persona_id_seq"
OWNED BY "public"."persona"."id";
SELECT setval('"public"."persona_id_seq"', 5, true);

-- ----------------------------
-- Primary Key structure for table cliente
-- ----------------------------
ALTER TABLE "public"."cliente" ADD CONSTRAINT "cliente_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table cuenta
-- ----------------------------
ALTER TABLE "public"."cuenta" ADD CONSTRAINT "cuenta_pkey" PRIMARY KEY ("numero_cuenta");

-- ----------------------------
-- Primary Key structure for table movimiento
-- ----------------------------
ALTER TABLE "public"."movimiento" ADD CONSTRAINT "movimiento_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table persona
-- ----------------------------
ALTER TABLE "public"."persona" ADD CONSTRAINT "uk_r5vsms84ih2viwd6tatk9o5pq" UNIQUE ("identificacion");

-- ----------------------------
-- Primary Key structure for table persona
-- ----------------------------
ALTER TABLE "public"."persona" ADD CONSTRAINT "persona_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table cliente
-- ----------------------------
ALTER TABLE "public"."cliente" ADD CONSTRAINT "fkkpvkbjg32bso6riqge70hwcel" FOREIGN KEY ("id") REFERENCES "persona" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table cuenta
-- ----------------------------
ALTER TABLE "public"."cuenta" ADD CONSTRAINT "fk4p224uogyy5hmxvn8fwa2jlug" FOREIGN KEY ("cliente_id") REFERENCES "cliente" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table movimiento
-- ----------------------------
ALTER TABLE "public"."movimiento" ADD CONSTRAINT "fkk10u787s9re28fue9gdscb5kt" FOREIGN KEY ("numero_cuenta") REFERENCES "cuenta" ("numero_cuenta") ON DELETE NO ACTION ON UPDATE NO ACTION;
