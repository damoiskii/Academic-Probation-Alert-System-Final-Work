/*
----------------------------------------------------------------------------------
                    Module: Artificial Intelligence (CMP4011)
                    Lab Tutor: Mr. Howard James
                    Class Group: Tuesdays @6pm
                    Year: 2023/2024 Semester 2
                    Assessment: Programming Group Project
                    Group Members:
                        Damoi Myers - 1703236
----------------------------------------------------------------------------------
*/

package com.app.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

// This class is to randomly assign a school and programme for a student
public class SchoolUtils {
        public static String SCHOOL;
        public static String PROGRAMME;

        // Get random result (grade)
        /*public static String getRandomGrade(){
                Random random = new Random();

                String[] grades = {
                        "A+", "A", "A-",
                        "B+", "B", "B-",
                        "C+", "C", "C-",
                        "D+", "D", "D-",
                        "E+", "E", "E-",
                        "F",
                };

                try {
                        return grades[random.nextInt(grades.length)];
                } catch (Exception e) {
                        return grades[0];
                }
        }*/

        // Get random result (grade point)
        public static double getRandomGradePoint(){
                Random random = new Random();

                double[] grades = {
                        4.30, 4.00, 3.70,
                        3.40, 3.10, 2.80,
                        2.50, 2.20, 1.90,
                        1.60, 1.30, 1.00,
                        0.70, 0.40, 0.10,
                        0.00,
                };

                try {
                        return grades[random.nextInt(grades.length)];
                } catch (Exception e) {
                        return grades[0];
                }
        }

        public static void pick() {
                randomlyPickSchool();
        }

        // Get Schools
        public static List<String> schools() {
                return List.of(
                                "Caribbean School of Nursing", "School of Allied Health & Wellness",
                                "School of Pharmacy", // (COHS)
                                "School of Business Administration", "School of Hospitality & Tourism Management",
                                "Joan Duncan School of Entrepreneurship, Ethics & Leadership",
                                "UTech/JIM School of Advanced Management", // (COBAM)
                                "School of Technical & Vocational Education", "School of Humanities & Social Sciences", // (FELS)
                                "School of Building & Land Management", "Caribbean School of Architecture", // (FOBE)
                                "School of Engineering", "School of Computing & Information Technology", // (SCIT)
                                "School of Natural & Applied Sciences", "School of Mathematics & Statistics",
                                "Caribbean School of Sport Sciences",
                                "Centre for Science-based Research, Entrepreneurship & Continuing Studies" // (FOSS)
                );
        }

        // Get Programmes by Schools
        private static Map<String, List<String>> schoolsWithProgrammes() {
                Map<String, List<String>> schools = new HashMap<>();

                // Define the schools and the associated programmes
                schools.put("School of Business Administration",
                                List.of("Bachelor of Business Administration (BBA)",
                                                "Bachelor of Science (BSc) in Accounting",
                                                "Bachelor of Science (BSc) in Administrative Management",
                                                "Bachelor of Science (BSc) in Economics"));

                schools.put("Joan Duncan School of Entrepreneurship",
                                List.of("Bachelor of Science (BSc) in Entrepreneurship"));

                schools.put("School of Hospitality & Tourism Management",
                                List.of("Bachelor of Science (BSc) in Food Service",
                                                "Bachelor of Science in Hospitality & Tourism",
                                                "Associate of Science in Baking and Pastry Arts"));

                schools.put("School of Allied Health & Wellness",
                                List.of("Bachelor of Science (BSc) in Medical Technology",
                                                "Bachelor of Science(BSc) in Medical Technology",
                                                "Bachelor of Science (BSc) in Child and Adolescent",
                                                "Bachelor of Science (BSc) in Dietetics & Nutrition",
                                                "Bachelor of Health Sciences",
                                                "Associate of Science Degree in Health Information Technology (Post RN)",
                                                "Certificate in Child & Adolescent Development"));

                schools.put("Caribbean School of Nursing",
                                List.of("Bachelor of Science (BSc) in Critical Care Nursing",
                                                "Bachelor of Science (BSc) in Nursing",
                                                "Bachelor of Science (BSc) in Nursing (Post RN)",
                                                "Bachelor of Science (BSc) in Midwifery (RN)",
                                                "Bachelor of Science (BSc) in Midwifery (RM)",
                                                "Certificate in Critical Care (CC) Nursing (Post RN)"));

                schools.put("School of Pharmacy",
                                List.of("Bachelor of Pharmacy", "Bachelor of Pharmacy (B.Pharm)",
                                                "Bachelor of Science in Pharmaceutical Technology (BSc Pharm Tech.)",
                                                "Pharmacy Technician Certificate"));

                schools.put("College of Oral Health Sciences",
                                List.of("Doctor of Medical Dentistry",
                                                "Bachelor of Science (BSc) in Dental Laboratory Technology",
                                                "Diploma in Dental Assisting (Expanded function)"));

                schools.put("School of Public Health & Health Technology",
                                List.of("Bachelor of Science (BSc)in Environmental Health",
                                                "Bachelor of Science (BSc) in Occupational Health, Safety & Environmental Management",
                                                "Bachelor of Science (BSc) in Public Health Nursing (Post-Basic)",
                                                "Diploma in Meats & Other Foods Inspection"));

                schools.put("School of Technical & Vocational Education",
                                List.of("Bachelor of Arts (BA) in Apparel Design, Production & Management",
                                                "Bachelor of Education in Technical and Vocational Education and Training (BEd TVET) in Business & Computer Studies",
                                                "Bachelor of Education in in Technical and Vocational Education and Training (BEd TVET) in Food Service Production and Management",
                                                "Bachelor of Education in Technical and Vocational Education and Training (BEd TVET) Specialization in Industrial Technology",
                                                "Bachelor of Science (BSc) in Entertainment Design, Production and Technology",
                                                "Associate of Arts in Image Consulting and Fashion Styling",
                                                "Associate of Science in Entertainment Design, Production and Technology"));

                schools.put("School of Humanities & Social Sciences",
                                List.of("Bachelor of Arts (BA) in Communication Arts & Technology"));

                schools.put("School of Computing & Information Technology",
                                List.of("Bachelor of Science (BSc) in Animation Production and Development",
                                                "Bachelor of Science (BSc) in Applied Artificial Intelligence",
                                                "Bachelor of Science (BSc) in Computer Information",
                                                "Bachelor of Science (BSc) in Computing",
                                                "Bachelor of Science (BSc) in Computer Network and Security",
                                                "Bachelor of Science (BSc) in Gaming"));

                schools.put("School of Engineering",
                                List.of("Bachelor of Engineering (B.Eng) in Agricultural Engineering",
                                                "Bachelor of Engineering (B.Eng.) in Chemical Engineering",
                                                "Bachelor of Engineering (BEng) in Civil Engineering",
                                                "Bachelor of Engineering (BEng) in Electrical and Computer Engineering",
                                                "Bachelor of Engineering (BEng) in Industrial Engineering",
                                                "Bachelor of Engineering (BEng) in Mechanical Engineering",
                                                "Diploma in Electrical Engineering",
                                                "Diploma in Mechanical Engineering"));

                schools.put("Faculty of Law",
                                List.of("Bachelor of Laws (LLB)"));

                schools.put("Caribbean School of Sport Sciences",
                                List.of("Bachelor of Science (BSc) in Sport Sciences"));

                schools.put("School of Mathematics & Statistics",
                                List.of("Bachelor of Science (BSc) in Actuarial Science",
                                                "Bachelor of Science (BSc) in Applied Statistics",
                                                "Bachelor of Science (BSc) in Mathematics & Education"));

                schools.put("School of Natural & Applied Sciences",
                                List.of("Bachelor of Science (BSc) in Applied Science",
                                                "Bachelor of Science (BSc) in Science and Education"));

                schools.put("Caribbean School of Architecture",
                                List.of("Bachelor of Arts in Architectural Studies (BAAS)"));

                schools.put("School of Building & Land Management",
                                List.of("Bachelor of Engineering (BEng) in Construction Engineering",
                                                "Bachelor of Science (BSc) in Construction Management (Post Diploma)",
                                                "Bachelor of Science (BSc) in Land Surveying and Geographic Information Sciences",
                                                "Bachelor of Science (BSc) in Quantity Surveying",
                                                "Bachelor of Science (BSc) in Mines and Quarry Management",
                                                "Bachelor of Science (BSc) in Real Estate Management & Valuation",
                                                "Bachelor of Science (BSc) in Urban and Regional Planning",
                                                "Associate Degree in Surveying and Geographic Information Technology",
                                                "Diploma in Construction Management",
                                                "Diploma in Structural Engineering"));

                return schools;
        }

        // Set school and programme
        private static void randomlyPickSchool() {
                Random random = new Random();

                List<String> schools = schools();
                Map<String, List<String>> schoolsWithProgrammes = schoolsWithProgrammes();

                // Get school
                try {
                        SCHOOL = schools.get(random.nextInt(schools.size()));
                } catch (Exception e) {
                        SCHOOL = schools.get(0);
                }

                // Get programme
                List<String> programmes = schoolsWithProgrammes.getOrDefault(SCHOOL, null);

                if (programmes != null) {
                        try {
                                PROGRAMME = programmes.get(random.nextInt(programmes.size()));
                        } catch (Exception e) {
                                PROGRAMME = programmes.get(0);
                        }
                }
        }

        public static String[] schoolInfo() {
                randomlyPickSchool();

                return new String[] { SCHOOL, PROGRAMME };
        }

        public static List<String> getProgrammes(String school) {
                Map<String, List<String>> schoolsWithProgrammes = schoolsWithProgrammes();

                // Get programme
                return schoolsWithProgrammes.getOrDefault(school, null);
        }

        // Get Random Courses
        public static String getRandomCourse() {
                Random random = new Random();

                List<String> courses = courses();

                try {
                        return courses.get(random.nextInt(courses.size()));
                } catch (Exception e) {
                        return courses.get(0);
                }
        }

        // Get Courses
        public static List<String> courses() {
                return List.of("Introduction to Psychology", "Principles of Economics", "World History",
                                "Organic Chemistry", "Calculus I", "Microbiology", "Political Science",
                                "English Literature", "Environmental Science", "Introduction to Computer Science",
                                "Astrophysics", "Marketing Principles", "Cultural Anthropology", "Linear Algebra",
                                "Creative Writing", "Cell Biology", "Public Speaking", "International Relations", "Statistics",
                                "Business Ethics", "Philosophy of Mind", "Human Anatomy", "Sociology of Gender",
                                "Geology and Earth Sciences", "Introduction to Linguistics", "Financial Accounting",
                                "Genetics", "Film Studies", "Macroeconomics", "Human Nutrition", "Spanish Language and Culture",
                                "Data Structures and Algorithms", "Comparative Literature", "Civil Engineering",
                                "Medical Ethics", "Quantum Mechanics", "Marketing Research", "Social Psychology",
                                "Political Philosophy", "Business Law", "Environmental Ethics", "Robotics Engineering",
                                "Classical Mythology", "Art History", "Public Health", "Game Design", "Music Theory",
                                "Developmental Psychology", "Cybersecurity", "French Literature", "Electrical Engineering",
                                "Anatomy and Physiology", "Managerial Accounting", "Molecular Biology", "Introduction to Philosophy",
                                "Economics of Globalization", "Environmental Geology", "Computer Networks",
                                "Social and Cultural Anthropology", "Discrete Mathematics", "Fiction Writing", "Neuroscience",
                                "Human Geography", "Business Communication", "Modern Physics", "Consumer Behavior",
                                "Social Work and Welfare", "Ancient History", "Probability and Statistics",
                                "Corporate Finance", "Ethics in Technology", "Artificial Intelligence", "Women's Studies",
                                "Structural Engineering", "Healthcare Management", "Human Resource Management",
                                "International Economics", "Environmental Sociology", "Programming in Java",
                                "Poetry Writing", "Immunology", "Abnormal Psychology", "Comparative Politics",
                                "Marketing Strategy", "Philosophy of Science", "Cell and Molecular Biology",
                                "Film Production", "Advanced Macroeconomics", "Nutrition and Dietetics",
                                "Italian Language and Culture", "Operating Systems", "Postcolonial Literature",
                                "Transportation Engineering", "Medical Sociology", "Astrophotography", "Music History",
                                "Educational Psychology", "Cryptography", "Spanish Literature", "Data Mining",
                                "Artificial Neural Networks", "Environmental Policy", "Cognitive Neuroscience",
                                "Business Analytics", "International Law", "Ancient Philosophy", "Structural Geology",
                                "Global Health", "Database Management", "Social Media Marketing", "Personality Psychology",
                                "Ethical Hacking", "International Business", "Contemporary Art", "Community Health",
                                "Computer Graphics", "French Language and Culture", "Operations Management",
                                "Population Ecology", "Forensic Psychology", "Macroeconomic Theory",
                                "Business Ethics in the Digital Age", "Environmental Engineering", "Modern Linguistics",
                                "Financial Markets", "Entrepreneurship", "Comparative Religion", "Control Systems Engineering",
                                "Health Informatics", "Spanish Linguistics", "Algorithms and Complexity",
                                "Human Rights Law", "Middle Eastern Studies", "Network Security", "Cultural Geography",
                                "Global Marketing", "Introduction to Archaeology", "Music Composition", "Educational Technology",
                                "Digital Media Production", "Human-Computer Interaction", "Economic Development",
                                "Gender and Society", "History of Science", "Biomedical Ethics", "Environmental Chemistry",
                                "Strategic Management", "International Relations Theory", "Art and Technology",
                                "Family Studies", "Machine Learning", "Japanese Language and Culture", "Fluid Mechanics",
                                "Public Relations", "Medieval Literature", "Biostatistics", "Social Theory",
                                "Network Programming", "Russian Literature", "Managerial Economics", "Marine Biology",
                                "Visual Arts", "Cross-Cultural Communication", "Game Development", "Classical Archaeology",
                                "Music Education", "Educational Leadership", "Digital Marketing", "Philosophy of Religion",
                                "Environmental Economics", "Web Development", "Developmental Biology", "International Finance",
                                "Business Innovation", "Cognitive Psychology", "Criminal Justice", "History of Art",
                                "Environmental Law", "Industrial Engineering", "Chinese Language and Culture", "Computer Organization and Architecture",
                                "Health Psychology", "International Marketing", "Introduction to Political Philosophy",
                                "Environmental Ethics", "Quantum Physics", "Media Studies", "Sustainable Design",
                                "Social Media Analysis", "Operations Research", "Human Evolution", "Ancient Languages (Latin/Greek)",
                                "Digital Illustration", "Microeconomics", "Comparative Cinema Studies", "Microbial Ecology",
                                "Strategic Planning", "International Human Rights", "Jazz Studies", "Educational Assessment",
                                "Information Security", "French Cinema", "Data Science", "Philosophy of Language",
                                "Climate Change Science", "Financial Accounting Theory", "Organizational Behavior",
                                "Environmental Sociology", "Robotics Programming", "International Development", "Advanced Poetry Writing",
                                "Game Theory", "Spanish Translation", "Human-Animal Studies", "Computer Vision",
                                "European History", "Social Entrepreneurship", "Environmental Physics", "Marketing Communication",
                                "Linguistic Anthropology", "Financial Management", "Environmental Management", "International Negotiation",
                                "Music Production", "Educational Policy", "Database Systems", "French Poetry",
                                "Molecular Genetics", "Political Economy", "Art and Architecture", "Healthcare Ethics",
                                "Linear Programming", "Human Osteology", "Cybersecurity Policy", "Public Health Nutrition",
                                "Modern Dance", "Cultural Heritage Studies", "Business Intelligence", "Cognitive Science",
                                "Environmental Planning", "Spanish Cinema", "Advanced Algorithms", "Social Stratification",
                                "Social Network Analysis", "Artificial Life", "Human Physiology", "Business Process Management",
                                "International Security", "Contemporary Photography", "Educational Psychology", "Computer Animation",
                                "Medieval History", "Climate Change Policy", "Managerial Psychology", "International Trade",
                                "Evolutionary Psychology", "Modern Philosophy", "Environmental Ethics", "Machine Translation", "Advanced Astrophysics",
                                "Business Negotiation", "Public Administration", "Health Communication", "Network Design and Management",
                                "Cognitive Neuroscience", "Comparative Literature", "Environmental Chemistry", "Financial Modeling",
                                "Organizational Communication", "Political Ecology", "Digital Storytelling",
                                "Human Development", "International Business Law", "Genetics and Society", "Environmental Biology",
                                "Operations and Supply Chain Management", "Public International Law", "Philosophy of Technology", "Business Analytics",
                                "Cultural Studies", "Social Impact Entrepreneurship", "Artificial Intelligence Ethics",
                                "French Renaissance Literature", "Cellular Biology", "Macroeconomic Policy", "Environmental Engineering",
                                "Modern Art History", "Cross-Cultural Psychology", "Mobile App Development", "Music and Society",
                                "Educational Technology Integration", "Digital Media Ethics", "Comparative Politics of Asia",
                                "Quantum Computing", "International Finance and Investments", "Environmental Geophysics",
                                "Business Sustainability", "Environmental Ethics", "Philosophy of Mind", "Advanced Spanish Grammar and Composition");
        }
}
