--Kullanici Giris Yapar
SELECT kullanici_id, kullanici_adi FROM kullanici WHERE kullanici_adi=? AND sifre=?;
-- Kullanici Kayıt Olma
INSERT INTO kullanici(kullanici_adi, sifre , ad ,soyad , adres ,telefon, email ) VALUES (?, ? ,? ,? , ?, ? ,?);
--Kullanici Guncelle
UPDATE kullanici SET kullanici_adi='"+ kullaniciAdi + "', sifre = '" + yeniSifre + "' , ad = '" + yeniAd + "', soyad = '" + yeniSoyad + "' , adres = '" + yeniAdres + "' ,telefon = '" + yeniTelefon + "', email = '" + yeniEmail + "' WHERE kullanici_id = '" + iD + "'  ";
--Kullanici İsmi Alma
SELECT kullanici_adi FROM kullanici WHERE kullanici_id='"+ iD + "'";
--Urun Ekle
INSERT INTO urun (urun_isim, fiyat, satici_id, stok) VALUES (?,?,?,?);


--My Market Place -- Satistaki Urunlerimi Gorüntüleme
SELECT urun_isim, fiyat, satis_sayisi,stok FROM urun WHERE satici_id = ?;
--My Market Place -- İsme Göre Filtreleme(INTERSECT KULLANIMI)
SELECT urun_isim, fiyat, satis_sayisi,stok FROM urun WHERE satici_id = ?;
INTERSECT
SELECT urun_isim, fiyat, satis_sayisi,stok FROM urun WHERE urun_isim LIKE '%?%';
--My Market Place -- Satis Sayisina Gore Filtreleme
SELECT urun_isim,fiyat,satis_sayisi,stok FROM urun WHERE satici_id = ? AND satis_sayisi BETWEEN ? AND ?;
--My Market Place -- Stok Sayisina Gore Filtreleme
SELECT urun_isim,fiyat,satis_sayisi,stok FROM urun WHERE satici_id = ? AND stok BETWEEN ? AND ?;
--My Market Place -- Fiyata Gore Filtreleme
SELECT urun_isim,fiyat,satis_sayisi,stok FROM urun WHERE satici_id = ? AND fiyat BETWEEN ? AND ?;


--Market Place -- Diğer Saticilarin Urunlerini Goruntuleme
SELECT urun_isim, fiyat, satis_sayisi,stok FROM urun WHERE satici_id <> ?;
--Market Place --İsme Göre Filtreleme Fonksiyonu Tanımı (RECORD VE CURSER KULLANIMI)
--Type Record Tanımı
CREATE TYPE urun_list AS (urun_isim varchar(100), fiyat NUMERIC, satis_sayisi INTEGER, stok INTEGER);
CREATE OR REPLACE FUNCTION urun_sorgu(
    p_kullanici_id INTEGER,
    p_urun_isim varchar(20)
)
RETURNS SETOF urun_list AS $$
DECLARE
    urun_row urun_list;
    cur CURSOR FOR SELECT u.urun_isim, u.fiyat, u.satis_sayisi, u.stok FROM urun u
        WHERE u.satici_id <> p_kullanici_id
        AND u.urun_isim LIKE '%'  p_urun_isim  '%';
BEGIN
    FOR urun_row IN cur
    LOOP
        RETURN NEXT urun_row;
    END LOOP;
    RETURN;
END;
$$ LANGUAGE 'plpgsql';
--Market Place -- İsme Göre Filtreleme Fonksiyonu Çağırımı
select * from urun_sorgu(?,?);
--Market Place -- İSME GÖRE FİLTRELEME
SELECT urun_isim,fiyat,satis_sayisi,stok FROM urun WHERE satici_id <> ? AND urun_isim LIKE '%?%' ;
--Market Place -- Satis Sayisina Gore Filtreleme
SELECT urun_isim,fiyat,satis_sayisi,stok FROM urun WHERE satici_id <> ? AND satis_sayisi BETWEEN ? AND ?;
--Market Place -- Stok Sayisina Gore Filtreleme
SELECT urun_isim,fiyat,satis_sayisi,stok FROM urun WHERE satici_id <> ? AND stok BETWEEN ? AND ?;
--Market Place -- Fiyata Gore Filtreleme
SELECT urun_isim,fiyat,satis_sayisi,stok FROM urun WHERE satici_id <> ? AND fiyat BETWEEN ? AND ?;


-- Urun Guncelleme -- update Product -- Fonksiyon Tanımlama
create or replace function updateProduct(v_urun_id integer, yeni_isim varchar(100), yeni_fiyat numeric, yeni_stok integer)
   returns integer as
$$
declare 
begin
    UPDATE urun SET urun_isim = yeni_isim,fiyat = yeni_fiyat,stok = yeni_stok WHERE urun_id = v_urun_id;
    IF FOUND THEN
    	return 1;
    ELSE
    	return 0;
    end if;
end;
$$ language 'plpgsql';


-- Urun Silme -- delete Product -- Fonksiyon Tanımlama
create or replace function deleteProduct(v_urun_id integer)
   returns integer as
$$
declare 	
begin
	DELETE FROM urun WHERE urun_id = v_urun_id;
	IF FOUND THEN
		return 1;
	ELSE
		return 0;
	end if;
end;
$$ language 'plpgsql';


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
				
-- Stok Kontrolü Yapan Trigger MESAJ DÖNDÜREN TRİGGER--TRIGGER 1
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

--Satın Alımında Siparişin Satırındaki Toplam Tutarı Hesaplayan Trigger 2--
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

-- Satın Alındıktan Sonra 4.Tablomuzdaki Total_Satış tutarımızı güncelleyen trigger 3(aggregate,Having KULLANIMI)--

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
--Saticinin Yaptığı sipariş adedi ve tüm siparişlerin toplam tutarı gösteren(VIEW KULLANIMI)--
CREATE OR REPLACE VIEW satis_tutar_view AS SELECT satici_id, siparis_sayisi, total_satis FROM satis_tutar;

SELECT *FROM satis_tutar_view INTERSECT SELECT satici_id,siparis_sayisi,total_satis from satis_tutar where satici_id = ?;
----------