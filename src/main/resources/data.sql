INSERT INTO TOPICS
    (topic_id, name, user_id)
VALUES
    (1, 'Object Oriented Programming', 1),
    (2, 'Best practices and Code Smells', 1),
    (3, 'Design Patterns', 1),
    (4, 'Software development methodologies', 1),
    (5, 'Application frameworks', 1),
    (6, 'UML Design', 1),
    (7, 'Formats to interchange data', 1),
    (8, 'Database', 1),
    (9, 'ORM', 1),
    (10, 'Web Services and communications', 1),
    (11, 'Version Control', 1),
    (12, 'Unit Testing', 1),
    (13, 'Concurrency', 1);

INSERT INTO RESOURCES
    (resource_id, description_name, url, topic_id)
VALUES
    (1, 'Object and Class', 'https://www.youtube.com/watch?v=lbXsrHGhBAU', 1),
    (2, 'Abstract class and Interface', 'https://www.geeksforgeeks.org/difference-between-abstract-class-and-interface-in-java/', 1),
    (3, 'Polymorphism', 'https://www.youtube.com/watch?v=xdAF4m_Fgdo', 1),
    (4, 'Singleton', '', 3);
