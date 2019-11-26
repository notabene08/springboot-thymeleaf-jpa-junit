package boot.jpa.junit.service;

import boot.jpa.junit.domain.hero.HeroRepository;
import boot.jpa.junit.dto.HeroSaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HeroServiceTest {

    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    private HeroService heroService;

    @After
    public void cleanUp() {
        heroRepository.deleteAll();
    }

    @Test
    public void HeroSaveRequestTest() {
        HeroSaveRequestDto input = HeroSaveRequestDto.builder()
                .name("github.com/notabene08")
                .age(26)
                .note("github.com/notabene08")
                .build();
        //when
        Long output = heroService.HeroSaveRequest(input);

        //then
        assertThat(output, is(1L));
    }
}