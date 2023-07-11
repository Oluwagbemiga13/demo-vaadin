package com.example.demo.service.simple;

import com.example.demo.dto.simple.AnswerDTO;
import com.example.demo.dto.simple.QuestionDTO;
import com.example.demo.entity.simple.Question;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.repository.simple.QuestionRepository;
import com.vaadin.flow.data.binder.Binder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService implements EntityService<Question, QuestionDTO> {

    Binder<QuestionDTO> binder = new Binder<>(QuestionDTO.class);

    private final QuestionRepository questionRepository;

    private final AnswerService answerService;

    private final QuestionMapper questionMapper;

    @Override
    public Class<QuestionDTO> getDTOClass() {
        return QuestionDTO.class;
    }

    @Override
    public Binder<QuestionDTO> getBinder() {
        return this.binder;
    }

    @Override
    public Class<Question> getEntityClass() {
        return Question.class;
    }

    @Override
    @Transactional  // Ensure the method runs in a transaction
    public QuestionDTO save(QuestionDTO questionDTO) {
//        // convert to entity and save
//        Question question = questionMapper.toEntity(questionDTO);
//        Question savedQuestion = questionRepository.save(question);
//
//        if(!questionDTO.getPossibleAnswers().isEmpty()) {
//            // save the possibleAnswers
//            for (AnswerDTO answerDTO : questionDTO.getPossibleAnswers()) {
//                answerDTO.setQuestion(questionMapper.toDto(savedQuestion)); // set the saved question
//                answerService.save(answerDTO);
//            }
//        }
//
//        // convert saved entity back to DTO and return
//        return questionMapper.toDto(savedQuestion);
        // check if a question with this name already exists
        Optional<Question> existingQuestion = questionRepository.findByName(questionDTO.getName());

        if (existingQuestion.isPresent()) {
            // if it exists, return the existing question instead of creating a new one
            return questionMapper.toDto(existingQuestion.get());
        }

        // if it doesn't exist, create a new one
        Question question = questionMapper.toEntity(questionDTO);
        Question savedQuestion = questionRepository.save(question);

        if (questionDTO.getPossibleAnswers() != null) {
            // save the possibleAnswers
            for (AnswerDTO answerDTO : questionDTO.getPossibleAnswers()) {
                answerDTO.setQuestion(questionMapper.toDto(savedQuestion)); // set the saved question
                answerService.save(answerDTO);
            }
        }

        return questionMapper.toDto(savedQuestion);
    }

    @Override
    public List<QuestionDTO> findAll() {
        return questionMapper.toDto(questionRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        questionRepository.deleteById(id);

    }

    @Override
    public void deleteAll(List<QuestionDTO> dtos) {
        questionRepository.deleteAll(questionMapper.toEntity(dtos));
    }

    @Override
    public String getEntityName() {
        return "Question";
    }

    @Override
    public Question findById(Long id) {
        Optional<Question> optional = questionRepository.findById(id);
        if (optional.isPresent()) return optional.get();
        else throw new IllegalArgumentException("ID :" + id + " does not exist");
    }

    // Implement other methods...
}
