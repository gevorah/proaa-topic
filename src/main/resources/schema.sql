DROP TABLE IF EXISTS Topics;
DROP TABLE IF EXISTS Resources;

CREATE TABLE Topics (
  topic_id INT NOT NULL,
  name VARCHAR(50) NOT NULL,
  user_id VARCHAR(50) NOT NULL,
  PRIMARY KEY (topic_id)
);

CREATE TABLE Resources (
  resource_id INT NOT NULL,
  description_name VARCHAR(50) NOT NULL,
  url VARCHAR(600) NOT NULL,
  topic_id INT NOT NULL,
  PRIMARY KEY (resource_id),
  FOREIGN KEY (topic_id) REFERENCES Topics(topic_id)
 );