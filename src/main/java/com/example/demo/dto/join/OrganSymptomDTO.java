package com.example.demo.dto.join;

import com.example.demo.dto.simple.OrganDTO;
import com.example.demo.dto.simple.PartDTO;
import com.example.demo.dto.simple.SymptomDTO;

public class OrganSymptomDTO extends JoinItemDTO{
    private OrganDTO organDTO;
    private SymptomDTO symptomDTO;

    public OrganSymptomDTO(OrganDTO organDTO, SymptomDTO symptomDTO) {
        super();
        this.organDTO = organDTO;
        this.symptomDTO = symptomDTO;
        setFirstDTO(organDTO);
        setSecondDTO(symptomDTO);
        setName(getFullName());
    }
}
