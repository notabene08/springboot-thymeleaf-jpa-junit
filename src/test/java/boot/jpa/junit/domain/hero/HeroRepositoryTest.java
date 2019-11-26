package boot.jpa.junit.domain.hero;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HeroRepositoryTest {

    @Autowired
    HeroRepository heroRepository;

    @After
    public void cleanup() {
        heroRepository.deleteAll();
    }

    @Test
    public void JpaAuditingTest(){
        LocalDateTime now = LocalDateTime.parse("2019-11-23T00:00:00");

        Hero input = Hero.builder()
                .name("github.com/notabene08")
                .age(25)
                .note("github.com/notabene08")
                .build();

        Hero output = heroRepository.save(input);

        assertTrue(output.getCreatedDate().isAfter(now));
        assertTrue(output.getModifiedDate().isAfter(now));
    }





}