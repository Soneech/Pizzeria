package org.soneech.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_data")
@Getter
@Setter
@NoArgsConstructor
public class OrderData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pizza_id")
    private Pizza pizza;

    private Integer count;
    private Integer cost;
}
