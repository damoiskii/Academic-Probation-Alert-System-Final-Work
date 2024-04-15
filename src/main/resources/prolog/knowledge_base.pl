%
%----------------------------------------------------------------------------------
%                    Module: Artificial Intelligence (CMP4011)
%                    Lab Tutor: Mr. Howard James
%                    Class Group: Tuesdays @6pm
%                    Year: 2023/2024 Semester 2
%                    Assessment: Programming Group Project
%                    Group Members:
%                        Damoi Myers - 1703236
%----------------------------------------------------------------------------------
%

% Default GPA value that will be used throughout the system
default_gpa(2.20).


% Facts to identify grade points for a given grade
grade_points('A+', 4.30).
grade_points('A', 4.00).
grade_points('A-', 3.70).
grade_points('B+', 3.40).
grade_points('B', 3.10).
grade_points('B-', 2.80).
grade_points('C+', 2.50).
grade_points('C', 2.20).
grade_points('C-', 1.90).
grade_points('D+', 1.60).
grade_points('D', 1.30).
grade_points('D-', 1.00).
grade_points('E+', 0.70).
grade_points('E', 0.40).
grade_points('E-', 0.10).
grade_points('F', 0.00).


% Get the grade letter
grade_letter(Grade, StudentGradePoints):-
    grade_points(Letter, Point), StudentGradePoints == Point, Grade = Letter, !.


% Get the total point earned for a module
points_earned(Credits, Points, PointsEarned):-
    grade_letter(_, Points),
    PointsEarned is (Credits * Points).


% Get the sum of all elements in the list: Will be used to sum to total number of credits
sum_list([], 0).
sum_list([H|T], Total):-
    sum_list(T, NewTotal), Total is (NewTotal + H).


% Get the total number of elements in the list: Will be used to tally to total number of modules taken
count_list([], 0).
count_list([_|T], Total) :- count_list(T, NewTotal), Total is (NewTotal + 1).



% Calculate cumulative GPA
cumulative_gpa(Num1, Num2, Result):-
    Num2 \= 0,               % Ensure Y is not zero to avoid division by zero error
    Result is Num1 / Num2.   % Perform division and unify the result with the variable Result



% Utech Staff Members
% Faculty Administrator Structures
utech_staff(name('Mrs. Jennifer Kelly'), email('jennifer.kelly@utech.edu.jm'), school('School of Business Administration'), role('Faculty Admin')).
utech_staff(name('Mr. Jonathan Howard'), email('jonathan.howard@utech.edu.jm'), school('Joan Duncan School of Entrepreneurship'), role('Faculty Admin')).
utech_staff(name('Ms. Maya Cooper'), email('maya.cooper@utech.edu.jm'), school('School of Hospitality & Tourism Management'), role('Faculty Admin')).
utech_staff(name('Mrs. Heather Richardson'), email('heather.richardson@utech.edu.jm'), school('School of Allied Health & Wellness'), role('Faculty Admin')).
utech_staff(name('Mr. Carter Nelson'), email('carter.nelson@utech.edu.jm'), school('Caribbean School of Nursing'), role('Faculty Admin')).
utech_staff(name('Ms. Isabelle Ramirez'), email('isabelle.ramirez@utech.edu.jm'), school('School of Pharmacy'), role('Faculty Admin')).
utech_staff(name('Mrs. Tara Griffin'), email('tara.griffin@utech.edu.jm'), school('College of Oral Health Sciences'), role('Faculty Admin')).
utech_staff(name('Mr. Cole Torres'), email('cole.torres@utech.edu.jm'), school('School of Public Health & Health Technology'), role('Faculty Admin')).
utech_staff(name('Ms. Zoe Butler'), email('zoe.butler@utech.edu.jm'), school('School of Technical & Vocational Education'), role('Faculty Admin')).
utech_staff(name('Mrs. Rebecca Flores'), email('rebecca.flores@utech.edu.jm'), school('School of Humanities & Social Sciences'), role('Faculty Admin')).
utech_staff(name('Mr. Dominic Peterson'), email('dominic.peterson@utech.edu.jm'), school('School of Computing & Information Technology'), role('Faculty Admin')).
utech_staff(name('Ms. Vanessa Murphy'), email('vanessa.murphy@utech.edu.jm'), school('School of Engineering'), role('Faculty Admin')).
utech_staff(name('Mrs. Alicia Gonzales'), email('alicia.gonzales@utech.edu.jm'), school('Faculty Of Law'), role('Faculty Admin')).
utech_staff(name('Mr. Blake Price'), email('blake.price@utech.edu.jm'), school('Caribbean School of Sport Sciences'), role('Faculty Admin')).
utech_staff(name('Ms. Maya Patel'), email('maya.patel@utech.edu.jm'), school('School of Mathematics & Statistics'), role('Faculty Admin')).
utech_staff(name('Mrs. Julia Hughes'), email('julia.hughes@utech.edu.jm'), school('School of Natural & Applied Sciences'), role('Faculty Admin')).
utech_staff(name('Mr. Hayden Jenkins'), email('hayden.jenkins@utech.edu.jm'), school('Caribbean School of Architecture'), role('Faculty Admin')).
utech_staff(name('Ms. Paige Carter'), email('paige.carter@utech.edu.jm'), school('School of Building & Land Management'), role('Faculty Admin')).


% Programme Director Structures
utech_staff(name('Mr. John Smith'), email('john.smith@utech.edu.jm'), school('School of Business Administration'), programme('Bachelor of Business Administration (BBA)'), role('Programme Director')).
utech_staff(name('Ms. Emily Johnson'), email('emily.johnson@utech.edu.jm'), school('School of Business Administration'), programme('Bachelor of Science (BSc) in Accounting'), role('Programme Director')).
utech_staff(name('Mrs. Susan Williams'), email('susan.williams@utech.edu.jm'), school('School of Business Administration'), programme('Bachelor of Science (BSc) in Administrative Management'), role('Programme Director')).
utech_staff(name('Mr. Michael Brown'), email('michael.brown@utech.edu.jm'), school('School of Business Administration'), programme('Bachelor of Science (BSc) in Economics'), role('Programme Director')).
utech_staff(name('Ms. Jessica Davis'), email('jessica.davis@utech.edu.jm'), school('Joan Duncan School of Entrepreneurship'), programme('Bachelor of Science (BSc) in Entrepreneurship'), role('Programme Director')).
utech_staff(name('Mrs. Jennifer Miller'), email('jennifer.miller@utech.edu.jm'), school('School of Hospitality & Tourism Management'), programme('Bachelor of Science (BSc) in Food Service'), role('Programme Director')).
utech_staff(name('Mr. David Wilson'), email('david.wilson@utech.edu.jm'), school('School of Hospitality & Tourism Management'), programme('Bachelor of Science in Hospitality & Tourism'), role('Programme Director')).
utech_staff(name('Ms. Amanda Taylor'), email('amanda.taylor@utech.edu.jm'), school('School of Hospitality & Tourism Management'), programme('Associate of Science in Baking and Pastry Arts'), role('Programme Director')).
utech_staff(name('Mrs. Melissa Anderson'), email('melissa.anderson@utech.edu.jm'), school('School of Allied Health & Wellness'), programme('Bachelor of Science (BSc) in Medical Technology'), role('Programme Director')).
utech_staff(name('Mr. James Thomas'), email('james.thomas@utech.edu.jm'), school('School of Allied Health & Wellness'), programme('Bachelor of Science(BSc) in Medical Technology'), role('Programme Director')).
utech_staff(name('Ms. Sarah Jackson'), email('sarah.jackson@utech.edu.jm'), school('School of Allied Health & Wellness'), programme('Bachelor of Science (BSc) in Child and Adolescent'), role('Programme Director')).
utech_staff(name('Mrs. Laura White'), email('laura.white@utech.edu.jm'), school('School of Allied Health & Wellness'), programme('Bachelor of Science (BSc) in Dietetics & Nutrition'), role('Programme Director')).
utech_staff(name('Mr. Christopher Martinez'), email('christopher.martinez@utech.edu.jm'), school('School of Allied Health & Wellness'), programme('Bachelor of Health Sciences'), role('Programme Director')).
utech_staff(name('Ms. Samantha Harris'), email('samantha.harris@utech.edu.jm'), school('School of Allied Health & Wellness'), programme('Associate of Science Degree in Health Information Technology (Post RN)'), role('Programme Director')).
utech_staff(name('Mrs. Kimberly Lee'), email('kimberly.lee@utech.edu.jm'), school('School of Allied Health & Wellness'), programme('Certificate in Child & Adolescent Development'), role('Programme Director')).
utech_staff(name('Mr. Daniel Gonzalez'), email('daniel.gonzalez@utech.edu.jm'), school('Caribbean School of Nursing'), programme('Bachelor of Science (BSc) in Critical Care Nursing'), role('Programme Director')).
utech_staff(name('Ms. Ashley Clark'), email('ashley.clark@utech.edu.jm'), school('Caribbean School of Nursing'), programme('Bachelor of Science (BSc) in Nursing'), role('Programme Director')).
utech_staff(name('Mrs. Angela Lewis'), email('angela.lewis@utech.edu.jm'), school('Caribbean School of Nursing'), programme('Bachelor of Science (BSc) in Nursing (Post RN)'), role('Programme Director')).
utech_staff(name('Mr. Matthew Hall'), email('matthew.hall@utech.edu.jm'), school('Caribbean School of Nursing'), programme('Bachelor of Science (BSc) in Midwifery (RN)'), role('Programme Director')).
utech_staff(name('Ms. Taylor Young'), email('taylor.young@utech.edu.jm'), school('Caribbean School of Nursing'), programme('Bachelor of Science (BSc) in Midwifery (RM)'), role('Programme Director')).
utech_staff(name('Mrs. Rachel King'), email('rachel.king@utech.edu.jm'), school('Caribbean School of Nursing'), programme('Certificate in Critical Care (CC) Nursing (Post RN)'), role('Programme Director')).
utech_staff(name('Mr. Joseph Wright'), email('joseph.wright@utech.edu.jm'), school('School of Pharmacy'), programme('Bachelor of Pharmacy", "Bachelor of Pharmacy (B.Pharm)'), role('Programme Director')).
utech_staff(name('Ms. Lauren Rodriguez'), email('lauren.rodriguez@utech.edu.jm'), school('School of Pharmacy'), programme('Bachelor of Science in Pharmaceutical Technology (BSc Pharm Tech.)'), role('Programme Director')).
utech_staff(name('Mrs. Patricia Scott'), email('patricia.scott@utech.edu.jm'), school('School of Pharmacy'), programme('Pharmacy Technician Certificate'), role('Programme Director')).
utech_staff(name('Mr. Andrew Green'), email('andrew.green@utech.edu.jm'), school('College of Oral Health Sciences'), programme('Doctor of Medical Dentistry'), role('Programme Director')).
utech_staff(name('Ms. Olivia Hill'), email('olivia.hill@utech.edu.jm'), school('College of Oral Health Sciences'), programme('Bachelor of Science (BSc) in Dental Laboratory Technology'), role('Programme Director')).
utech_staff(name('Mrs. Elizabeth Adams'), email('elizabeth.adams@utech.edu.jm'), school('College of Oral Health Sciences'), programme('Diploma in Dental Assisting (Expanded function)'), role('Programme Director')).
utech_staff(name('Mr. Ryan Baker'), email('ryan.baker@utech.edu.jm'), school('School of Public Health & Health Technology'), programme('Bachelor of Science (BSc)in Environmental Health'), role('Programme Director')).
utech_staff(name('Ms. Madison Martinez'), email('madison.martinez@utech.edu.jm'), school('School of Public Health & Health Technology'), programme('Bachelor of Science (BSc) in Occupational Health, Safety & Environmental Management'), role('Programme Director')).
utech_staff(name('Mrs. Nicole Carter'), email('nicole.carter@utech.edu.jm'), school('School of Public Health & Health Technology'), programme('Bachelor of Science (BSc) in Public Health Nursing (Post-Basic)'), role('Programme Director')).
utech_staff(name('Mr. Joshua Perez'), email('joshua.perez@utech.edu.jm'), school('School of Public Health & Health Technology'), programme('Diploma in Meats & Other Foods Inspection'), role('Programme Director')).
utech_staff(name('Ms. Hannah Rivera'), email('hannah.rivera@utech.edu.jm'), school('School of Technical & Vocational Education'), programme('Bachelor of Arts (BA) in Apparel Design, Production & Management'), role('Programme Director')).
utech_staff(name('Mrs. Victoria Mitchell'), email('victoria.mitchell@utech.edu.jm'), school('School of Technical & Vocational Education'), programme('Bachelor of Education in Technical and Vocational Education and Training (BEd TVET) in Business & Computer Studies'), role('Programme Director')).
utech_staff(name('Mr. Justin Roberts'), email('justin.roberts@utech.edu.jm'), school('School of Technical & Vocational Education'), programme('Bachelor of Education in in Technical and Vocational Education and Training (BEd TVET) in Food Service Production and Management'), role('Programme Director')).
utech_staff(name('Ms. Grace Turner'), email('grace.turner@utech.edu.jm'), school('School of Technical & Vocational Education'), programme('Bachelor of Education in Technical and Vocational Education and Training (BEd TVET) Specialization in Industrial Technology'), role('Programme Director')).
utech_staff(name('Mrs. Danielle Phillips'), email('danielle.phillips@utech.edu.jm'), school('School of Technical & Vocational Education'), programme('Bachelor of Science (BSc) in Entertainment Design, Production and Technology'), role('Programme Director')).
utech_staff(name('Mr. Brandon Campbell'), email('brandon.campbell@utech.edu.jm'), school('School of Technical & Vocational Education'), programme('Associate of Arts in Image Consulting and Fashion Styling'), role('Programme Director')).
utech_staff(name('Ms. Chloe Evans'), email('chloe.evans@utech.edu.jm'), school('School of Technical & Vocational Education'), programme('Associate of Science in Entertainment Design, Production and Technology'), role('Programme Director')).
utech_staff(name('Mrs. Maria Torres'), email('maria.torres@utech.edu.jm'), school('School of Humanities & Social Sciences'), programme('Bachelor of Arts (BA) in Communication Arts & Technology'), role('Programme Director')).
utech_staff(name('Mr. Kevin Cooper'), email('kevin.cooper@utech.edu.jm'), school('School of Computing & Information Technology'), programme('Bachelor of Science (BSc) in Animation Production and Development'), role('Programme Director')).
utech_staff(name('Ms. Victoria Parker'), email('victoria.parker@utech.edu.jm'), school('School of Computing & Information Technology'), programme('Bachelor of Science (BSc) in Applied Artificial Intelligence'), role('Programme Director')).
utech_staff(name('Mrs. Amanda Morris'), email('amanda.morris@utech.edu.jm'), school('School of Computing & Information Technology'), programme('Bachelor of Science (BSc) in Computer Information'), role('Programme Director')).
utech_staff(name('Mr. Eric Rivera'), email('eric.rivera@utech.edu.jm'), school('School of Computing & Information Technology'), programme('Bachelor of Science (BSc) in Computing'), role('Programme Director')).
utech_staff(name('Ms. Natalie Diaz'), email('natalie.diaz@utech.edu.jm'), school('School of Computing & Information Technology'), programme('Bachelor of Science (BSc) in Computer Network and Security'), role('Programme Director')).
utech_staff(name('Mrs. Karen Barnes'), email('karen.barnes@utech.edu.jm'), school('School of Computing & Information Technology'), programme('Bachelor of Science (BSc) in Gaming'), role('Programme Director')).
utech_staff(name('Mr. Nicholas Edwards'), email('nicholas.edwards@utech.edu.jm'), school('School of Engineering'), programme('Bachelor of Engineering (B.Eng) in Agricultural Engineering'), role('Programme Director')).
utech_staff(name('Ms. Kaitlyn Stewart'), email('kaitlyn.stewart@utech.edu.jm'), school('School of Engineering'), programme('Bachelor of Engineering (B.Eng.) in Chemical Engineering'), role('Programme Director')).
utech_staff(name('Mrs. Michelle Sanchez'), email('michelle.sanchez@utech.edu.jm'), school('School of Engineering'), programme('Bachelor of Engineering (BEng) in Civil Engineering'), role('Programme Director')).
utech_staff(name('Mr. Tyler Morris'), email('tyler.morris@utech.edu.jm'), school('School of Engineering'), programme('Bachelor of Engineering (BEng) in Electrical and Computer Engineering'), role('Programme Director')).
utech_staff(name('Ms. Amber Gonzalez'), email('amber.gonzalez@utech.edu.jm'), school('School of Engineering'), programme('Bachelor of Engineering (BEng) in Industrial Engineering'), role('Programme Director')).
utech_staff(name('Mr. Benjamin Ramirez'), email('benjamin.ramirez@utech.edu.jm'), school('School of Engineering'), programme('Bachelor of Engineering (BEng) in Mechanical Engineering'), role('Programme Director')).
utech_staff(name('Ms. Hailey Foster'), email('hailey.foster@utech.edu.jm'), school('School of Engineering'), programme('Diploma in Electrical Engineering'), role('Programme Director')).
utech_staff(name('Mrs. Samantha Russell'), email('samantha.russell@utech.edu.jm'), school('School of Engineering'), programme('Diploma in Mechanical Engineering'), role('Programme Director')).
utech_staff(name('Mr. Jacob Coleman'), email('jacob.coleman@utech.edu.jm'), school('Faculty of Law'), programme('Bachelor of Laws (LLB)'), role('Programme Director')).
utech_staff(name('Ms. Kayla Henderson'), email('kayla.henderson@utech.edu.jm'), school('Caribbean School of Sport Sciences'), programme('Bachelor of Science (BSc) in Sport Sciences'), role('Programme Director')).
utech_staff(name('Mrs. Allison Ortiz'), email('allison.ortiz@utech.edu.jm'), school('School of Mathematics & Statistics'), programme('Bachelor of Science (BSc) in Actuarial Science'), role('Programme Director')).
utech_staff(name('Mr. Austin Barnes'), email('austin.barnes@utech.edu.jm'), school('School of Mathematics & Statistics'), programme('Bachelor of Science (BSc) in Applied Statistics'), role('Programme Director')).
utech_staff(name('Ms. Lily Simmons'), email('lily.simmons@utech.edu.jm'), school('School of Mathematics & Statistics'), programme('Bachelor of Science (BSc) in Mathematics & Education'), role('Programme Director')).
utech_staff(name('Mrs. Stephanie Price'), email('stephanie.price@utech.edu.jm'), school('School of Natural & Applied Sciences'), programme('Bachelor of Science (BSc) in Applied Science'), role('Programme Director')).
utech_staff(name('Mr. Gabriel Sullivan'), email('gabriel.sullivan@utech.edu.jm'), school('School of Natural & Applied Sciences'), programme('Bachelor of Science (BSc) in Science and Education'), role('Programme Director')).
utech_staff(name('Ms. Sydney Coleman'), email('sydney.coleman@utech.edu.jm'), school('Caribbean School of Architecture'), programme('Bachelor of Arts in Architectural Studies (BAAS)'), role('Programme Director')).
utech_staff(name('Mr. Sawyer Wilson'), email('sawyer.wilson@utech.edu.jm'), school('School of Building & Land Management'), programme('Bachelor of Engineering (BEng) in Construction Engineering'), role('Programme Director')).
utech_staff(name('Ms. Ruby Adams'), email('ruby.adams@utech.edu.jm'), school('School of Building & Land Management'), programme('Bachelor of Science (BSc) in Construction Management (Post Diploma)'), role('Programme Director')).
utech_staff(name('Mrs. Giselle Thompson'), email('giselle.thompson@utech.edu.jm'), school('School of Building & Land Management'), programme('Bachelor of Science (BSc) in Land Surveying and Geographic Information Sciences'), role('Programme Director')).
utech_staff(name('Mr. Ashton Carter'), email('ashton.carter@utech.edu.jm'), school('School of Building & Land Management'), programme('Bachelor of Science (BSc) in Quantity Surveying'), role('Programme Director')).
utech_staff(name('Ms. Harper Martinez'), email('harper.martinez@utech.edu.jm'), school('School of Building & Land Management'), programme('Bachelor of Science (BSc) in Mines and Quarry Management'), role('Programme Director')).
utech_staff(name('Mrs. Juliette Phillips'), email('juliette.phillips@utech.edu.jm'), school('School of Building & Land Management'), programme('Bachelor of Science (BSc) in Real Estate Management & Valuation'), role('Programme Director')).
utech_staff(name('Mr. Lincoln Scott'), email('lincoln.scott@utech.edu.jm'), school('School of Building & Land Management'), programme('Bachelor of Science (BSc) in Urban and Regional Planning'), role('Programme Director')).
utech_staff(name('Ms. Olive Jackson'), email('olive.jackson@utech.edu.jm'), school('School of Building & Land Management'), programme('Associate Degree in Surveying and Geographic Information Technology'), role('Programme Director')).
utech_staff(name('Mrs. Delaney Garcia'), email('delaney.garcia@utech.edu.jm'), school('School of Building & Land Management'), programme('Diploma in Construction Management'), role('Programme Director')).
utech_staff(name('Mr. Augustus Brown'), email('augustus.brown@utech.edu.jm'), school('School of Building & Land Management'), programme('Diploma in Structural Engineering'), role('Programme Director')).


% Advisor Structures
utech_staff(name('Ms. Penelope Hernandez'), email('penelope.hernandez@utech.edu.jm'), school('School of Business Administration'), role('Advisor'), advice_students_whose_id_starts_with(['10', '14', '18', '22', '26'])).
utech_staff(name('Mrs. Aria Anderson'), email('aria.anderson@utech.edu.jm'), school('School of Business Administration'), role('Advisor'), advice_students_whose_id_starts_with(['11', '15', '19', '23', '27'])).
utech_staff(name('Mr. Kieran Taylor'), email('kieran.taylor@utech.edu.jm'), school('School of Business Administration'), role('Advisor'), advice_students_whose_id_starts_with(['12', '16', '20', '24', '28'])).
utech_staff(name('Ms. Camille Martinez'), email('camille.martinez@utech.edu.jm'), school('School of Business Administration'), role('Advisor'), advice_students_whose_id_starts_with(['13', '17', '21', '25', '29'])).
utech_staff(name('Mrs. Freya Thompson'), email('freya.thompson@utech.edu.jm'), school('Joan Duncan School of Entrepreneurship'), role('Advisor'), advice_students_whose_id_starts_with(['10', '14', '18', '22', '26'])).
utech_staff(name('Mr. Declan Adams'), email('declan.adams@utech.edu.jm'), school('Joan Duncan School of Entrepreneurship'), role('Advisor'), advice_students_whose_id_starts_with(['11', '15', '19', '23', '27'])).
utech_staff(name('Ms. Seraphina Rodriguez'), email('seraphina.rodriguez@utech.edu.jm'), school('Joan Duncan School of Entrepreneurship'), role('Advisor'), advice_students_whose_id_starts_with(['12', '16', '20', '24', '28'])).
utech_staff(name('Mrs. Bianca Campbell'), email('bianca.campbell@utech.edu.jm'), school('Joan Duncan School of Entrepreneurship'), role('Advisor'), advice_students_whose_id_starts_with(['13', '17', '21', '25', '29'])).
utech_staff(name('Mr. Finnegan Thompson'), email('finnegan.thompson@utech.edu.jm'), school('School of Hospitality & Tourism Management'), role('Advisor'), advice_students_whose_id_starts_with(['10', '14', '18', '22', '26'])).
utech_staff(name('Ms. Thalia Martinez'), email('thalia.martinez@utech.edu.jm'), school('School of Hospitality & Tourism Management'), role('Advisor'), advice_students_whose_id_starts_with(['11', '15', '19', '23', '27'])).
utech_staff(name('Mrs. Elodie Jackson'), email('elodie.jackson@utech.edu.jm'), school('School of Hospitality & Tourism Management'), role('Advisor'), advice_students_whose_id_starts_with(['12', '16', '20', '24', '28'])).
utech_staff(name('Mr. Malachi Brown'), email('malachi.brown@utech.edu.jm'), school('School of Hospitality & Tourism Management'), role('Advisor'), advice_students_whose_id_starts_with(['13', '17', '21', '25', '29'])).
utech_staff(name('Ms. Adelaide Garcia'), email('adelaide.garcia@utech.edu.jm'), school('School of Allied Health & Wellness'), role('Advisor'), advice_students_whose_id_starts_with(['10', '14', '18', '22', '26'])).
utech_staff(name('Mrs. Genevieve Phillips'), email('genevieve.phillips@utech.edu.jm'), school('School of Allied Health & Wellness'), role('Advisor'), advice_students_whose_id_starts_with(['11', '15', '19', '23', '27'])).
utech_staff(name('Mr. Ezekiel Scott'), email('ezekiel.scott@utech.edu.jm'), school('School of Allied Health & Wellness'), role('Advisor'), advice_students_whose_id_starts_with(['12', '16', '20', '24', '28'])).
utech_staff(name('Ms. Juniper Hernandez'), email('juniper.hernandez@utech.edu.jm'), school('School of Allied Health & Wellness'), role('Advisor'), advice_students_whose_id_starts_with(['13', '17', '21', '25', '29'])).
utech_staff(name('Mrs. Isla Anderson'), email('isla.anderson@utech.edu.jm'), school('Caribbean School of Nursing'), role('Advisor'), advice_students_whose_id_starts_with(['10', '14', '18', '22', '26'])).
utech_staff(name('Mr. Atticus Taylor'), email('atticus.taylor@utech.edu.jm'), school('Caribbean School of Nursing'), role('Advisor'), advice_students_whose_id_starts_with(['11', '15', '19', '23', '27'])).
utech_staff(name('Ms. Magnolia Martinez'), email('magnolia.martinez@utech.edu.jm'), school('Caribbean School of Nursing'), role('Advisor'), advice_students_whose_id_starts_with(['12', '16', '20', '24', '28'])).
utech_staff(name('Mrs. Dahlia Thompson'), email('dahlia.thompson@utech.edu.jm'), school('Caribbean School of Nursing'), role('Advisor'), advice_students_whose_id_starts_with(['13', '17', '21', '25', '29'])).
utech_staff(name('Mr. Sebastian Adams'), email('sebastian.adams@utech.edu.jm'), school('School of Pharmacy'), role('Advisor'), advice_students_whose_id_starts_with(['10', '14', '18', '22', '26'])).
utech_staff(name('Ms. Evangeline Rodriguez'), email('evangeline.rodriguez@utech.edu.jm'), school('School of Pharmacy'), role('Advisor'), advice_students_whose_id_starts_with(['11', '15', '19', '23', '27'])).
utech_staff(name('Mrs. Aurora Campbell'), email('aurora.campbell@utech.edu.jm'), school('School of Pharmacy'), role('Advisor'), advice_students_whose_id_starts_with(['12', '16', '20', '24', '28'])).
utech_staff(name('Mr. Jasper Thompson'), email('asper.thompson@utech.edu.jm'), school('School of Pharmacy'), role('Advisor'), advice_students_whose_id_starts_with(['13', '17', '21', '25', '29'])).
utech_staff(name('Ms. Clementine Martinez'), email('clementine.martinez@utech.edu.jm'), school('College of Oral Health Sciences'), role('Advisor'), advice_students_whose_id_starts_with(['10', '14', '18', '22', '26'])).
utech_staff(name('Mrs. Nova Jackson'), email('nova.jackson@utech.edu.jm'), school('College of Oral Health Sciences'), role('Advisor'), advice_students_whose_id_starts_with(['11', '15', '19', '23', '27'])).
utech_staff(name('Mr. Phoenix Brown'), email('phoenix.brown@utech.edu.jm'), school('College of Oral Health Sciences'), role('Advisor'), advice_students_whose_id_starts_with(['12', '16', '20', '24', '28'])).
utech_staff(name('Ms. Ivy Garcia'), email('ivy.garcia@utech.edu.jm'), school('College of Oral Health Sciences'), role('Advisor'), advice_students_whose_id_starts_with(['13', '17', '21', '25', '29'])).
utech_staff(name('Mrs. Luna Phillips'), email('luna.phillips@utech.edu.jm'), school('School of Public Health & Health Technology'), role('Advisor'), advice_students_whose_id_starts_with(['10', '14', '18', '22', '26'])).
utech_staff(name('Mr. Orion Scott'), email('orion.scott@utech.edu.jm'), school('School of Public Health & Health Technology'), role('Advisor'), advice_students_whose_id_starts_with(['11', '15', '19', '23', '27'])).
utech_staff(name('Ms. Willow Hernandez'), email('willow.hernandez@utech.edu.jm'), school('School of Public Health & Health Technology'), role('Advisor'), advice_students_whose_id_starts_with(['12', '16', '20', '24', '28'])).
utech_staff(name('Mrs. Ember Anderson'), email('ember.anderson@utech.edu.jm'), school('School of Public Health & Health Technology'), role('Advisor'), advice_students_whose_id_starts_with(['13', '17', '21', '25', '29'])).
utech_staff(name('Mr. Atlas Taylor'), email('atlas.taylor@utech.edu.jm'), school('School of Technical & Vocational Education'), role('Advisor'), advice_students_whose_id_starts_with(['10', '14', '18', '22', '26'])).
utech_staff(name('Ms. Juno Martinez'), email('juno.martinez@utech.edu.jm'), school('School of Technical & Vocational Education'), role('Advisor'), advice_students_whose_id_starts_with(['11', '15', '19', '23', '27'])).
utech_staff(name('Mrs. Freesia Thompson'), email('freesia.thompson@utech.edu.jm'), school('School of Technical & Vocational Education'), role('Advisor'), advice_students_whose_id_starts_with(['12', '16', '20', '24', '28'])).
utech_staff(name('Mr. River Adams'), email('river.adams@utech.edu.jm'), school('School of Technical & Vocational Education'), role('Advisor'), advice_students_whose_id_starts_with(['13', '17', '21', '25', '29'])).
utech_staff(name('Ms. Dahlia Rodriguez'), email('dahlia.rodriguez@utech.edu.jm'), school('School of Humanities & Social Sciences'), role('Advisor'), advice_students_whose_id_starts_with(['10', '14', '18', '22', '26'])).
utech_staff(name('Mrs. Marigold Campbell'), email('marigold.campbell@utech.edu.jm'), school('School of Humanities & Social Sciences'), role('Advisor'), advice_students_whose_id_starts_with(['11', '15', '19', '23', '27'])).
utech_staff(name('Mr. Zephyr Thompson'), email('zephyr.thompson@utech.edu.jm'), school('School of Humanities & Social Sciences'), role('Advisor'), advice_students_whose_id_starts_with(['12', '16', '20', '24', '28'])).
utech_staff(name('Ms. Calliope Martinez'), email('calliope.martinez@utech.edu.jm'), school('School of Humanities & Social Sciences'), role('Advisor'), advice_students_whose_id_starts_with(['13', '17', '21', '25', '29'])).
utech_staff(name('Mrs. Luna Jackson'), email('luna.jackson@utech.edu.jm'), school('School of Computing & Information Technology'), role('Advisor'), advice_students_whose_id_starts_with(['10', '14', '18', '22', '26'])).
utech_staff(name('Mr. Asher Brown'), email('asher.brown@utech.edu.jm'), school('School of Computing & Information Technology'), role('Advisor'), advice_students_whose_id_starts_with(['11', '15', '19', '23', '27'])).
utech_staff(name('Ms. Phoebe Garcia'), email('phoebe.garcia@utech.edu.jm'), school('School of Computing & Information Technology'), role('Advisor'), advice_students_whose_id_starts_with(['12', '16', '20', '24', '28'])).
utech_staff(name('Mrs. Aster Phillips'), email('aster.phillips@utech.edu.jm'), school('School of Computing & Information Technology'), role('Advisor'), advice_students_whose_id_starts_with(['13', '17', '21', '25', '29'])).
utech_staff(name('Mr. Kai Scott'), email('kai.scott@utech.edu.jm'), school('School of Engineering'), role('Advisor'), advice_students_whose_id_starts_with(['10', '14', '18', '22', '26'])).
utech_staff(name('Ms. Persephone Hernandez'), email('persephone.hernandez@utech.edu.jm'), school('School of Engineering'), role('Advisor'), advice_students_whose_id_starts_with(['11', '15', '19', '23', '27'])).
utech_staff(name('Mrs. Juniper Anderson'), email('juniper.anderson@utech.edu.jm'), school('School of Engineering'), role('Advisor'), advice_students_whose_id_starts_with(['12', '16', '20', '24', '28'])).
utech_staff(name('Mr. Leo Taylor'), email('leo.taylor@utech.edu.jm'), school('School of Engineering'), role('Advisor'), advice_students_whose_id_starts_with(['13', '17', '21', '25', '29'])).
utech_staff(name('Ms. Iris Martinez'), email('iris.martinez@utech.edu.jm'), school('Faculty of Law'), role('Advisor'), advice_students_whose_id_starts_with(['10', '14', '18', '22', '26'])).
utech_staff(name('Mrs. Clementine Thompson'), email('clementine.thompson@utech.edu.jm'), school('Faculty of Law'), role('Advisor'), advice_students_whose_id_starts_with(['11', '15', '19', '23', '27'])).
utech_staff(name('Mr. Finn Adams'), email('finn.adams@utech.edu.jm'), school('Faculty of Law'), role('Advisor'), advice_students_whose_id_starts_with(['12', '16', '20', '24', '28'])).
utech_staff(name('Ms. Luna Rodriguez'), email('luna.rodriguez@utech.edu.jm'), school('Faculty of Law'), role('Advisor'), advice_students_whose_id_starts_with(['13', '17', '21', '25', '29'])).
utech_staff(name('Mrs. Hazel Campbell'), email('hazel.campbell@utech.edu.jm'), school('Caribbean School of Sport Sciences'), role('Advisor'), advice_students_whose_id_starts_with(['10', '14', '18', '22', '26'])).
utech_staff(name('Mr. Theo Thompson'), email('theo.thompson@utech.edu.jm'), school('Caribbean School of Sport Sciences'), role('Advisor'), advice_students_whose_id_starts_with(['11', '15', '19', '23', '27'])).
utech_staff(name('Ms. Violet Martinez'), email('violet.martinez@utech.edu.jm'), school('Caribbean School of Sport Sciences'), role('Advisor'), advice_students_whose_id_starts_with(['12', '16', '20', '24', '28'])).
utech_staff(name('Mrs. Elara Jackson'), email('elara.jackson@utech.edu.jm'), school('Caribbean School of Sport Sciences'), role('Advisor'), advice_students_whose_id_starts_with(['13', '17', '21', '25', '29'])).
utech_staff(name('Mr. Rowan Brown'), email('rowan.brown@utech.edu.jm'), school('School of Mathematics & Statistics'), role('Advisor'), advice_students_whose_id_starts_with(['10', '14', '18', '22', '26'])).
utech_staff(name('Ms. Aurora Garcia'), email('aurora.garcia@utech.edu.jm'), school('School of Mathematics & Statistics'), role('Advisor'), advice_students_whose_id_starts_with(['11', '15', '19', '23', '27'])).
utech_staff(name('Mrs. Iris Phillips'), email('iris.phillips@utech.edu.jm'), school('School of Mathematics & Statistics'), role('Advisor'), advice_students_whose_id_starts_with(['12', '16', '20', '24', '28'])).
utech_staff(name('Mr. Milo Scott'), email('milo.scott@utech.edu.jm'), school('School of Mathematics & Statistics'), role('Advisor'), advice_students_whose_id_starts_with(['13', '17', '21', '25', '29'])).
utech_staff(name('Ms. Selene Hernandez'), email('selene.hernandez@utech.edu.jm'), school('School of Natural & Applied Sciences'), role('Advisor'), advice_students_whose_id_starts_with(['10', '14', '18', '22', '26'])).
utech_staff(name('Mrs. Lyra Anderson'), email('lyra.anderson@utech.edu.jm'), school('School of Natural & Applied Sciences'), role('Advisor'), advice_students_whose_id_starts_with(['11', '15', '19', '23', '27'])).
utech_staff(name('Mr. Jude Taylor'), email('jude.taylor@utech.edu.jm'), school('School of Natural & Applied Sciences'), role('Advisor'), advice_students_whose_id_starts_with(['12', '16', '20', '24', '28'])).
utech_staff(name('Ms. Ruby Martinez'), email('ruby.martinez@utech.edu.jm'), school('School of Natural & Applied Sciences'), role('Advisor'), advice_students_whose_id_starts_with(['13', '17', '21', '25', '29'])).
utech_staff(name('Mrs. Nora Thompson'), email('nora.thompson@utech.edu.jm'), school('Caribbean School of Architecture'), role('Advisor'), advice_students_whose_id_starts_with(['10', '14', '18', '22', '26'])).
utech_staff(name('Mr. Silas Adams'), email('silas.adams@utech.edu.jm'), school('Caribbean School of Architecture'), role('Advisor'), advice_students_whose_id_starts_with(['11', '15', '19', '23', '27'])).
utech_staff(name('Mr. Elijah Bennett'), email('elijah.bennett@utech.edu.jm'), school('Caribbean School of Architecture'), role('Advisor'), advice_students_whose_id_starts_with(['12', '16', '20', '24', '28'])).
utech_staff(name('Mrs. Harper James'), email('harper.james@utech.edu.jm'), school('Caribbean School of Architecture'), role('Advisor'), advice_students_whose_id_starts_with(['13', '17', '21', '25', '29'])).
utech_staff(name('Ms. Ivy Thompson'), email('ivy.thompson@utech.edu.jm'), school('School of Building & Land Management'), role('Advisor'), advice_students_whose_id_starts_with(['10', '14', '18', '22', '26'])).
utech_staff(name('Mrs. Penelope Reed'), email('penelope.reed@utech.edu.jm'), school('School of Building & Land Management'), role('Advisor'), advice_students_whose_id_starts_with(['11', '15', '19', '23', '27'])).
utech_staff(name('Mr. Grayson Parker'), email('grayson.parker@utech.edu.jm'), school('School of Building & Land Management'), role('Advisor'), advice_students_whose_id_starts_with(['12', '16', '20', '24', '28'])).
utech_staff(name('Ms. Ruby Nelson'), email('ruby.nelson@utech.edu.jm'), school('School of Building & Land Management'), role('Advisor'), advice_students_whose_id_starts_with(['13', '17', '21', '25', '29'])).



% Faculty Administrators: Given a school and returning an admin name and email
faculty_admin(School, Name, Email):-
    utech_staff(name(Name), email(Email), school(School), role('Faculty Admin')), !.


% Programme Directors: Given a school & programme and returning a programme director's name and email
programme_director(School, Programme, Name, Email):-
    utech_staff(name(Name), email(Email), school(School), programme(Programme), role('Programme Director')), !.


% Advisors: Given a school & first two character of student's ID# and returning a advisor's name and email
advisor(School, IDFirstTwoChars, Name, Email):-
    utech_staff(name(Name), email(Email), school(School), role('Advisor'), advice_students_whose_id_starts_with(List)),
    member(IDFirstTwoChars, List), !.

