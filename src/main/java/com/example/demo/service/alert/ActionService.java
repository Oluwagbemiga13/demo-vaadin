package com.example.demo.service.alert;

import com.example.demo.dto.alert.ActionDTO;
import com.example.demo.entity.alerts.Action;
import com.example.demo.mapper.ActionMapper;
import com.example.demo.repository.alert.ActionRepository;
import com.example.demo.service.simple.EntityService;
import com.vaadin.flow.data.binder.Binder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActionService implements EntityService<Action, ActionDTO> {

    private final ActionRepository actionRepository;

    private final ActionMapper actionMapper;

    @Override
    public Class<ActionDTO> getDTOClass() {
        return ActionDTO.class;
    }

    @Override
    public Binder<ActionDTO> getBinder() {
        return new Binder<>();
    }

    @Override
    public Class<Action> getEntityClass() {
        return Action.class;
    }

    @Override
    public ActionDTO save(ActionDTO dto) {
        if (dto.getId() == null) {
            Action entity = actionMapper.toEntity(dto);
            entity.setId(new Action().getId());
            actionRepository.save(entity);
            return actionMapper.toDto(entity);
        } else {
            actionRepository.save(actionMapper.toEntity(dto));
            return dto;
        }
    }

    @Override
    public List<ActionDTO> findAll() {
        return actionMapper.toDto(actionRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        actionRepository.deleteById(id);
    }

    @Override
    public void deleteAll(List<ActionDTO> dtos) {
        dtos.forEach(i -> actionRepository.delete(actionMapper.toEntity(i)));
    }

    @Override
    public String getEntityName() {
        return "Action";
    }

    @Override
    public Action findById(Long id) {
        return actionRepository.findById(id).get();
    }
}
