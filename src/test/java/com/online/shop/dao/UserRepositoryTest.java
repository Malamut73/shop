package com.online.shop.dao;

import com.online.shop.domain.User;
import com.online.shop.domain.enums.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@SqlGroup({@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:initUsers.sql")})
class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserDAO userDAO;

    @Test
    void checkFindByName() {
        //have
        User user = new User();
        user.setName("TestUser");
        user.setPassword("password");
        user.setEmail("test-email@mail.ru");

        entityManager.persist(user);

        //execute
        User actualUser = userDAO.findFirstByName("TestUser");

        //check
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(user.getName(), actualUser.getName());
        Assertions.assertEquals(user.getPassword(), actualUser.getPassword());
        Assertions.assertEquals(user.getEmail(), actualUser.getEmail());

    }

    @Test
    void checkFindByNameAfterSql() {
        //execute
        User actualUser = userDAO.findFirstByName("admin");

        //check
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(5, actualUser.getId());
        Assertions.assertEquals("admin", actualUser.getName());
        Assertions.assertEquals("adminpass", actualUser.getPassword());
        Assertions.assertEquals("admin@mail.ru", actualUser.getEmail());
        Assertions.assertEquals(Role.ADMIN, actualUser.getRole());

    }
}
