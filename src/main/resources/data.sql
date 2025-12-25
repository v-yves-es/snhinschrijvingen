-- School Years initialization
INSERT INTO school_years (id, description, start_date, end_date, is_active) VALUES
('2024-2025', 'Schooljaar 2024-2025', '2024-09-01', '2025-08-31', true),
('2025-2026', 'Schooljaar 2025-2026', '2025-09-01', '2026-08-31', false),
('2026-2027', 'Schooljaar 2026-2027', '2026-09-01', '2027-08-31', false);

-- Nationalities initialization
INSERT INTO nationalities (code, name_nl, name_fr, name_en, display_order) VALUES
-- Most common nationalities first
('BE', 'Belgisch', 'Belge', 'Belgian', 1),
('NL', 'Nederlands', 'Néerlandais', 'Dutch', 2),
('FR', 'Frans', 'Français', 'French', 3),
('DE', 'Duits', 'Allemand', 'German', 4),
('GB', 'Brits', 'Britannique', 'British', 5),
('IT', 'Italiaans', 'Italien', 'Italian', 6),
('ES', 'Spaans', 'Espagnol', 'Spanish', 7),
('PT', 'Portugees', 'Portugais', 'Portuguese', 8),
('PL', 'Pools', 'Polonais', 'Polish', 9),
('RO', 'Roemeens', 'Roumain', 'Romanian', 10),
('UK', N'Oekraïens', 'Ukrainienne', 'Ukrainian', 10),
('MA', 'Marokkaans', 'Marocain', 'Moroccan', 11);

-- Study Domains (applicable from year 3 onwards)
INSERT INTO study_domains (code, name, display_order, applicable_from_year) VALUES
('EO', 'Economie en organisatie', 1, 3),
('MW', 'Maatschappij en welzijn', 2, 3),
('STEM', 'STEM', 3, 3),
('TC', 'Taal en cultuur', 4, 3);

-- Study Orientations
INSERT INTO study_orientations (code, name, display_order) VALUES
('D', 'Doorstroom', 1),
('DA', 'Doorstroom/arbeidsmarkt', 2),
('A', 'Arbeidsmarkt', 3);

-- Study Programs Year 1
INSERT INTO study_programs (code, name, study_year, domain_id, orientation_id, display_order, is_active) VALUES
('1A-LAT', '1A Latijn (accent)', 1, NULL, NULL, 1, true),
('1A-STEM', '1A STEM (accent)', 1, NULL, NULL, 2, true),
('1A-VERK-A', '1A Verkenning (accent)', 1, NULL, NULL, 3, true),
('1A-VERK-P', '1A Verkenning (plus)', 1, NULL, NULL, 4, true),
('1B', '1B', 1, NULL, NULL, 5, true);

-- Study Programs Year 2
INSERT INTO study_programs (code, name, study_year, domain_id, orientation_id, display_order, is_active) VALUES
('2A-EO', '2 A Economie en Organisatie', 2, NULL, NULL, 1, true),
('2A-KT', '2 A Klassieke Talen (Latijn)', 2, NULL, NULL, 2, true),
('2A-MW', '2 A Maatschappij en Welzijn', 2, NULL, NULL, 3, true),
('2A-MTW', '2 A Moderne Talen en Wetenschappen', 2, NULL, NULL, 4, true),
('2A-STEM', '2 A STEM-wetenschappen', 2, NULL, NULL, 5, true),
('2B-EO', '2 B Economie en Organisatie (Retail)', 2, NULL, NULL, 6, true),
('2B-MW', '2 B Maatschappij en Welzijn (Lifestyle, Voeding en Lichaamszorg)', 2, NULL, NULL, 7, true);

-- Study Programs Year 3 - Economie en organisatie
INSERT INTO study_programs (code, name, study_year, domain_id, orientation_id, display_order, is_active) VALUES
('3-BW', '3 Bedrijfswetenschappen (BW)', 3, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'D'), 1, true),
('3-BW-CLIL', '3 Bedrijfswetenschappen (BW) met CLIL', 3, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'D'), 2, true),
('3-E', '3 Economische Wetenschappen (E)', 3, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'D'), 3, true),
('3-E-CLIL', '3 Economische Wetenschappen (E) met CLIL', 3, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'D'), 4, true),
('3-BO', '3 Bedrijf en Organisatie (BO)', 3, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'DA'), 5, true),
('3-OL', '3 Organisatie en Logistiek (OL)', 3, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'A'), 6, true);

-- Study Programs Year 3 - Maatschappij en welzijn
INSERT INTO study_programs (code, name, study_year, domain_id, orientation_id, display_order, is_active) VALUES
('3-H', '3 Humane Wetenschappen (H)', 3, (SELECT id FROM study_domains WHERE code = 'MW'), (SELECT id FROM study_orientations WHERE code = 'D'), 7, true),
('3-H-CLIL', '3 Humane wetenschappen (H) met CLIL', 3, (SELECT id FROM study_domains WHERE code = 'MW'), (SELECT id FROM study_orientations WHERE code = 'D'), 8, true),
('3-MW', '3 Maatschappij- en Welzijnswetenschappen (MW)', 3, (SELECT id FROM study_domains WHERE code = 'MW'), (SELECT id FROM study_orientations WHERE code = 'D'), 9, true),
('3-MW-CLIL', '3 Maatschappij- en welzijnswetenschappen (MW) met CLIL', 3, (SELECT id FROM study_domains WHERE code = 'MW'), (SELECT id FROM study_orientations WHERE code = 'D'), 10, true),
('3-CM', '3 Creatie en Mode (CM)', 3, (SELECT id FROM study_domains WHERE code = 'MW'), (SELECT id FROM study_orientations WHERE code = 'DA'), 11, true);

-- Study Programs Year 3 - STEM
INSERT INTO study_programs (code, name, study_year, domain_id, orientation_id, display_order, is_active) VALUES
('3-N', '3 Natuurwetenschappen (N)', 3, (SELECT id FROM study_domains WHERE code = 'STEM'), (SELECT id FROM study_orientations WHERE code = 'D'), 12, true),
('3-N-CLIL', '3 Natuurwetenschappen (N) met CLIL', 3, (SELECT id FROM study_domains WHERE code = 'STEM'), (SELECT id FROM study_orientations WHERE code = 'D'), 13, true),
('3-S', '3 Natuurwetenschappen - STEM (S)', 3, (SELECT id FROM study_domains WHERE code = 'STEM'), (SELECT id FROM study_orientations WHERE code = 'D'), 14, true),
('3-S-CLIL', '3 Natuurwetenschappen - STEM (S) met CLIL', 3, (SELECT id FROM study_domains WHERE code = 'STEM'), (SELECT id FROM study_orientations WHERE code = 'D'), 15, true);

-- Study Programs Year 3 - Taal en cultuur
INSERT INTO study_programs (code, name, study_year, domain_id, orientation_id, display_order, is_active) VALUES
('3-L', '3 Latijn (L)', 3, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 16, true),
('3-L-CLIL', '3 Latijn (L) met CLIL', 3, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 17, true),
('3-T', '3 Moderne talen (T)', 3, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 18, true),
('3-T-CLIL', '3 Moderne talen (T) met CLIL', 3, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 19, true),
('3-TC', '3 Taal en Communicatie (TC)', 3, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'DA'), 20, true);

-- Study Programs Year 4 - Economie en organisatie
INSERT INTO study_programs (code, name, study_year, domain_id, orientation_id, display_order, is_active) VALUES
('4-BW', '4 Bedrijfswetenschappen (BW)', 4, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'D'), 1, true),
('4-BW-CLIL', '4 Bedrijfswetenschappen (BW) met CLIL', 4, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'D'), 2, true),
('4-E', '4 Economische Wetenschappen (E)', 4, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'D'), 3, true),
('4-E-CLIL', '4 Economische Wetenschappen (E) met CLIL', 4, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'D'), 4, true),
('4-BO', '4 Bedrijf en Organisatie (BO)', 4, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'DA'), 5, true),
('4-OL', '4 Organisatie en Logistiek (OL)', 4, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'A'), 6, true);

-- Study Programs Year 4 - Maatschappij en welzijn
INSERT INTO study_programs (code, name, study_year, domain_id, orientation_id, display_order, is_active) VALUES
('4-H', '4 Humane Wetenschappen (H)', 4, (SELECT id FROM study_domains WHERE code = 'MW'), (SELECT id FROM study_orientations WHERE code = 'D'), 7, true),
('4-H-CLIL', '4 Humane Wetenschappen (H) met CLIL', 4, (SELECT id FROM study_domains WHERE code = 'MW'), (SELECT id FROM study_orientations WHERE code = 'D'), 8, true),
('4-MW', '4 Maatschappij- en Welzijnswetenschappen (MW)', 4, (SELECT id FROM study_domains WHERE code = 'MW'), (SELECT id FROM study_orientations WHERE code = 'D'), 9, true),
('4-MW-CLIL', '4 Maatschappij- en Welzijnswetenschappen (MW) met CLIL', 4, (SELECT id FROM study_domains WHERE code = 'MW'), (SELECT id FROM study_orientations WHERE code = 'D'), 10, true),
('4-CM', '4 Creatie en mode (CM)', 4, (SELECT id FROM study_domains WHERE code = 'MW'), (SELECT id FROM study_orientations WHERE code = 'DA'), 11, true);

-- Study Programs Year 4 - STEM
INSERT INTO study_programs (code, name, study_year, domain_id, orientation_id, display_order, is_active) VALUES
('4-N', '4 Natuurwetenschappen (N)', 4, (SELECT id FROM study_domains WHERE code = 'STEM'), (SELECT id FROM study_orientations WHERE code = 'D'), 12, true),
('4-N-CLIL', '4 Natuurwetenschappen (N) met CLIL', 4, (SELECT id FROM study_domains WHERE code = 'STEM'), (SELECT id FROM study_orientations WHERE code = 'D'), 13, true),
('4-S', '4 Natuurwetenschappen - STEM (S)', 4, (SELECT id FROM study_domains WHERE code = 'STEM'), (SELECT id FROM study_orientations WHERE code = 'D'), 14, true),
('4-S-CLIL', '4 Natuurwetenschappen - STEM (S) met CLIL', 4, (SELECT id FROM study_domains WHERE code = 'STEM'), (SELECT id FROM study_orientations WHERE code = 'D'), 15, true);

-- Study Programs Year 4 - Taal en cultuur
INSERT INTO study_programs (code, name, study_year, domain_id, orientation_id, display_order, is_active) VALUES
('4-L', '4 Latijn (L)', 4, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 16, true),
('4-L-CLIL', '4 Latijn (L) met CLIL', 4, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 17, true),
('4-T', '4 Moderne Talen (T)', 4, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 18, true),
('4-T-CLIL', '4 Moderne talen (T) met CLIL', 4, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 19, true),
('4-TC', '4 Taal en communicatie (TC)', 4, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'DA'), 20, true);

-- Study Programs Year 5 - Economie en organisatie
INSERT INTO study_programs (code, name, study_year, domain_id, orientation_id, display_order, is_active) VALUES
('5-BW', '5 Bedrijfswetenschappen (BW)', 5, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'D'), 1, true),
('5-BW-CLIL', '5 Bedrijfswetenschappen (BW) met CLIL', 5, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'D'), 2, true),
('5-ET', '5 Economie-Moderne Talen (ET)', 5, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'D'), 3, true),
('5-ET-CLIL', '5 Economie-Moderne Talen (ET) met CLIL', 5, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'D'), 4, true),
('5-EW', '5 Economie-Wiskunde (EW)', 5, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'D'), 5, true),
('5-EW-CLIL', '5 Economie-Wiskunde (EW) met CLIL', 5, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'D'), 6, true),
('5-BO', '5 Bedrijfsorganisatie (BO)', 5, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'DA'), 7, true),
('5-OS', '5 Onthaal, Organisatie en Sales (OS)', 5, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'A'), 8, true);

-- Study Programs Year 5 - Maatschappij en welzijn
INSERT INTO study_programs (code, name, study_year, domain_id, orientation_id, display_order, is_active) VALUES
('5-H', '5 Humane wetenschappen (H)', 5, (SELECT id FROM study_domains WHERE code = 'MW'), (SELECT id FROM study_orientations WHERE code = 'D'), 9, true),
('5-H-CLIL', '5 Humane wetenschappen (H) met CLIL', 5, (SELECT id FROM study_domains WHERE code = 'MW'), (SELECT id FROM study_orientations WHERE code = 'D'), 10, true),
('5-WW', '5 Welzijnswetenschappen (WW)', 5, (SELECT id FROM study_domains WHERE code = 'MW'), (SELECT id FROM study_orientations WHERE code = 'D'), 11, true),
('5-WW-CLIL', '5 Welzijnswetenschappen (WW) met CLIL', 5, (SELECT id FROM study_domains WHERE code = 'MW'), (SELECT id FROM study_orientations WHERE code = 'D'), 12, true),
('5-MO', '5 Mode (MO)', 5, (SELECT id FROM study_domains WHERE code = 'MW'), (SELECT id FROM study_orientations WHERE code = 'DA'), 13, true);

-- Study Programs Year 5 - STEM (shared with other domains)
INSERT INTO study_programs (code, name, study_year, domain_id, orientation_id, display_order, is_active) VALUES
('5-EW-STEM', '5 Economie-Wiskunde (EW)', 5, (SELECT id FROM study_domains WHERE code = 'STEM'), (SELECT id FROM study_orientations WHERE code = 'D'), 14, true),
('5-EW-STEM-CLIL', '5 Economie-Wiskunde (EW) met CLIL', 5, (SELECT id FROM study_domains WHERE code = 'STEM'), (SELECT id FROM study_orientations WHERE code = 'D'), 15, true),
('5-LWE', '5 Latijn-Wetenschappen (LWE)', 5, (SELECT id FROM study_domains WHERE code = 'STEM'), (SELECT id FROM study_orientations WHERE code = 'D'), 16, true),
('5-LWE-CLIL', '5 Latijn-Wetenschappen (LWE) met CLIL', 5, (SELECT id FROM study_domains WHERE code = 'STEM'), (SELECT id FROM study_orientations WHERE code = 'D'), 17, true),
('5-LW', '5 Latijn-Wiskunde (LW)', 5, (SELECT id FROM study_domains WHERE code = 'STEM'), (SELECT id FROM study_orientations WHERE code = 'D'), 18, true),
('5-LW-CLIL', '5 Latijn-Wiskunde (LW) met CLIL', 5, (SELECT id FROM study_domains WHERE code = 'STEM'), (SELECT id FROM study_orientations WHERE code = 'D'), 19, true),
('5-TWE', '5 Moderne Talen-Wetenschappen (TWE)', 5, (SELECT id FROM study_domains WHERE code = 'STEM'), (SELECT id FROM study_orientations WHERE code = 'D'), 20, true),
('5-TWE-CLIL', '5 Moderne Talen-Wetenschappen (TWE) met CLIL', 5, (SELECT id FROM study_domains WHERE code = 'STEM'), (SELECT id FROM study_orientations WHERE code = 'D'), 21, true),
('5-WEW', '5 Wetenschappen-Wiskunde (WEW)', 5, (SELECT id FROM study_domains WHERE code = 'STEM'), (SELECT id FROM study_orientations WHERE code = 'D'), 22, true),
('5-WEW-CLIL', '5 Wetenschappen-Wiskunde (WEW) met CLIL', 5, (SELECT id FROM study_domains WHERE code = 'STEM'), (SELECT id FROM study_orientations WHERE code = 'D'), 23, true);

-- Study Programs Year 5 - Taal en cultuur (some shared)
INSERT INTO study_programs (code, name, study_year, domain_id, orientation_id, display_order, is_active) VALUES
('5-ET-TC', '5 Economie-Moderne Talen (ET)', 5, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 24, true),
('5-ET-TC-CLIL', '5 Economie-Moderne Talen (ET) met CLIL', 5, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 25, true),
('5-LT', '5 Latijn-Moderne Talen (LT)', 5, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 26, true),
('5-LT-CLIL', '5 Latijn-Moderne Talen (LT) met CLIL', 5, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 27, true),
('5-LWE-TC', '5 Latijn-Wetenschappen (LWE)', 5, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 28, true),
('5-LWE-TC-CLIL', '5 Latijn-Wetenschappen (LWE) met CLIL', 5, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 29, true),
('5-LW-TC', '5 Latijn-Wiskunde (LW)', 5, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 30, true),
('5-LW-TC-CLIL', '5 Latijn-Wiskunde (LW) met CLIL', 5, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 31, true),
('5-T', '5 Moderne Talen (T)', 5, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 32, true),
('5-T-CLIL', '5 Moderne Talen (T) met CLIL', 5, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 33, true),
('5-TWE-TC', '5 Moderne Talen-Wetenschappen (TWE)', 5, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 34, true),
('5-TWE-TC-CLIL', '5 Moderne Talen-Wetenschappen (TWE) met CLIL', 5, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 35, true),
('5-TW', '5 Taal- en Communicatiewetenschappen (TW)', 5, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 36, true),
('5-TW-CLIL', '5 Taal- en Communicatiewetenschappen (TW)  met CLIL', 5, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 37, true),
('5-TC', '5 Taal en Communicatie (TC)', 5, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'DA'), 38, true);

-- Study Programs Year 6 - Economie en organisatie
INSERT INTO study_programs (code, name, study_year, domain_id, orientation_id, display_order, is_active) VALUES
('6-BW', '6 Bedrijfswetenschappen (BW)', 6, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'D'), 1, true),
('6-BW-CLIL', '6 Bedrijfswetenschappen (BW) met CLIL', 6, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'D'), 2, true),
('6-ET', '6 Economie-Moderne Talen (ET)', 6, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'D'), 3, true),
('6-ET-CLIL', '6 Economie-Moderne Talen (ET) met CLIL', 6, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'D'), 4, true),
('6-EW', '6 Economie-Wiskunde (EW)', 6, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'D'), 5, true),
('6-EW-CLIL', '6 Economie-Wiskunde (EW) met CLIL', 6, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'D'), 6, true),
('6-BO', '6 Bedrijfsorganisatie (BO)', 6, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'DA'), 7, true),
('6-OS', '6 Onthaal, Organisatie en Sales (OS)', 6, (SELECT id FROM study_domains WHERE code = 'EO'), (SELECT id FROM study_orientations WHERE code = 'A'), 8, true);

-- Study Programs Year 6 - Maatschappij en welzijn
INSERT INTO study_programs (code, name, study_year, domain_id, orientation_id, display_order, is_active) VALUES
('6-H', '6 Humane Wetenschappen (H)', 6, (SELECT id FROM study_domains WHERE code = 'MW'), (SELECT id FROM study_orientations WHERE code = 'D'), 9, true),
('6-H-CLIL', '6 Humane Wetenschappen (H) met CLIL', 6, (SELECT id FROM study_domains WHERE code = 'MW'), (SELECT id FROM study_orientations WHERE code = 'D'), 10, true),
('6-WW', '6 Welzijnswetenschappen (WW)', 6, (SELECT id FROM study_domains WHERE code = 'MW'), (SELECT id FROM study_orientations WHERE code = 'D'), 11, true),
('6-WW-CLIL', '6 Welzijnswetenschappen (WW) met CLIL', 6, (SELECT id FROM study_domains WHERE code = 'MW'), (SELECT id FROM study_orientations WHERE code = 'D'), 12, true),
('6-MO', '6 Mode (MO)', 6, (SELECT id FROM study_domains WHERE code = 'MW'), (SELECT id FROM study_orientations WHERE code = 'DA'), 13, true);

-- Study Programs Year 6 - STEM
INSERT INTO study_programs (code, name, study_year, domain_id, orientation_id, display_order, is_active) VALUES
('6-WEW', '6 Wetenschappen-Wiskunde (WEW)', 6, (SELECT id FROM study_domains WHERE code = 'STEM'), (SELECT id FROM study_orientations WHERE code = 'D'), 14, true),
('6-WEW-CLIL', '6 Wetenschappen-Wiskunde (WEW) met CLIL', 6, (SELECT id FROM study_domains WHERE code = 'STEM'), (SELECT id FROM study_orientations WHERE code = 'D'), 15, true);

-- Study Programs Year 6 - Taal en cultuur
INSERT INTO study_programs (code, name, study_year, domain_id, orientation_id, display_order, is_active) VALUES
('6-ET-TC', '6 Economie-Moderne talen (ET)', 6, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 16, true),
('6-ET-TC-CLIL', '6 Economie-Moderne talen (ET) met CLIL', 6, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 17, true),
('6-LT', '6 Latijn-Moderne Talen (LT)', 6, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 18, true),
('6-LT-CLIL', '6 Latijn-Moderne Talen (LT) met CLIL', 6, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 19, true),
('6-LWE', '6 Latijn-Wetenschappen (LWE)', 6, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 20, true),
('6-LWE-CLIL', '6 Latijn-Wetenschappen (LWE) met CLIL', 6, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 21, true),
('6-LW', '6 Latijn-Wiskunde (LW)', 6, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 22, true),
('6-LW-CLIL', '6 Latijn-Wiskunde (LW) met CLIL', 6, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 23, true),
('6-T', '6 Moderne Talen (T)', 6, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 24, true),
('6-T-CLIL', '6 Moderne Talen (T) met CLIL', 6, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 25, true),
('6-TWE', '6 Moderne Talen-Wetenschappen (TWE)', 6, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 26, true),
('6-TWE-CLIL', '6 Moderne Talen-Wetenschappen (TWE) met CLIL', 6, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 27, true),
('6-TW', '6 Taal- en Communicatiewetenschappen (TW)', 6, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 28, true),
('6-TW-CLIL', '6 Taal- en Communicatiewetenschappen (TW)  met CLIL', 6, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'D'), 29, true),
('6-TC', '6 Taal en Communicatie (TC)', 6, (SELECT id FROM study_domains WHERE code = 'TC'), (SELECT id FROM study_orientations WHERE code = 'DA'), 30, true);


