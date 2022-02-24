# CurrencySpringREST

Projekt został stworzony w celach edukacyjnych pozwala on na scrapowanie co godzine infromacji walutowych ze strony https://tradingeconomics.com/currencies. Następnie zapisuje je w bazie danych (Postgres/Hibernate). Dostęp do API otrzymujemy w pełni po zalogawniu się (implementacja za pomocą Spring Security wraz z własnymi użytkownikami, którzy są dodani w pliku CurrencyConfig.java). W pliku SecurityConfig.java możemy wybrać czy chcemy autentykacje za pomocą formularza lub tokenu JWT. Następnie do dyspozycji mamy 3 endpointy:<br />
http://localhost:8080/api - otrzymamy liste aktualnych kursów<br />
http://localhost:8080/api/full_info - otrzymamy liste wszsytkich kursów jakie zapisaliśmy w bazie<br />
http://localhost:8080/api/waluta1/waluta2/ilosc np /api/USD/PLN/30 - otrzymamy infromacje o przewalutowaniu 30 USD na PLN<br />

W projekcie zostały również stworzone testy dla każdej z warstw projektu (przy pomocy Junit/Mockito).
