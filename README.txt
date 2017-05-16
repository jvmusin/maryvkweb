Для запуска:
	
	1. Пройти по этой ссылке (нужно для того, чтобы приложение могло получать информацию о друзьях):
	https://oauth.vk.com/authorize?client_id=5992417&display=page&redirect_uri=https://oauth.vk.com/blank.html&scope=offline,friends&response_type=token&v=5.52

	2. После перехода в адресной строке браузера будет что-то похожее на это:
	https://oauth.vk.com/blank.html#access_token=cb8352589b1218c1682dc15a2332f95acd45885decfda64607fb13afbadc72eace993b60344eb66bb7b0e&expires_in=0&user_id=39666902
	
	3. Значение access_token занести в application.yml в свойство vk.access-token
	
	4. В application.yml поставить свои настройки доступа к бд и свойство vk.owner-id (vk id)
		Если хочется, можно изменить значение vk.default-period-to-seek, это задержка между обновлениями в миллисекундах (по дефолту обновление каждые 2 минуты)

Собрать (или пересобрать сервис можно командой 'mvnw clean install'
Запустить сервис можно либо как 'mvnw spring-boot:run', либо (если терминал открыт в корне проекта) 'java -jar target\maryvkweb-0.0.1-SNAPSHOT.jar'

Сервис будет находиться по адресу http://localhost:8080/