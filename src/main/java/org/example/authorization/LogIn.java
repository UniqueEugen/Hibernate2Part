package org.example.authorization;

import myLibrary.console.Console;
import myLibrary.input.Scan;
import org.example.entity.Person;
import org.example.entity.User;
import org.example.menu.Menu;
import org.example.service.PersonService;
import org.example.service.serviceImpl.PersonServiceImpl;

import java.util.List;

public class LogIn {
    public void authorization() {

        PersonService personService = new PersonServiceImpl();
        try {
            List<Person> people = personService.showPeople();
            if(people.isEmpty()) throw new NullPointerException();
            authorizationListNotEmpty(people);

        }catch (NullPointerException nullPointerException){
            Console.log("\n\nСписок пользователей пуст!");
        }
    }
    private void authorizationListNotEmpty(List<Person> people){
        Console.log("Введите логин: ");
        String login = Scan.stringScan();
        Console.log("Введите пароль: ");
        String password = Scan.stringScan();
        User currentUser = null;
        for(Person p : people) {
            if(p.getUser().getLogin().equals(login) && p.getUser().getPassword().equals(password)) {
                currentUser = p.getUser();
                p.setPersonId(people.size());
            }
            if (p.getUser().getLogin().equals(login) && !p.getUser().getPassword().equals(password)) {
                Console.log("Проверьте корректность пароля!");
            }
        }
        if (currentUser != null) {
            Console.log("Авторизация пройдена успешно! Добро пожаловать " +
                    currentUser.getPerson().getSurname() + " " + currentUser.getPerson().getName());
            Menu menu = new Menu();
            String role = currentUser.getRole();
            switch (role) {
                case "Admin":
                    menu.AdminMenu();
                    break;
                case "User":
                    menu.UserMenu(currentUser);
                    break;
            }
        }
        else {
            Console.log("Такого пользователя не найдено");
        }
    }
}

