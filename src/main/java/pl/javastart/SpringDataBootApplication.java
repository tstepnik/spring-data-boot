package pl.javastart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pl.javastart.model.Car;
import pl.javastart.repository.CarRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringDataBootApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(SpringDataBootApplication.class, args);


        List<Car> cars = new ArrayList<>();
        cars.add(new Car("A4", "Audi", 49000.0));
        cars.add(new Car("A5", "Audi", 67000.0));
        cars.add(new Car("Auris", "Toyota", 35000.0));
        cars.add(new Car("Insignia", "Opel", 29500.0));
        cars.add(new Car("A8", "Audi", 28000.0));
        cars.add(new Car("Corolla", "Toyota", 31000.0));
        cars.add(new Car("Vectra", "Opel", 29500.0));
        cars.add(new Car("Astra", "Opel", 29500.0));


        CarRepository carRepo = ctx.getBean(CarRepository.class);
        cars.forEach(carRepo::save); //zapisujemy samochody

        System.out.println("All Toyota cars:");
        List<Car> Alltoyotas = carRepo.findByBrand("Toyota");
        print(Alltoyotas);

        System.out.println("Find first 'Opel' :");
        Car opel = carRepo.findFirstByBrand("Opel");
        System.out.println(opel);

        System.out.println("Audi for 49000:");
        List<Car> audiCars49000 = carRepo.findAllByBrandAndPrice("Audi", 49000);
        print(audiCars49000);

        System.out.println("Toyota or Opel:");
        print(carRepo.findAllByBrandOrBrand("Toyota", "Opel"));

        System.out.println("Cars with brand starting with 'A':");
        print(carRepo.findAllByBrandLike("A%"));

        System.out.println("Cars with name ending with 'tra':");
        print(carRepo.findAllByNameEndingWith("tra"));


        System.out.println("Car cheaper than 30000:");
        print(carRepo.findAllByPriceLessThan(30000));

        System.out.println("Cars with price between 30k-40k");
        print(carRepo.findAllByPriceBetween(30000, 40000));

        System.out.println("All Audi cars, ordering ascending:");
        print(carRepo.findAllByBrandOrderByPriceAsc("Audi"));

        System.out.println("Cars with brand:");
        print(carRepo.findAllByBrandNotNull());

        System.out.println("Cars name in{Insignia,Corolla");

        List<String> carNames = Stream.of("Insignia", "Corolla").collect(Collectors.toList());
        print(carRepo.findAllByNameIn(carNames));

        ctx.close();
    }

    public static void print(List<Car> cars) {
        System.out.println();
        cars.forEach(System.out::println);
        System.out.println();
    }
}

