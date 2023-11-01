package org.example.menu;

import myLibrary.console.Console;
import myLibrary.input.Scan;
import org.example.entity.Car;
import org.example.entity.Company;
import org.example.entity.Person;
import org.example.entity.User;
import org.example.service.CompanyService;
import org.example.service.PersonService;
import org.example.service.serviceImpl.CompanyServiceImpl;
import org.example.service.serviceImpl.PersonServiceImpl;

import java.util.List;

public class MenuFunctions {
    PersonService personService = new PersonServiceImpl();
    CompanyService companyService = new CompanyServiceImpl();

    public MenuFunctions() {
    }

    //---PERSON AND USER---//

    public void addPerson() {
        Console.log("---Добавление пользователя---");
        Person person = getPersonInfo();
        if (person != null) {
            if (personService.addPerson(person)) {
                Console.log("---Добавление выполнено!---");
            }
        }
    }

    private Person getPersonInfo() {
        Person person = null;
        Console.log("Введите имя пользователя: ");
        String name = Scan.stringScan();
        Console.log("Введите фамилию пользователя: ");
        String surname = Scan.stringScan();
        Console.log("Введите возраст пользователя: ");
        Integer age = Scan.intScan();
        Console.log("Введите телефон пользователя: ");
        String phone = Scan.stringScan();
        Console.log("Введите почту пользователя: ");
        String mail = Scan.stringScan();
        //if (Validator.correctPerson(name, surname, age, phone, mail)) {
        User user = getUserInfo();
        if (user != null) {
            person = new Person(name, surname, age, phone, mail);
            user.setPerson(person);
            person.setUser(user);
        } else {
            Console.log("Пароль или логин не корректны!");
        }
      /*  }
        else {
            System.out.println("Личные данные не корректны!");
        }*/
        return person;
    }

    private User getUserInfo() {
        User user = null;
        Console.log("Введите логин пользователя: ");
        String login = Scan.stringScan();
        Console.log("Введите пароль пользователя: ");
        String password = Scan.stringScan();
        //  if(Validator.correctUser(login, password)) {
        if (checkUniqueLogin(login)) {
            user = new User(login, password, "User");
        } else {
            Console.log("Такой логин уже занят!");
        }
        //}
        return user;
    }

    private boolean checkUniqueLogin(String login) {
        boolean isUnique = true;
        try {
            for (Person p : getPeople()) {
                if (p.getUser().getLogin().equals(login)) {
                    isUnique = false;
                }
            }
        } catch (NullPointerException nullPointerException) {
            isUnique = true;
        }
        return isUnique;
    }

    public void updatePerson() {
        Console.log("---Изменение пользователя---");
        showPeople();
        Console.log("Выберите ID пользователя для изменения: ");
        Integer id = Scan.intScan();
        if (getPersonId(id)) {
            Person person = findPersonById(id);
            if (person != null) {
                changeDataFromPerson(person);
                changeDataFromUser(person);
                if (personService.updatePerson(person)) {
                    Console.log("---Изменение выполнено!---");
                }
            }
        }
    }

    private Person changeDataFromPerson(Person person) {
        Console.log("Введите имя пользователя: ");
        String name = Scan.stringScan();
        Console.log("Введите фамилию пользователя: ");
        String surname = Scan.stringScan();
        Console.log("Введите возраст пользователя: ");
        Integer age = Scan.intScan();
        Console.log("Введите телефон пользователя: ");
        String phone = Scan.stringScan();
        Console.log("Введите почту пользователя: ");
        String mail = Scan.stringScan();
        //  if (Validator.correctPerson(name, surname, age, phone, mail)) {
        person.setName(name);
        person.setSurname(surname);
        person.setAge(age);
        person.setPhone(phone);
        person.setMail(mail);
       /* }
        else {
            System.out.println("Личные данные не корректны!");
        }*/
        return person;
    }

    private Person changeDataFromUser(Person person) {

        Console.log("Введите логин пользователя: ");
        String login = Scan.stringScan();
        Console.log("Введите пароль пользователя: ");
        String password = Scan.stringScan();
        //   if(Validator.correctUser(login, password)) {
        person.getUser().setLogin(login);
        person.getUser().setPassword(password);
        // }
        return person;
    }

    public void updateLoginAndPassword(User user) {
        Console.log("---Изменение логина и пароля---");
        changeDataFromUser(user.getPerson());
        if (personService.updatePerson(user.getPerson())) {
            Console.log("---Изменение выполнено!---");
        }

    }

    public void deletePerson() {
        Console.log("---Удаление пользователя---");
        showPeople();
        Console.log("Выберите ID пользователя для изменения: ");
        Integer id = Scan.intScan();
        if (getPersonId(id)) {
            if (personService.deletePerson(id)) {
                System.out.println("---Удаление выполнено!---");
            }
        }
    }

    private boolean getPersonId(Integer id) {
        boolean isAppropriateNumber = false;
        //   if (Validator.correctId(id)) {
        if (!(id < 0) && !(id > getPeople().size())) {
            isAppropriateNumber = true;
        } else {
            Console.log("Такого ID нет!");
        }
       /* }
        else {
            System.out.println("ID не корректно!");
        }*/
        return isAppropriateNumber;
    }

    public void showPeople() {
        List<Person> people = getPeople();
        if (people.size() != 0) {
            System.out.format("%10s%20s%20s%10s%20s%30s%20s", "ID |", "Имя |", "Фамилия |", "Возраст |", "Телефон |", "Почта |", "Логин |");
            for (Person p : people) {
                System.out.println(" ");
                System.out.format("%10s%20s%20s%10s%20s%30s%20s", p.getPersonId() + " |", p.getName() + " |",
                        p.getSurname() + " |", p.getAge() + " |",
                        p.getPhone() + " |", p.getMail() + " |", p.getUser().getLogin() + " |");

            }
            Console.log(" ");
        } else {
            Console.log("Нет пользователей!");
        }
    }

    private List<Person> getPeople() {
        List<Person> people = personService.showPeople();
        return people;
    }

    private Person findPersonById(int id) {
        Person person = personService.findPersonById(id);
        return person;
    }

    //---COMPANY---//

    public String addCompany() {
        Console.log("---Добавление компании---");
        String result = null;
        Console.log("Введите название компании: ");
        String name = Scan.stringScan();
        Console.log("Введите страну происхождения компании: ");
        String country = Scan.stringScan();
        //  if (Validator.correctCompany(name, country)) {
        Company company = new Company(name, country);
        if (companyService.addCompany(company)) {
            result = "---Добавление выполнено!---";
        }
      /*  }
        else {
            result = "Данные не корректны!";
        }*/
        return result;
    }

    public String updateCompany() {
        String result = null;
        Console.log("---Изменение компании---");
        showCompanies();
        Console.log("Выберите ID компании для изменения: ");
        Integer id = Scan.intScan();
        if (getCompanyId(id)) {
            Console.log("Введите название компании: ");
            String name = Scan.stringScan();
            Console.log("Введите страну происхождения компании: ");
            String country = Scan.stringScan();
            //     if (Validator.correctCompany(name, country)) {
            Company company = companyService.findCompanyById(id);
            company.setCompanyName(name);
            company.setCompanyCountry(country);
            if (companyService.updateCompany(company)) {
                Console.log("---Изменение выполнено!---");
            }
          /*  }
            else {
                System.out.println("Данные не корректны!");
            }*/
        }

        return result;
    }

    public void deleteCompany() {
        Console.log("---Удаление компании---");
        showCompanies();
        Console.log("Введите ID компании для удаления: ");
        Integer id = Scan.intScan();
        if (getCompanyId(id)) {
            if (companyService.deleteCompany(id)) {
                Console.log("---Удаление выполнено!---");
            }
        }
    }

    private boolean getCompanyId(Integer id) {
        boolean isAppropriateNumber = false;
        //   if (Validator.correctId(id)) {
        if (!(id < 0) && !(id > getCompanies().size())) {
            isAppropriateNumber = true;
        } else {
            Console.log("Такого ID нет!");
        }
       /* }
        else {
            System.out.println("ID не корректно!");
        }*/
        return isAppropriateNumber;
    }

    public void showCompanies() {
        List<Company> companies = getCompanies();
        if (companies.size() != 0) {
            theHeaderForCompany();
            for (Company c : companies) {
                theTableForCompany(c);
            }
            Console.log(" ");
        } else {
            Console.log("Нет компаний!");
        }
    }

    private void theTableForCompany(Company c) {
        Console.log(" ");
        System.out.format("%10s%20s%30s", c.getCompanyId() + " |", c.getCompanyName() + " |", c.getCompanyCountry() + " |");
        Console.log(" ");
    }

    private void theHeaderForCompany() {
        System.out.format("%10s%20s%30s", " ID |", "Название |", "Страна происхождения |");
    }

    private List<Company> getCompanies() {
        List<Company> companies = companyService.showCompanies();
        return companies;
    }

    public void showOneCompany() {
        Company company = findCompanyByName();
        if (company != null) {
            theHeaderForCompany();
            theTableForCompany(company);
        }
    }

    private Company findCompanyByName() {
        Console.log("Введите название компании: ");
        String name = Scan.stringScan();
        boolean isFound = false;
        for (Company c : getCompanies()) {
            if (c.getCompanyName().equals(name)) {
                isFound = true;
            }
        }
        Company company = null;
        if (isFound) {
            company = companyService.findCompanyByName(name);
        } else {
            Console.log("Такой компании не найдено!");
        }
        return company;
    }

    //---CAR---//

    public void addCar() {
        Console.log("---Добавление машины---");
        showCompanies();
        Console.log("Выберите ID компании для авто: ");
        Integer id = Scan.intScan();
        Car car = getCarInfo();
        if (car != null) {
            Company company = companyService.findCompanyById(id);
            car.setCompany(company);
            company.addCar(car);
            if (companyService.updateCompany(company)) {
                Console.log("---Добавление выполнено!---");
            }
        }
    }

    private Car getCarInfo() {
        Console.log("Введите название: ");
        String name = Scan.stringScan();
        Console.log("Введите год создания: ");
        Integer year = Scan.intScan();
        Console.log("Введите пробег: ");
        Integer distance = Scan.intScan();
        Console.log("Введите вид топлива: ");
        String fuel = Scan.stringScan();
        Console.log("Введите расход: ");
        String fuelConsumption = Scan.stringScan();
        Console.log("Введите цену: ");
        Integer price = Scan.intScan();
        // if (Validator.correctCar(name, year, distance, fuel, fuelConsumption, price)) {
        //     if (Validator.correctFuel(fuel)) {
        Car car = new Car();
        car.setName(name);
        car.setYear(year);
        car.setDistance(distance);
        car.setFuel(fuel);
        car.setFuelConsumption(fuelConsumption);
        car.setPrice(price);
        return car;
          /*  }
            else {
                System.out.println("Введите топливо: Бензин или Дизель!");
            }*/
       /* }
        else {
            System.out.println("Данные не корректны!");
        }*/
    }

    public void deleteCar() {
        Console.log("---Удаление машины---");
        showCars();
        Company company = findCompanyByName();
        if (company != null) {
            Console.log("Введите название машины: ");
            String name = Scan.stringScan();
            Car car = findCarInList(company, name);
            if (car != null) {
                company.getCars().remove(car);
                if (companyService.updateCompany(company)) {
                    Console.log("---Удаление выполнено!---");
                }
            }
        }
    }

    public void updateCar() {
        Console.log("---Изменение машины---");
        showCars();
        Company company = findCompanyByName();
        if (company != null) {
            Console.log("Введите название машины: ");
            String nameForChange = Scan.stringScan();
            Car car = findCarInList(company, nameForChange);
            if (car != null) {
                Console.log("Введите название: ");
                String name = Scan.stringScan();
                Console.log("Введите год создания: ");
                Integer year = Scan.intScan();
                Console.log("Введите пробег: ");
                Integer distance = Scan.intScan();
                Console.log("Введите вид топлива: ");
                String fuel = Scan.stringScan();
                Console.log("Введите расход: ");
                String fuelConsumption = Scan.stringScan();
                Console.log("Введите цену: ");
                Integer price = Scan.intScan();
              /*  if (Validator.correctCar(name, year, distance, fuel, fuelConsumption, price)) {
                    if (Validator.correctFuel(fuel)) {*/
                car.setName(name);
                car.setYear(year);
                car.setDistance(distance);
                car.setFuel(fuel);
                car.setFuelConsumption(fuelConsumption);
                car.setPrice(price);
            } else {
                Console.log("Введите топливо: Бензин или Дизель!");
            }
               /* }
                else {
                    System.out.println("Данные не корректны!");
                }
                if (companyService.updateCompany(company)) {
                    System.out.println("---Изменение выполнено!---");
                }*/
            // }
        }
    }

    public void findCarByName() {
        Console.log("Введите название машины: ");
        String name = Scan.stringScan();
        Car car = null;
        for (Company company : getCompanies()) {
            car = findCarInList(company, name);
            if (car != null) {
                theHeaderForCar();
                theTableForCar(car);
            }
        }
        Console.log(" ");
    }

    private Car findCarInList(Company company, String name) {
        Car car = null;
        if (!company.getCars().isEmpty()) {
            for (Car c : company.getCars()) {
                if (c.getName().equals(name)) {
                    car = c;
                }
            }
            if (car == null) {
                Console.log("Такой машины не найдено в компании " + company.getCompanyName());
            }

        }
        return car;
    }

    public void showCarsFromOneCompany() {
        showCompanies();
        Company company = findCompanyByName();
        if (company != null) {
            if (!company.getCars().isEmpty()) {
                theHeaderForCar();
                for (Car c : company.getCars()) {
                    theTableForCar(c);
                }
                Console.log(" ");
            } else {
                Console.log("Компания " + company.getCompanyName() + " не имеет моделей!");
            }
        }
    }

    public void showCars() {
        List<Company> companies = getCompanies();
        if (companies.size() != 0) {
            theHeaderForCar();
            for (Company c : companies) {
                List<Car> cars = c.getCars();
                if (!cars.isEmpty()) {
                    for (Car car : cars) {
                        theTableForCar(car);
                    }
                    Console.log(" ");
                }

            }
        } else {
            Console.log("Нет пользователей!");
        }
    }

    private void theHeaderForCar() {
        System.out.format("%15s%20s%10s%10s%10s%15s%10s%20s", "ID |", "Название |", "Год |", "Пробег |", "Топливо |",
                "Расход топлива |", "Цена |", " Компания|");
    }

    private void theTableForCar(Car car) {
        Console.log(" ");
        System.out.format("%15s%20s%10s%10s%10s%16s%10s%20s", car.getCarId() + " |", car.getName() + " |",
                car.getYear() + " |", car.getDistance() + " |", car.getFuel() + " |",
                car.getFuelConsumption() + " |", car.getPrice() + " |", car.getCompany().getCompanyName() + " |");
    }
}

