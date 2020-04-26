package com.example.demo.service;

import com.example.demo.domain.Gender;
import com.example.demo.domain.Person;
import com.example.demo.domain.PersonRepository;
import com.example.demo.exception.NotFoundException;
import com.example.demo.service.dto.PersonDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Before
    public void setUp() throws Exception {

        personRepository.save(
                Person.builder()
                        .firstName("호석")
                        .lastName("이")
                        .gender(Gender.MALE)
                        .birthDate(LocalDate.of(1985, 2, 1))
                        .build()
        );
    }

    @After
    public void tearDown() throws Exception {

        personRepository.deleteAll();
    }

    @Test
    public void findAll() {

        //given
        personRepository.save(
                Person.builder()
                        .firstName("호진")
                        .lastName("이")
                        .gender(Gender.MALE)
                        .birthDate(LocalDate.of(1983, 7, 4))
                        .build()
        );

        //when
        List<PersonDto.Response> responseList = personService.findAll();

        //then
        assertThat(responseList.size(), is(2));
    }

    @Test
    public void findById() {

        //given
        Person person = personRepository.save(Person.builder()
                .firstName("호진")
                .lastName("이")
                .gender(Gender.MALE)
                .birthDate(LocalDate.of(1983, 7, 4))
                .build());

        //when
        PersonDto.Response response = personService.findById(person.getId());

        //then
        assertThat(response.getFirstName(), is("호진"));
        assertThat(response.getLastName(), is("이"));
        assertThat(response.getGender(), is(Gender.MALE));
        assertThat(response.getAge(), is(36L));
    }

    @Test
    public void findById_not_found() {

        //expect
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("리소스를 찾지 못했습니다.");

        //when
        personService.findById(0L);
    }

    @Test
    public void add() {

        //given
        PersonDto.Create create = new PersonDto.Create();
        create.setBirthDate(LocalDate.of(1983, 7, 4));
        create.setFirstName("호진");
        create.setLastName("이");
        create.setGender(Gender.MALE);

        //when
        PersonDto.Response response = personService.add(create);

        //then
        assertThat(personRepository.count(), is(2L));
        assertThat(response.getId(), is(notNullValue()));
    }

    @Test
    public void delete() {

        //given
        Long id = personRepository.findAll().get(0).getId();

        //when
        personService.delete(id);

        //then
        assertThat(personRepository.count(), is(0L));
    }

    @Test
    public void delete_not_found() {

        //expect
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("리소스를 찾지 못했습니다.");

        //when
        personService.delete(0L);
    }

    @Test
    public void update() {

        //given
        Long id = personRepository.findAll().get(0).getId();

        PersonDto.Update update = new PersonDto.Update();
        update.setBirthDate(LocalDate.of(1983, 7, 4));
        update.setFirstName("호진");
        update.setLastName("이");

        //when
        personService.update(id, update);

        //then
        personRepository.findById(id)
                .ifPresent(person -> {
                    assertThat(person.getFirstName(), is("호진"));
                    assertThat(person.getLastName(), is("이"));
        });
    }

    @Test
    public void update_not_found() {

        //given
        PersonDto.Update update = new PersonDto.Update();
        update.setFirstName("호진");
        update.setLastName("이");

        //expect
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("리소스를 찾지 못했습니다.");

        //when
        personService.update(0L, update);
    }
}
