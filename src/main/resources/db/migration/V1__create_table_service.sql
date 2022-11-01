CREATE TABLE service (
  "id" varchar(30) PRIMARY KEY,
  "name" text NOT NULL,
  "display_name" text NOT NULL,
  "description" text NOT NULL,
  "url" text NOT NULL,
  "created_at" TIMESTAMP NOT NULL,
  "updated_at" TIMESTAMP
)
