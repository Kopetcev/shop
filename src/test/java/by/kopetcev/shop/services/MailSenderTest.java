package by.kopetcev.shop.services;

import by.kopetcev.shop.model.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest(classes = {MailSender.class})
class MailSenderTest {

    @MockBean
    private  JavaMailSender mailSenderMock;

    @Autowired
    private MailSender service;

    @Test
    void shouldISend() {
        User user = new User(2L, "User", "$2y$12$6LEfdRapKUiv/w3/Mu2bYuyOFy5pnmx1pMDKjIrJdoO/57gP73iae", Role.ROLE_USER,
                "User@mail.com", Status.ACTIVE);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("e.kopetc@yandex.ru");
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Title");
        mailMessage.setText("Hello");

        service.send(user, "Title", "Hello");
        Mockito.verify(mailSenderMock, Mockito.times(1)).send(mailMessage);
    }

}