#Проект "REST-сервис для сбора метрик полученных от IoT оборудования и составления статистики."

---

##Инструкция по запуску:
    1) Для запуска приложения нужно создать базу данных и подправить файл src/main/resources/hibernate.properties.
    2) Для запуска тестов нужно создать бд и править файл: src/test/resources/hibernate-test.properties.

---

##Описание реализованных методов API
####Методы устройств:
1. Получить все устройства, _get request:_ __"/devices"__, возможные параметры при запросе: __type__ (Передается в числовом виде: 0  = SMART_MOBILE, 1 = SMART_FRIDGE, 2 = SMART_WATCH), __date_of_create__ (передается в формате yyyy-MM-dd), __page__ (номер страницы при пагинации), __count__ (количество элементов на странице при пагинации).
2. Получить устройство по серийному номеру, _get request:_ __"/devices/{serial_number}"__.
3. Получить все события для устройства по его серийному номеру, _get request:_ __"/devices/{serial_number}/occasions"__, возможные параметры: __date_of_create__ (передается в формате yyyy-MM-dd), __page__ (номер страницы при пагинации), __count__ (количество элементов на странице при пагинации).

4. Добавить новое устройство, _post request:_ __"/devices"__, принимает json в виде: 
\
    {
        "serialNumber": "1234567890",
        "deviceName": "device_1",
        "deviceType": "SMART_MOBILE"
    }

__Примечание:__ при сохранении, выдается ключ, по которому впоследствии можно будет создавать события. Запомните этот ключ или запишите, так как он сразу же будет зашифрован. Так же устройства, которые были автоматически добавлены в систему сразу имеют зашифрованный вид ключей и, следовательно, мы не знаем их расшифровку => не можем создавать у них события. Для получения такой возможности создайте свой девайс.

####Методы событий:

1. Получить все события, _get request:_ __"/occasions"__.
2. Получить события по id, _get request:_ __"/occasions/{id}"__
3. Сохранить событие, _post request:_ __"/occasions"__, принимает json в виде:
\
    {
        "deviceID": 1,
        "occasionType": "Count calls on the Sunday",
        "payloadDTO": {
            "secretKey": "Secret_key for device with id 1",
            "countCallsInDay": "8"
        }
    }
4. Получить статистику по событиям, отфильтрованым по датам, _get request:_ __"/occasions/statistic"__, может принимать параметры: (__start_date__ и __end_date__ в формате: _yyyy-MM-dd_, которые задают промежуток дат, по которым искать события).

####Методы списка активных устройств:

1. Получить список всех активных устройств, _get request:_ __"/active_devices"__
