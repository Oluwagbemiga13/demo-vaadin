package com.example.demo.util;

import com.example.demo.entity.Organ;
import com.example.demo.entity.Part;
import com.example.demo.entity.Symptom;
import com.example.demo.repository.OrganRepository;
import com.example.demo.repository.PartRepository;
import com.example.demo.repository.SymptomRepository;
import com.example.demo.service.OrganSymptomService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.LongStream;

@Service
@Transactional
@Slf4j
public class DummyDataCreator {

    private final OrganRepository organRepository;
    private final SymptomRepository symptomRepository;

    private final OrganSymptomService organSymptomService;

    private final PartRepository partRepository;

    @Autowired
    public DummyDataCreator(OrganRepository organRepository, SymptomRepository symptomRepository, OrganSymptomService organSymptomService, PartRepository partRepository) {
        this.organRepository = organRepository;
        this.symptomRepository = symptomRepository;
        this.organSymptomService = organSymptomService;
        this.partRepository = partRepository;

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
                .map(name -> new Symptom(null, name,new HashSet<>(), new HashSet<>()))
                .toList();
        symptomRepository.saveAll(symptoms);

        List<Part> parts = partNames.stream()
                        .map(name -> new Part(null,name, new HashSet<>(), new HashSet<>()))
                        .toList();
        partRepository.saveAll(parts);

                // Generate organSymptoms
        LongStream.range(1L,10)
                        .forEach(l -> {
                            organSymptomService.createRelation(l, l);
                            log.info("Created relation " + l + ":" + l);
                        });
    }


}
