DROP TABLE IF EXISTS statuses;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS user_roles;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 100000;

CREATE TABLE user_roles
(
  id        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  role_name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE users
(
  id       INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name     VARCHAR                  NOT NULL,
  password VARCHAR                  NOT NULL,
  role_id  INTEGER                  NOT NULL,
  enabled  BOOL DEFAULT FALSE       NOT NULL,
  FOREIGN KEY (role_id) REFERENCES user_roles (id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE statuses (
  id                 INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name               VARCHAR   NOT NULL,
  date_time          TIMESTAMP NOT NULL,
  user_id            INTEGER   NOT NULL,
  start_new_work_day BOOL NOT NULL,
  end_work_day       BOOL NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE UNIQUE INDEX status_unique_user_datetime_idx
  ON statuses (user_id, date_time);