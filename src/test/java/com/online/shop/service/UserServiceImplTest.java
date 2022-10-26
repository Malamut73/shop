package com.online.shop.service;

import com.online.shop.dao.UserDAO;
import com.online.shop.domain.User;
import com.online.shop.dto.UserDTO;
import com.online.shop.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.mail.MailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

class UserServiceImplTest {

    private UserServiceImpl userService;
    private PasswordEncoder passwordEncoder;
    private UserDAO userDAO;
    private MailSenderService mailSenderService;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before All tests");
    }

    @BeforeEach
    void setUp() {
        System.out.println("Before each test");
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userDAO = Mockito.mock(UserDAO.class);
        mailSenderService = Mockito.mock(MailSenderService.class);

        userService = new UserServiceImpl(userDAO, passwordEncoder, mailSenderService);
    }

    @AfterEach
    void afterEach(){
        System.out.println("After each test");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("After All test");
    }

    @Test
    void checkFindByName() {
        //have
        String name = "petr";
        User expectedUser = User.builder().id(1L).name(name).build();

        Mockito.when(userDAO.findFirstByName(Mockito.anyString())).thenReturn(expectedUser);

        //execute
        User actualUser = userService.findByName(name);

        //check
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser, actualUser);

    }

    @Test
    void checkFindByNameExact() {
        //have
        String name = "petr";
        User expectedUser = User.builder().id(1L).name(name).build();

        Mockito.when(userDAO.findFirstByName(Mockito.eq(name))).thenReturn(expectedUser);

        //execute
        User actualUser = userService.findByName(name);
        User rndUser = userService.findByName(UUID.randomUUID().toString());

        //check
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser, actualUser);

        Assertions.assertNull(rndUser);

    }

    @Test
    void checkSaveIncorrectPassword(){
        //have
        UserDTO userDto = UserDTO.builder()
                .password("password")
                .matchPassword("another")
                .build();

        //execute
        Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userService.save(userDto);
            }
        });

    }

    @Test
    void checkSave(){
        //have
        UserDTO userDto = UserDTO.builder()
                .username("name")
                .email("email")
                .password("password")
                .matchPassword("password")
                .build();

        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("password");

        //execute
        boolean result = userService.save(userDto);

        //check
        Assertions.assertTrue(result);
        Mockito.verify(passwordEncoder).encode(Mockito.anyString());
        Mockito.verify(userDAO).save(Mockito.any());

    }
}
