package com.example.demo.service.simple;

import com.example.demo.dto.simple.AnswerDTO;
import com.example.demo.entity.simple.Answer;
import com.example.demo.mapper.AnswerMapper;
import com.example.demo.repository.simple.AnswerRepository;
import com.vaadin.flow.data.binder.Binder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService implements EntityService<Answer, AnswerDTO> {

    private final AnswerRepository answerRepository;

    private final AnswerMapper answerMapper;

    @Override
    public Class<AnswerDTO> getDTOClass() {
        return AnswerDTO.class;
    }

    @Override
    public Binder<AnswerDTO> getBinder() {
        return new Binder<>(AnswerDTO.class);
    }

    @Override
    public Class<Answer> getEntityClass() {
        return Answer.class;
    }

    @Override
    @Transactional
    public AnswerDTO save(AnswerDTO answerDTO) {
        // check if answer with this name already exists
        Optional<Answer> optionalAnswer = answerRepository.findByName(answerDTO.getName());

        if (optionalAnswer.isPresent()) {
            // if it exists, return the existing answer instead of creating a new one
            return answerMapper.toDto(optionalAnswer.get());
        }

        // if it doesn't exist, create a new one
        Answer answer = answerMapper.toEntity(answerDTO);
        Answer savedAnswer = answerRepository.save(answer);

        return answerMapper.toDto(savedAnswer);
    }

    @Override
    public List<AnswerDTO> findAll() {
        return answerMapper.toDto(answerRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        answerRepository.deleteById(id);
    }

    @Override
    public void deleteAll(List<AnswerDTO> dtos) {
        answerRepository.deleteAll(answerMapper.toEntity(dtos));
    }

    @Override
    public String getEntityName() {
        return "Answer";
    }

    @Override
    public Answer findById(Long id) {
        Optional<Answer> optional = answerRepository.findById(id);
        if (optional.isPresent())return optional.get();
        else throw new IllegalArgumentException("ID :" + id + " does not exist");
    }

    // Implement other methods...
}
