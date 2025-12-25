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

-- Other European countries
('AT', 'Oostenrijks', 'Autrichien', 'Austrian', 11),
('BG', 'Bulgaars', 'Bulgare', 'Bulgarian', 12),
('HR', 'Kroatisch', 'Croate', 'Croatian', 13),
('CY', 'Cypriotisch', 'Chypriote', 'Cypriot', 14),
('CZ', 'Tsjechisch', 'Tchèque', 'Czech', 15),
('DK', 'Deens', 'Danois', 'Danish', 16),
('EE', 'Ests', 'Estonien', 'Estonian', 17),
('FI', 'Fins', 'Finlandais', 'Finnish', 18),
('GR', 'Grieks', 'Grec', 'Greek', 19),
('HU', 'Hongaars', 'Hongrois', 'Hungarian', 20),
('IE', 'Iers', 'Irlandais', 'Irish', 21),
('LV', 'Lets', 'Letton', 'Latvian', 22),
('LT', 'Litouws', 'Lituanien', 'Lithuanian', 23),
('LU', 'Luxemburgs', 'Luxembourgeois', 'Luxembourgish', 24),
('MT', 'Maltees', 'Maltais', 'Maltese', 25),
('NO', 'Noors', 'Norvégien', 'Norwegian', 26),
('SE', 'Zweeds', 'Suédois', 'Swedish', 27),
('SK', 'Slowaaks', 'Slovaque', 'Slovak', 28),
('SI', 'Sloveens', 'Slovène', 'Slovenian', 29),
('CH', 'Zwitsers', 'Suisse', 'Swiss', 30),

-- Other continents - Africa
('DZ', 'Algerijns', 'Algérien', 'Algerian', 31),
('EG', 'Egyptisch', 'Égyptien', 'Egyptian', 32),
('MA', 'Marokkaans', 'Marocain', 'Moroccan', 33),
('TN', 'Tunesisch', 'Tunisien', 'Tunisian', 34),
('ZA', 'Zuid-Afrikaans', 'Sud-Africain', 'South African', 35),

-- Asia
('CN', 'Chinees', 'Chinois', 'Chinese', 36),
('IN', 'Indiaas', 'Indien', 'Indian', 37),
('JP', 'Japans', 'Japonais', 'Japanese', 38),
('KR', 'Zuid-Koreaans', 'Sud-Coréen', 'South Korean', 39),
('PH', 'Filipijns', 'Philippin', 'Filipino', 40),
('TH', 'Thais', 'Thaïlandais', 'Thai', 41),
('TR', 'Turks', 'Turc', 'Turkish', 42),
('VN', 'Vietnamees', 'Vietnamien', 'Vietnamese', 43),

-- Americas
('BR', 'Braziliaans', 'Brésilien', 'Brazilian', 44),
('CA', 'Canadees', 'Canadien', 'Canadian', 45),
('MX', 'Mexicaans', 'Mexicain', 'Mexican', 46),
('US', 'Amerikaans', 'Américain', 'American', 47),

-- Oceania
('AU', 'Australisch', 'Australien', 'Australian', 48),
('NZ', 'Nieuw-Zeelands', 'Néo-Zélandais', 'New Zealand', 49),

-- Middle East
('IL', 'Israëlisch', 'Israélien', 'Israeli', 50),
('SA', 'Saoedisch', 'Saoudien', 'Saudi', 51),
('AE', 'Emiratisch', 'Émirien', 'Emirati', 52);
