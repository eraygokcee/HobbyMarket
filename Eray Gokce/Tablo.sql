-- Tablo Oluşturma -- Tüm Kod Tek Seferde Çalışabilir Sırada Yazılmıştır.
CREATE SEQUENCE kullanici_id_seq START WITH 20011000;
CREATE TABLE kullanici (
 kullanici_id INTEGER DEFAULT nextval('kullanici_id_seq') PRIMARY KEY,
 kullanici_adi VARCHAR(50) UNIQUE NOT NULL,
 sifre VARCHAR(50) NOT NULL,
 ad VARCHAR(100),
 soyad VARCHAR(100),
 adres VARCHAR(255),
 telefon NUMERIC,
 email VARCHAR(55));

CREATE SEQUENCE urun_id_seq START WITH 1000;
CREATE TABLE urun (
 urun_id INTEGER DEFAULT nextval('urun_id_seq') PRIMARY KEY,
 urun_isim VARCHAR(100) NOT NULL,
 fiyat NUMERIC(10, 2) NOT NULL,
 satici_id INTEGER REFERENCES kullanici(kullanici_id),
 stok INTEGER NOT NULL,
 satis_sayisi INTEGER DEFAULT 0
);

CREATE SEQUENCE siparis_id_seq START WITH 5000;
CREATE TABLE Siparis (
 siparis_id INTEGER DEFAULT nextval('siparis_id_seq') PRIMARY KEY,
 siparis_tarihi DATE DEFAULT CURRENT_DATE,
 alici_id INTEGER REFERENCES kullanici(kullanici_id),
 satici_id INTEGER REFERENCES kullanici(kullanici_id),
 urun_id INTEGER REFERENCES urun(urun_id),
 adet INTEGER NOT NULL,
 toplam_ucret NUMERIC(10, 2) 
);

CREATE TABLE satis_tutar(
satici_id INTEGER REFERENCES kullanici(kullanici_id),
siparis_sayisi INTEGER NOT NULL,
total_satis NUMERIC(10,2) NOT NULL
);
                      --TABLO DOLDURMA--
INSERT INTO kullanici(kullanici_adi, sifre, ad, soyad, adres, telefon, email)
VALUES
('eda', 'eda123', 'Eda', 'Altun', '123 Main St', 5551234567, 'eda.altun@example.com'),
('emir', 'emir123', 'Emir', 'Can', '456 Oak St', 5552345678, 'emir.can@example.com'),
('emirOzturk', 'emirOzturk123', 'Emir', 'Ozturk', '789 Pine St', 5559876543, 'emir.ozturk@example.com'),
('eray', 'eray123', 'Eray', 'Gokce', '987 Elm St', 5558765432, 'era.gokce@example.com'),
('michael', 'pass654', 'Michael', 'Jackson', '654 Birch St', 5557654321, 'michael.jackson@example.com'),
('emily', 'pass321', 'Emily', 'White', '321 Cedar St', 5556543210, 'emily.white@example.com'),
('ryan', 'pass012', 'Ryan', 'Martin', '012 Maple St', 5555432109, 'ryan.martin@example.com'),
('laura', 'pass345', 'Laura', 'Davis', '345 Walnut St', 5554321098, 'laura.davis@example.com'),
('brian', 'pass678', 'Brian', 'Harris', '678 Pine St', 5553210987, 'brian.harris@example.com'),
('sophie', 'pass901', 'Sophie', 'Thomas', '901 Oak St', 5552109876, 'sophie.thomas@example.com');

INSERT INTO urun(urun_isim, fiyat, satici_id, stok)
VALUES
('Laptop', 2499, 20011001, 30),
('Smartphone', 899, 20011002, 50),
('Smartwatch', 199, 20011003, 20),
('Tablet', 599, 20011001, 15),
('Wireless Earbuds', 129, 20011002, 40),
('Digital Camera', 799, 20011004, 25),
('Bluetooth Speaker', 149, 20011000, 35),
('Gaming Console', 499, 20011004, 10),
('Fitness Tracker', 79, 20011003, 30),
('4K TV', 1499, 20011000, 5);

-- Ürün Satın Alma Fonksiyonu --
CREATE OR REPLACE FUNCTION satin_al(p_alici_id INT, p_satici_id INT, p_urun_id INT, p_adet INT)
RETURNS VOID AS $$
BEGIN
    -- Siparişi ekleyin
    INSERT INTO Siparis (alici_id, satici_id, urun_id, adet)
	VALUES (p_alici_id, p_satici_id, p_urun_id, p_adet);

    -- Stok ve satış sayısını güncelleyin
    UPDATE urun
    SET stok = stok - p_adet,
        satis_sayisi = satis_sayisi + p_adet
    WHERE urun_id = p_urun_id;

END;
$$ LANGUAGE 'plpgsql';

				----------Satın alım fonksiyonundan  etkilenen Trigger Fonksiyonları---------
				
-- Stok Kontrolü Yapan Trigger--
CREATE OR REPLACE FUNCTION stok_kontrol()
RETURNS TRIGGER AS $$
BEGIN

    IF NEW.adet > (SELECT stok FROM urun WHERE urun_id = NEW.urun_id) THEN
        RAISE EXCEPTION 'Stok yetersiz';
	ELSE
		RAISE NOTICE 'Stok kontrolü başarılı, sipariş eklenebilir';
    END IF;
	
    RETURN NEW;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE TRIGGER before_siparis_insert
BEFORE INSERT ON Siparis
FOR EACH ROW
EXECUTE FUNCTION stok_kontrol();

--Satın Alımında Siparişin Satırındaki Toplam Tutarı Hesaplayan Trigger--
CREATE OR REPLACE FUNCTION toplam_fiyat()
RETURNS TRIGGER AS $$
BEGIN
  NEW.toplam_ucret = (SELECT fiyat FROM urun WHERE urun_id = NEW.urun_id) * NEW.adet;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_insert_siparis
BEFORE INSERT ON Siparis
FOR EACH ROW
EXECUTE FUNCTION toplam_fiyat();

-- Satın Alındıktan Sonra 4.Tablomuzdaki Total_Satış tutarımızı güncelleyen trigger(aggregate,Having)--

CREATE OR REPLACE FUNCTION update_satis_tutar()
RETURNS TRIGGER AS $$
DECLARE
    satis_tutar_row satis_tutar%ROWTYPE;
BEGIN
    -- Satici_id'ye göre satis_tutar tablosunda ilgili satirin varlığını kontrol edilir
    SELECT * INTO satis_tutar_row
    FROM satis_tutar
    WHERE satici_id = NEW.satici_id;

    -- Eğer satici_id'ye ait bir satir yoksa(satici daha once satıs yapmadıysa) yeni bir satir ekleyin
    IF NOT FOUND THEN
        INSERT INTO satis_tutar (satici_id, siparis_sayisi, total_satis)
        VALUES (NEW.satici_id, 1, NEW.toplam_ucret);
    ELSE
        -- Eğer satir varsa, siparis_sayisi'ni artırın ve total_satis'i güncelleyin
        UPDATE satis_tutar
        SET siparis_sayisi = satis_tutar_row.siparis_sayisi + 1,
            total_satis = (SELECT SUM(toplam_ucret) 
                           FROM Siparis 
                           WHERE satici_id = NEW.satici_id 
                           HAVING COUNT(siparis_id) > 1)
        WHERE satici_id = NEW.satici_id;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER after_insert_siparis
AFTER INSERT ON Siparis
FOR EACH ROW
EXECUTE FUNCTION update_satis_tutar();
------

--Öncesinde SQL dosyanızda aşağıdaki satın_al fonksiyonunu ve triggerlarını tanımlayınız.--
select satin_al(20011004, 20011001, 1000, 2);
select satin_al(20011005, 20011002, 1001, 1);
select satin_al(20011006, 20011003, 1002, 3);
select satin_al(20011007, 20011001, 1003, 1);
select satin_al(20011008, 20011002, 1004, 2);
select satin_al(20011009, 20011003, 1008, 1);
select satin_al(20011007, 20011001, 1000, 3);
select satin_al(20011004, 20011000, 1009, 1);
select satin_al(20011005, 20011000, 1006, 2);
select satin_al(20011006, 20011003, 1008, 1);




















