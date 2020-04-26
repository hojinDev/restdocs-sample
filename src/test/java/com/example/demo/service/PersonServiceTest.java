package com.example.demo.service;

import com.example.demo.domain.Gender;
import com.example.demo.domain.Person;
import com.example.demo.domain.PersonRepository;
import com.example.demo.exception.NotFoundException;
import com.example.demo.service.dto.PersonDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {

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
        assertThat(responseList).hasSize(2);
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
        assertThat(response.getFirstName()).isEqualTo("호진");
        assertThat(response.getLastName()).isEqualTo("이");
        assertThat(response.getGender()).isEqualTo(Gender.MALE);
        assertThat(response.getAge()).isEqualTo(36L);
    }

    @Test
    public void findById_not_found() {

        Throwable exception = assertThrows(NotFoundException.class, () -> personService.findById(0L));

        assertThat(exception.getMessage()).isEqualTo("리소스를 찾지 못했습니다.");
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
        assertThat(personRepository.count()).isEqualTo(2L);
        assertThat(response.getId()).isEqualTo(9L);
    }

    @Test
    public void delete() {

        //given
        Long id = personRepository.findAll().get(0).getId();

        //when
        personService.delete(id);

        //then
        assertThat(personRepository.count()).isEqualTo(0);
    }

    @Test
    public void delete_not_found() {

        Throwable exception = assertThrows(NotFoundException.class, () -> personService.delete(0L));

        assertThat(exception.getMessage()).isEqualTo("리소스를 찾지 못했습니다.");
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
                    assertThat(person.getFirstName()).isEqualTo("호진");
                    assertThat(person.getLastName()).isEqualTo("이");
                });
    }

    @Test
    public void update_not_found() {

        //given
        PersonDto.Update update = new PersonDto.Update();
        update.setFirstName("호진");
        update.setLastName("이");


        Throwable exception = assertThrows(NotFoundException.class, () -> personService.update(0L, update));

        assertThat(exception.getMessage()).isEqualTo("리소스를 찾지 못했습니다.");
    }
}
