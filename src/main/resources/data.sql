CREATE TABLE departments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE employees (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    position VARCHAR(255) NOT NULL,
    salary DOUBLE NOT NULL,
    department_id BIGINT,
    FOREIGN KEY (department_id) REFERENCES departments(id)
);

INSERT INTO departments (id, name)
VALUES
    (1, 'Отдел продаж'),
    (2, 'Отдел маркетинга'),
    (3, 'Отдел IT');

INSERT INTO employees (id, first_name, last_name, position, salary, department_id)
VALUES
    (1, 'Иван', 'Иванов', 'Менеджер', 50000.0, 1),
    (2, 'Петр', 'Петров', 'Программист', 40000.0, 3),
    (3, 'Мария', 'Маркова', 'Маркетолог', 45000.0, 2),
    (4, 'Сергей', 'Сергеев', 'Продавец', 35000.0, 1),
    (5, 'Елена', 'Еленова', 'Аналитик', 42000.0, 3);