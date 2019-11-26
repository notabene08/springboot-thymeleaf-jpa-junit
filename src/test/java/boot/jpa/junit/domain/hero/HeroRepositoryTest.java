package boot.jpa.junit.domain.hero;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

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
    public void JpaAuditingTest() {
        // given
        LocalDateTime now = LocalDateTime.parse("2019-11-23T00:00:00");

        Hero input = Hero.builder()
                .name("github.com/notabene08")
                .age(26)
                .note("github.com/notabene08")
                .build();

        // when
        Hero output = heroRepository.save(input);

        // then
        assertTrue(output.getCreatedDate().isAfter(now));
        assertTrue(output.getModifiedDate().isAfter(now));
    }

    @Test
    public void HeroSaveRequestTest() {
        Hero input = Hero.builder()
                .name("github.com/notabene08")
                .age(26)
                .note("github.com/notabene08")
                .build();


        Hero output = heroRepository.save(input);

        assertThat(input, is(output));
    }

    @Test
    public void HeroFindAllIdResponseTest() {
        //given
        IntStream.rangeClosed(1, 10).forEach(i ->
                heroRepository.save(Hero.builder()
                        .name("github.com/notabene08")
                        .age(26)
                        .note("github.com/notabene08")
                        .build()));

        //when
        List<Hero> output = heroRepository.findAll();

        //then
        assertThat(output.size(), is(10));
    }

    @Test
    public void HeroFindByResponseTest(){
        //given
        Hero input = Hero.builder()
                .name("github.com/notabene08")
                .age(26)
                .note("github.com/notabene08")
                .build();

        heroRepository.save(input);

        //when
        Hero output = heroRepository.findById(1L).orElse(null); //exception

        //then
        assertThat(input.getName(), is(output.getName()));
        assertThat(input.getAge(), is(output.getAge()));
        assertThat(input.getNote(), is(output.getNote()));
    }

    @Test
    public void HeroUpdateRequestTest(){
        Hero input = Hero.builder()
                .name("github.com/notabene08")
                .age(25)
                .note("github.com/notabene08")
                .build();

        heroRepository.save(input);

        //when
        heroRepository.save(Hero.builder()
                .id(1L)
                .name("github.com/notabene08")
                .age(25)
                .note("github.com/notabene08")
                .build());

        //then
        Hero output = heroRepository.findById(1L).orElse(null);
        assertThat(input.getId(),is(output.getId()));
        assertThat(toStringCreateDate(input.getCreatedDate()), is(toStringCreateDate(output.getCreatedDate())));
        assertTrue(output.getModifiedDate()
                .isAfter(input.getModifiedDate()));
    }

    public String toStringCreateDate(LocalDateTime createDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Optional.ofNullable(createDate)
                .map(formatter::format)
                .orElse("");
    }
}