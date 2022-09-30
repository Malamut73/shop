INSERT INTO users (id, archive, email, name, password, role)
VALUES (1, false, 'mail@mail.ru', 'admin', '$2a$10$lDbtfDsH.SmyiUXxNqGEjOT1PSOZOYQSx5fzZg765Swq2HIDUTE3C', 'ADMIN');

UPDATE user_seq set next_val=2;
