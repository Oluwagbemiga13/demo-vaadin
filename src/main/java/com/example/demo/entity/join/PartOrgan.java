package com.example.demo.entity.join;

import com.example.demo.entity.simple.Organ;
import com.example.demo.entity.simple.Part;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "part_organ")
public class PartOrgan extends JoinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "part_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Part part;

    @ManyToOne
    @JoinColumn(name = "organ_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Organ organ;

}

