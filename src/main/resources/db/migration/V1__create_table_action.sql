CREATE TABLE action (
  "id" varchar(30) PRIMARY KEY,
  "service" text NOT NULL,
  "name" text NOT NULL,
  "input_name" text NOT NULL,
  "input_sample" TEXT,
  "output_name" text NOT NULL,
  "output_sample" TEXT,
  "created_at" TIMESTAMP NOT NULL,
  "updated_at" TIMESTAMP
)
