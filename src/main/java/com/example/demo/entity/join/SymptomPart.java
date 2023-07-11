package com.example.demo.entity.join;


import com.example.demo.entity.simple.Part;
import com.example.demo.entity.simple.Symptom;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "symptom_part")
public class SymptomPart extends JoinEntity {

    @ManyToOne
    @JoinColumn(name = "part_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Part part;

    @ManyToOne
    @JoinColumn(name = "symptom_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Symptom symptom;
}

