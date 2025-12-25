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


