package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private int carId;

    @Column(name = "name")
    private String name;

    @Column(name = "year")
    private int year;

    @Column(name = "distance")
    private int distance;

    @Column(name = "fuel")
    private String fuel;

    @Column(name = "fuel_consumption")
    private String fuelConsumption;

    @Column(name = "price")
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", distance=" + distance +
                ", fuel='" + fuel + '\'' +
                ", fuelConsumption='" + fuelConsumption + '\'' +
                ", price=" + price +
                ", company=" + company +
                '}';
    }
}
