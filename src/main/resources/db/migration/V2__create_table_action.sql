CREATE TABLE action (
  "id" varchar(30) PRIMARY KEY,
  "service" varchar(30) NOT NULL,
  "name" text NOT NULL,
  "input_name" text NOT NULL,
  "input_sample" TEXT,
  "output_name" text NOT NULL,
  "output_sample" TEXT,
  "has_health_check" BOOLEAN NOT NULL,
  "created_at" TIMESTAMP NOT NULL,
  "updated_at" TIMESTAMP,
  CONSTRAINT fk_action_service
    FOREIGN KEY(service) REFERENCES service(id)
)
