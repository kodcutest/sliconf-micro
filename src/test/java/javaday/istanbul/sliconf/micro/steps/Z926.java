package javaday.istanbul.sliconf.micro.steps;

import cucumber.api.java.Before;
import cucumber.api.java.tr.Diyelimki;
import cucumber.api.java.tr.Eğerki;
import cucumber.api.java.tr.Ozaman;
import javaday.istanbul.sliconf.micro.CucumberConfiguration;
import javaday.istanbul.sliconf.micro.builder.EventBuilder;
import javaday.istanbul.sliconf.micro.builder.UserBuilder;
import javaday.istanbul.sliconf.micro.model.User;
import javaday.istanbul.sliconf.micro.model.event.Event;
import javaday.istanbul.sliconf.micro.model.response.ResponseMessage;
import javaday.istanbul.sliconf.micro.service.UserPassService;
import javaday.istanbul.sliconf.micro.service.event.EventRepositoryService;
import javaday.istanbul.sliconf.micro.service.user.UserRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CucumberConfiguration.class})
@WebAppConfiguration
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class Z926 {

    @Autowired
    private EventRepositoryService eventRepositoryService;

    @Autowired
    private UserRepositoryService userRepositoryService;

    private Event event;
    private User user;

    private User dbUser;

    @Before
    public void init() {
        // eventRepositoryService = mock(EventRepositoryService.class);
        // userRepositoryService = mock(UserRepositoryService.class);
    }

    @Diyelimki("^etkinlik sahibi daha önceden JugEvents sistemine kayıtlıdır$")
    public void etkinlik_sahibi_daha_önceden_JugEvents_sistemine_kayıtlıdır() throws Throwable {
        user = new UserBuilder()
                .setName("Osman Uykulu")
                .setEmail("osman16@osman.com")
                .setPassword("1234!")
                .build();

        UserPassService userPassService = new UserPassService();
        user = userPassService.createNewUserWithHashedPassword(user);

        assertNotNull(user);

        userRepositoryService.save(user);
    }

    @Eğerki("^etkinlik sahibi yeni bir etkinliği etkinlik kurallarına uygun bir şekilde girerse$")
    public void etkinlik_sahibi_yeni_bir_etkinliği_etkinlik_kurallarına_uygun_bir_şekilde_girerse() throws Throwable {

        User tmpUser = new UserBuilder()
                .setName("Osman Uykulu")
                .setEmail("osman16@osman.com")
                .setPassword("1234!")
                .build();

        tmpUser.setId("user-id-123");

        // when(userRepositoryService.findByEmail(user.getEmail())).thenReturn(tmpUser);

        dbUser = userRepositoryService.findByEmail(user.getEmail());

        assertNotNull(dbUser);

        user.setId(dbUser.getId());

        event = new EventBuilder().setName("javaday 2018 - 1").
                setDate(LocalDateTime.of(2018, 5, 27, 10, 0)).
                setExecutiveUser(user.getId()).build();

        assertNotNull(event);
    }

    @Ozaman("^sistem etkinlik sahibinin yeni etkinliğini başarılı bir şekilde oluşturur\\.$")
    public void sistem_etkinlik_sahibinin_yeni_etkinliğini_başarılı_bir_şekilde_oluşturur() throws Throwable {
        ResponseMessage eventTemp = new ResponseMessage(true, "Event saved successfully!", event);

        // when(eventRepositoryService.save(event)).thenReturn(eventTemp);

        assertTrue(eventRepositoryService.save(event).isStatus());
    }
}
