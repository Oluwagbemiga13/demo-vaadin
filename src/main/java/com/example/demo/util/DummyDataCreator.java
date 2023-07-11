package com.example.demo.util;

import com.example.demo.entity.simple.Alert;
import com.example.demo.entity.simple.Organ;
import com.example.demo.entity.simple.Part;
import com.example.demo.entity.simple.Symptom;
import com.example.demo.repository.join.OrganSymptomRepository;
import com.example.demo.repository.simple.*;
import com.example.demo.service.join.OrganSymptomService;
import com.example.demo.service.simple.AlertService;
import com.example.demo.service.simple.AnswerService;
import com.example.demo.service.simple.PartService;
import com.example.demo.service.simple.QuestionService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.LongStream;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class DummyDataCreator {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final AlertService alertService;

    private final OrganRepository organRepository;
    private final SymptomRepository symptomRepository;

    private final OrganSymptomService organSymptomService;

    private final PartService partService;
    private final PartRepository partRepository;

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final AlertRepository alertRepository;

    private final OrganSymptomRepository organSymptomRepository;


    @PostConstruct
    void init() {
        generateLongLists();

        testAlertConnection();


    }

    private void generateLongLists() {
        List<String> organNames = List.of("Liver", "Stomach", "Kidneys", "Pancreas", "Brain", "Intestines",
                "Spleen", "Bladder", "Gallbladder", "Thyroid", "Adrenal glands", "Pituitary gland",
                "Thymus", "Bone marrow", "Prostate", "Testes", "Ovaries", "Breast", "Sweat glands",
                "Salivary glands", "Tonsils", "Appendix", "Sigmoid colon", "Rectum", "Anal canal",
                "Peritoneum", "Diaphragm", "Ureters", "Urethra", "Penis", "Clitoris", "Vagina",
                "Fallopian tubes", "Uterus", "Cervix", "Pleura", "Pericardium", "Spermatic cord",
                "Epididymis", "Vas deferens", "Seminal vesicles", "Uterine tubes", "Fimbriae",
                "Ovary ligament", "Round ligament", "Uterosacral ligament", "Broad ligament"
        );

        List<String> symptomNames = List.of(
                "Fever", "Headache", "Nausea", "Vomiting", "Fatigue", "Sore throat",
                "Chest pain", "Abdominal pain", "Joint pain", "Muscle pain",
                "Dizziness", "Diarrhea", "Constipation", "Bloating", "Heartburn", "Rash", "Itching",
                "Swelling", "Bruising", "Bleeding", "Sweating", "Chills", "Cramps", "Difficulty sleeping",
                "Poor appetite", "Thirst", "Weight loss", "Weight gain", "Increased urination",
                "Decreased urination", "Urgent urination", "Painful urination", "Bloody urine",
                "Blood in stool", "Black stool", "Yellow stool", "Red eyes", "Blurred vision",
                "Double vision", "Tinnitus", "Earache", "Nosebleed", "Sinus congestion",
                "Dry mouth", "Bad breath", "Mouth sores", "Tongue discoloration", "Joint swelling",
                "Back pain", "Neck pain", "Shoulder pain", "Arm pain", "Hand pain", "Leg pain",
                "Foot pain", "Toe pain", "Muscle weakness", "Tremors", "Seizures", "Memory loss",
                "Confusion", "Depression", "Anxiety", "Panic attacks", "Suicidal thoughts",
                "Sexual dysfunction", "Low libido", "Hot flashes", "Night sweats", "Irregular periods",
                "Heavy periods", "Painful periods", "Breast pain", "Breast lumps", "Breast discharge",
                "Vaginal discharge", "Vaginal itching", "Vaginal odor", "Pelvic pain", "Infertility",
                "Missed periods", "Premenstrual syndrome", "Premenstrual dysphoric disorder",
                "Erectile dysfunction", "Premature ejaculation", "Delayed ejaculation"
        );

        List<String> partNames = List.of("Head", "Neck", "Shoulder", "Arm", "Elbow", "Forearm",
                "Wrist", "Hand", "Chest", "Abdomen", "Hip", "Thigh", "Knee", "Leg",
                "Ankle", "Foot", "Spine", "Skull", "Jaw", "Face", "Ear", "Nose",
                "Mouth", "Tongue", "Teeth"
        );

        // Generate organs
        List<Organ> organs = organNames.stream()
                .map(name -> new Organ(null, name, new HashSet<>(), new HashSet<>()))
                .toList();
        organRepository.saveAll(organs);

        // Generate symptoms
        List<Symptom> symptoms = symptomNames.stream()
                .map(name -> new Symptom(null, name, new HashSet<>(), new HashSet<>()))
                .toList();
        symptomRepository.saveAll(symptoms);

        List<Part> parts = partNames.stream()
                .map(name -> new Part(null, name, new HashSet<>(), new HashSet<>()))
                .toList();
        partRepository.saveAll(parts);

        // Generate organSymptoms
        LongStream.range(1, 10)
                .forEach(l -> {
                    organSymptomService.createRelation(l, l);
                    log.info("Created relation " + l + ":" + l);
                });

        LongStream.range(1, 10)
                .forEach(l -> {
                    partService.createRelation(l, l);
                    log.info("Created relation " + l + ":" + l);
                });

    }

    public void testAlertConnection() {

        Alert alert = new Alert();
        alert.setOrganSymptom(organSymptomRepository.findById(1L).get());
        alert.setMessage("HELLO");
        alertRepository.save(alert);
        // create a question
//        Question question = new Question();
//        question.setName("Some Question?");
//        question = questionRepository.save(question); // save the question to get the ID
//        log.info("Question created with ID: {}", question.getId());
//
//        // create an answer
//        Answer answer = new Answer();
//        answer.setName("Some Answer");
//        answer.setQuestion(question); // set the question
//        answerRepository.save(answer);
//        log.info("Answer created with ID: {}", answer.getId());
//
//        // create an alert
//        Alert alert = new Alert();
//        alert.setMessage("Some Alert");
//        alert.setSeverity("High");
//        alert.setQuestion(question); // set the question
//        alertRepository.save(alert);
//        log.info("Alert created with ID: {}", alert.getId());
//        log.info("Alert contain question : {}",alert.getQuestion().getName());


    }


}
