package com.example.demo.util;

import com.example.demo.entity.Organ;
import com.example.demo.entity.Symptom;
import com.example.demo.repository.OrganRepository;
import com.example.demo.repository.SymptomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DummyDataCreator {

    private final OrganRepository organRepository;
    private final SymptomRepository symptomRepository;

    @Autowired
    public DummyDataCreator(OrganRepository organRepository, SymptomRepository symptomRepository) {
        this.organRepository = organRepository;
        this.symptomRepository = symptomRepository;
        generateOrgansAndSymptoms();
    }

    public void generateOrgansAndSymptoms() {
        List<String> organNames = List.of(
                "Heart", "Lungs", "Liver", "Stomach", "Kidneys", "Pancreas", "Brain", "Intestines",
                "Spleen", "Bladder", "Gallbladder", "Thyroid", "Adrenal glands", "Pituitary gland",
                "Thymus", "Bone marrow", "Prostate", "Testes", "Ovaries", "Breast", "Sweat glands",
                "Salivary glands", "Tonsils", "Appendix", "Sigmoid colon", "Rectum", "Anal canal",
                "Peritoneum", "Diaphragm", "Ureters", "Urethra", "Penis", "Clitoris", "Vagina",
                "Fallopian tubes", "Uterus", "Cervix", "Pleura", "Pericardium", "Spermatic cord",
                "Epididymis", "Vas deferens", "Seminal vesicles", "Uterine tubes", "Fimbriae",
                "Ovary ligament", "Round ligament", "Uterosacral ligament", "Broad ligament"
        );

        List<String> symptomNames = List.of(
                "Fever", "Headache", "Nausea", "Vomiting", "Fatigue", "Cough", "Sore throat",
                "Shortness of breath", "Chest pain", "Abdominal pain", "Joint pain", "Muscle pain",
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
                .map(name -> new Organ(null, name))
                .toList();
        organRepository.saveAll(organs);

        // Generate symptoms
        List<Symptom> symptoms = symptomNames.stream()
                .map(name -> new Symptom(null, name))
                .toList();
        symptomRepository.saveAll(symptoms);
    }
}
