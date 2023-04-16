package com.example.demo.util;

import com.example.demo.entity.Organ;
import com.example.demo.entity.Symptom;
import com.example.demo.repository.OrganRepository;
import com.example.demo.repository.SymptomRepository;
import com.example.demo.service.OrganSymptomService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
@Slf4j
public class DummyDataCreator {

    private final OrganRepository organRepository;
    private final SymptomRepository symptomRepository;

    private final OrganSymptomService organSymptomService;

    @Autowired
    public DummyDataCreator(OrganRepository organRepository, SymptomRepository symptomRepository, OrganSymptomService organSymptomService) {
        this.organRepository = organRepository;
        this.symptomRepository = symptomRepository;
        this.organSymptomService = organSymptomService;

        generateLongLists();
        //createDummyOrganWithSymptoms();
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
        // Generate organs
        List<Organ> organs = organNames.stream()
                .map(name -> new Organ(null, name, new HashSet<>()))
                .toList();
        organRepository.saveAll(organs);

        // Generate symptoms
        List<Symptom> symptoms = symptomNames.stream()
                .map(name -> new Symptom(null, name,new HashSet<>()))
                .toList();
        symptomRepository.saveAll(symptoms);
    }

//    @org.springframework.transaction.annotation.Transactional
//    public void createDummyOrganWithSymptoms() {
//        Organ organ1 = new Organ(null, "Heart");
//        Organ organ2 = new Organ(null, "Lungs");
//        organRepository.saveAll(List.of(organ1,organ2));
//        List<Organ> organsFromDB = organRepository.findAll();
//
//        Symptom symptom1 = new Symptom(null, "Cough");
//        Symptom symptom2 = new Symptom(null, "Shortness of breath");
//        symptomRepository.saveAll(List.of(symptom1,symptom2));
//        List<Symptom> symptomsFromDB = symptomRepository.findAll();
//
////        OrganWithSymptoms organWithSymptoms1 = new OrganWithSymptoms(null, organsFromDB.get(0).getName(), Set.of(new SymptomWithOrgans(symptomsFromDB.get(0), organsFromDB.get(0))));
////        OrganWithSymptoms organWithSymptoms2 = new OrganWithSymptoms(null, organsFromDB.get(1).getName(), Set.of(new SymptomWithOrgans(symptomsFromDB.get(1), organsFromDB.get(1))));
////
////        List<OrganWithSymptoms> organWithSymptomsList = new ArrayList<>();
////        organWithSymptomsList.add(organWithSymptoms1);
////        organWithSymptomsList.add(organWithSymptoms2);
////
////        organWithSymptomsRepository.saveAll(organWithSymptomsList);
//    }


}
