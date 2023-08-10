package com.example.demo.dto.join;

import com.example.demo.dto.simple.OrganDTO;
import com.example.demo.dto.simple.PartDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class PartOrganDTO extends JoinItemDTO {

    private PartDTO part;
    private OrganDTO organ;

    public PartOrganDTO(PartDTO part, OrganDTO organ) {
        super();
        this.part = part;
        this.organ = organ;
        setFirstDTO(part);
        setSecondDTO(organ);
        setName(getFullName());
    }



}
