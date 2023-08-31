package org.itstep.safetywork.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "responsibleForMachinery")
@EqualsAndHashCode(exclude = {"machineryList", "employee"})
@ToString(exclude = {"machineryList", "employee"})
public class ResponsibleForMachinery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Employee employee;
    @Column(name = "order_number")
    private String orderNumber;
    @Column(name = "date_order")
    private LocalDate dateOrder;
    @OneToMany(mappedBy = "responsibleForMachinery")
    private List<Machinery> machineryList = new ArrayList<>();

    public ResponsibleForMachinery(String orderNumber, LocalDate dateOrder) {
        this.orderNumber = orderNumber;
        this.dateOrder = dateOrder;
    }
}
